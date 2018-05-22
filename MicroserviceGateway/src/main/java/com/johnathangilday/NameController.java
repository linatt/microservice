package com.johnathangilday;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Path("/name")
@Produces(MediaType.TEXT_PLAIN)
public class NameController {

    @GET
    public String getName(@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname) throws IOException {
        try {
            String url = "http://localhost:8001/name?firstname=" + firstname + "&lastname=" + lastname;
            //reg name
            URL obj = new URL(url);

            System.out.print("\nSending GET-Request: " + url + "\n");
            //
            //build connection
            HttpURLConnection httpcon = (HttpURLConnection) obj.openConnection();

            int responseCode = httpcon.getResponseCode();

            System.out.print("\nStatuscode: " + responseCode + "\n");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpcon.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //System.out.println(response.toString());

            //JSONObject test = new JSONObject(response.toString());
            return response.toString();
        }catch (Exception e){
            System.out.print(e + "\n");
            throw e;
        }
    }

    @GET
    @Path("/{id}")
    public Name getNameById(@PathParam("id") final int id) {
        throw new NameNotFoundException(id); // no greetings ever found for this example
    }
}
