/**
 * Created by Trigger on 2015/6/24.
 */
package org.mzalive.continuelibrary.communication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BaseFunctions {
    public static String httpConnection(ArrayList<String> keys_name, ArrayList<String> keys_value, String action){
        String      result = "";
        String value_pairs = BaseFunctions.encode_params(keys_name, keys_value, action);
        Log.d("params", value_pairs);

        URL url;
        try {
//            url = new URL(GlobalSettings.REQUEST_URL);
            url = new URL(GlobalSettings.REQUEST_URL_TEST);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(3000);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(value_pairs);
            out.flush();
            out.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader buffer = new BufferedReader(in);
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    result += inputLine + "\n";
                }
                in.close();
            }
            else{
                result = "{\"error_code\":\"9999\"}";
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("MalformedException", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("IOException", e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            Log.d("exception", e.getMessage());
        }
        Log.d("Result", result);
        return result;
    }

    private static String encode_params(ArrayList<String> keys_name, ArrayList<String> keys_value, String action){
        String result = "";
        String value = "";
        try{
            result = "action" + "=" + URLEncoder.encode(action,"utf-8");
            for(int i = 0 ; i < keys_name.size(); i++){
                result = result +"&" + keys_name.get(i) + "=" + URLEncoder.encode(keys_value.get(i),"utf-8");
            }
        }catch (UnsupportedEncodingException e){
            Log.d("encode params error", e.getMessage());
        }
        return result;
    }
}
