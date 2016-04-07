package ru.akisterev.theviptatu;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Алексей on 31.03.2016.
 * Делает rest запросы на сервис yclient
 *
 */
public class YclientConnect {
    private final int POST_METOD = 1;
    private final int GET_METOD = 2;
    public static final String REQUEST_SERVICE = "book_services";
    public static final String REQUEST_RECORD = "book_record";
    public static final String REQUEST_STAFF = "book_staff";
    public static final String REQUEST_DATES = "book_dates";

    String urlStr;
    String defUrlStr = "";
    String getUrlParamStr = "";
    HttpURLConnection conn = null;
    URL url;
    String numCompany = "";
    String request = "";
    JSONObject postParamObj;
    String authorization = "";

    public YclientConnect(){
        this("http://private-anon-ae843f62b-yclients.apiary-proxy.com/api/v1");
    }
    public YclientConnect (String url){
        this.defUrlStr = url;
    }
    public YclientConnect setRequest (String request){
        this.request = request;
        return this;
    }
    public YclientConnect setNumCompany (String numCompany){
        this.numCompany = numCompany;
        return this;
    }
    public YclientConnect setAuthorization(String authorization){
        this.authorization = authorization;
        return this;
    }
    public JSONObject get(Map<String, String> params){
        boolean first = true;

        getUrlParamStr += "?";
        for(Map.Entry<String, String> par : params.entrySet()){
            if(first){
                first = false;
            }else{
                getUrlParamStr += "&";
            }
            getUrlParamStr += par.getKey()+"="+par.getValue();
            try {
                getUrlParamStr = URLEncoder.encode(getUrlParamStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return getResponse(GET_METOD);
    }
    public JSONObject get(){
        getUrlParamStr = "";
        return getResponse(GET_METOD);
    }
    public JSONObject post(JSONObject paramObj){
        this.postParamObj = paramObj;
        return getResponse(POST_METOD);
    }
    private JSONObject getResponse(int metod){
        setUrl();

        try {
            conn = (HttpURLConnection) url.openConnection();
            if(metod == POST_METOD){
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
            }else {
                conn.setRequestMethod("GET");
            }
            conn.setRequestProperty("Content-Type", "application/json;");
            if(!authorization.equals("")) {
                conn.setRequestProperty("Authorization", authorization);
            }
            conn.connect();

            //Отправим данные методом пост
            if (metod == POST_METOD){
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(postParamObj.toString());
                out.flush();
                out.close();
            }

            int codeResult = conn.getResponseCode();
            System.out.println(codeResult);

            InputStream in;
            if(codeResult == HttpURLConnection.HTTP_OK){
                in = conn.getInputStream();
            }else{
                in = conn.getErrorStream();
            }

            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            StringBuffer sb = new StringBuffer();
            String line;

            while ((line = br.readLine()) != null){
                sb.append(line);
            }

            br.close();

            String response = sb.toString();
            JSONObject respObj = new JSONObject();
            try {
                respObj.put("code", codeResult);
                respObj.put("body", response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respObj;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void setUrl() {
        if (!request.equals("")){
            request = "/"+request;
        }
        if (!numCompany.equals("")){
            numCompany = "/"+numCompany;
        }
        urlStr = defUrlStr+request+numCompany;

        try {
            url = new URL(urlStr+getUrlParamStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
