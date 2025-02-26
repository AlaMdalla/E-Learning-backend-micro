package E_Learning.Project.Entity;

import E_Learning.Project.Enums.Tags;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TagsDeserializer extends JsonDeserializer<List<Tags>> {
    @Override
    public List<Tags> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        List<Tags> tags = new ArrayList<>();
        if (node.isArray()) {
            for (JsonNode element : node) {
                tags.add(Tags.valueOf(element.asText()));
            }
        } else if (node.isTextual()) {
            tags.add(Tags.valueOf(node.asText()));
        }
        return tags;
    }
}
