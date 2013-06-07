package com.example.guildwarseventdemo.fragments;

import com.example.guildwarseventdemo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The fragment represents the content of the events.
 * @author peng
 *
 */
public class EventsFragment extends Fragment {
    private ViewPager m_viewPager = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.worlds_selection, container);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    private void init() {
        
    }
    
    /**
     * It contains a gridview to show the worlds for user's selection.
     * @author peng
     *
     */
    private class WorldsFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.worlds_selection_item, container);
        }
        
    }
}
