package by.epam.jwd.cyberbets.controller;

public final class Parameters {
    private Parameters() {
    }

    // GLOBAL
    public static final String ACTION = "action";
    public static final String JSON_MAP = "jsonMap";
    public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    public static final String CONNECTION_PROPERTIES = "connection.db";

    // REQUEST PARAMS
    public static final String EMAIL_PARAM = "email";
    public static final String PASSWORD_PARAM = "password";
    public static final String REPEATED_PASSWORD_PARAM = "repeatedPassword";

    // ACTION NAMES
    public static final String REGISTER_ACTION = "register";
    public static final String IGNORE_ACTION = "ignore";
    public static final String FORWARD_ERROR_PAGE_ACTION = "forwardErrorPage";

    // PAGES
    public static final String ERROR_PAGE = "/WEB-INF/view/error404.jsp";
    public static final String ROOT_PAGE = "/WEB-INF/view/home.jsp";
    public static final String NEWS_PAGE = "/WEB-INF/view/news.jsp";
    public static final String TEAM_RATING_PAGE = "/WEB-INF/view/team-rating.jsp";
    public static final String SUPPORT_PAGE = "/WEB-INF/view/support.jsp";
    public static final String CONTACTS_PAGE = "/WEB-INF/view/contacts.jsp";
    public static final String PRIVACY_POLICY_PAGE = "/WEB-INF/view/privacy-policy.jsp";
    public static final String DEPOSIT_PAGE = "/WEB-INF/view/user/deposit.jsp";
    public static final String MYBETS_PAGE = "/WEB-INF/view/user/mybets.jsp";
    public static final String ACCOUNT_SETTINGS_PAGE = "/WEB-INF/view/user/settings.jsp";
    public static final String TRANSACTIONS_PAGE = "/WEB-INF/view/user/transactions.jsp";
    public static final String WITHDRAWAL_PAGE = "/WEB-INF/view/user/withdrawal.jsp";
    public static final String ADMIN_PANEL_PAGE = "/WEB-INF/view/admin/admin.jsp";

    // ROUTES
    public static final String ACTION_LISTENER_ROUTE = "/action/";
    public static final String ROOT_ROUTE = "/";
    public static final String NEWS_ROUTE = "/news/";
    public static final String TEAM_RATING_ROUTE = "/rating/";
    public static final String SUPPORT_ROUTE = "/support/";
    public static final String CONTACTS_ROUTE = "/contacts/";
    public static final String PRIVACY_POLICY_ROUTE = "/privacy-policy/";
    public static final String DEPOSIT_ROUTE = "/deposit/";
    public static final String MYBETS_ROUTE = "/mybets/";
    public static final String ACCOUNT_SETTINGS_ROUTE = "/settings/";
    public static final String TRANSACTIONS_ROUTE = "/transactions/";
    public static final String WITHDRAWAL_ROUTE = "/withdrawal/";
    public static final String ADMIN_PANEL_ROUTE = "/admin/";
}
