package com.example.guildwarseventdemo.entity;

/**
 * The class stores the infomation of a event.
 * @author peng
 *
 */
public class Event {
    public static final String WARMUP = "Warmup";
    public static final String PREPARATION = "Preparation";
    public static final String ACTIVE = "Active";
    public static final String SUCCESS = "Success";
    public static final String FAIL = "Fail";
    public static final String INACTIVE = "Inactive";
    
    public Event(int w_id, int m_id, String e_id,String state){
        world_id = w_id;
        map_id = m_id;
        event_id = e_id;
        currentStatus = state;
    }
    
    private int world_id;
    private int map_id;
    private String event_id;
    
    private String currentStatus; 
    
    public int getWorldId() {
        return world_id;
    }
    
    public int getMapId() {
        return map_id;
    }
    
    public String getEventId() {
        return event_id;
    }
    
    public String getState() {
        return currentStatus;
    }
    
}