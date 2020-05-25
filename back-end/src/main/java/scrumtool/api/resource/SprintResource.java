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
import org.restlet.util.Series;

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
                            System.out.println(currentSprint);
                            map.put("sprint", currentSprint);
                            return new JsonMapRepresentation(map);
                        }
                    }
                }
                else {
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
        return new JsonMapRepresentation(mapError);
    }

}
