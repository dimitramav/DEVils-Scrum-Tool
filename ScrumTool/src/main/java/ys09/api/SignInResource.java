package ys09.api;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;


import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.auth.AuthScope;
import ys09.auth.CustomAuth;
import ys09.data.DataAccess;
import ys09.model.Project;
import com.google.gson.Gson;
import ys09.model.SignIn;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.model.SignInResponse;

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
          //int key = dataAccess.checkSignIn(signin); //TODO: error Yolanda
          int  key=1;
          Map<String, Object> map = new HashMap<>();

          if (key != 0) {
              // Create a JJWT
              CustomAuth customAuth = new CustomAuth();
              String token = customAuth.createToken(signin.getEmail());
              //Create object for authorization rights
              List<Integer> projectAsDev = dataAccess.createAuthProjectList(signin.getId(),"developer");
              List <Integer> projectAsOwner = dataAccess.createAuthProjectList(signin.getId(),"product_owner");
              List <Integer> projectAsScrum = dataAccess.createAuthProjectList(signin.getId(),"scrum_master");
              AuthScope AuthorizedProjects= new AuthScope(projectAsDev,projectAsOwner,projectAsScrum);

              SignInResponse response = new SignInResponse(key, token);
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
