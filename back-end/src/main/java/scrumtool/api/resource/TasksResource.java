package scrumtool.api.resource;

import scrumtool.auth.CustomAuth;
import scrumtool.conf.Configuration;
import scrumtool.data.DataAccess;
import scrumtool.data.entities.SprintDB;
import scrumtool.data.entities.TaskDB;
import scrumtool.data.entities.TeamDB;
import scrumtool.model.*;
import scrumtool.api.representation.JsonMapRepresentation;

import com.google.gson.Gson;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Patch;
import org.restlet.util.Series;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TasksResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    // Story has multiple tasks
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

        // Check if the token is ok
        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Get tasks
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

                    // Check if is Epic
                    String isCurrent = getQuery().getValues("isCurrent");
                    Boolean current;
                    if (isCurrent == null) {
                        current = false;
                        mapError.put("error", "Missing isCurrent Query Parameter");
                        return new JsonMapRepresentation(mapError);
                    }
                    else {
                        current = Boolean.parseBoolean(isCurrent);
                        if(current) {
                            // Get the current sprint info !
                            SprintDB sprintDB = new SprintDB();
                            Sprint currentSprint = sprintDB.getProjectCurrentSprint(projectId);

                            // find the id of the current sprint
                            int currentSprintId = currentSprint.getIdSprint();
                            System.out.println(currentSprintId);
                            // Find the tasks of the current sprint
                            List<Task> tasks = dataAccess.getSprintTasks(currentSprintId);
                            map.put("tasks", tasks);
                            return new JsonMapRepresentation(map);
                        }
                    }
                } else {
                    mapError.put("error", "Unauthorized sprint access");
                    return new JsonMapRepresentation(mapError);
                }
            } else {
                mapError.put("error", "Unauthorized projects");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        return new JsonMapRepresentation(mapError);
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

        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Insert tasks
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Task newTask = gson.fromJson(str, Task.class);
                    // Insert Task
                    TaskDB taskDB = new TaskDB();
                    Task task = taskDB.insert(newTask);
                    //Project response = dataAccess.insertProject(newProject, user, "Product Owner");
                    // Set the response headers
                    map.put("task", task);
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
        // Convert it to boolean

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

        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Insert the pbi given (either epic or story)
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Task task = gson.fromJson(str, Task.class);
                    TaskDB taskDB = new TaskDB();
                    // Update
                    Task response = taskDB.update(task);
                    // Set the response headers
                    map.put("task", response);
                    return new JsonMapRepresentation(map);
                }
                catch(IOException e) {
                    mapError.put("error", "System Exception");
                    return new JsonMapRepresentation(mapError);
                }
            } else {
                mapError.put("error", "Unauthorized projects");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }

    // Delete task
    @Patch
    public Representation update(Representation entity) {
        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Get UserId
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        int userId = Integer.parseInt(userIdStr);
        // Convert it to boolean

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

        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Insert the pbi given (either epic or story)
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Task task = gson.fromJson(str, Task.class);
                    try {
                        TaskDB taskDB = new TaskDB();
                        // Update
                        int response = taskDB.delete(task);
                        // Set the response headers
                        map.put("task", response);
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
            } else {
                mapError.put("error", "Unauthorized projects");
                return new JsonMapRepresentation(mapError);
            }
        } else {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
    }
}
