

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Request {

    public static void main (String[] args){

        try {

            String url ="http://localhost:8000/name?name=lina";
            URL obj = new URL(url);
            HttpURLConnection httpcon =(HttpURLConnection) obj.openConnection();

            int responseCode = httpcon.getResponseCode();
            System.out.println("\nSending GET Request zu " + url);

            System.out.println("\nRespondCOde: " + responseCode);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(httpcon.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine=in.readLine())!= null){
                    response.append(inputLine);
                }
                in.close();

                //System.out.println(response.toString());

            //JSONObject test = new JSONObject(response.toString());
            System.out.println("Server: " + response.toString());

            }catch (Exception e){

        }




    }


}





