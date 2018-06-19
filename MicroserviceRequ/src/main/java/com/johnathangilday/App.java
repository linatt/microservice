package com.johnathangilday;

import com.johnathangilday.jaxrs.ProxyResourceConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

/**
 * Point of entry. Configure and start webapp
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        final Config config = config();

       // final int port = config.getInt("port");
        final int port = 8001;
        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        final ProxyResourceConfig app = new ProxyResourceConfig();

        // start jetty
        logger.info("listening on port {}", port);
        final Server server = JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app));
        try {
            server.start();

         //   String url = "http://localhost/8080/name/register?name=Microservice&url=http://localhost/8001";
            String url = "http://localhost/8080/name/register?name=Microservice&url=test";
            System.out.println("URL to Databaseu: " + url);
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

//            System.out.println("Register");
//            //register in DB
//
//            Client client = ClientBuilder.newClient();
//            WebTarget webTarget = client.target("http://localhost/8080/name/register");
//
//            MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
//            formData.add("name", "MircoserviceRequ");
//          //  formData.add("url", "http://localhost/8001");
//            formData.add("url", "test");
////            Response response = webTarget.request().post(Entity.form(formData));
//
//            ClientResponse response = webResource
//                    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
//                    .post(ClientResponse.class, formData);
//

            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Config config() {
        final Config defaultConfig = ConfigFactory.load();

        final Optional<File> externalConfigFile = Optional.ofNullable(System.getProperty("config")).map(File::new);
        if (externalConfigFile.isPresent() && !externalConfigFile.get().exists()) {
            throw new RuntimeException("external config file " + externalConfigFile.get().getAbsolutePath() + " not found");
        }

        return externalConfigFile
                .map(ConfigFactory::parseFile)
                .map(c -> c.withFallback(defaultConfig))
                .orElse(defaultConfig);
    }


}