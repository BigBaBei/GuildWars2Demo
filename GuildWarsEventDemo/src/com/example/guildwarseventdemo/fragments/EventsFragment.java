package com.example.guildwarseventdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.guildwarseventdemo.MainActivity.onBackListener;
import com.example.guildwarseventdemo.MainActivity;
import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.fragments.WorldsFragment.onWorldClickListener;
import com.example.guildwarseventdemo.setting.GlobalSettings;

/**
 * The fragment represents the content of the events.
 * @author peng
 *
 */
public class EventsFragment extends Fragment {
    
    public final static String STACK_NAME = "worlds selection";
    
    //User control.
    private ViewPager m_viewPager = null;
    private Button m_btnUS = null;
    private Button m_btnEU = null;
    private FrameLayout m_flEvents = null;
    
    private FragmentManager m_fragManager = null;
    
    //Worlds Fragment.
    private WorldsFragment[] m_fragmentUS = null;
    private WorldsFragment[] m_fragmentEU = null;
    
    //Adapters.
    private WorldsAdapter m_adapterUS = null;
    private WorldsAdapter m_adapterEU = null;
    
    private boolean isWorldClicked = false;
    
    private OnClickListener m_onClickListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            if(isWorldClicked) {
                showWorlds();
                m_fragManager.popBackStack();
                isWorldClicked = false;
            }
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
    
    //TODO: Wolrd item click listener.
    private onWorldClickListener m_onWorldClickListener = new onWorldClickListener() {

        @Override
        public void onClick(WikiFragment f) {
            
            m_fragManager.beginTransaction()
            .replace(R.id.id_fl_events, f)
            .addToBackStack(STACK_NAME)
            .commit();
            showEvents();
            isWorldClicked = true;
        }
        
    };
    
    //TODO:Back item press listener.
    private onBackListener m_onBackListener = new onBackListener() {

        @Override
        public void onCustomBackPressed() {
            showWorlds();
            isWorldClicked = false;
        }
        
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("peng", "Event:onCreate");
        m_fragManager = this.getFragmentManager();
        
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d("peng", "Event:onCreateView");
        View v = inflater.inflate(R.layout.worlds_selection, container,false);
        m_viewPager = (ViewPager) v.findViewById(R.id.id_vp_worlds);
        m_btnUS = (Button) v.findViewById(R.id.id_btn_us);
        m_btnEU = (Button) v.findViewById(R.id.id_btn_eu);
        
        //TODO:
        m_flEvents = (FrameLayout)v.findViewById(R.id.id_fl_events);
        
        m_btnUS.setOnClickListener(m_onClickListener);
        m_btnEU.setOnClickListener(m_onClickListener);
        
        return v;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("peng", "Event:onActivityCreated");
        initArray();
        
        m_adapterUS = new WorldsAdapter(m_fragManager, m_fragmentUS);
        m_adapterEU = new WorldsAdapter(m_fragManager, m_fragmentEU);
        
        if(GlobalSettings.INSTANCE.getServer().equals(CommonConstant.SERVER_EU)) {
            m_viewPager.setAdapter(m_adapterEU);
        }
        else {
            m_viewPager.setAdapter(m_adapterUS);
        }
        ((MainActivity)this.getActivity()).setBackListener(m_onBackListener);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.d("peng", "Event:onDestoryView");
//        for(int i=0;i<m_fragmentUS.length;i++) {
//            m_fragmentUS[i].onDestroyView();
//        }
//        
//        for(int i=0;i<m_fragmentEU.length;i++) {
//            m_fragmentEU[i].onDestroyView();
//        }
        super.onDestroyView();
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
            m_fragmentUS[i].setOnWorldClickListener(m_onWorldClickListener);
        }
        for(int i=0;i<pageCountEU;i++) {
            m_fragmentEU[i] = WorldsFragment.newInstance(i, fEU[i], 1);
            m_fragmentEU[i].setOnWorldClickListener(m_onWorldClickListener);
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
    
    private void showEvents() {
//        m_flEvents.setAnimation(AnimationUtils.makeInAnimation(getActivity(), true));
        m_flEvents.setVisibility(View.VISIBLE);
        m_viewPager.setAnimation(AnimationUtils.makeOutAnimation(getActivity(), true));
        m_viewPager.setVisibility(View.GONE);
    }
    
    private void showWorlds() {
        m_viewPager.setAnimation(AnimationUtils.makeInAnimation(getActivity(), false));
        m_viewPager.setVisibility(View.VISIBLE);
//        m_flEvents.setAnimation(AnimationUtils.makeOutAnimation(getActivity(), false));
        m_flEvents.setVisibility(View.GONE);
    }
    
//    /**
//     * The worlds adapter to show in view pager.(Deprecated:For not refreshing ui when fragment id replaced.)
//     * @author peng
//     *
//     */
//    private class WorldsAdapter extends FragmentPagerAdapter  {
//        
//        private WorldsFragment[] fragmentArray;
//        
//        public WorldsAdapter(FragmentManager fm, WorldsFragment[] fragment) {
//            super(fm);
//            fragmentArray = fragment;
//        }
//
//        @Override
//        public Fragment getItem(int arg0) {
//            return fragmentArray[arg0];
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentArray.length;
//        }
//        
//        
//    }
    
    /**
     * The worlds adapter to show in view pager.
     * @author peng
     *
     */
    private class WorldsAdapter extends FragmentStatePagerAdapter {
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
