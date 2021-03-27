package by.epam.jwd.cyberbets.listener;

import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        logger.info("Connection pool was successfully initialized");

        Path applicationResourcesSourcePath = Path.of(servletContext.getInitParameter("APP_RESOURCES_SOURCE_PATH"));
        Path webappResourcesPath = Path.of(servletContext.getRealPath("/resources/application"));

        if(Files.isSymbolicLink(webappResourcesPath)) {
           logger.info("Application resources are already linked to webapp resource path");
        } else {
            try {
                Files.createSymbolicLink(webappResourcesPath, applicationResourcesSourcePath);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("Unable to link application resources");
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.INSTANCE.destroyPool();
        logger.info("Connection pool was successfully destroyed");
    }
}
