package com.example.guildwarseventdemo.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.entity.Event;
import com.example.guildwarseventdemo.entity.EventEntity;

public class HttpRequestUtils {
    
    /**
     * Get the Json format from the specified url.
     * @param url the url of Guild Wars2 API.
     * @return the json string.
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String getJsonFromUrl(String url) throws ClientProtocolException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setHeader("Content-type","application/json");
        HttpGet httpGet = new HttpGet(url);
        
        InputStream inputStream = null;
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        
        inputStream = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        
        String  line = null;
        while((line=reader.readLine())!=null) {
            sb.append(line+"\n");
        }
        reader.close();
        inputStream.close();
        
        return sb.toString();
    }
    
    /**
     * Get the Event object by tranferring a event json.
     * @param resultJson
     * @return
     * @throws JSONException
     */
    public static ArrayList<Event> getEventFromJson(String resultJson) throws JSONException {
//        JSONObject jObject = new JSONObject(resultJson);
//        
//        int world_id = jObject.getInt(CommonConstant.TAG_WORDL_ID);
//        int map_id = jObject.getInt(CommonConstant.TAG_MAP_ID);
//        String event_id = jObject.getString(CommonConstant.TAG_EVENT_ID);
//        String state = jObject.getString(CommonConstant.TAG_STATE);
//        
//        return new Event(world_id,map_id,event_id,state);
        ArrayList<Event> result = new ArrayList<Event>();
        JSONArray jsonArray = (new JSONObject(resultJson)).getJSONArray(CommonConstant.TAG_ARRAY_EVENT);
        
        ArrayList<String> filterList = new ArrayList<String>();
        for(int i=0,size=CommonConstant.DRAG_EVENT.length;i<size;i++) {
            filterList.add(CommonConstant.DRAG_EVENT[i]);
        }
        
        for(int i=0,size=jsonArray.length();i<size;i++) {
            JSONObject jObject = jsonArray.optJSONObject(i);
            
            if(jObject==null) continue;
            
            
            String event_id = jObject.getString(CommonConstant.TAG_EVENT_ID);
            if(filterList.contains(event_id)) {
                int world_id = jObject.getInt(CommonConstant.TAG_WORDL_ID);
                int map_id = jObject.getInt(CommonConstant.TAG_MAP_ID);
                String state = jObject.getString(CommonConstant.TAG_STATE);
                result.add(new Event(world_id,map_id,event_id,state));
            }
        }
        return result;
    }
    
    /**
     * Get the event entity from by tranferring a XXX_names json.
     * @param resultJson
     * @param type
     * @return
     * @throws JSONException
     */
    public static EventEntity getEventEntityFromJson(String resultJson,int type) throws JSONException {
//        JSONObject jObject = new JSONObject(resultJson);
//        
//        int id = jObject.getInt(CommonConstant.TAG_ID);
//        String name = jObject.getString(CommonConstant.TAG_NAME);
//        
//        return new EventEntity(id,name,type);
        
//        ArrayList<EventEntity> result = new ArrayList<EventEntity>();
//        JSONArray jsonArray = new JSONObject(resultJson).getJSONArray(CommonConstant.TAG_ID);
        EventEntity result = new EventEntity(type);
        JSONArray jsonArray = new JSONArray(resultJson);
        
        for(int i=0,size=jsonArray.length();i<size;i++) {
            JSONObject jObject = jsonArray.optJSONObject(i);
            
            if(jObject==null) continue;
            
            Object id = null;
            if(type==1) id = jObject.getString(CommonConstant.TAG_ID);  
            else id = jObject.getInt(CommonConstant.TAG_ID);
            String name = jObject.getString(CommonConstant.TAG_NAME);
//            result.add(new EventEntity(id,name,type));
            result.put(id, name);
        }
        return result;
    }
    
}
