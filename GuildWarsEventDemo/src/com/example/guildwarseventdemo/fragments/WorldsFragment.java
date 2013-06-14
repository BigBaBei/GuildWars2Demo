package com.example.guildwarseventdemo.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.setting.GlobalSettings;

/**
 * It contains a gridview to show the worlds for user's selection.
 * @author peng
 *
 */
public class WorldsFragment extends Fragment implements OnItemClickListener{
    
    private static final String INDEX = "index";
    private static final String WORLDS = "worlds";
    private static final String TYPE = "type";
    
    private int m_index = 0;
    private String[] m_worlds = null;
    /**
     * 0:US;1:EU.
     */
    private int m_type = 0;
    
    private GridView m_gridView = null;
    
    private onWorldClickListener m_onclickListener = null;
    
    public void setOnWorldClickListener(onWorldClickListener l) {
        m_onclickListener = l;
    }
    
    public static WorldsFragment newInstance(int index,String[] worlds,int type) {
        WorldsFragment f = new WorldsFragment();
        
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        args.putStringArray(WORLDS, worlds);
        args.putInt(TYPE, type);
        f.setArguments(args);
        
        return f;
    }
    
    
    
    @Override
    public void onAttach(Activity activity) {
        Log.d("peng","worlds:onAttach");
        super.onAttach(activity);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("peng", "worlds:onCreate");
        m_index = getArguments().getInt(INDEX);
        m_worlds = getArguments().getStringArray(WORLDS);
        cutNullTailInArray();
        m_type = getArguments().getInt(TYPE);
        
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d("peng", "worlds:onCreateView");
        View v = inflater.inflate(R.layout.worlds_selection_item, container, false);
        m_gridView = (GridView) v.findViewById(R.id.id_gv_worlds);
        
        if(m_worlds!=null) {
            m_gridView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, m_worlds));
        }
        
        m_gridView.setOnItemClickListener(this);
        return v;
    }
    
    @Override
    public void onStop() {
        Log.d("peng", "worlds:onStop--->"+m_type);
        super.onStop();
    }



    @Override
    public void onDestroyView() {
        Log.d("peng", "worlds:onDestroyView");
        super.onDestroyView();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        GlobalSettings.INSTANCE.setWorld(m_worlds[position]);
        WikiFragment wikiFragment = new WikiFragment();
        Bundle b4 = new Bundle();
        wikiFragment.setArguments(b4);
        m_onclickListener.onClick(wikiFragment);
    }
    
    private void cutNullTailInArray() {
        int index;
        boolean hasNull = false;
        for(index=0;index<m_worlds.length;index++) {
            if(m_worlds[index]==null) {
                hasNull = true;
                break;
            }
        }
        if(hasNull) {
            String[] newArray = new String[index];
            for(int j=0;j<index;j++) {
                newArray[j] = m_worlds[j]; 
            }
            m_worlds = newArray;
        }
    }
    
    /**
     * Interface definition for callback to be invoked when a world has been selected by user.
     * @author peng
     *
     */
    public interface onWorldClickListener {
        /**
         * Called when a world item clicked.
         * @param f
         */
        void onClick(WikiFragment f);
        
    }
}
