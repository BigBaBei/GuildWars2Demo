package com.example.guildwarseventdemo.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guildwarseventdemo.MainActivity;
import com.example.guildwarseventdemo.MainActivity.onBackListener;
import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.animation.RotateZAnimation;
import com.example.guildwarseventdemo.constant.CommonConstant;
import com.example.guildwarseventdemo.fragments.ProgressDialogFragment.FecthEventsCallBack;
import com.example.guildwarseventdemo.fragments.WorldsFragment.onWorldClickListener;
import com.example.guildwarseventdemo.setting.GlobalSettings;
import com.example.guildwarseventdemo.widgets.MirroredTextView;

/**
 * The fragment represents the content of the events.
 * @author peng
 *
 */
public class EventsFragment extends Fragment {
    public static final String EVENTS = "events";
    
    //User control.
    private ViewPager m_viewPager = null;
    private Button m_btnUS = null;
    private Button m_btnEU = null;
    private FrameLayout m_flEvents = null;
    private LinearLayout  m_pageLayout = null;
    
    private ViewGroup m_vgTitle = null;
    private MirroredTextView m_tvMirroredTitle = null;
    private MirroredTextView m_tvTitle = null;
    
    private FragmentManager m_fragManager = null;
    
    //Worlds Fragment.
    private WorldsFragment[] m_fragmentUS = null;
    private WorldsFragment[] m_fragmentEU = null;
    
    //Adapters.
    private WorldsAdapter m_adapterUS = null;
    private WorldsAdapter m_adapterEU = null;
    
    private boolean m_isWorldClicked = false;
    
    private ImageView[] m_pageDotimage = null;
    private int m_prevPos = 0;
    
    //Animation
    /**
     * change creation state: eu and us textview title is normal.
     */
    private boolean m_isMirrored = false;
    
    private int m_prevBtnClickedId = 0;
    
    private OnClickListener m_onClickListener = new OnClickListener(){

        @Override
        public void onClick(View v) {
            if(m_isWorldClicked) {
                showWorlds();
                m_fragManager.popBackStack();
                m_isWorldClicked = false;
            }
            switch(v.getId()) {
            case R.id.id_btn_eu:
                if(m_prevBtnClickedId==v.getId()) return;
                GlobalSettings.INSTANCE.setServer(CommonConstant.SERVER_EU);
                m_viewPager.setAdapter(m_adapterEU);
                addPageDot(m_fragmentEU.length);
                currentPageDot(0,0);
                changeTitle(true,180,90);
                break;
            case R.id.id_btn_us:
                if(m_prevBtnClickedId==v.getId()) return;
                GlobalSettings.INSTANCE.setServer(CommonConstant.SERVER_US);
                m_viewPager.setAdapter(m_adapterUS);
                addPageDot(m_fragmentUS.length);
                currentPageDot(0,0);
                changeTitle(false,0,90);
                break;
            }
            m_prevBtnClickedId = v.getId();
        }
        
    };
    
    private final class DisplayNextView implements Animation.AnimationListener {
        private final boolean isReverse;
        
        public DisplayNextView(boolean isReverse) {
            this.isReverse = isReverse;
        }

        @Override
        public void onAnimationStart(Animation animation) {
            
        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            m_vgTitle.post(new SwapViews(isReverse));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            
        }
        
    }
    
    private final class SwapViews implements Runnable {
        private final boolean isReverse;
        
        public SwapViews(boolean isReverse) {
            this.isReverse = isReverse;
        }

        @Override
        public void run() {
            final float centerX = m_tvMirroredTitle.getWidth()/2.0f;
            final float centerY = m_tvMirroredTitle.getHeight()/2.0f;
            RotateZAnimation anim = null;
            if(isReverse) {
                m_tvTitle.setText(GlobalSettings.INSTANCE.getServer());
                m_tvTitle.setVisibility(View.VISIBLE);
                m_tvMirroredTitle.setVisibility(View.GONE);
                
                Log.d("peng","m_tvTitle: "+m_tvTitle.getText());
                
                anim = new RotateZAnimation(90, 0, centerX, centerY, 0, false);
            }
            else {
                m_tvMirroredTitle.setText(GlobalSettings.INSTANCE.getServer());
                m_tvMirroredTitle.setVisibility(View.VISIBLE);
                m_tvTitle.setVisibility(View.GONE);
                
                Log.d("peng","m_tvMirroredTitle: "+m_tvMirroredTitle.getText());
                
                anim = new RotateZAnimation(90, 180, centerX, centerY, 0, false);
            }
            
            anim.setFillAfter(true);
            anim.setDuration(500);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            m_vgTitle.startAnimation(anim);
        }
        
    }
    
