package de.project2018.jaxrs;

import de.project2018.NameController;
import de.project2018.NameBinder;
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
