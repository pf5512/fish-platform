package com.ippteam.fish.dao.nosql.mongodb.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Created by isunimp on 16/11/25.
 */
public class CustomMongoIdSerializer extends JsonSerializer<ObjectId> {

    public void serialize(ObjectId value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(value.toString());
    }
}