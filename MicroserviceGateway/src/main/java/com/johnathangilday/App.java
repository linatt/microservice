package com.johnathangilday;

import com.johnathangilday.jaxrs.NameResourceConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.Handler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;
import java.util.Optional;

/**
 * Point of entry. Configure and start webapp
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) {
        final int port = 3000;
        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        // start jetty
        logger.info("listening on port {}", port);
        final NameResourceConfig app = new NameResourceConfig();

        final Server server = JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app), false);

        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath("/web");
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });

        resource_handler.setResourceBase("./webapp");
        contextHandler.setHandler(resource_handler);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { contextHandler });

        server.setHandler(handlers);
        try {
            server.start();
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