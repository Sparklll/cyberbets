package by.epam.jwd.cyberbets.controller;

public final class Parameters {
    private Parameters() {
    }

    // GLOBAL
    public static final String ACTION = "action";
    public static final String GUEST_ROLE = "guest";
    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";

    public static final String CONNECTION_PROPERTIES = "connection.db";
    public static final String JSON_MAP = "jsonMap";
    public static final String JSON_UTF8_CONTENT_TYPE = "application/json; charset=UTF-8";
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String UTF8_CHARSET = "UTF-8";


    // REQUEST PARAMS
    public static final String NEW_AVATAR_PARAM = "newAvatar";
    public static final String CURRENT_PASSWORD_PARAM = "currentPassword";
    public static final String NEW_PASSWORD_PARAM = "newPassword";
    public static final String REPEATED_NEW_PASSWORD_PARAM = "repeatedNewPassword";
    public static final String UPSHOT_ID_PARAM = "upshotId";
    public static final String EVENT_RESULT_ID_PARAM = "eventResultId";
    public static final String AMOUNT_PARAM = "amount";
    public static final String EVENT_RESULTS_PARAM = "eventResults";
    public static final String LEAGUE_ID_PARAM = "leagueId";
    public static final String FIRST_TEAM_ID_PARAM = "firstTeamId";
    public static final String SECOND_TEAM_ID_PARAM = "secondTeamId";
    public static final String FORMAT_ID_PARAM = "formatId";
    public static final String ROYALTY_PERCENTAGE_PARAM = "royaltyPercentage";
    public static final String START_DATE_PARAM = "startDate";
    public static final String EVENT_FORMAT_PARAM = "eventFormat";
    public static final String EVENT_PARAM = "event";
    public static final String DISCIPLINE_PARAM = "discipline";
    public static final String LEAGUE_ICON_PARAM = "leagueIcon";
    public static final String LEAGUE_NAME_PARAM = "leagueName";
    public static final String TEAM_RATING_PARAM = "teamRating";
    public static final String TEAM_LOGO_PARAM = "teamLogo";
    public static final String TEAM_NAME_PARAM = "teamName";
    public static final String PATH_PARAM = "path";
    public static final String ID_PARAM = "id";
    public static final String EMAIL_PARAM = "email";
    public static final String PASSWORD_PARAM = "password";
    public static final String REPEATED_PASSWORD_PARAM = "repeatedPassword";

    // RESPONSE PARAMS / PROPERTIES
    public static final String BALANCE_PARAM = "balance";
    public static final String STATUS_PARAM = "status";
    public static final String STATUS_OK = "ok";
    public static final String STATUS_DENY = "deny";
    public static final String STATUS_EXCEPTION = "exception";
    public static final String DATA_PROPERTY = "data";



    // REQUEST & SESSION ATTRIBUTES
    public static final String BETS_DATA_ATTR = "betsData";
    public static final String ACCOUNTS_NUMBER_ATTR = "accountsNumber";
    public static final String MONTH_REGISTRATIONS_ATTR = "monthRegistrations";
    public static final String ACTIVE_EVENTS_NUMBER_ATTR = "activeEventsNumber";
    public static final String MONTH_DEPOSIT_TRANSACTIONS_AMOUNT_ATTR = "monthDepositTransactionsAmount";
    public static final String MONTH_PROFIT_ATTR = "monthProfit";
    public static final String ACTIVE_EVENTS_BETS_AMOUNT_ATTR = "activeEventsBetsAmount";
    public static final String ACCOUNT_TRANSACTIONS_ATTR = "accountTransactions";
    public static final String EVENT_COEFFICIENTS_ATTR = "eventCoefficients";
    public static final String EVENT_RESULTS_ATTR = "eventResults";
    public static final String EVENT_BETS_ATTR = "eventBets";
    public static final String LOCALE_ATTR = "locale";
    public static final String LIVE_EVENTS_ATTR = "liveEvents";
    public static final String UPCOMING_EVENTS_ATTR = "upcomingEvents";
    public static final String PAST_EVENTS_ATTR = "pastEvents";
    public static final String ACCOUNT_ATTR = "account";
    public static final String ACCOUNT_ID_ATTR = "accountId";
    public static final String ACCOUNT_EMAIL_ATTR = "accountEmail";
    public static final String ACCOUNT_AVATAR_PATH_ATTR = "accountAvatarPath";
    public static final String AUTH_ATTR = "auth";
    public static final String ROLE_ATTR = "role";
    public static final String BALANCE_ATTR = "balance";

    // COOKIES
    public static final String LANG_COOKIE = "lang";
    public static final String DISCIPLINE_FILTER_COOKIE = "discipline_filter";

