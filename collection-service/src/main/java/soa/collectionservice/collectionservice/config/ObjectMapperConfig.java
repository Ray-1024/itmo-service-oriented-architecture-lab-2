package soa.collectionservice.collectionservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class ObjectMapperConfig {
    @Produces(MediaType.APPLICATION_XML)
    public ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }
}