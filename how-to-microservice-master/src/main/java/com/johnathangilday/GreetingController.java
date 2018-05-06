package com.johnathangilday;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/name")
@Produces(MediaType.TEXT_PLAIN)
public class GreetingController {

    @GET
    public String getGreeting(@QueryParam("name") String name) {
        return name;
    }

    @GET
    @Path("/{id}")
    public Greeting getGreetingById(@PathParam("id") final int id) {
        throw new GreetingNotFoundException(id); // no greetings ever found for this example
    }
}
