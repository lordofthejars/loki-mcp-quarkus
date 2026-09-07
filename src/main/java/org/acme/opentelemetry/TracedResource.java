package org.acme.opentelemetry;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.MediaType;


import org.jboss.logging.Logger;

@Path("/")
public class TracedResource {

    private static final Logger LOG = Logger.getLogger(TracedResource.class);

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("hello");
        return "hello";
    }

}
