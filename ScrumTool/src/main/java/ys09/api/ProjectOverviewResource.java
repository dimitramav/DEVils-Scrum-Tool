package ys09.api;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.auth.CustomAuth;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.Limits;
import java.io.Reader;
import org.restlet.resource.Post;
import org.restlet.ext.json.JsonRepresentation;
import org.json.JSONObject;
import com.google.gson.Gson;
import org.restlet.representation.BufferingRepresentation;
import org.restlet.data.ClientInfo;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;
import java.io.IOException;

import ys09.model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.restlet.Message;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.util.Series;

//import static org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED;


public class ProjectOverviewResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Unauthorized access if user is not the product owner
        // Get UserId
        String userId = getRequestAttributes().get("userId").toString();
        if (userId.equals("null")) {
            mapError.put("error", "Unauthorized user");
            return new JsonMapRepresentation(mapError);
        }
        int user = Integer.parseInt(userId);

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

        if(customAuth.checkAuthToken(token)) {
            // Get Project Overview Information
            if(customAuth.userValidation(token, userId)) {
                // Get project and its current sprint Information information
                Project project = dataAccess.getCurrentProject(projectId);
                Sprint  sprint  = dataAccess.getProjectCurrentSprint(projectId);
                // Create a ProjectOverview item to store the data needed for overview page
                ProjectOverview projectOverviewItem = new ProjectOverview();
                projectOverviewItem.project = project;
                projectOverviewItem.setCurrentSprintId(sprint.getIdSprint());
                projectOverviewItem.setCurrentSprintNum(sprint.getNumSprint());
                projectOverviewItem.setCurrentSprintExpDate(sprint.getDeadlineDate());
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
