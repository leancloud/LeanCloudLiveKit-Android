package com.daweibayu.livedemo;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.daweibayu.livedemo.im.CustomUserProvider;
import com.daweibayu.livedemo.im.LCChatKit;
import com.daweibayu.livedemo.im.LCLiveIMMessage;
import com.daweibayu.livedemo.im.LiveBarrageMessage;
import com.daweibayu.livedemo.play.LiveGiftMessage;
import com.pili.pldroid.streaming.StreamingEnv;

/**
 * Created by wli on 16/7/18.
 */
public class LiveApp extends Application {

  private final String APP_ID = "683jigxkqb10jrirelvd9vcn9ywbq2o436lfz1kngsvigm27";
  private final String APP_KEY = "ualzl8f8pxmryous77m3gf2z0dyhrhk6xdb7zkiu6flc0jxy";

  @Override
  public void onCreate() {
    super.onCreate();
    StreamingEnv.init(getApplicationContext());

    AVOSCloud.setDebugLogEnabled(true);
    LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
    LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
    AVIMMessageManager.registerAVIMMessageType(LCLiveIMMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LiveBarrageMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LiveGiftMessage.class);
  }
}
