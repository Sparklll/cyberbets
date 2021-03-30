package by.epam.jwd.cyberbets.listener;

import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.utils.ResourceSaver;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    private static final String WEBAPP_APPLICATION_RESOURCES_PATH = "/resources/application";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        logger.info("Connection pool was successfully initialized");

        Path applicationResourcesSourcePath = Path.of(servletContext.getInitParameter("APP_RESOURCES_SOURCE_PATH"));
        Path webappApplicationResourcesPath = Path.of(servletContext.getRealPath(WEBAPP_APPLICATION_RESOURCES_PATH));
        Path webappRootPath = Path.of(servletContext.getRealPath("/"));
        ResourceSaver.init(webappRootPath.toString());

        if (Files.isSymbolicLink(webappApplicationResourcesPath)) {
            logger.info("Application resources are already linked to webapp resource path");
        } else {
            try {
                Files.createSymbolicLink(webappApplicationResourcesPath, applicationResourcesSourcePath);
                logger.info("Successfully create symbolic link to app resources source on startup");
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
