package com.prograssoft.shahad.business.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;

public class CustomerDeserializer extends JsonDeserializer<Customer> {


    @Override
    public Customer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);

        // Ensure type exists
        JsonNode typeNode = node.get("customerType");
        if (typeNode == null) {
            throw new IllegalArgumentException("Missing 'type' field in JSON");
        }

        String type = typeNode.asText().toLowerCase();

        switch (type) {
            case "individual":
                return IndividualCustomer.builder()
                        .phoneNumber(getLong(node, "phoneNumber"))
                        .regitrationDate(LocalDate.parse(getText(node, "registrationDate")))
                        .firstName(getText(node, "firstName"))
                        .lastName(getText(node, "lastName"))
                        .nationalId(getLong(node, "nationalId"))
                        .birthDate(LocalDate.parse(getText(node, "birthDate")))
                        .build();

            case "corporate":
                return CorporateCustomer.builder()
                        .phoneNumber(getLong(node, "phoneNumber"))
                        .regitrationDate(LocalDate.now())
                        .name(getText(node, "name"))
                        .tradeLicenseNumber(getLong(node, "tradeLicenseNumber"))
                        .foundingDate(LocalDate.parse(getText(node, "foundingDate")))
                        .build();

            default:
                throw new IllegalArgumentException("Unknown customer type: " + type);
        }
    }

    // Helper methods to avoid NPEs
    private String getText(JsonNode node, String fieldName, boolean failIfNotFound) {
        JsonNode value = node.get(fieldName);
        if (value == null && failIfNotFound) {
            throw new IllegalArgumentException("Missing field: " + fieldName);
        }
        return value == null || value.isNull() ? null : value.asText();
    }

    private String getText(JsonNode node, String fieldName) {
        return getText(node, fieldName, true);
    }

    private long getLong(JsonNode node, String fieldName) {
        JsonNode value = node.get(fieldName);
        if (value == null) {
            throw new IllegalArgumentException("Missing field: " + fieldName);
        }
        return value.asLong();
    }
    }

