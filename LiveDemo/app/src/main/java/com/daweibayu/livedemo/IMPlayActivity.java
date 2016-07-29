package com.daweibayu.livedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wli on 16/7/21.
 */
public class IMPlayActivity extends AppCompatActivity {

  public static final String LIVE_CONVERSATION_ID_KEY = "conversationId";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_im_play);
  }
}