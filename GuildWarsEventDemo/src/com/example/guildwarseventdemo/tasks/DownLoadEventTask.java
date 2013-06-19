package com.example.guildwarseventdemo.tasks;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.example.guildwarseventdemo.entity.Event;
import com.example.guildwarseventdemo.entity.EventEntity;
import com.example.guildwarseventdemo.entity.EventsResult;
import com.example.guildwarseventdemo.interfaces.OnTaskListener;
import com.example.guildwarseventdemo.utility.HttpRequestUtils;

import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

public class DownLoadEventTask extends AsyncTask<String, Void, Object> {
    
    /*
     * The event json's type.
     *     -1:  All the json.
     *      0:  Event;
     *      1:  Event name;
     *      2:  Map name;
     *      3:  World name;
     */
    private int mType;
    
    private OnTaskListener m_onTaskListener = null;
    
    private int mWorldId = 1008;
    
    
    public DownLoadEventTask(int type,OnTaskListener listener) {
        mType = type;
        m_onTaskListener = listener;
    }
    
    public void setJsonType(int type) {
        this.mType = type;
    }
    
    
    @Override
    protected Object doInBackground(String... params) {
        /**
         *      0:  Event;
         *      1:  Event name;
         *      2:  Map name;
         *      3:  World name;
         */
        SparseArray<Object> eventresultArray = new SparseArray<Object>();
        
        if(mType==-1){
            try {
                for(int i=0;i<params.length;i++) {
                    String jsonReuslt = HttpRequestUtils.getJsonFromUrl(params[i]);
                    //When length <4,it means params contain 1-4 not event,but event entity.
                    if(params.length==4&&i==0) eventresultArray.put(i, HttpRequestUtils.getEventFromJson(jsonReuslt));
                    else eventresultArray.put(i, HttpRequestUtils.getEventEntityFromJson(jsonReuslt, i));
                }
                
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("peng",eventresultArray.size()+","+params.length);
            return eventresultArray;
        }
        else {
            try {
                String jsonReuslt = HttpRequestUtils.getJsonFromUrl(params[0]);
                if(mType==0) return HttpRequestUtils.getEventFromJson(jsonReuslt);
                else return HttpRequestUtils.getEventEntityFromJson(jsonReuslt, mType);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onPostExecute(Object result) {
        m_onTaskListener.onTaskFinished(aggregateResult((SparseArray<Object>) result));
        super.onPostExecute(result);
    }
    
    @SuppressWarnings("unchecked")
    private ArrayList<EventsResult> aggregateResult(SparseArray<Object> eventresultArray) {
        ArrayList<EventsResult> result = new ArrayList<EventsResult>();
        
        if(eventresultArray==null||eventresultArray.size()==0) return result;
        
        ArrayList<Event> tempEventList = (ArrayList<Event>) eventresultArray.get(0);
        EventEntity tempEventNameMap = (EventEntity) eventresultArray.get(1);
        EventEntity tempMapNameMap = (EventEntity) eventresultArray.get(2);
        EventEntity tempWorldNameMap = (EventEntity) eventresultArray.get(3);
        for(int i=0,size=tempEventList.size();i<size;i++) {
            Event tempEvent = tempEventList.get(i); 

            if(!tempEvent.getState().equals(Event.ACTIVE)) continue;
            
            String worldName = tempWorldNameMap.get(tempEvent.getWorldId());
            String mapName = tempMapNameMap.get(tempEvent.getMapId());
            String eventName = tempEventNameMap.get(tempEvent.getEventId());
            result.add(new EventsResult(worldName,mapName,eventName,tempEvent.getState()));
        }
        
        return result;
    }
    
}
