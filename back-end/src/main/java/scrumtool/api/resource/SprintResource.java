package scrumtool.api.resource;

import scrumtool.auth.CustomAuth;
import scrumtool.conf.Configuration;
import scrumtool.data.entities.SprintDB;
import scrumtool.data.entities.TeamDB;
import scrumtool.data.DataAccess;
import scrumtool.model.Project;
import scrumtool.model.Sprint;
import scrumtool.model.Team;
import scrumtool.api.representation.JsonMapRepresentation;

import com.google.gson.Gson;
import org.restlet.representation.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Patch;
import org.restlet.util.Series;

import java.sql.SQLException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SprintResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
    // Post: Create new Sprint
    @Override
    protected Representation post(Representation entity) throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();
        //System.out.println("Inside post");

        // Get UserId
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Get projectId
        String projectIdStr = getRequestAttributes().get("projectId").toString();
        int projectId = Integer.parseInt(projectIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // Insert a project only for the current user (Product Owner)
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Check if user is a member of project
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Sprint sprint = gson.fromJson(str, Sprint.class);
                    // Insert
                    SprintDB sprintDB = new SprintDB();
                    int sprint_id = sprintDB.createNewSprint(sprint);
                    // Set the response headers
                    map.put("Sprint_id", sprint_id);
                    return new JsonMapRepresentation(map);
                }
                catch(IOException e) {
                    mapError.put("result", "System Exception");
                    return new JsonMapRepresentation(mapError);
                }
            }
            else {
                mapError.put("error", "Unauthorized project");
                return new JsonMapRepresentation(mapError);
            }
        }
        else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }

    @Override
    protected Representation get() throws ResourceException {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Get UserId
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Get the projectId
        String projectIdStr = getRequestAttributes().get("projectId").toString();
        if (projectIdStr.equals("null")) {
            mapError.put("error", "Unauthorized project");
            return new JsonMapRepresentation(mapError);
        }
        int projectId = Integer.parseInt(projectIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // Insert a project only for the current user (Product Owner)
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Check if user is a member of project
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Find the team members
                boolean flag = false;
                TeamDB teamDB = new TeamDB();
                List<Team> teamList = teamDB.getTeamMembers(projectId);
                for(Team member:teamList) {
                    if(member.getIdUser() == userId) {
                        flag = true;
                    }
                }

                if (flag) {
                    // If id is in the team members' list
                    // Return sprints data

                    // Check sprint id
                    String sprintIdStr = getQuery().getValues("sprintId");
                    if (sprintIdStr == null) {
                        // Get all sprints of this project
                        SprintDB sprintDB = new SprintDB();
                        List<Sprint> sprints = sprintDB.getAllProjectSprints(projectId);
                        map.put("sprint", sprints);
                        return new JsonMapRepresentation(map);
                    }
                    else {
                        int sprintId = Integer.parseInt(sprintIdStr);
                        // Get the current sprint info !
                        SprintDB sprintDB = new SprintDB();
                        Sprint currentSprint = sprintDB.getProjectSprint(projectId, sprintId);
                        if (currentSprint == null) {
                            mapError.put("error", "Sprint not found");
                            return new JsonMapRepresentation(mapError);
                        }
                        map.put("sprint", currentSprint);
                        return new JsonMapRepresentation(map);
                    }
                } else {
                    mapError.put("error", "Unauthorized sprint access");
                    return new JsonMapRepresentation(mapError);
                }
            } else {
                mapError.put("error", "Unauthorized project");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();
        //System.out.println("Inside post");

        // Get UserId
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Get projectId
        String projectIdStr = getRequestAttributes().get("projectId").toString();
        int projectId = Integer.parseInt(projectIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // Insert a project only for the current user (Product Owner)
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Check if user is a member of project
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Sprint sprint = gson.fromJson(str, Sprint.class);
                    // update sprint
                    SprintDB sprintDB = new SprintDB();
                    sprint = sprintDB.updateCurrentSprint(sprint);
                    // Set the response headers
                    map.put("results", sprint);
                    return new JsonMapRepresentation(map);
                }
                catch(IOException e) {
                    mapError.put("error", "System Exception");
                    return new JsonMapRepresentation(mapError);
                }
            } else {
                mapError.put("error", "Unauthorized project");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }


    @Patch
    public Representation update(Representation entity) {
        // Delete sprint from project
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Get UserId
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);

        // Get projectId
        String projectIdStr = getRequestAttributes().get("projectId").toString();
        int projectId = Integer.parseInt(projectIdStr);

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        // Insert a project only for the current user (Product Owner)
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Check if user is a member of project
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Sprint sprint = gson.fromJson(str, Sprint.class);
                    try {
                        SprintDB sprintDB = new SprintDB();
                        sprint = sprintDB.deleteCurrentSprint(sprint);
                        if (sprint == null) {
                            mapError.put("error", "System Exception");
                            return new JsonMapRepresentation(mapError);
                        }
                        map.put("results", sprint);
                        return new JsonMapRepresentation(map);
                    }
                    catch (SQLException e) {
                        mapError.put("error", "System Exception");
                        System.out.println("Transaction Error!1!1");
                        return new JsonMapRepresentation(mapError);
                    }
                }
                catch(IOException e) {
                    mapError.put("result", "System Exception");
                    return new JsonMapRepresentation(mapError);
                }
            } else {
                mapError.put("error", "Unauthorized project");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }
}
