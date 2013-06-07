package com.example.guildwarseventdemo;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.entity.EventsResult;
import com.example.guildwarseventdemo.interfaces.OnTaskListener;
import com.example.guildwarseventdemo.task.DownLoadEventTask;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
      
//    private TextView m_tv_result = null;
//    private Button m_btn_get = null;
//    
//    private Dialog m_progressDlg = null;
//    
//    private OnTaskListener m_listner = new OnTaskListener() {
//
//        @SuppressWarnings("unchecked")
//        @Override
//        public void onTaskFinished(Object result) {
//            m_progressDlg.dismiss();
//            if(result==null) {
//                m_tv_result.setText("Get results error!");
//            }
//            else {
//                m_tv_result.setText("Total events are "+((ArrayList<EventsResult>)result).size());
//            }
//        }
//        
//    };
    
    private ListView m_lvLeftDrawer = null;
    private String[] m_panelTitles = new String[] {
            "Dynamic Events",
            "Wolrd vs World",
            "Item and Recipe",
            "Guild",
            "Wiki"
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
//        m_tv_result = (TextView) findViewById(R.id.id_tv_result);
//        
//        m_progressDlg = new Dialog(this,R.style.FullScreenDialog);
//        m_progressDlg.setContentView(new ProgressBar(this));
//        
//        m_btn_get = (Button) findViewById(R.id.id_btn_get);
//        m_btn_get.setOnClickListener(new OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                m_progressDlg.show();
//                //-1 for all.
//                doMyEventStuff(-1);
//            }});
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void init() {
        m_lvLeftDrawer = (ListView) findViewById(R.id.id_lv_left_drawer);
        m_lvLeftDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, m_panelTitles));
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
