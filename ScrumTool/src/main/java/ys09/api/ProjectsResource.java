package ys09.api;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import ys09.conf.Configuration;
import ys09.data.DataAccess;
import ys09.data.Limits;
import ys09.model.Project;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.restlet.Message;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.util.Series;


public class ProjectsResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        List<Project> projects = dataAccess.getProjects();
        // New map string (which is the json name) and objects
        Map<String, Object> map = new HashMap<>();
        // Access the headers of the request !
        Series requestHeaders = (Series)getRequest().getAttributes().get("org.restlet.http.headers");
        String token = requestHeaders.getFirstValue("Authorization");
        System.out.println(token);
        // TO DO:  Check if the token is ok !

        //map.put("start", xxx);
        //map.put("count", xxx);
        //map.put("total", xxx);
        // all the projects with a string
        map.put("results", projects);
        // Set the response headers
        return new JsonMapRepresentation(map);
    }




    // Post Representation
    /*@Override
    protected Representation post(Representation entity) throws ResourceException {

        //Foo targetObject = new Gson().fromJson(entity, Project.class);
        System.out.println(entity);
        try{
            String str = entity.getText();
            System.out.println(str);
            Map<String, Object> map = new HashMap<>();
            map.put("results", entity);
            // Now Create from String the JAVA object
            Gson gson = new Gson();
            Project project = gson.fromJson(str, Project.class);
            dataAccess.insertProject(project);
            // Insert the Project to the database

            Map<String, Object> map1 = new HashMap<>();
            map1.put("results", project);
            return new JsonMapRepresentation(map1);
        }

        catch(IOException e) {

        }
        return null;

    }*/
}
