package ys09.api;

import ys09.auth.CustomAuth;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.Limits;
import ys09.model.Project;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.IOException;
import java.io.Reader;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import static org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED;


public class ProjectsResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Get UserId
        String userId = getRequestAttributes().get("userId").toString();
        if (userId.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int user = Integer.parseInt(userId);

        // Read the values of url (limit and nearestDeadline) for pagination
        String strcount = getQuery().getValues("limit");
        if (strcount == null) {
            strcount = "0";
        }
        String strstart = getQuery().getValues("nearestDeadline");
        String isDone = getQuery().getValues("isDone");

        DateFormat format = new SimpleDateFormat("yyyy-M-d");
        Date expDate = new Date(0L);
        try { expDate = format.parse(strstart); }
        catch (Exception e) { System.out.println("No date"); }
        System.out.println(expDate);
        System.out.println(strcount);
        Limits limit = new Limits(Integer.parseInt(strcount), expDate);

        // Access the headers of the request !
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            // Get Projects only for the current user
            // Show them in the index page
            if(customAuth.userValidation(token, userId)) {
                List<Project> projects = dataAccess.getUserProjects(user, limit, Boolean.parseBoolean(isDone));
                map.put("results", projects);
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



    @Override
    protected Representation post(Representation entity) throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();
        //System.out.println("Inside post");

        // Get UserId
        String userId = getRequestAttributes().get("userId").toString();
        if (userId.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int user = Integer.parseInt(userId);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            // Insert a project only for the current user (Product Owner)
            if(customAuth.userValidation(token, userId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Project newProject = gson.fromJson(str, Project.class);
                    String response = dataAccess.insertProject(newProject, user, "Product Owner");
                    // Set the response headers
                    map.put("Project insert", response);
                    return new JsonMapRepresentation(map);
                }
                catch(IOException e) {
                    mapError.put("result", "System Exception");
                    return new JsonMapRepresentation(mapError);
                }
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

