package edu.hawaii.its.holiday.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class TypeDeserializer extends StdDeserializer<List<Type>> {

    private static final long serialVersionUID = -5134522590268844426L;
    private static Map<String, Type> typeMap = new HashMap<>();

    static {
        typeMap.put("Federal", new Type(2, "Federal", 1));
        typeMap.put("UH", new Type(3, "UH", 1));
        typeMap.put("State", new Type(4, "State", 1));
    }

    protected TypeDeserializer() {
        super(List.class);
    }

    @Override
    public List<Type> deserialize(JsonParser jp, DeserializationContext cntx) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        // System.out.println("<><><><> DESER; node: " + node);
        List<Type> list = new ArrayList<>();
        node.forEach(n -> {
            Type i = typeMap.get(n.asText());
            if (i != null) {
                Type p = new Type();
                p.setId(i.getId());
                p.setVersion(i.getVersion());
                p.setDescription(i.getDescription());

                list.add(p);
            }

            // System.out.println("<><><><> DESER; node: " + n.asText());
        });

        // System.out.println();
        // System.out.println("<><><><> DESER; list: " + node + "\n");

        return list;
    }

}
