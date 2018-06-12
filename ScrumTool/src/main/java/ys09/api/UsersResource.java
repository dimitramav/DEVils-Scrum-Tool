package ys09.api;


import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import ys09.auth.CustomAuth;
import ys09.data.DataAccess;
import ys09.model.PBI;
import com.google.gson.Gson;
import ys09.model.SignInResponse;
import ys09.model.User;
import ys09.conf.Configuration;
import java.io.IOException;
import java.util.*;

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

          // Return token for auth
          int key = dataAccess.insertUser(user);

          // Return token for auth
          CustomAuth customAuth = new CustomAuth();
          String token = customAuth.createToken(Integer.toString(key));
          SignInResponse response = new SignInResponse(key, token);
          map.put("results", response);
          return new JsonMapRepresentation(map);
      }

      catch(IOException e) {
          map.put("result", "System Exception");
          return new JsonMapRepresentation(map);
      }
      // Maybe sent a response message ?

  }

    @Override
    protected Representation get() throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Unauthorized access if user is not the product owner
        // Get UserId
        String userId = getRequestAttributes().get("userId").toString();
        if (userId.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int user = Integer.parseInt(userId);
        // Access the headers of the request !
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            String fullname = getQuery().getValues("fullname");
            String fullname_array[]=fullname.split(".");
            String firstname=fullname_array[0];
            String lastname= fullname_array[1];
            System.out.println("hi "+firstname+ "hi "+lastname);

            if(customAuth.userValidation(token, userId)) {
                // Get all the user's information
                //List<PBI> profile = dataAccess.getProfile(user,username,password);
                //map.put("results", profile);
                // Set the response headers
                return new JsonMapRepresentation(map);
            }
            else {
                mapError.put("error", "Unauthorized projects");
                return new JsonMapRepresentation(mapError);
            }
        }
        else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
  }
}
