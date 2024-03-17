package br.com.postech.software.architecture.techchallenge.api.gateway.service.http;

import java.util.Map;

public interface HttpAdapter {	
	<T> T post(String url, Map<String, Object> parametros, Class<T> type) throws Exception;
	
	<T> T post(String url, Map<String, Object> parametros) throws Exception;
}
