package by.epam.jwd.cyberbets.controller;

public final class Parameters {
    private Parameters() {
    }

    // GLOBAL
    public static final String ACTION = "action";


    // ACTION NAMES
    public static final String IGNORE = "ignore";
    public static final String FORWARD_ERROR_PAGE = "forwardErrorPage";

    // PAGES

    public static final String ROOT_PAGE = "/WEB-INF/view/home.jsp";
    public static final String NEWS_PAGE = "/WEB-INF/view/news.jsp";
    public static final String TEAM_RATING_PAGE = "/WEB-INF/view/team-rating.jsp";
    public static final String SUPPORT_PAGE = "/WEB-INF/view/support.jsp";
    public static final String CONTACTS_PAGE = "/WEB-INF/view/contacts.jsp";
    public static final String PRIVACY_POLICY_PAGE = "/WEB-INF/view/privacy-policy.jsp";
    public static final String ERROR_PAGE = "/WEB-INF/view/error404.jsp";

    // ROUTES
    public static final String ROOT_ROUTE = "/";
    public static final String NEWS_ROUTE = "/news/";
    public static final String TEAM_RATING_ROUTE = "/rating/";
    public static final String SUPPORT_ROUTE = "/support/";
    public static final String CONTACTS_ROUTE = "/contacts/";
    public static final String PRIVACY_POLICY_ROUTE = "/privacy-policy/";


    // LOCALE
    public static final String LOCALE_LANG_DE = "de";
    public static final String LOCALE_LANG_EN = "en";
    public static final String LOCALE_LANG_FR = "fr";
    public static final String LOCALE_LANG_RU = "ru";

    // COOKIES
}
