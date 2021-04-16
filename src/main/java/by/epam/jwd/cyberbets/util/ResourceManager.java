package by.epam.jwd.cyberbets.utils;

import by.epam.jwd.cyberbets.domain.Resource;
import by.epam.jwd.cyberbets.utils.exception.UtilException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public final class ResourceManager {
    private static final String LEAGUES_PATH = "/resources/application/leagues";
    private static final String TEAMS_PATH = "/resources/application/teams";
    private static final String AVATARS_PATH = "/resources/application/avatars";
    private static String WEBAPP_ROOT_PATH;

    private ResourceManager() {
    }

    public static void init(String webappRootPath){
        WEBAPP_ROOT_PATH = webappRootPath;
    }

    public enum ResourceType {
        LEAGUE_ICON(LEAGUES_PATH),
        TEAM_LOGO(TEAMS_PATH),
        ACCOUNT_AVATAR(AVATARS_PATH);

        private final String path;

        ResourceType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static String uploadImage(ResourceType resourceType, String dataUrl) throws UtilException {
        String resourcePath = resourceType.getPath();

        byte[] decodedImage = decodeBase64(dataUrl);
        String extension = getDataUrlExtension(dataUrl);

        String imageFileName = UUID.randomUUID() + extension;
        Path destination = Paths.get(WEBAPP_ROOT_PATH, resourcePath, imageFileName);
        try {
            Files.write(destination, decodedImage);
            return Paths.get(resourcePath, imageFileName).toString();
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    public static void updateImage(ResourceType resourceType, Resource imageResource, String dataUrl) throws UtilException {
        String oldImageResourcePath = imageResource.getPath();
        removeResource(oldImageResourcePath);
        String newImageResourcePath = ResourceManager.uploadImage(resourceType, dataUrl);
        imageResource.setPath(newImageResourcePath);
    }

    public static void removeResource(String resourcePath) throws UtilException {
        Path target = Paths.get(WEBAPP_ROOT_PATH, resourcePath);
        try {
            Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

    private static byte[] decodeBase64(String dataUrl) {
        String delimiter = ",";
        String[] dataUrlParts = dataUrl.split(delimiter);
        String data = dataUrlParts[1];
        return Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
    }

    private static String getDataUrlExtension(String dataUrl) {
        return "." + dataUrl.substring(dataUrl.indexOf("/") + 1, dataUrl.indexOf(";base64"));
    }
}
