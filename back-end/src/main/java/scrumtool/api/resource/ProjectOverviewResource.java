package scrumtool.api.resource;

import scrumtool.auth.CustomAuth;
import scrumtool.conf.Configuration;
import scrumtool.data.DataAccess;
import scrumtool.data.Limits;
import scrumtool.data.entities.SprintDB;
import scrumtool.model.*;
import scrumtool.api.representation.JsonMapRepresentation;

import org.restlet.Message;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.data.ClientInfo;
import org.restlet.util.Series;
import org.restlet.representation.Representation;
import org.restlet.representation.BufferingRepresentation;
//import org.restlet.engine.header.Header;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Patch;
import org.restlet.resource.Post;
import org.restlet.ext.json.JsonRepresentation;

import org.json.JSONObject;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


//import static org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED;


public class ProjectOverviewResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Unauthorized access if user is not the product owner
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
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

        // Access the headers of the request !
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Get Project Overview Information
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Get project and its current sprint Information information
                Project project = dataAccess.getCurrentProject(projectId);
                SprintDB sprintDB = new SprintDB();
                Sprint sprint = null;
                String sprintIdStr = getQuery().getValues("sprintId");
                if (sprintIdStr != null) {
                    int sprintId = Integer.parseInt(sprintIdStr);
                    sprint = sprintDB.getProjectSprint(projectId, sprintId);
                }
                else sprint = sprintDB.getProjectCurrentSprint(projectId);

                // Create a ProjectOverview item to store the data needed for overview page
                ProjectOverview projectOverviewItem = new ProjectOverview();
                projectOverviewItem.project = project;
                projectOverviewItem.setCurrentSprintId(sprint.getIdSprint());
                projectOverviewItem.setCurrentSprintNum(sprint.getNumSprint());
                projectOverviewItem.setCurrentSprintExpDate(sprint.getDeadlineDate());
                projectOverviewItem.setCurrentSprintGoal(sprint.getGoal());
                // Find the tasks and issues belong to this sprint
                List<Task> sprintTasks = dataAccess.getSprintTasks(sprint.getIdSprint());
                List<Issue> sprintIssues = dataAccess.getSprintIssues(sprint.getIdSprint());
                // Find how many tasks and issues are in state of todo, doing or done
                int i;
                for (i = 0; i < sprintTasks.size(); i++){
                    if (sprintTasks.get(i).getState() == 1)
                        projectOverviewItem.setTodo(projectOverviewItem.getTodo()+1);
                    else if (sprintTasks.get(i).getState() == 2)
                        projectOverviewItem.setDoing(projectOverviewItem.getDoing()+1);
                    else if (sprintTasks.get(i).getState() == 3)
                        projectOverviewItem.setDone(projectOverviewItem.getDone()+1);
                }
                for (i = 0; i < sprintIssues.size(); i++){
                    if (sprintIssues.get(i).getState() == 1)
                        projectOverviewItem.setTodoIssues(projectOverviewItem.getTodoIssues()+1);
                    else if (sprintIssues.get(i).getState() == 2)
                        projectOverviewItem.setDoingIssues(projectOverviewItem.getDoingIssues()+1);
                    else if (sprintIssues.get(i).getState() == 3)
                        projectOverviewItem.setDoneIssues(projectOverviewItem.getDoneIssues()+1);
                }
                map.put("results", projectOverviewItem);
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



    // Update the project information
    @Override
    protected Representation put(Representation entity) throws ResourceException {

       // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Unauthorized access if user is not the product owner
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
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

        // Access the headers of the request !
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Get Project Overview Information
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Update project information
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    String isDone = getQuery().getValues("isDone");
                    if (isDone == null) {
                        Project projectItem = gson.fromJson(str, Project.class);
                        Project response = dataAccess.updateCurrentProject(projectItem);
                        map.put("results", response);
                    }   // Update isDone value only
                    else {
                        Boolean isDoneBool = gson.fromJson(str, Boolean.class);
                        Boolean response = dataAccess.updateProjectIsDone(isDoneBool, projectId);
                        map.put("results", response);
                    }
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


    // Delete project
    @Patch
    protected Representation update(Representation entity) throws ResourceException {

       // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Unauthorized access if user is not the product owner
        String userIdStr = getRequestAttributes().get("userId").toString();
        if (userIdStr.equals("null")) {
            mapError.put("error", "Unauthorized user");
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

        // Access the headers of the request !
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if (customAuth.checkUserAuthToken(token, userIdStr)) {
            // Get Project Overview Information
            if (dataAccess.userMemberOfProject(userId, projectId)) {
                // Update project information
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Project projectItem = gson.fromJson(str, Project.class);
                    //Project response = dataAccess.updateCurrentProject(projectItem);
                    System.out.println(projectId);
                    map.put("results", projectId);
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


    // Post Representation
    /*@Override
    protected Representation post(Representation entity) throws ResourceException {

        //Foo targetObject = new Gson().fromJson(entity, Project.class);
        System.out.println(entity);
        try {
            // Get the json representation from the frontend
            String str = entity.getText();
            //System.out.println(str);
            Map<String, Object> map = new HashMap<>();
            map.put("results", entity);
            // Now Create from String the JAVA object
            Gson gson = new Gson();
            Project project = gson.fromJson(str, Project.class);

            // Insert the Project to the database
            dataAccess.insertProject(project, 1, "Developer");

            Map<String, Object> mapError = new HashMap<>();
            mapError.put("results", project);
            return new JsonMapRepresentation(mapError);
        }

        catch(IOException e) {
            Map<String, String> mapError = new HashMap<>();
            mapError.put("result", "System Exception");
            return new JsonMapRepresentation(mapError);
        }
    }*/
}
