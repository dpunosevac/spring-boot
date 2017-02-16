/**
 * 
 */
package org.socialfun.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * This class do the custom json deserialization for date and time
 * 
 * @author dpunosevac
 *
 */
public class CustomJsonDateTimeSerializer extends JsonSerializer<Date>{
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator,
	 * com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		String formattedDate = dateFormat.format(value);
		gen.writeString(formattedDate);
	}
}
