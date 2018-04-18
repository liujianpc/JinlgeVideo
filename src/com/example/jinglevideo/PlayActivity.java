package com.example.jinglevideo;

import android.content.Intent;
import android.os.Bundle;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author XP-PC-XXX
 */
public class PlayActivity extends BaseActivity {

    private cn.jzvd.JZVideoPlayerStandard videoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        this.videoplayer = (JZVideoPlayerStandard) findViewById(R.id.video_player);
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            String title = intent.getStringExtra("title");
            videoplayer.setUp(url, JZVideoPlayer.SCREEN_WINDOW_NORMAL, title);
        }
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
