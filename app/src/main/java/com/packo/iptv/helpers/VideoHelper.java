package com.packo.iptv.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class VideoHelper extends VideoView {
    private PlayPauseListener mListener;
    public void setPlayPauseListener(PlayPauseListener listener) {
        mListener = listener;
    }

    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onPlay();
        }
    }

    public static interface PlayPauseListener {
        void onPlay();
        void onPause();
    }

    public VideoHelper(Context context) {
        super(context);
    }

    public VideoHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public VideoHelper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
