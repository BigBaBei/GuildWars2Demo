package com.example.guildwarseventdemo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guildwarseventdemo.fragments.EventsFragment;
import com.example.guildwarseventdemo.fragments.ProgressDialogFragment;
import com.example.guildwarseventdemo.fragments.WikiFragment;
import com.example.guildwarseventdemo.setting.GlobalSettings;

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
    /**
     * Title of action bar.
     */
    private String m_title = null;
    
    private FragmentManager m_fragManager = null;
    
    private onBackListener m_backListener = null;
    
    public void setBackListener(onBackListener l) {
        m_backListener = l;
    }
    
    private int m_currentPosition = 0;
    
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.id_menu_about:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.about_title)
            .setView(LayoutInflater.from(this).inflate(R.layout.about_content, null))
            .setPositiveButton("Close", null);
            builder.create().show();
            break;
        }
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
    
    

    @Override
    public void onBackPressed() {
        if(m_currentPosition==0) {
            //Handle the dynamic events's back event.
            m_backListener.onCustomBackPressed();
        }
        super.onBackPressed();
    }
    
    @Override
    protected void onDestroy() {
        GlobalSettings.INSTANCE.saveSettings();
        super.onDestroy();
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
        GlobalSettings.INSTANCE.init(this);
    }
    
    private class TitlesClickLitener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            m_currentPosition = position; 
            //Close the left panel when clicking the item of list view.
            m_drawerLayout.closeDrawer(m_lvLeftDrawer);
            
            //TODO:Always clear the backstack of dynamic events ui when go to any other ui or reenter it for now.
            m_fragManager.popBackStack(ProgressDialogFragment.STACK_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            
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
    
    /**
     * Set the action bar's title.
     * @param title
     */
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
    
    /**
     * Take care of the back button clicked.
     * @author peng
     *
     */
    public interface onBackListener {
        /**
         * Called when back button is clicked and handle the custom events for different purposes.
         */
        void onCustomBackPressed();
    }
}
