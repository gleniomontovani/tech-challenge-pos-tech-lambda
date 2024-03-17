package br.com.postech.software.architecture.techchallenge.api.gateway.service.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import br.com.postech.software.architecture.techchallenge.api.gateway.configuration.ApiGatewayClientProperties;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.exception.BusinessException;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.serializer.DateDeserializer;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.serializer.DateSerializer;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.util.Constantes;
import br.com.postech.software.architecture.techchallenge.api.gateway.service.util.Util;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@Data
@RequiredArgsConstructor
@Slf4j
public class Proxy implements HttpAdapter {
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Date.class, getDateSerializer())
			.registerTypeAdapter(Date.class, getDateDeserializer()).setPrettyPrinting().create();
	
    private static final String AUTORIZATION 	  =	"Authorization";
    private static final String BEARER 		 	  =	"Bearer ";
    private static final String MEDIA_TYPE 	 	  =	"application/json; charset=utf-8";
    private static final String DADOS 		 	  =	"dados";
    
    private static final int OK 				  = 200;
    private Jwt jwt;
    private String resource;
    
    private final ApiGatewayClientProperties properties;
    
    public <T> T get(Class<T> tipo) throws Exception {
		T objeto = null;
		Reader reader = null;

		HttpClient client = null;
		HttpGet request = null;
		HttpResponse response = null;
		
		log.info("Executando chamada get ao recurso: " + properties.getUri() + this.resource + ". Tipo: " + tipo);
		try {
			
			client = HttpClientBuilder.create().build();

			request = new HttpGet(properties.getUri() + this.resource);
			this.configureHeader(request);

			response = client.execute(request);
			int codigoRetorno = response.getStatusLine().getStatusCode();
			if(OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				objeto = GSON.fromJson(reader, tipo);
			}else {
				return processarRetorno(response, "get", tipo);
			}
		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return objeto;
	}
    
    public <T> List<T> get(List<T> type) throws Exception{
		Reader reader = null;
		List<T> colecao = null;

		HttpClient client = null;
		HttpGet request = null;
		HttpResponse response = null;
		
		log.info("Executando chamada get ao recurso: " + properties.getUri() + this.resource);

		try {
			client = HttpClientBuilder.create().build();

			request = new HttpGet(properties.getUri() + this.resource);
			this.configureHeader(request);

			response = client.execute(request);

			int codigoRetorno = response.getStatusLine().getStatusCode();
			if(OK == codigoRetorno) {
				reader = getReader(response.getEntity().getContent());
				colecao = GSON.fromJson(reader,  new ProxyTokenType<T>().getType());
			}else {
				return processarRetorno(response, "get", type.getClass());
			}
			
		} catch (IOException e) {
			tratarException(e);
		} finally {
			finalizar(reader, request);
		}

		return colecao;
	}
    
    public <T> T get(Class<T> tipo, String pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (StringUtils.isNotBlank(pathParam)) {
			newURL.append("/").append(pathParam);
		}
		setResource(newURL.toString());
		return get(tipo);		
	}
	
	public <T> T get(Class<T> tipo, List<String> pathParam) throws Exception {
		StringBuilder newURL = new StringBuilder(this.resource);
		if (!Util.isNullOrEmpty(pathParam)) {
			for (String vlParametro : pathParam) {
				newURL.append("/").append(vlParametro);
			}
		}
		setResource(newURL.toString());
		return get(tipo);
		
	}

	/**
	 * Metodo post para envio de requisição. Este metodo ira retornar object.
	 * 
	 * @param url - URL que será feita a requisição.
	 * @param parametros - Os parametros da requisicao. Isso inclui os headers.
	 * */
	@SuppressWarnings("deprecation")
	@Override
	public <T> T post(String url, Map<String, Object> parametros, Class<T> type) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		MediaType mediaJson = MediaType.parse(MEDIA_TYPE);
		Request request   = null;
		Response response = null;
        try {
        	RequestBody body = RequestBody.create(mediaJson, Util.removerCaracteres(gson.toJson(parametros.get(DADOS))));
           
        	Headers headers = obterHeaders(parametros);
        	request = new Request.Builder()
            		.url(url)
            		.post(body)
            		.headers(headers)
                    .build();
        	
            Call chamador = new OkHttpClient().newCall(request);
			response = chamador.execute();
			
			return processarRetorno(response, type.getClass());
			
		} catch (IOException e) {
			throw new BusinessException("Falha ao fazer a requisição ao servidor!");
		}
	}
	
	/**
	 * Metodo post para envio de requisição. Este metodo ira retornar uma String em formato JSON
	 * 
	 * @param url - URL que será feita a requisição.
	 * @param parametros - Os parametros da requisicao. Isso inclui os headers.
	 * */
	
	@SuppressWarnings("deprecation")
	@Override
	public <T> T post(String url, Map<String, Object> parametros) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		MediaType mediaJson = MediaType.parse(MEDIA_TYPE);
		Request request   = null;
		Response response = null;
        try {
        	RequestBody body = RequestBody.create(mediaJson, Util.removerCaracteres(gson.toJson(parametros.get(DADOS))));
           
        	Headers headers = obterHeaders(parametros);
        	request = new Request.Builder()
            		.url(url)
            		.post(body)
            		.headers(headers)
                    .build();
        	
            Call chamador = new OkHttpClient().newCall(request);
			response = chamador.execute();
			
			return processarRetorno(response);
			
		} catch (IOException e) {
			throw new BusinessException("Falha ao fazer a requisição ao servidor!");
		}
	}

	private Headers obterHeaders(Map<String, Object> parametros) {
		Headers.Builder builder = new Headers.Builder();
		//Estes dois seram fixos, ou seja, sao obrigatorios para qualquer requisicao.
		builder.add(AUTORIZATION, BEARER+parametros.get(Constantes.PARAMETER_ACCESS_TOKEN));
		
		//Obtem os headers para o cabecalho da requisicao
		Headers headers = builder.build();
		
		return headers;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T processarRetorno(Response response)
			throws Exception{
		
		int codigoRetorno = response.code();		
		if (OK != codigoRetorno) {
			throw new BusinessException(HttpStatus.valueOf(codigoRetorno), Util.removerCaracteres(response.body().string()));
		}
		
		return (T) response.body().string();
	}
	
	private <T> T processarRetorno(Response response, Type classType)
			throws Exception{
		T retorno = null;
		int codigoRetorno = response.code();
		if (OK == codigoRetorno) {
			retorno = GSON.fromJson(getReader(response.body().byteStream()), classType);
		} else {
			throw new BusinessException(HttpStatus.valueOf(codigoRetorno), Util.removerCaracteres(response.body().string()));
		}
		
		return retorno;
	}
	
	private <T> T processarRetorno(HttpResponse response, String methodType, Type classType)
			throws IOException, Exception {
		T retorno = null;
		int codigoRetorno = response.getStatusLine().getStatusCode();
		if (OK == codigoRetorno) {
			retorno = GSON.fromJson(getReader(response.getEntity().getContent()), classType);
		} 
		return retorno;
	}
		
	private void configureHeader(HttpMessage request) throws Exception {
		request.setHeader(HttpHeaders.CONTENT_TYPE, javax.ws.rs.core.MediaType.APPLICATION_JSON);
		request.addHeader(HttpHeaders.ACCEPT, javax.ws.rs.core.MediaType.APPLICATION_JSON);
		String applicationToken = Objects.nonNull(jwt) ? jwt.getTokenValue() : null;
		if (Objects.isNull(applicationToken)) {
			throw new BusinessException("Não autorizado!");
		}
		
		request.addHeader("Authorization", "Bearer "+ applicationToken);
	}

	private static void tratarException(IOException e) throws Exception {
		String message = e.getMessage() != null ? e.getMessage()
				: "";
		throw new BusinessException(message, e);
	}
	
	private static void finalizar(Reader reader, HttpRequestBase request) {
		finalizar(request);
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				log.error("Erro interno no servidor!", e);
			}
		}
	}
	
	private static void finalizar(HttpRequestBase request) {
		if (request != null) {
			request.reset();
		}
	}

	protected static DateSerializer getDateSerializer() {
		return new DateSerializer();
	}

	protected static DateDeserializer getDateDeserializer() {
		return new DateDeserializer();
	}
	
	protected Reader getReader(InputStream content) {
		return new InputStreamReader(content, StandardCharsets.UTF_8);
	}
	
	private static class ProxyTokenType<T> extends TypeToken<ArrayList<T>> {
    }
}
