package com.example.guildwarseventdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.guildwarseventdemo.fragments.EventsFragment;
import com.example.guildwarseventdemo.fragments.WikiFragment;

public class MainActivity extends FragmentActivity {
      
    private DrawerLayout m_drawerLayout = null;
    private ListView m_lvLeftDrawer = null;
    private ActionBarDrawerToggle m_drawerToggle = null;
    private String[] m_panelTitles = new String[] {
            "Dynamic Events",
            "Wolrd vs World",
            "Item and Recipe",
            "Guild",
            "Wiki"
    };
    private String m_title = null;
    
    
    private FragmentManager m_fragManager = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        init();
        if(savedInstanceState==null) {
            selectItem(0);
        }
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Pass the configuration changed.
        m_drawerToggle.onConfigurationChanged(newConfig);
    }
    
    

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        m_drawerToggle.syncState();
    }

    private void init() {
        m_drawerLayout = (DrawerLayout) findViewById(R.id.id_dl_root);
        
        m_lvLeftDrawer = (ListView) findViewById(R.id.id_lv_left_drawer);
        m_lvLeftDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, m_panelTitles));
        m_lvLeftDrawer.setOnItemClickListener(new TitlesClickLitener());
        
        m_drawerToggle = new ActionBarDrawerToggle(
                this,
                m_drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
                ) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                    }
            
        };
        m_drawerLayout.setDrawerListener(m_drawerToggle);
        
        m_fragManager = this.getSupportFragmentManager();
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
    }
    
    /**
     * Whether network is ok or not.
     * @return
     */
    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        
        return networkInfo!=null&&networkInfo.isConnected();
    }
    
    private class TitlesClickLitener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            m_drawerLayout.closeDrawer(m_lvLeftDrawer);
            selectItem(position);
        }
        
    }
    
    /**
     * Select the left panel's item.
     * @param position The position to select.
     */
    private void selectItem(int position) {
        switch(position) {
        case 0:
            EventsFragment contentFragment = new EventsFragment();
            Bundle bundle = new Bundle();
            contentFragment.setArguments(bundle);
            
            m_fragManager.beginTransaction()
                .replace(R.id.id_fl_content_frame, contentFragment)
                .commit();
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        case 4:
            WikiFragment wikiFragment = new WikiFragment();
            Bundle b4 = new Bundle();
            wikiFragment.setArguments(b4);
            m_fragManager.beginTransaction()
            .replace(R.id.id_fl_content_frame, wikiFragment)
            .commit();
            
            break;
        }
        
        m_lvLeftDrawer.setItemChecked(position, true);
        setTitle(m_panelTitles[position]);
        
    }
    
    private void setTitle(String title) {
        m_title = title;
        getActionBar().setTitle(m_title);
    }
    
//    /**
//     * Download the json by type.
//     * @param type
//     */
//    private void doMyEventStuff(int type) {
//        if(!isConnected()) return;
//        String url[] = new String[4];
//        
//        url[0] = CommonConstant.url+CommonConstant.DANAMIC_EVENT+"?"+CommonConstant.TAG_WORDL_ID+"="+1008;
//        url[1] = CommonConstant.url+CommonConstant.EVENT_NAME;
//        url[2] = CommonConstant.url+CommonConstant.MAP_NAME;
//        url[3] = CommonConstant.url+CommonConstant.WORLD_NAME;
//        
//        DownLoadEventTask task = new DownLoadEventTask(type, m_listner);
//        task.execute(url);
//    }
    
}
