package ru.nyrk.gisgmp.web.converters.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;

public class MyObjectMapper extends ObjectMapper implements Serializable {


    DateTimeFormatter frmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    @PostConstruct
    public void init() {
        JodaModule jm = new JodaModule();
        jm.addSerializer(DateTime.class, new JsonSerializer<DateTime>() {
            @Override
            public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeString(value.toString(frmt));
            }
        });
        jm.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeString(value.toString(frmt));
            }
        });

        this.registerModule(jm);
        this.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
    }
}
