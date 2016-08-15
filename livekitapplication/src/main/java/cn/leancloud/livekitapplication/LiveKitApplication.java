package cn.leancloud.livekitapplication;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.pili.pldroid.streaming.StreamingEnv;

import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.im.LCLKIMMessage;
import cn.leancloud.leancloudlivekit.im.LCLKIMBarrageMessage;
import cn.leancloud.leancloudlivekit.im.LCLKGiftMessage;

/**
 * Created by wli on 16/8/5.
 */
public class LiveKitApplication extends Application {

  // 此 id 与 key 仅供测试使用
  private final String APP_ID = "683jigxkqb10jrirelvd9vcn9ywbq2o436lfz1kngsvigm27";
  private final String APP_KEY = "ualzl8f8pxmryous77m3gf2z0dyhrhk6xdb7zkiu6flc0jxy";

  @Override
  public void onCreate() {
    super.onCreate();
    StreamingEnv.init(getApplicationContext());
    LCLiveKit.getInstance().setProfileProvider(LiveAppProvider.getInstance());
    AVOSCloud.setDebugLogEnabled(true);
    LCLiveKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);

    AVObject.registerSubclass(LCLiveRoom.class);
    AVIMMessageManager.registerAVIMMessageType(LCLKIMMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LCLKIMBarrageMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LCLKGiftMessage.class);
  }

}
