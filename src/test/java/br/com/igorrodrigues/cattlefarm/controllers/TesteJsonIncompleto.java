package br.com.igorrodrigues.cattlefarm.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import br.com.igorrodrigues.cattlefarm.models.flock.Bovine;

public class TesteJsonIncompleto {
	
	public static void main(String[] args) throws Exception, JsonMappingException, IOException {
		String conteudo = "{\"bovine\": {\n" + 
				"   \"id\": 1,\n" + 
				"   \"nick\": \"1\",\n" + 
				"   \"value\": 10,\n" + 
				"   \"birth\": \"2018-05-20\",\n" + 
				"   \"age\": \"P3M20D\",\n" + 
				"   \"weight\": 500,\n" + 
				"   \"sex\": \"MALE\",\n" + 
				"   \"status\": \"ACTIVE\",\n" + 
				"   \"type\": \"BOI\",\n" + 
				"   \"weightArrobaFree\": 16.66,\n" + 
				"   \"weightArroba\": 33.33,\n" + 
				"   \"countChild\": 0\n" + 
				"   \"jururu\": 0\n" +
				"}}";
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY); 
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		mapper.setDateFormat(df);
		mapper.registerModule(new JSR310Module());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Bovine value = mapper.readValue(conteudo, Bovine.class);
		System.out.println(value.getId());
	}

}
