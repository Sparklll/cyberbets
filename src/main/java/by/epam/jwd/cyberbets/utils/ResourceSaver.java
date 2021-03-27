package by.epam.jwd.cyberbets.utils;

public final class ResourceSaver {
    private static final String LEAGUES_PATH = "/resources/application/leagues";
    private static final String TEAMS_PATH = "/resources/application/teams";
    private static final String AVATARS_PATH = "/resources/application/avatars";

    private ResourceSaver() {
    }

    public enum Resource {
        LEAGUE_LOGO(LEAGUES_PATH),
        TEAM_LOGO(TEAMS_PATH),
        ACCOUNT_AVATAR(AVATARS_PATH);

        private final String path;

        Resource(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static void uploadImage(Resource resource) {
        String resourcePath = resource.getPath();

    }
}