    private void changeTitle(boolean isReverse,int start, int end) {
        if(!m_isMirrored) {
//            m_tvMirroredTitle.setMirroredText(true);
        }
        float centerX = m_tvMirroredTitle.getWidth()/2;
        float centerY = m_tvMirroredTitle.getHeight()/2;
        RotateZAnimation anim = null;
        anim = new RotateZAnimation(start, end, centerX, centerY, 0, isReverse);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setDuration(500);
        anim.setAnimationListener(new DisplayNextView(isReverse));
        m_vgTitle.startAnimation(anim);
        
    }
    
    //Wolrd item click listener.
    private onWorldClickListener m_onWorldClickListener = new onWorldClickListener() {

        @Override
        public void onClick() {
            
            ProgressDialogFragment pdf = ProgressDialogFragment.newInstance(getActivity());
            pdf.setFectchEventsCallBack(m_fetchEventsCallback);
            pdf.show(m_fragManager, null);
            
            m_isWorldClicked = true;
        }
        
    };
    
    /*
     * Back item press listener.
     */
    private onBackListener m_onBackListener = new onBackListener() {

        @Override
        public void onCustomBackPressed() {
            showWorlds();
            m_isWorldClicked = false;
        }
        
    };
    
    /**
     * Handle the fetch events task when finished.
     */
    private FecthEventsCallBack m_fetchEventsCallback = new FecthEventsCallBack() {

        @Override
        public void onTaskFinished() {
            showEvents();
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
        m_flEvents = (FrameLayout)v.findViewById(R.id.id_fl_events);
        m_pageLayout = (LinearLayout)v.findViewById(R.id.id_ll_page);
        m_vgTitle = (ViewGroup)v.findViewById(R.id.id_fl_world_title);
        m_tvMirroredTitle =  (MirroredTextView) v.findViewById(R.id.id_tv_world_server);
        m_tvTitle = (MirroredTextView) v.findViewById(R.id.id_tv_world_server_reverse);
        
        m_tvMirroredTitle.setText(GlobalSettings.INSTANCE.getServer());
        
        m_btnUS.setOnClickListener(m_onClickListener);
        m_btnEU.setOnClickListener(m_onClickListener);
        
        m_prevBtnClickedId = GlobalSettings.INSTANCE.getServer().equals(CommonConstant.SERVER_EU)?R.id.id_btn_eu:R.id.id_btn_us;
        
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
            addPageDot(m_fragmentEU.length);
        }
        else {
            m_viewPager.setAdapter(m_adapterUS);
            addPageDot(m_fragmentUS.length);
        }
        
        currentPageDot(0,0);
        
        m_viewPager.setOnPageChangeListener(new OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                currentPageDot(m_prevPos,position);
                m_prevPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                
            }});
        
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
    
    /**
     * Show the events UI and hide the worlds UI.
     */
    private void showEvents() {
        m_flEvents.setAnimation(AnimationUtils.makeInChildBottomAnimation(getActivity()));
        m_flEvents.setVisibility(View.VISIBLE);
        m_viewPager.setVisibility(View.GONE);
        m_pageLayout.setVisibility(View.GONE);
        m_vgTitle.setVisibility(View.GONE);
    }
    
    /**
     * Show the worlds UI and hide the events UI.
     */
    private void showWorlds() {
        m_viewPager.setAnimation(AnimationUtils.makeInChildBottomAnimation(getActivity()));
        m_pageLayout.setAnimation(AnimationUtils.makeInChildBottomAnimation(getActivity()));
        m_viewPager.setVisibility(View.VISIBLE);
        m_pageLayout.setVisibility(View.VISIBLE);
        m_vgTitle.setVisibility(View.VISIBLE);
        m_flEvents.setVisibility(View.GONE);
    }
    
    private void addPageDot(int pageCount) {
        m_prevPos = 0;
        m_pageLayout.removeAllViews();
        if(m_pageDotimage==null) {
            m_pageDotimage = new ImageView[3];
            for(int i=0;i<3;i++) {
                ImageView imageDot = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.page_dot_view, null);
                Bitmap v = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager_unselected);
                imageDot.setImageBitmap(v);
                m_pageDotimage[i] = imageDot;
            }
        }
        for(int i=0;i<pageCount;i++){
            m_pageLayout.addView(m_pageDotimage[i], i);
        }
    }
    
    /**
     * Set the current dot of page to red.
     * @param prevPos
     * @param pos
     */
    private void currentPageDot(int prevPos,int pos) {
        ImageView imageDot = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.page_dot_view, null);
        Bitmap v = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager_selected);
        imageDot.setImageBitmap(v);
        
        if(prevPos!=pos) resetOldPageDot(prevPos);
        
        m_pageLayout.removeViewAt(pos);
        m_pageLayout.addView(imageDot, pos);
    }
    
    private void resetOldPageDot(int previousPostion) {
        m_pageLayout.removeViewAt(previousPostion);
        m_pageLayout.addView(m_pageDotimage[previousPostion], previousPostion);
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
