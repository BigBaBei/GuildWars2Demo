package com.example.guildwarseventdemo.fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.entity.EventsResult;
import com.example.guildwarseventdemo.interfaces.OnTaskListener;
import com.example.guildwarseventdemo.setting.GlobalSettings;
import com.example.guildwarseventdemo.task.DragonTimerTask;
import com.example.guildwarseventdemo.utility.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A fragment shows the dynamic events of specified world of a server.
 * It can filter events to show the type of concerned events.
 * @author peng
 *
 */
public class EventViewFragment extends Fragment {
    
    private ListView m_lvEvents = null;
    
    private ArrayList<EventsResult> m_eventsList = new ArrayList<EventsResult>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.events_list, container, false);
        m_lvEvents = (ListView) v.findViewById(R.id.id_lv_events);
        
        return v;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
   /**
    * Fetch dynamicEvents.
    */
    private void fetchEvents() {
        if(Utility.isConnected(getActivity())) return;
        
        int world_id = -1;
        String world_name = GlobalSettings.INSTANCE.getWorld();
        String[] worldNameArray = CommonConstant.WORLD_NAME_US;
        if(GlobalSettings.INSTANCE.getServer().equals(CommonConstant.SERVER_US)) {
            for(int i=0,size=CommonConstant.WORLD_NAME_US.length;i<size;i++) {
                if(world_name.equals(worldNameArray[i])) {
                    world_id = CommonConstant.WORLD_ID_US[i];
                    break;
                }
            }
            
        }
        else {
            for(int i=0,size=CommonConstant.WORLD_NAME_EU.length;i<size;i++) {
                if(world_name.equals(worldNameArray[i])) {
                    world_id = CommonConstant.WORLD_ID_EU[i];
                    break;
                }
            }
        }
        
        if(world_id==-1) return;
        String strUrl = CommonConstant.url+CommonConstant.DANAMIC_EVENT+"?"+CommonConstant.TAG_WORDL_ID+"="+world_id;
        DragonTimerTask task = new DragonTimerTask();
        task.execute(strUrl);
         
         try {
            m_eventsList = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
