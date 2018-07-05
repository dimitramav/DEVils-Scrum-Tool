package ys09.api;

import ys09.auth.CustomAuth;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.TeamDB;
import ys09.model.Issue;
import ys09.model.Team;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.IOException;
import java.io.Reader;
import org.json.JSONObject;
import com.google.gson.Gson;

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


public class IssuesResource extends ServerResource {

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

        // Get projectId
        String projectIdStr = getRequestAttributes().get("projectId").toString();
        if (projectIdStr.equals("null")) {
            mapError.put("error", "Unauthorized project");
            return new JsonMapRepresentation(mapError);
        }
        int projectId = Integer.parseInt(projectIdStr);

        // Get taskId
        String taskIdStr = getRequestAttributes().get("taskId").toString();
        if (taskIdStr.equals("null")) {
            mapError.put("error", "Unauthorized project");
            return new JsonMapRepresentation(mapError);
        }
        int taskId = Integer.parseInt(taskIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            // Get issues for current task
            if(customAuth.userValidation(token, userIdStr)) {
                // Find the team members
                boolean flag = false;
                TeamDB teamDB = new TeamDB();
                List<Team> teamList = teamDB.getTeamMembers(projectId);
                for (Team member:teamList) {
                    if (member.getIdUser() == userId) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    // If user's id is in the team members' list, return a list of issues
                    List<Issue> issues = dataAccess.getTaskIssues(taskId);
                    map.put("results", issues);
                    // Set the response headers
                    return new JsonMapRepresentation(map);
                }
                else {
                    mapError.put("error", "Unauthorized sprint access");
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

        // Get projectId
        String projectIdStr = getRequestAttributes().get("projectId").toString();
        if (projectIdStr.equals("null")) {
            mapError.put("error", "Unauthorized project");
            return new JsonMapRepresentation(mapError);
        }
        int projectId = Integer.parseInt(projectIdStr);

        // Get taskId
        String taskIdStr = getRequestAttributes().get("taskId").toString();
        if (taskIdStr.equals("null")) {
            mapError.put("error", "Unauthorized project");
            return new JsonMapRepresentation(mapError);
        }
        int taskId = Integer.parseInt(taskIdStr);

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
            if(customAuth.userValidation(token, userIdStr)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Issue newIssue = gson.fromJson(str, Issue.class);
                    // Insert Issue in database
                    Issue response = dataAccess.insertTaskIssue(newIssue, taskId);
                    // Set the response headers
                    map.put("results", response);
                    return new JsonMapRepresentation(map);
                }
                catch(IOException e) {
                    mapError.put("error", "System Exception");
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

