package ys09.api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import ys09.auth.CustomAuth;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.SprintDB;
import ys09.data.TeamDB;
import ys09.model.PBI;
import ys09.model.Sprint;
import ys09.model.Task;
import ys09.model.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksResource extends ServerResource {
    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    // Story has multiple tasks
    // We want sprint id
    protected Representation get() throws ResourceException {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Get UserId
        String userId = getRequestAttributes().get("userId").toString();
        if (userId.equals("null")) {
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int user = Integer.parseInt(userId);

        // Get ProjectId


        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }

        CustomAuth customAuth = new CustomAuth();

        // Check if the token is ok
        if(customAuth.checkAuthToken(token)) {
            // Insert a project only for the current user (Product Owner)
            if(customAuth.userValidation(token, userId)) {
                // Get the projectId
                String projectId = getRequestAttributes().get("projectId").toString();

                if (projectId.equals("null")) {
                    mapError.put("error", "Unauthorized projects");
                    return new JsonMapRepresentation(mapError);
                }
                int project = Integer.parseInt(projectId);
                // Find the team members
                boolean flag = false;
                TeamDB teamDB = new TeamDB();
                List<Team> teamList = teamDB.getTeamMembers(project);
                for(Team member:teamList) {
                    if(member.getIdUser() == user) {
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
                            Sprint currentSprint = sprintDB.getProjectCurrentSprint(project);

                            // find the id of the current sprint
                            int currentSprintId = currentSprint.getIdSprint();
                            System.out.println(currentSprintId);
                            // Find the tasks of the current sprint
                            List<Task> tasks = dataAccess.getSprintTasks(currentSprintId);
                            map.put("tasks", tasks);
                            return new JsonMapRepresentation(map);
                        }
                    }

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
        return new JsonMapRepresentation(mapError);
    }

}
