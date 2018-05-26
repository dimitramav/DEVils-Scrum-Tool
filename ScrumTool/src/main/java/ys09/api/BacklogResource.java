package ys09.api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import ys09.auth.CustomAuth;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.Limits;
import ys09.model.Epic;
import ys09.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacklogResource extends ServerResource {
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
            mapError.put("error", "Unauthorized projects");
            return new JsonMapRepresentation(mapError);
        }
        int user = Integer.parseInt(userId);

        // Read the query parameters of the url
        String isEpic = getQuery().getValues("isEpic");
        Boolean epic;
        if (isEpic == null) {
            epic = false;
        }
        else {
            epic = Boolean.parseBoolean(isEpic);
        }
        // Convert it to boolean

        // Get projectId
        String projectId = getRequestAttributes().get("projectId").toString();
        int idProject = Integer.parseInt(projectId);
        // Get Epic_id
        String epicId = getQuery().getValues("epicId");
        System.out.println(epicId);
        int idEpic;
        if(epicId == null) {
            idEpic = 0;
        }
        else {
            idEpic = Integer.parseInt(epicId);
        }

        //int idEpic = Integer.parseInt(epicId);

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
                // Get all the pbis of this project which is Epics
                List<Epic> epics = dataAccess.getProjectEpics(idProject, epic, idEpic);
                map.put("results", epics);
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
}
