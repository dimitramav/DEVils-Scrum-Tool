package ys09.api;

import com.google.gson.Gson;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.model.Email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UserExistsResource extends ServerResource{
    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
    // Post for Sign Up
    @Override
    protected Representation post(Representation entity) throws ResourceException {
        // Representation object contains the body of the request
        // Handle the exception for the getText
        try {
            // Get the whole json body representation
            String str = entity.getText();
            // Now Create from String the JAVA object
            Gson gson = new Gson();
            Email email = gson.fromJson(str, Email.class);
            // Check if email and password match
            boolean response = dataAccess.userExists(email.getMail());

            Map<String, Integer> map = new HashMap<>();
            if(response) {
                map.put("exists", 1);
                return new JsonMapRepresentation(map);
            }
            else {
                map.put("exists", 0);
                return new JsonMapRepresentation(map);
            }
        }

        catch (IOException e) {
            Map <String, String> map1 = new HashMap<>();
            map1.put("Message", "System Exception");
            return new JsonMapRepresentation(map1);
        }
    }
}
