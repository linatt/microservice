package com.johnathangilday;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
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
        final ResourceConfig app = new ResourceConfig();

        // start jetty
        logger.info("listening on port {}", port);
        final Server server = JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app));
        try {
            server.start();


            try {

                String url ="http://localhost:8000/name?firstname=lina&lastname=brunken";
                //reg name
                URL obj = new URL(url);

                System.out.print("\nSending GET-Request: " + url + "\n");
                //
                //build connection
                HttpURLConnection httpcon =(HttpURLConnection) obj.openConnection();

                int responseCode = httpcon.getResponseCode();

                System.out.print("\nStatuscode: " + responseCode+ "\n");

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
                System.out.print(e + "\n");

            }




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