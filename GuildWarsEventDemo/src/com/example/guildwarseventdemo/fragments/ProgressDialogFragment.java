package com.example.guildwarseventdemo.fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.entity.EventsResult;
import com.example.guildwarseventdemo.setting.GlobalSettings;
import com.example.guildwarseventdemo.tasks.DragonTimerTask;
import com.example.guildwarseventdemo.utility.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ProgressDialogFragment extends DialogFragment {
    
    public static final String EVENTS = "events";
    public final static String STACK_NAME = "worlds selection";
    
    private ArrayList<EventsResult> m_eventsList = new ArrayList<EventsResult>();
    
    private Context m_context = null;
    
    /**
     * A wait thread get the downloading task result.
     */
    private Thread m_thread = null;
    
    /**
     * The task download the dragon events.
     */
    private DragonTimerTask m_task = null;
    
    private FecthEventsCallBack m_fetchEventsCallback = null;
    
    public void setFectchEventsCallBack(FecthEventsCallBack callback) {
        m_fetchEventsCallback = callback;
    }
    
    public static ProgressDialogFragment newInstance(Context c) {
        ProgressDialogFragment f = new ProgressDialogFragment();
        f.setContext(c);
        return f;
        
    }
    
    public void setContext(Context c) {
        m_context = c;
    }
  
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),R.style.FullScreenDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(new ProgressBar(getActivity()));
        return dialog;
    }
    
    

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        fetchEvents();
    }

    


    @Override
    public void dismiss() {
        m_task.cancel(true);
        m_thread.interrupt();
        super.dismiss();
    }

    /**
     * Fetch dynamicEvents.
     */
     private void fetchEvents() {
         if(!Utility.isConnected(m_context)) {
             Toast.makeText(getActivity(), "Network is not connected.", Toast.LENGTH_SHORT).show();
             return;
         }
         
         int world_id = -1;
         String world_name = GlobalSettings.INSTANCE.getWorld();
         if(GlobalSettings.INSTANCE.getServer().equals(CommonConstant.SERVER_US)) {
             String[] worldNameArray = CommonConstant.WORLD_NAME_US;
             for(int i=0,size=CommonConstant.WORLD_NAME_US.length;i<size;i++) {
                 if(world_name.equals(worldNameArray[i])) {
                     world_id = CommonConstant.WORLD_ID_US[i];
                     break;
                 }
             }
             
         }
         else {
             String[] worldNameArray = CommonConstant.WORLD_NAME_EU;
             for(int i=0,size=CommonConstant.WORLD_NAME_EU.length;i<size;i++) {
                 if(world_name.equals(worldNameArray[i])) {
                     world_id = CommonConstant.WORLD_ID_EU[i];
                     break;
                 }
             }
         }
         
         if(world_id==-1) return;
//         String strUrl = CommonConstant.url+CommonConstant.DANAMIC_EVENT+"?"+CommonConstant.TAG_WORDL_ID+"="+world_id + "&"+CommonConstant.TAG_EVENT_ID+"=";
//         String[] events = CommonConstant.DRAG_EVENT;
//         String[] strUrls = new String[CommonConstant.DRAGON_EVENT_NAME.length];
//         for(int i=0,size=CommonConstant.DRAGON_EVENT_NAME.length;i<size;i++) {
//             strUrls[i] = strUrl + events[i];
//         }
//         DragonTimerTask task = new DragonTimerTask();
//         task.execute(strUrls[0]);
         String strUrl = CommonConstant.url+CommonConstant.DANAMIC_EVENT+"?"+CommonConstant.TAG_WORDL_ID+"="+world_id;
         m_task = new DragonTimerTask();
         m_task.execute(strUrl);
          
         m_thread = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    m_eventsList = m_task.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                
                gotoEventsView();
            }});
         m_thread.start();
     }
     
     private void gotoEventsView() {
         
         new Handler(Looper.getMainLooper()).post(new Runnable(){

            @Override
            public void run() {
                Bundle b = new Bundle();
                b.putParcelableArrayList(EVENTS, m_eventsList);
                EventViewFragment f = new EventViewFragment();
                f.setArguments(b);
                if(getFragmentManager()==null) return;
                getFragmentManager().beginTransaction()
                    .replace(R.id.id_fl_events, f)
                    .addToBackStack(STACK_NAME)
                    .commit();
                
                m_fetchEventsCallback.onTaskFinished();
                
                dismiss();
            }});
         
     }
     
     /**
      * Call back of fetching events.
      * @author peng
      *
      */
     public interface FecthEventsCallBack { 
         /**
          * Called when dragon downloading task is finished.
          */
         void onTaskFinished();
     }
}
