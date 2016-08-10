package cn.leancloud.leancloudlivekit.handler;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMClientEventHandler;

/**
 * Created by wli on 16/8/4.
 */
public class LCLKClientEventHandler extends AVIMClientEventHandler {

  private static LCLKClientEventHandler eventHandler;

  public static synchronized LCLKClientEventHandler getInstance() {
    if (null == eventHandler) {
      eventHandler = new LCLKClientEventHandler();
    }
    return eventHandler;
  }

  private LCLKClientEventHandler() {
  }


  private volatile boolean connect = false;

  /**
   * 是否连上聊天服务
   *
   * @return
   */
  public boolean isConnect() {
    return connect;
  }

  public void setConnectAndNotify(boolean isConnect) {
    connect = isConnect;
//    EventBus.getDefault().post(new LCIMConnectionChangeEvent(connect));
  }

  @Override
  public void onConnectionPaused(AVIMClient avimClient) {
    setConnectAndNotify(false);
  }

  @Override
  public void onConnectionResume(AVIMClient avimClient) {
    setConnectAndNotify(true);
  }

  @Override
  public void onClientOffline(AVIMClient avimClient, int i) {
//    LCIMLogUtils.d("client " + avimClient.getClientId() + " is offline!");
  }
}

