package br.com.postech.software.architecture.techchallenge.api.gateway.service.serializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFormat {

	String value();	
}
