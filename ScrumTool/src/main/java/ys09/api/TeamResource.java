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

import ys09.data.TeamDB;
import ys09.model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.restlet.Message;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.util.Series;


public class TeamResource extends ServerResource {

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

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            // Get Project Team Members
            if(customAuth.userValidation(token, userId)) {
                // Get project and its current sprint Information information
                TeamDB teamDB = new TeamDB();
                List<Team> teamList = teamDB.getTeamMembers(projectId);
                // Find the role that each member has in project
                for (int i = 0; i < teamList.size(); i++){
                    String role = dataAccess.getMemberRole(teamList.get(i).getIdUser(), projectId);
                    teamList.get(i).setRole(role);
                }
                map.put("results", teamList);
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


    // Insert a new member into project
    @Override
    protected Representation post(Representation entity) throws ResourceException {

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

        // Access the headers of the request!
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "null");
            return new JsonMapRepresentation(mapError);
        }
        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            // Insert the member
            if(customAuth.userValidation(token, userIdStr)) {
                // Get the whole json body representation
                try {
                    String str = entity.getText();
                    // Now Create from String the JAVA object
                    Gson gson = new Gson();
                    Team member = gson.fromJson(str, Team.class);
                    // Insert
                    TeamDB teamDB = new TeamDB();
                    Team response = teamDB.insertNewMember(member, projectId);
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
