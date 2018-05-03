package ys09.api;


import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.data.DataAccess;
import ys09.model.Project;
import com.google.gson.Gson;
import ys09.model.User;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import java.io.IOException;

// Endpoints for user entity
public class UsersResource extends ServerResource {

  private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
  // Post Representation
  @Override
  protected Representation post(Representation entity) throws ResourceException {
      // Representation object contains the body of the request
      // Handle the exception for the getText
      try{
          // Get the whole json body representation
          String str = entity.getText();
          // Now Create from String the JAVA object
          Gson gson = new Gson();
          User user = gson.fromJson(str, User.class);
          // Insert the User to the database
          dataAccess.insertUser(user);

          return null;
      }

      catch(IOException e) {

      }
      // Maybe sent a response message ?
      return null;
  }
}
