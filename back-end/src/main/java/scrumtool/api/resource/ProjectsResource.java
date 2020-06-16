package scrumtool.api.resource;

import scrumtool.auth.CustomAuth;
import scrumtool.conf.Configuration;
import scrumtool.data.DataAccess;
import scrumtool.data.Limits;
import scrumtool.model.Project;
import scrumtool.api.representation.JsonMapRepresentation;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Patch;
import org.restlet.util.Series;

import org.json.JSONObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.sql.*;
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
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Read the values of url for pagination
        String strCurrentPage = getQuery().getValues("currentPage");
        String isDone = getQuery().getValues("isDone");

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // Get Projects only for the current user
        // Show them in the index page
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Return either the number of total projects, or the num of projects asked (pagination)
            if (strCurrentPage == null) {
                int totalNumOfCurrentProjects = dataAccess.getUserProjectsNumber(userId, Boolean.parseBoolean(isDone));
                map.put("results", totalNumOfCurrentProjects);
            }
            else {  // Calculate the indexes of projects asked in sql base, which will be ordered by deadlineDate
                Limits limit = new Limits(Integer.parseInt(strCurrentPage));
                List<Project> projects = dataAccess.getUserProjects(userId, limit, Boolean.parseBoolean(isDone));
                map.put("results", projects);
            }
            // Set the response headers
            return new JsonMapRepresentation(map);
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
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // In project post, we do not check for project, as it will never insert
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Get the whole json body representation
            try {
                String str = entity.getText();
                // Now Create from String the JAVA object
                Gson gson = new Gson();
                Project newProject = gson.fromJson(str, Project.class);
                try {
                    Project response = dataAccess.insertProject(newProject, userId, "Product Owner");
                    // Set the response headers
                    if (response == null){
                        mapError.put("error", "System Exception");
                        System.out.println("Transaction Error!!!");
                        return new JsonMapRepresentation(mapError);
                    }
                    map.put("results", response);
                    return new JsonMapRepresentation(map);
                }
                catch (SQLException e) {
                    mapError.put("error", "System Exception");
                    System.out.println("Transaction Error!1!1");
                    return new JsonMapRepresentation(mapError);
                }
            }
            catch(IOException e) {
                mapError.put("error", "System Exception");
                return new JsonMapRepresentation(mapError);
            }
        }
        else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }


    @Patch
    public Representation update(Representation entity) {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();
        //System.out.println("Inside post");

        // Get UserId
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // In project post, we do not check for project, as it will never insert
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Get the whole json body representation
            try {
                String str = entity.getText();
                // Now Create from String the JAVA object
                Gson gson = new Gson();
                int projectId = gson.fromJson(str, Integer.class);
                try {
                    Boolean response = dataAccess.deleteProject(projectId);
                    // Set the response headers
                    if (response == false) {
                        mapError.put("error", "System Exception");
                        System.out.println("Transaction Error!!!");
                        return new JsonMapRepresentation(mapError);
                    }
                    map.put("results", response);
                    return new JsonMapRepresentation(map);
                }
                catch (SQLException e) {
                    mapError.put("error", "System Exception");
                    System.out.println("Transaction Error!1!1");
                    return new JsonMapRepresentation(mapError);
                }
            } catch(IOException e) {
                mapError.put("error", "System Exception");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }
}
