package org.simonscode.moodleapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PropertyBasedDeserializer<T> extends StdDeserializer<T> {

    private final String property;
    private Map<String, Class<? extends T>> deserializationClasses;

    public PropertyBasedDeserializer(Class<T> baseClass, String property) {
        super(baseClass);
        this.property = property;
        deserializationClasses = new HashMap<>();
    }

    public void register(String value, Class<? extends T> deserializationClass) {
        deserializationClasses.put(value, deserializationClass);
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode tree = mapper.readTree(p);

        Class<? extends T> deserializationClass = findDeserializationClass(tree);
        if (deserializationClass == null) {
            throw JsonMappingException.from(ctxt,
                    "No registered unique properties found for polymorphic deserialization");
        }

        return mapper.treeToValue(tree, deserializationClass);
    }

    private Class<? extends T> findDeserializationClass(JsonNode tree) {
        Iterator<Map.Entry<String, JsonNode>> fields = tree.fields();
        Class<? extends T> deserializationClass = null;

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            if (field.getKey().equals(property)) {
                deserializationClass = deserializationClasses.get(field.getValue().asText());
                break;
            }
        }
        return deserializationClass;
    }
}