package com.example.guildwarseventdemo.entity;

import java.util.HashMap;

/**
 * The entity differs from type.
 * @author peng
 *
 */
public class EventEntity extends HashMap<Object,String>{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 1: Event name;</br>
     * 2: Map name;</br>
     * 3: World name;
     */
    private int type;
//    private Object id;
//    private String name;
    
//    /**
//     * Constructs.
//     * @param id the entity's id.
//     * @param name The entity's name or content.
//     * @param type 
//     *      0: Event name;
//     *      1: Map name;
//     *      2: World name;
//     */
//    public EventEntity(Object id, String name, int type) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//    }
    
    public EventEntity(int type) {
        this.type = type;
    }
    
//    public Object getId() {
//        return id;
//    }
//    
//    public String getName() {
//        return name;
//    }
    
    public int getType() {
        return type;
    }
}