    // DISCIPLINE FILTER
    public static final String CSGO_DISC = "csgo";
    public static final String DOTA2_DISC = "dota2";
    public static final String LOL_DISC = "lol";
    public static final String VALORANT_DISC = "valorant";

    // ACTION NAMES
    public static final String UPDATE_ACCOUNT_ACTION = "updateAccount";
    public static final String DEPOSIT_ACTION = "deposit";
    public static final String WITHDRAW_ACTION = "withdraw";
    public static final String PLACE_BET_ACTION = "placeBet";
    public static final String UPDATE_BET_ACTION = "updateBet";
    public static final String REFUND_BET_ACTION = "refundBet";
    public static final String LOAD_BET_MODAL_ACTION = "loadBetModal";
    public static final String LOAD_EVENT_COEFFICIENTS_ACTION = "loadEventCoefficients";
    public static final String LOAD_COEFFICIENTS_ACTION = "loadCoefficients";
    public static final String LOAD_EVENT_RESULTS_ACTION = "loadEventResults";
    public static final String LOAD_EVENT_SECTION_ACTION = "loadEventSection";
    public static final String LOAD_EVENT_ACTION = "loadEvent";
    public static final String INSERT_EVENT_ACTION = "insertEvent";
    public static final String UPDATE_EVENT_ACTION = "updateEvent";
    public static final String DELETE_EVENT_ACTION = "deleteEvent";
    public static final String LOAD_LEAGUE_ACTION = "loadLeague";
    public static final String INSERT_LEAGUE_ACTION = "insertLeague";
    public static final String UPDATE_LEAGUE_ACTION = "updateLeague";
    public static final String DELETE_LEAGUE_ACTION = "deleteLeague";
    public static final String LOAD_TEAM_ACTION = "loadTeam";
    public static final String INSERT_TEAM_ACTION = "insertTeam";
    public static final String UPDATE_TEAM_ACTION = "updateTeam";
    public static final String DELETE_TEAM_ACTION = "deleteTeam";
    public static final String REGISTER_ACTION = "register";
    public static final String LOGIN_ACTION = "login";
    public static final String LOGOUT_ACTION = "logout";
    public static final String IGNORE_ACTION = "ignore";
    public static final String FORWARD_ERROR_PAGE_ACTION = "forwardErrorPage";

    // PAGES / SECTIONS
    public static final String BET_MODAL = "/WEB-INF/view/xml/bet-modal.jsp";
    public static final String EVENT_SECTION = "/WEB-INF/view/xml/events.jsp";
    public static final String ERROR_PAGE = "/WEB-INF/view/error404.jsp";
    public static final String HOME_PAGE = "/WEB-INF/view/home.jsp";
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
    public static final String ADMIN_DASHBOARD_SECTION = "/WEB-INF/view/admin/dashboard.jsp";
    public static final String ADMIN_EVENTS_SECTION = "/WEB-INF/view/admin/events.jsp";
    public static final String ADMIN_LEAGUES_SECTION = "/WEB-INF/view/admin/leagues.jsp";
    public static final String ADMIN_TEAMS_SECTION = "/WEB-INF/view/admin/teams.jsp";
    public static final String ADMIN_NEWS_SECTION = "/WEB-INF/view/admin/news.jsp";
    public static final String ADMIN_ACCOUNTS_SECTION = "/WEB-INF/view/admin/accounts.jsp";
    public static final String ADMIN_TRANSACTIONS_SECTION = "/WEB-INF/view/admin/transactions.jsp";
    public static final String ADMIN_SUPPORT_SECTION = "/WEB-INF/view/admin/support.jsp";
    public static final String ADMIN_LOGS_SECTION = "/WEB-INF/view/admin/logs.jsp";


    // ROUTES
    public static final String ACTION_LISTENER_ROUTE = "/action/";
    public static final String HOME_ROUTE = "/";
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

    public static final String ADMIN_DASHBOARD_ROUTE = "/admin/";
    public static final String ADMIN_EVENTS_ROUTE = "/admin/events/";
    public static final String ADMIN_LEAGUES_ROUTE = "/admin/leagues/";
    public static final String ADMIN_TEAMS_ROUTE = "/admin/teams/";
    public static final String ADMIN_NEWS_ROUTE = "/admin/news/";
    public static final String ADMIN_ACCOUNTS_ROUTE = "/admin/accounts/";
    public static final String ADMIN_TRANSACTIONS_ROUTE = "/admin/transactions/";
    public static final String ADMIN_SUPPORT_ROUTE = "/admin/support/";
    public static final String ADMIN_LOGS_ROUTE = "/admin/logs/";
}
