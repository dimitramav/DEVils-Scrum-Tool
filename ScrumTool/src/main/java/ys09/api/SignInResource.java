package ys09.api;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;


import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.auth.CustomAuth;
import ys09.data.DataAccess;
import ys09.model.Project;
import com.google.gson.Gson;
import ys09.model.SignIn;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Endpoints for user entity
public class SignInResource extends ServerResource {

  private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
  // Post for Sign Up
  @Override
  protected Representation post(Representation entity) throws ResourceException {
      // Representation object contains the body of the request
      // Handle the exception for the getText
      try{
          // Get the whole json body representation
          String str = entity.getText();
          // Now Create from String the JAVA object
          Gson gson = new Gson();
          SignIn signin = gson.fromJson(str, SignIn.class);
          // Check if email and password match
          String response = dataAccess.checkSignIn(signin);

          Map<String, String> map = new HashMap<>();

          

          if (response.equals("OK")) {
              // Create a JJWT
              CustomAuth customAuth = new CustomAuth();
              String token = customAuth.createToken(signin.getEmail());
              map.put("auth-token", token);
          }
          else if(response.equals("Wrong Password")) {
              map.put("Message", "Wrong Password");
          }
          else if(response.equals("Not Exists")) {
              map.put("Message", "User does not exists.");
          }
          // Return proper message with JSON
          return new JsonMapRepresentation(map);
      }

      catch(IOException e) {
        Map <String, String> map1 = new HashMap<>();
        map1.put("Message", "System Exception");
        return new JsonMapRepresentation(map1);
      }
  }
}
