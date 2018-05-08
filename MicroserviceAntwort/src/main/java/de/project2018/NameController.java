package de.project2018;

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
            zeichenfolge="cool";
        }else{
            zeichenfolge="nicht so cool";
        }

        return zeichenfolge;
    }

    @GET
    @Path("/{id}")
    public Name getNameById(@PathParam("id") final int id) {
        throw new NameNotFoundException(id); // no greetings ever found for this example
    }
}
