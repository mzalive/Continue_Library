package org.mzalive.continuelibrary.communication;

/**
 * Created by Trigger on 2015/6/26.
 */
public class GlobalSettings {
    public final static String REQUEST_URL = "http://continuelibrary.mzalive.org/server/index.php";

    public final  static String ACTION_LOGIN = "login";
    public final static String ACTION_GETMYBORROWLIST = "getMyBorrowlist";
    public final static String ACTION_GETMYWISHLIST = "getMyWishlist";
    public final static String ACTION_SETPASSWORD = "setPassword";
    public final static String ACTION_BORROWBOOK = "borrowBook";
    public final static String ACTION_RETURNBOOK = "returnBook";
    public final static String ACTION_GETBOOKLIST = "getBooklist";
    public final static String ACTION_GETBOOKCOUNT = "getBookCount";
    public final static String ACTION_ADDHEAT = "addHeat";
    public final static String ACTION_GETWISHLIST = "getWishlist";
    public final static String ACTION_SEARCH = "search";

    public final static int UNKNOWN_ERROR = 999;
    public final static int RESULT_OK = 1000;
    public final static int NO_CONTENT = 2000;
    public final static int AUTHORIZATION_ERROR = 2001;
    public final static int BOOK_ALL_BORROWED = 2002;
    public final static int BOOK_RETURNED = 2003;
    public final static int DATABASE_OPERATION_ERROR = 4001;
    public final static int ALREADY_ADDED = 5001;
    public final static int NETWORK_ERROR = 9999;
}
