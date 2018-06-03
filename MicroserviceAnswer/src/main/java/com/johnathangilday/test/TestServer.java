package com.johnathangilday.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestServer {
    @Test
    public void ServertestLina(){
        try{
            String firstname = "lina";
            String lastname = "brunken";
            String url = "http://localhost:8000/name?firstname=" + firstname + "&lastname=" + lastname;
            //reg name
            URL obj = new URL(url);

            System.out.print("\nSending GET-Request: " + url + "\n");
            //
            //build connection
            HttpURLConnection httpcon = (HttpURLConnection) obj.openConnection();

            int responseCode = httpcon.getResponseCode();

            Assert.assertEquals(200, responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpcon.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Assert.assertEquals("Hallo Lina!", response.toString());


        }catch (Exception e){
            System.out.print(e + "\n");
        }


    }@Test
    public void ServertestUnknown(){
        try{
            String firstname = "unknown";
            String lastname = "lol";
            String url = "http://localhost:8000/name?firstname=" + firstname + "&lastname=" + lastname;
            //reg name
            URL obj = new URL(url);

            System.out.print("\nSending GET-Request: " + url + "\n");
            //
            //build connection
            HttpURLConnection httpcon = (HttpURLConnection) obj.openConnection();

            int responseCode = httpcon.getResponseCode();

            Assert.assertEquals(200, responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpcon.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Assert.assertEquals("Hallo Unbekannt!", response.toString());


        }catch (Exception e){
            System.out.print(e + "\n");
        }


    }
}
