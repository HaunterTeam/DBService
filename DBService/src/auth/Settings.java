package auth;

public final class Settings {
	
	// Common Settings
	public static final String REQ_TYPE = "GET";
	public static final String BASE_PROTOCOL = "http://";
	
	public static final String BASE_PORT = "8088";
	public static final String BASE_URL = "localhost";
	public static String BASE_PATH = "/dbservice/ws/people";
	
	
	
	// Facebook Service
	public static final String LOCAL_FB_BASE_URL = "localhost:";
	public static final String LOCAL_FB_BASE_PORT = "8020";
	public static final String LOCAL_FB_BASE_PATH = "/project-facebook/auth?token=";

	public static final String FB_BASE_URL = "restinfacebookservice.herokuapp.com";
	public static final String FB_BASE_PORT = "";
	public static final String FB_BASE_PATH = "/project-facebook/auth?token=";
	
    public static final String FB_JSON_OUT_ID_ATTR = "id";
    public static final String FB_JSON_OUT_NAME_ATTR = "first_name";
    public static final String FB_JSON_OUT_LOCATION_ATTR = "location";
    public static final String FB_JSON_OUT_STATUS_OBJ = "status";
    public static final String FB_JSON_OUT_STATUS_CODE_ATTR = "code";
    public static final String FB_JSON_OUT_STATUS_MESSAGE_ATTR = "message";
    public static final String FB_JSON_OUT_RESULT_OBJ = "result";
    
    public final static int FB_OK_REQ = 200;
    public final static int FB_ERR_REQ = 1;
    public final static String FB_OK_MESSAGE = "Valid Request";
    public final static String FB_ERR_MESSAGE = "Invalid OAuth access token";
}