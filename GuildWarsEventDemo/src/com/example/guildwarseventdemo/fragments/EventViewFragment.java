package com.example.guildwarseventdemo.fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.entity.EventsResult;
import com.example.guildwarseventdemo.setting.GlobalSettings;
import com.example.guildwarseventdemo.tasks.DragonTimerTask;
import com.example.guildwarseventdemo.utility.Utility;

/**
 * A fragment shows the dynamic events of specified world of a server.
 * It can filter events to show the type of concerned events.
 * @author peng
 *
 */
public class EventViewFragment extends Fragment {
    
    private ListView m_lvEvents = null;
    
    /**
     * Event list to be shown.
     */
    private ArrayList<EventsResult> m_eventsList = new ArrayList<EventsResult>();
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_eventsList =getArguments().getParcelableArrayList(ProgressDialogFragment.EVENTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.events_list, container, false);
        m_lvEvents = (ListView) v.findViewById(R.id.id_lv_events);
        
        m_lvEvents.setAdapter(new EventAdapter(getActivity()));
        return v;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    /**
     * The list view's adapter shows the content of events.
     * @author peng
     *
     */
    private class EventAdapter extends BaseAdapter {
        
        private LayoutInflater mInflater;
        
        public EventAdapter(Context c) {
            mInflater = LayoutInflater.from(c);
        }
        
        @Override
        public int getCount() {
            return m_eventsList.size();
        }

        @Override
        public Object getItem(int position) {
            return m_eventsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            EventsResult result = m_eventsList.get(position);
            if(convertView==null) {
                convertView  = mInflater.inflate(R.layout.events_list_item, null);
                holder = new ViewHolder();
                holder.event = (TextView) convertView.findViewById(R.id.id_tv_event_name);
                holder.map = (TextView) convertView.findViewById(R.id.id_tv_map_name);
                holder.state = (TextView) convertView.findViewById(R.id.id_tv_state);
                
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            if(result!=null) {
                holder.event.setText(result.getEventName());
                holder.map.setText(result.getMapName());
                holder.state.setText(result.getState());
            }
            return convertView;
        }
        
        /**
         * Hold the reusable resource IDs.
         * @author peng
         *
         */
        class ViewHolder {
            TextView event;
            TextView map;
            TextView state;
        }
    }
}
