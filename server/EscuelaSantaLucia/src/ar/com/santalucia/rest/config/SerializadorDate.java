package ar.com.santalucia.rest.config;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class SerializadorDate implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public JsonElement serialize(Date src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(format.format(src));
	}


	public Date deserialize(JsonElement json, Type typeOfSrc,
			JsonDeserializationContext context) throws JsonParseException {
		
        try {
            return format.parse(json.getAsJsonPrimitive().getAsString());
        } catch (ParseException exception) {
            throw new JsonParseException(exception.getMessage());
        }
	}

}
