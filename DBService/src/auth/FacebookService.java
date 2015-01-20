package auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.h2.store.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author roberto
 */
public class FacebookService {

    public static final String REQ_TYPE = "GET";
    public static final String BASE_URL = "https://graph.facebook.com/";
    public static final String PARAM = "";

    public static final String FB_API_VERSION = "v2.2";
    public static final String FB_QUERY_INFO = "/me?fields=id,first_name&access_token=";
    public static final String FB_QUERY_PHOTO = "/me/picture?type=normal&height=120&width=120&redirect=false&fields=url&access_token=";

    public FacebookService() { }

    public JSONObject getInfo() throws MalformedURLException, JSONException, IOException {
        String token = "";
        JSONObject facebook_result = new JSONObject();
        facebook_result.put("info", getInfoByToken(token));
        facebook_result.put("image", getProfileImageByToken(token));
        return facebook_result;
    }

    public JSONObject getInfoByToken(String token) throws MalformedURLException, IOException, JSONException {

        String url = BASE_URL + FB_API_VERSION + FB_QUERY_INFO + token;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(REQ_TYPE);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject o = new JSONObject(response.toString());
//        String id = o.getString("id");
//        String name = o.getString("first_name");
//
        return o;
    }

    public JSONObject getProfileImageByToken(String token) throws MalformedURLException, IOException, JSONException {

        String url = BASE_URL + FB_API_VERSION + FB_QUERY_PHOTO + token;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(REQ_TYPE);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jo = new JSONObject(response.toString());
        JSONObject data = jo.getJSONObject("data");
//        return data.getString("url");

        JSONObject o = new JSONObject();
        o.put("image_url", data.getString("url"));
        return o;
    }

}