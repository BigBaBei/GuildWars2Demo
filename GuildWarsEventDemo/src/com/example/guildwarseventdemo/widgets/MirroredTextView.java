package com.example.guildwarseventdemo.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class MirroredTextView extends TextView {
    
    private boolean m_ismirrored = false;
    

    public MirroredTextView(Context context) {
        super(context);
    }

    public MirroredTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MirroredTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(m_ismirrored) {
            canvas.translate(getWidth(), 0);
            canvas.scale(-1, 1);
        }
        super.onDraw(canvas);
    }
    
    
    public void setMirroredText(boolean mirrored) {
        m_ismirrored = mirrored;
    }

}
