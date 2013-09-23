package com.bravisoftware.samples.tictactoe.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.http.MediaType;

public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                                        MediaType.APPLICATION_JSON.getSubtype(),                        
                                                                        Charset.forName("utf8")                     
                                                                        );
    
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
