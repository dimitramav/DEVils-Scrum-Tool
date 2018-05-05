package ys09.api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.conf.Configuration;

import java.util.HashMap;
import java.util.Map;

public class ConfigResource extends ServerResource {

    @Override
    protected Representation get() throws ResourceException {

        Map<String, String> map = new HashMap<>();
        Configuration config = Configuration.getInstance();
        for (String key : Configuration.CONFIG_KEYS) {
            map.put(key, config.getProperty(key));
        }

        return new JsonMapRepresentation(map);
    }
}
