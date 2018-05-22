package com.johnathangilday.jaxrs;

import com.johnathangilday.ProxyController;
import com.johnathangilday.ProxyBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Use Jersey {@link ResourceConfig} to do application level component binding
 */
public class ProxyResourceConfig extends ResourceConfig {

    public ProxyResourceConfig() {
        register(new ProxyBinder());
        register(ProxyController.class);
        register(ObjectMapperProvider.class);
    }
}
