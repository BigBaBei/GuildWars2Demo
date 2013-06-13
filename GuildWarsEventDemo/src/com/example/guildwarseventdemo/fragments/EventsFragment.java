package com.example.guildwarseventdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.setting.GlobalSettings;

/**
 * The fragment represents the content of the events.
 * @author peng
 *
 */
public class EventsFragment extends Fragment {
    private ViewPager m_viewPager = null;
    private Button m_btnUS = null;
    private Button m_btnEU = null;
    private FragmentManager m_fragManager = null;
    private WorldsFragment[] m_fragmentUS = null;
    private WorldsFragment[] m_fragmentEU = null;
    
    private WorldsAdapter m_adapterUS = null;
    private WorldsAdapter m_adapterEU = null;
    
    private OnClickListener m_onClickListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
            case R.id.id_btn_eu:
                GlobalSettings.INSTANCE.setServer(CommonConstant.SERVER_EU);
                m_viewPager.setAdapter(m_adapterEU);
                break;
            case R.id.id_btn_us:
                GlobalSettings.INSTANCE.setServer(CommonConstant.SERVER_US);
                m_viewPager.setAdapter(m_adapterUS);
                break;
            }
        }
        
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        m_fragManager = this.getFragmentManager();
        initArray();
        
        m_adapterUS = new WorldsAdapter(m_fragManager, m_fragmentUS);
        m_adapterEU = new WorldsAdapter(m_fragManager, m_fragmentEU);
        
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.worlds_selection, container,false);
        m_viewPager = (ViewPager) v.findViewById(R.id.id_vp_worlds);
        m_btnUS = (Button) v.findViewById(R.id.id_btn_us);
        m_btnEU = (Button) v.findViewById(R.id.id_btn_eu);
        
        m_btnUS.setOnClickListener(m_onClickListener);
        m_btnEU.setOnClickListener(m_onClickListener);
        
        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(GlobalSettings.INSTANCE.getServer().equals(CommonConstant.SERVER_EU)) {
            m_viewPager.setAdapter(m_adapterEU);
        }
        else {
            m_viewPager.setAdapter(m_adapterUS);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void invalidateView() {
        if(GlobalSettings.INSTANCE.getServer().equals(CommonConstant.SERVER_EU)) {
            m_viewPager.setAdapter(m_adapterEU);
        }
        else {
            m_viewPager.setAdapter(m_adapterUS);
        }
    }
    

    @Override
    public void onResume() {
        if(m_viewPager.getAdapter()!=null) Log.d("peng", "not null");
        else Log.d("peng", null);
        invalidateView();
        m_viewPager.invalidate();
        super.onResume();
    }

    /**
     * Initialize the fragment array to be shown for user selection.
     */
    private void initArray() {
        int len = 12;
        int pageCountUS = CommonConstant.WORLD_NAME_US.length/len+(CommonConstant.WORLD_NAME_US.length%len==0?0:1);
        int pageCountEU = CommonConstant.WORLD_NAME_EU.length/len+(CommonConstant.WORLD_NAME_EU.length%len==0?0:1);
        String[][] fUS = new String[pageCountUS][len];
        String[][] fEU = new String[pageCountEU][len];
        divide(fUS,len,pageCountUS,0);
        divide(fEU,len,pageCountEU,1);
        
        m_fragmentUS = new WorldsFragment[pageCountUS];
        m_fragmentEU = new WorldsFragment[pageCountEU];
        
        for(int i=0;i<pageCountUS;i++) {
            m_fragmentUS[i] = WorldsFragment.newInstance(i, fUS[i], 0);
        }
        for(int i=0;i<pageCountEU;i++) {
            m_fragmentEU[i] = WorldsFragment.newInstance(i, fEU[i], 1);
        }
    }
    
    /**
     * Divide the big array into severals by UI's page count.
     * @param array the big array.
     * @param len Subarray's length.
     * @param pageCount UI's count.
     */
    private void divide(String[][] array,int len,int pageCount,int type) {
        for(int i=0;i<pageCount;i++) {
            for(int j=0;j<len;j++) {
                int index = i*len+j;
                if(type==0){
                    if(CommonConstant.WORLD_NAME_US.length>index) {
                        array[i][j] =CommonConstant.WORLD_NAME_US[index];
                    }
                    else break;
                }
                else{
                    if(CommonConstant.WORLD_NAME_EU.length>index) {
                        array[i][j] =CommonConstant.WORLD_NAME_EU[index];
                    }
                    else break;
                }
            }
        }
    }
    
    /**
     * The worlds adapter to show in view pager.
     * @author peng
     *
     */
    private class WorldsAdapter extends FragmentPagerAdapter  {
        
        private WorldsFragment[] fragmentArray;
        
        public WorldsAdapter(FragmentManager fm, WorldsFragment[] fragment) {
            super(fm);
            fragmentArray = fragment;
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentArray[arg0];
        }

        @Override
        public int getCount() {
            return fragmentArray.length;
        }
        
        
    }
    
    
}
