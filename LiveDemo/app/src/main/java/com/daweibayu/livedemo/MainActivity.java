package com.daweibayu.livedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daweibayu.LiveRecordActivity;
import com.daweibayu.livedemo.play.LivePlayFragment;
import com.daweibayu.livedemo.record.RecordActivity;
import com.pili.Stream;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.record_view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(MainActivity.this, RecordActivity.class);
        startActivity(intent);
//        gotoRecordActivity();
      }
    });

    findViewById(R.id.play_view).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gotoIMActivity();
      }
    });
  }

  private void getStream() {
    new Thread(new Runnable() {
      @Override
      public void run() {

        Intent intent = new Intent(MainActivity.this, RecordActivity.class);
        startActivity(intent);
      }
    }).start();
  }

  private void gotoIMActivity() {
    Intent intent = new Intent(MainActivity.this, IMPlayActivity.class);
    intent.putExtra(IMPlayActivity.LIVE_CONVERSATION_ID_KEY, "2f+spG1ZkOw5UBvN8i7noQ==");
    intent.putExtra(LivePlayFragment.LIVE_STREAM_PATH, "rtmp://live.hkstv.hk.lxdns.com/live/hks");
    startActivity(intent);
  }

  private void gotoRecordActivity() {
    Intent intent = new Intent(MainActivity.this, LiveRecordActivity.class);
    intent.putExtra(IMPlayActivity.LIVE_CONVERSATION_ID_KEY, "2f+spG1ZkOw5UBvN8i7noQ==");
    startActivity(intent);
  }
}
