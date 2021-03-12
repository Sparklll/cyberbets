package by.epam.jwd.cyberbets.controller.route;

public enum RouteEnum {


    private String pageRoute;

    RouteEnum(String pageRoute) {
        this.pageRoute = pageRoute;
    }

    public String getPageRoute() {
        return pageRoute;
    }
}
