package by.epam.jwd.cyberbets.listener;

import by.epam.jwd.cyberbets.dao.connection.ConnectionPool;
import by.epam.jwd.cyberbets.service.job.LoadCoefficientsJob;
import by.epam.jwd.cyberbets.service.job.LoadEventJob;
import by.epam.jwd.cyberbets.service.job.UpdateEventStatusJob;
import by.epam.jwd.cyberbets.utils.ResourceManager;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    private static final String WEBAPP_APPLICATION_RESOURCES_PATH = "/resources/application";

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        ConnectionPool connectionPool = ConnectionPool.INSTANCE;
        logger.info("Connection pool was successfully initialized");

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new LoadEventJob(), 0, 30, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new LoadCoefficientsJob(),0, 5, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new UpdateEventStatusJob(),0, 1, TimeUnit.MINUTES);


        Path applicationResourcesSourcePath = Path.of(servletContext.getInitParameter("APP_RESOURCES_SOURCE_PATH"));
        Path webappApplicationResourcesPath = Path.of(servletContext.getRealPath(WEBAPP_APPLICATION_RESOURCES_PATH));
        Path webappRootPath = Path.of(servletContext.getRealPath("/"));
        ResourceManager.init(webappRootPath.toString());

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
        ServletContext servletContext = servletContextEvent.getServletContext();

        scheduler.shutdown();
        ConnectionPool.INSTANCE.destroyPool();
        logger.info("Connection pool was successfully destroyed");

        Path link = Paths.get(servletContext.getRealPath("/"),
                WEBAPP_APPLICATION_RESOURCES_PATH);
        try {
            Files.deleteIfExists(link);
            logger.info("Successfully remove symbolic link to app resources source on shutdown");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
