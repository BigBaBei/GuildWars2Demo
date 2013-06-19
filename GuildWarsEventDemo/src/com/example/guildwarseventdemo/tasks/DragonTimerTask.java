package com.example.guildwarseventdemo.tasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.entity.Event;
import com.example.guildwarseventdemo.entity.EventsResult;
import com.example.guildwarseventdemo.interfaces.OnTaskListener;
import com.example.guildwarseventdemo.setting.GlobalSettings;
import com.example.guildwarseventdemo.utility.HttpRequestUtils;

import android.os.AsyncTask;

/**
 * The task downloads the dragon timer event.
 * @author peng
 *
 */
public class DragonTimerTask extends AsyncTask<String, Void, ArrayList<EventsResult>> {

    
    @Override
    protected ArrayList<EventsResult> doInBackground(String... params) {
        ArrayList<Event> eventList = new ArrayList<Event>();
        try {
            for(int i=0;i<params.length;i++) {
                String jsonResult = HttpRequestUtils.getJsonFromUrl(params[i]);
                eventList.addAll(HttpRequestUtils.getEventFromJson(jsonResult));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventTranfserToEventResult(eventList);
    }
    
    
    
    @Override
    protected void onPostExecute(ArrayList<EventsResult> result) {
        super.onPostExecute(result);
    }

    /**
     * Transfer the event list to event result list.
     * @param targetList The event list.
     * @return The event result list.
     */
    private ArrayList<EventsResult> eventTranfserToEventResult(ArrayList<Event> targetList) {
        ArrayList<EventsResult> resultList = new ArrayList<EventsResult>();
        for(Event event: targetList) {
            int indexE = 0;
            int indexM = 0;
            
            int[] mapIds = CommonConstant.MAP_ID;
            for(int i=0;i<mapIds.length;i++) {
                if(event.getMapId()==mapIds[i]) {
                    indexM = i;
                    break;
                }
            }
            
            String[] eventIds = CommonConstant.DRAG_EVENT; 
            for(int i=0;i<eventIds.length;i++) {
                if(event.getEventId().equals(eventIds[i])) {
                    indexE = i;
                    break;
                }
            }
            
            EventsResult result = new EventsResult(CommonConstant.MAP_NAMES[indexM], CommonConstant.DRAGON_EVENT_NAME[indexE], event.getState());
            resultList.add(result);
        }
        return resultList;
    }
}
