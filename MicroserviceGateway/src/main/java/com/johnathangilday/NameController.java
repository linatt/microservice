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
            String urlDB = "http://localhost:8080/name/MicroserviceRequ";
            //String url = urlDB+"/name?firstname=" + firstname + "&lastname=" + lastname;
            //asking for url from Microservice
            URL obj = new URL(urlDB);

            System.out.print("\nSending GET-Request: " + urlDB + "\n");
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
            String urlService = response.toString(); //http://localhost:8001
            System.out.println("URL-SERVICE: " + urlService);



            String url = urlService+"/name?firstname=" + firstname + "&lastname=" + lastname;
            System.out.println("URL to MicroserviceRequ: " + url);
            System.out.println(url);
            URL obj2 = new URL(url);

            System.out.print("\nSending GET-Request: " + url + "\n");
            //
            //build connection
            HttpURLConnection httpcon2 = (HttpURLConnection) obj2.openConnection();

            int responseCode2 = httpcon2.getResponseCode();

            System.out.print("\nStatuscode: " + responseCode2 + "\n");

            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(httpcon2.getInputStream()));
            String inputLine2;
            StringBuffer response2 = new StringBuffer();
            while ((inputLine2 = in2.readLine()) != null) {
                response2.append(inputLine2);
            }
            in2.close();

            //System.out.println(response.toString());

            //JSONObject test = new JSONObject(response.toString());
            String AnswerRequest= response2.toString();

            return AnswerRequest;

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
