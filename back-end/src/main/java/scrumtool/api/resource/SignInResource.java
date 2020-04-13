package scrumtool.api.resource;

import scrumtool.auth.CustomAuth;
import scrumtool.data.DataAccess;
import scrumtool.model.Project;
import scrumtool.model.User;
import scrumtool.model.SignIn;
import scrumtool.conf.Configuration;
import scrumtool.data.DataAccess;
import scrumtool.model.LocalStorage;
import scrumtool.api.representation.JsonMapRepresentation;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import com.google.gson.Gson;
import java.security.Key;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Endpoints for user entity
public class SignInResource extends ServerResource {

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
          SignIn signin = gson.fromJson(str, SignIn.class);
          // Check if email and password match
          User user = dataAccess.checkSignIn(signin);
          //System.out.println(key);
          Map<String, Object> map = new HashMap<>();

          if (user != null) {           // Key is the userId for a real user
              // Create a JJWT
              int key = user.getId();
              String username = user.getUsername();
              CustomAuth customAuth = new CustomAuth();
              String token = customAuth.createToken(Integer.toString(key));
              //System.out.println(user.getUsername());

              LocalStorage response = new LocalStorage(key, username, token);
              map.put("results", response);
              return new JsonMapRepresentation(map);
          }
          else {
              map.put("Message", "Wrong Email or Password");
          }
          // Return proper message with JSON
          return new JsonMapRepresentation(map);
      }

      catch (IOException e) {
        Map <String, String> map1 = new HashMap<>();
        map1.put("Message", "System Exception");
        return new JsonMapRepresentation(map1);
      }
  }
}
