package net.surfm.jpatool;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import net.surfm.exception.SurfmRuntimeException;
import net.surfm.infrastructure.JsonMap;


public class JsonMapConverter implements AttributeConverter<JsonMap, String> {

	private static  final ObjectMapper mapper = new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.setDateFormat(new ISO8601DateFormat());

	@Override
	public String convertToDatabaseColumn(JsonMap attribute) {
		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new SurfmRuntimeException(e); 
		}
	}

	@Override
	public JsonMap convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, JsonMap.class);
		} catch (Exception e) {
			throw new SurfmRuntimeException(e);
		}
	}
	


}
