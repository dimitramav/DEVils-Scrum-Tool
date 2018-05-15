package ys09.api;

import ys09.auth.CustomAuth;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.Limits;
import ys09.model.Project;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Post;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.BufferingRepresentation;
import org.restlet.data.ClientInfo;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;
import org.json.JSONObject;

import java.io.Reader;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentMap;
import org.restlet.Message;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.util.Series;

import static org.restlet.data.Status.CLIENT_ERROR_UNAUTHORIZED;


public class ProjectsResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        Map<String, String> mapError = new HashMap<>();

        // Get UserId
        String userId = getRequestAttributes().get("userId").toString();
        int user = Integer.parseInt(userId);

        // Read the values of url (limit and nearestDeadline) for pagination
        String strcount = getQuery().getValues("limit");
        String strstart = getQuery().getValues("nearestDeadline");
        DateFormat format = new SimpleDateFormat("yyyy-M-d");
        Date expDate = new Date(Long.MAX_VALUE);
        try { expDate = format.parse(strstart); }
        catch (Exception e) { System.out.println("No date"); }
        System.out.println(expDate);
        System.out.println(strcount);
        Limits limit = new Limits(Integer.parseInt(strcount), expDate);

        // Access the headers of the request !
        /*Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("auth");

        if (token == null) {
            mapError.put("error", "Client Error Unauthorized");
            return new JsonMapRepresentation(mapError);
        }

        CustomAuth customAuth = new CustomAuth();

        if(customAuth.checkAuthToken(token)) {
            // Get Projects only for the current user
            // Show them in the index page
            List<Project> projects = dataAccess.getUserProjects(2);
            map.put("results", projects);
            // Set the response headers
            return new JsonMapRepresentation(map);
        }
        else {
            mapError.put("error", "Client Error Unauthorized");
            return new JsonMapRepresentation(mapError);
        }

        //map.put("start", xxx);
        //map.put("count", xxx);
        //map.put("total", xxx);
        // all the projects with a string

        // Set the response headers
        //return new JsonMapRepresentation(map);*/
        List<Project> projects = dataAccess.getUserProjects(user, limit);
        map.put("results", projects);
        return new JsonMapRepresentation(map);
    }
}


/*

    // Post Representation
    @Override
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
            dataAccess.insertProject(project);

            Map<String, Object> mapError = new HashMap<>();
            mapError.put("results", project);
            return new JsonMapRepresentation(mapError);
        }

        catch(IOException e) {
            Map<String, String> mapError = new HashMap<>();
            mapError.put("result", "System Exception");
            return new JsonMapRepresentation(mapError);
        }
    }
}
*/
