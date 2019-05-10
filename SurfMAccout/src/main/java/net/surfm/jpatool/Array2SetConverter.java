package net.surfm.jpatool;

import java.util.Set;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.surfm.exception.SurfmRuntimeException;

public class Array2SetConverter implements AttributeConverter<Set<String>, String> {

	
	private static final  ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String convertToDatabaseColumn(Set<String> attribute) {
		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new SurfmRuntimeException(e);
		}
	}

	@Override
	public Set<String> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData,SetString.class);
		} catch (Exception e) {
			throw new SurfmRuntimeException("dbData="+dbData,e);
		}
	}
	


}
