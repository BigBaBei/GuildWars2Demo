package com.example.guildwarseventdemo.entity;

import com.example.guildwarseventdemo.setting.GlobalSetting;

/**
 * A class stores the aggregated events' result 
 * @author peng
 *
 */
public class EventsResult {
    private String world_name;
    private String map_name;
    private String event_name;
    private String state;
    private long time;
    
    public EventsResult(String w_name,String m_name,String e_name,String stat) {
        this(w_name,m_name,e_name,stat,System.currentTimeMillis());
    }
    
    public EventsResult(String m_name,String e_name,String stat) {
        this(GlobalSetting.INSTANCE.getWorld(),m_name,e_name,stat,System.currentTimeMillis());
    }
    
    public EventsResult(String w_name,String m_name,String e_name,String stat,long time) {
        world_name = w_name;
        map_name = m_name;
        event_name = e_name;
        this.time = time;
    }
    
    public String getWorldName() {
        return world_name;
    }
    
    public String getMapName() {
        return map_name;
    }
    
    public String getEventName() {
        return event_name;
    }
    
    public String getState() {
        return state;
    }
}
