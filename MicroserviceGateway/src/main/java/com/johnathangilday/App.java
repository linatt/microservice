package com.johnathangilday;

import com.johnathangilday.jaxrs.NameResourceConfig;
import com.johnathangilday.servlet.WebInterfaceServlet;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
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
        final int port = 3000;
        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        // start jetty
        logger.info("listening on port {}", port);
        final NameResourceConfig app = new NameResourceConfig();

//        final Server server = JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app), false);
        Server server = new Server(port);
        ContextHandler webContextHandler = new ContextHandler();
        //contextHandler.setContextPath("");
        webContextHandler.setContextPath("/web");
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });

        resource_handler.setResourceBase("./webapp");
        webContextHandler.setHandler(resource_handler);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder holder = new ServletHolder(new ServletContainer(ResourceConfig.forApplication(app)));
        context.addServlet(holder, "/*");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { webContextHandler, context });
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