package ys09.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


/**
 * The Restlet App, mapping URL patterns to ServerSideResources.
 */
public class RestfulApp extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {

		Router router = new Router(getContext());

		//GET
		router.attach("/config", ConfigResource.class);

		//GET
		// Map the URI to a java endpoint
		router.attach("/projects", ProjectsResource.class);

		// POST
		// Get Unauthorized, access only to admin
		router.attach("/users", UsersResource.class);

		return router;
	}

}
