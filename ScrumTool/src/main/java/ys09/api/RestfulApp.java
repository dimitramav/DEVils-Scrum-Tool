package ys09.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.engine.application.CorsFilter;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import java.util.Arrays;
import java.util.HashSet;


/**
 * The Restlet App, mapping URL patterns to ServerSideResources.
 */
public class RestfulApp extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {

		// Create a Restlet router that defines routes
		final Router router = new Router(getContext());

		// Add a CORS filter to allow cross-domain requests
		CorsFilter corsFilter = new CorsFilter(getContext(), router);
		corsFilter.setAllowedOrigins(new HashSet<String>(Arrays.asList("*")));
		corsFilter.setAllowedHeaders(new HashSet<String>(Arrays.asList("*")));
		corsFilter.setAllowedCredentials(true);
		// Setup up resource routing
		// ...

		  // Important!

		//GET
		router.attach("/config", ConfigResource.class);

		//GET
		// Map the URI to a java endpoint
		router.attach("/projects", ProjectsResource.class);

		// Sprints

		// POST
		// Get Unauthorized, access only to admin
		router.attach("/users", UsersResource.class);
		// POST
		router.attach("/signin", SignInResource.class);
		// POST
		router.attach("/exists", UserExistsResource.class);

		return corsFilter;
	}

}
