package com.appointmentchecker.service.providers.drlib;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrLibInfoResponseDeserializer extends JsonDeserializer<DrLibInfoResponse> {

    @Override
    public DrLibInfoResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode nodeTree = jsonParser.getCodec().readTree(jsonParser);

        JsonNode profileNode =  nodeTree.get("data").get("profile");
        JsonNode agendasNode = nodeTree.get("data").get("agendas");

        int id = profileNode.get("id").asInt();
        String name = profileNode.get("name_with_title").asText();

        Iterator<JsonNode> it = agendasNode.elements();

        List<DrLibAgenda> agendaList = new ArrayList<>();
        while(it.hasNext()) {
            JsonNode agendaNode = it.next();

            List<Integer> visitMotiveIds = new ArrayList<>();
            for (JsonNode motive : agendaNode.get("visit_motive_ids")) {
                visitMotiveIds.add(motive.asInt(0));
            }

            agendaList.add(new DrLibAgenda(
                    agendaNode.get("id").asInt(0),
                    agendaNode.get("booking_disabled").asBoolean(true),
                    visitMotiveIds, agendaNode.get("practitioner_id").asInt(0),
                    agendaNode.get("practice_id").asInt(0)
            ));
        }

        return new DrLibInfoResponse(id, name, agendaList);
    }
}
