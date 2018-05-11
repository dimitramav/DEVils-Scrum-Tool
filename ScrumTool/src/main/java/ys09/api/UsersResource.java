package ys09.api;


import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.auth.CustomAuth;
import ys09.data.DataAccess;
import ys09.model.Project;
import com.google.gson.Gson;
import ys09.model.User;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Endpoints for user entity
public class UsersResource extends ServerResource {

  private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
  // Post Representation
  @Override
  protected Representation post(Representation entity) throws ResourceException {
      // Representation object contains the body of the request
      // Handle the exception for the getText
      Map map = new HashMap<String, String>();
      try {
          // Get the whole json body representation
          String str = entity.getText();
          // Now Create from String the JAVA object
          Gson gson = new Gson();
          User user = gson.fromJson(str, User.class);

          // Check if this user exists in the database

          // Insert the User to the database
          dataAccess.insertUser(user);
          // Return token for auth
          CustomAuth customAuth = new CustomAuth();
          String token = customAuth.createToken(user.getEmail());
          map.put("auth-token", token);
          return new JsonMapRepresentation(map);

      }

      catch(IOException e) {
          map.put("result", "System Exception");
          return new JsonMapRepresentation(map);
      }
      // Maybe sent a response message ?

  }
}
