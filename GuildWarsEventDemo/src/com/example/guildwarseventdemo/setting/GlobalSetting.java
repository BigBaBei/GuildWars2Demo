package com.example.guildwarseventdemo.setting;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The static class for global setting.
 * @author peng
 *
 */
public class GlobalSetting {
    public static final String TAG ="GlobalSetting";
    
    public static final GlobalSetting INSTANCE = new GlobalSetting();
    
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
    
    private GlobalSetting() {}
    
    public void init(Context context) {
        m_context = context;
        if(m_context!=null)
        {
            m_setting = m_context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
            m_strWorld = m_setting.getString(WORLD, WORLD_DEFAULT);
            m_nViewType = m_setting.getInt(VIEWTYPE, 0);
            m_strServer = m_setting.getString(SERVER, SERVER_DEFAULT);
        }
    }
    
    public String getWorld() {
        return m_strWorld;
    }
    
    public int getViewType() {
        return m_nViewType;
    }
    
    public String getServer() {
        return m_strServer;
    }
    
    
}
