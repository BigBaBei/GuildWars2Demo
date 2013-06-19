package com.example.guildwarseventdemo.fragments;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.guildwarseventdemo.R;
import com.example.guildwarseventdemo.constant.CommonConstant;

public class WikiFragment extends Fragment {
    
    private WebView m_webView = null;
    private ProgressBar m_progressbar = null;
    private ImageButton m_backButton = null;
    private ImageButton m_stopButton = null;
    private ImageButton m_forwardButton = null;
    
    private OnClickListener m_browserOnclikListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
            case R.id.id_ib_wiki_back:
                m_webView.goBack();
                break;
            case R.id.id_ib_wiki_stop:
                m_webView.stopLoading();
                m_stopButton.setBackgroundResource(R.drawable.wiki_nav_stop);
                break;
            case R.id.id_ib_wiki_forward:
                m_webView.goForward();
                break;
            }
        }
        
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wiki_layout, container,false);
        m_webView = (WebView) v.findViewById(R.id.id_wv_wiki);
        m_progressbar = (ProgressBar) v.findViewById(R.id.id_pb_wiki_progress);
        
        m_webView.loadUrl(CommonConstant.URL_WIKI);
        m_webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                m_webView.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                m_progressbar.setProgress(0);
                m_stopButton.setBackgroundResource(R.drawable.nav_refresh);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                m_stopButton.setBackgroundResource(R.drawable.wiki_nav_stop);
            }
            
        });
        m_webView.setWebChromeClient(new WebChromeClient(){
            
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("peng","progress: "+newProgress);
                m_progressbar.setProgress(newProgress);
            }
            
        });
        
        addBrowserButtons();
        
        return v;
    }
    
    

    @Override
    public void onDestroyView() {
        HideBrowserButtons();
        
        super.onDestroyView();
    }

    /**
     * Add broswer's button including back,stop,forward.
     */
    private void addBrowserButtons() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        if(actionBar.getCustomView()==null) {
            actionBar.setCustomView(R.layout.wiki_broswer_arrow);
        }else {
            actionBar.getCustomView().setVisibility(View.VISIBLE);
        }
        
        View v = actionBar.getCustomView();
        m_backButton = (ImageButton) v.findViewById(R.id.id_ib_wiki_back);
        m_stopButton = (ImageButton) v.findViewById(R.id.id_ib_wiki_stop);
        m_forwardButton = (ImageButton) v.findViewById(R.id.id_ib_wiki_forward);
        
        m_backButton.setOnClickListener(m_browserOnclikListener);
        m_stopButton.setOnClickListener(m_browserOnclikListener);
        m_forwardButton.setOnClickListener(m_browserOnclikListener);
    }
    
    /**
     * Hide the browser's button from action bar.
     */
    private void HideBrowserButtons(){
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.getCustomView().setVisibility(View.GONE);
    }
    
}
