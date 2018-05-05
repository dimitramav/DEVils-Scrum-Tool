package ys09.conf;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

/**
 * The web application (context) listener.
 *
 * This is the app configuration entry-point.
 */
public class Listener implements ServletContextListener {

    /**
     * Invoked by the container when the context has been initialized.
     *
     * We place all our app configuration logic here. Specifically:
     * (a) we load the properties file indicated by the respective properties web.xml parameter.
     * We expect this file to be located at the classpath.
     * (b) we initialize (setup) the Configuration singleton that holds all configuration options.
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext ctx = servletContextEvent.getServletContext();

            final Properties props = new Properties();

            String pathToProperties = ctx.getInitParameter("properties");

            try (final InputStream inputStream = getClass().getResourceAsStream(pathToProperties)) {
                props.load(inputStream);
            }

            Configuration.getInstance().setup(ctx.getContextPath(), props);
        }
        catch(Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Invoked by the container when the context has been destroyed.
     * @param servletContextEvent
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //do nothing
    }

}
