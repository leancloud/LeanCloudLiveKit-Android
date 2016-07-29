package com.daweibayu.livedemo.record;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.daweibayu.livedemo.LiveServer;
import com.daweibayu.livedemo.R;
import com.pili.Stream;

/**
 * Created by wli on 16/7/18.
 */


public class RecordActivity extends AppCompatActivity {

  LiveRecordFragment liveRecordFragment;

  private Handler mainThreadHandler;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record);
    liveRecordFragment = (LiveRecordFragment) getSupportFragmentManager().findFragmentById(R.id.record_fragment);

    mainThreadHandler = new Handler(getMainLooper());
    new Thread(new Runnable() {
      @Override
      public void run() {
        final Stream stream = LiveServer.getStream();
        mainThreadHandler.post(new Runnable() {
          @Override
          public void run() {
            liveRecordFragment.setRecordStream(stream.toJsonString());
            liveRecordFragment.startRecord();
          }
        });
      }
    }).start();
  }



}