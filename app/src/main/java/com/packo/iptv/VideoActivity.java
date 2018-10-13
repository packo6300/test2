package com.packo.iptv;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class VideoActivity extends AppCompatActivity  {
    public static VideoView videoView;
    private static ProgressBar progressBar;
    private static TextView infoText;
    private AdView mAdView,mAdView2;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView = (VideoView) findViewById(R.id.videoView_video2);
        progressBar = findViewById(R.id.progressBar);
        infoText = findViewById(R.id.info_txt);
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView2 = (AdView) findViewById(R.id.adView2);
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView2.loadAd(new AdRequest.Builder().build());
        url=getIntent().getExtras().getString("url");
        init(url);
    }
    private void init(String u) {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                infoText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                mAdView.setVisibility(View.GONE);
                mAdView2.setVisibility(View.GONE);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                infoText.setText("Contenido no disponible");
                progressBar.setVisibility(View.GONE);
                videoView.setVisibility(View.GONE);
                mAdView.setVisibility(View.VISIBLE);
                mAdView2.setVisibility(View.VISIBLE);
                return false;
            }
        });
        videoView.setKeepScreenOn(true);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        videoView.setMediaController(mc);

        Uri uri = Uri.parse(u);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
    }
}
