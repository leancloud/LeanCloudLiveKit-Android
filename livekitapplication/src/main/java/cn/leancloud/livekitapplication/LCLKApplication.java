package cn.leancloud.livekitapplication;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.pili.pldroid.streaming.StreamingEnv;

import cn.leancloud.leancloudlivekit.LCLiveKit;

/**
 * Created by wli on 16/8/5.
 */
public class LCLKApplication extends Application {

  // 此 id 与 key 仅供测试使用
  private final String APP_ID = "683jigxkqb10jrirelvd9vcn9ywbq2o436lfz1kngsvigm27";
  private final String APP_KEY = "ualzl8f8pxmryous77m3gf2z0dyhrhk6xdb7zkiu6flc0jxy";

  @Override
  public void onCreate() {
    super.onCreate();
    StreamingEnv.init(getApplicationContext());
    LCLiveKit.getInstance().setProfileProvider(LCLKAppProvider.getInstance());
    LCLiveKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
    AVOSCloud.setDebugLogEnabled(true);
    AVObject.registerSubclass(LCLiveRoom.class);
  }

}
