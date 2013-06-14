package com.example.guildwarseventdemo.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * The static class for global setting.
 * @author peng
 *
 */
public class GlobalSettings {
    public static final String TAG ="GlobalSetting";
    
    public static final GlobalSettings INSTANCE = new GlobalSettings();
    
    public static final String WORLD = "world";
    public static final String VIEWTYPE = "view type";
    public static final String SERVER = "server";
    
    private static final String WORLD_DEFAULT = "";
    private static final String SERVER_DEFAULT = "";
    
    private static Context m_context;
    
    private static SharedPreferences m_setting;
    
    private static String m_strWorld = "";
    /**
     * 0:
     * 1:
     * 2:
     */
    private static int m_nViewType; 
    
    private static String m_strServer = "";
    
    private static Editor m_editor = null;
    
    private GlobalSettings() {}
    
    public void init(Context context) {
        m_context = context;
        if(m_context!=null)
        {
            m_setting = m_context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
            m_editor = m_setting.edit();
            m_strWorld = m_setting.getString(WORLD, WORLD_DEFAULT);
            m_nViewType = m_setting.getInt(VIEWTYPE, 0);
            m_strServer = m_setting.getString(SERVER, SERVER_DEFAULT);
        }
    }
    
    public String getWorld() {
        return m_strWorld;
    }
    
    public void setWorld(String world) {
        m_strWorld = world;
    }
    
    public int getViewType() {
        return m_nViewType;
    }
    
    public String getServer() {
        return m_strServer;
    }
    
    public void setServer(String server) {
        m_strServer = server;
    }
    
    public void saveSettings() {
        m_editor.putString(WORLD, m_strWorld);
        m_editor.putInt(VIEWTYPE, m_nViewType);
        m_editor.putString(SERVER, m_strServer);
        m_editor.commit();
    }
}
