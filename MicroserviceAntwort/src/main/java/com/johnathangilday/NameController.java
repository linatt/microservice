package com.johnathangilday;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/name")
@Produces(MediaType.TEXT_PLAIN)
public class NameController {

    @GET
    public String getName(@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname) {
        Name reg = new Name(firstname,lastname);

        String zeichenfolge;
        if(reg.firstname.equals("lina")){
            zeichenfolge="Hallo Lina!";
        }else{
            zeichenfolge="Hallo Unbekannt!";
        }

        return zeichenfolge;
    }

    @GET
    @Path("/{id}")
    public Name getNameById(@PathParam("id") final int id) {
        throw new NameNotFoundException(id); // no greetings ever found for this example
    }
}
