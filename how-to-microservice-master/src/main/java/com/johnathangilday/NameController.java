package com.johnathangilday;

import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/name")
@Produces(MediaType.APPLICATION_JSON)
public class NameController {

        @GET
        public JSONObject getName() {
            try {
                System.out.println("hey");
                return new JSONObject("Hey");
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
        }

//        @GET
//        @Path("/{id}")
//        public Greeting getGreetingById(@PathParam("id") final int id) {
//            throw new GreetingNotFoundException(id); // no greetings ever found for this example
//        }
}

