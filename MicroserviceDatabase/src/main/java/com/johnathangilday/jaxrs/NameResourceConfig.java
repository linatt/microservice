package com.johnathangilday.jaxrs;

import com.johnathangilday.NameController;
import com.johnathangilday.NameBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Use Jersey {@link ResourceConfig} to do application level component binding
 */
public class NameResourceConfig extends ResourceConfig {

    public NameResourceConfig() {
        register(new NameBinder());
        register(NameController.class);
        register(NameNotFoundExceptionMapper.class);
        register(ObjectMapperProvider.class);
    }
}
