package cn.leancloud.leancloudlivekit;


import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.SignatureFactory;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.pili.pldroid.streaming.StreamingEnv;

import cn.leancloud.leancloudlivekit.handler.LCLKClientEventHandler;
import cn.leancloud.leancloudlivekit.handler.LCLKConversationHandler;
import cn.leancloud.leancloudlivekit.handler.LCLKMessageHandler;
import cn.leancloud.leancloudlivekit.im.LCLKGiftMessage;
import cn.leancloud.leancloudlivekit.im.LCLKIMBarrageMessage;
import cn.leancloud.leancloudlivekit.im.LCLKIMMessage;
import cn.leancloud.leancloudlivekit.im.LCLKIMStatusMessage;

/**
 * Created by wli on 16/8/4.
 * * LeanCloudLiveKit 的管理类
 */
public class LCLiveKit {

  private static LCLiveKit lcLiveKit;
  private LCLiveKitProvider liveKitProvider;
  private String currentUserId;

  private LCLiveKit() {
  }

  public static synchronized LCLiveKit getInstance() {
    if (null == lcLiveKit) {
      lcLiveKit = new LCLiveKit();
    }
    return lcLiveKit;
  }

  /**
   * 初始化 LeanCloudLiveKit，此函数要在 Application 的 onCreate 中调用
   *
   * @param context
   * @param appId
   * @param appKey
   */
  public void init(Context context, String appId, String appKey) {
    if (TextUtils.isEmpty(appId)) {
      throw new IllegalArgumentException("appId can not be empty!");
    }
    if (TextUtils.isEmpty(appKey)) {
      throw new IllegalArgumentException("appKey can not be empty!");
    }

    StreamingEnv.init(context);

    AVOSCloud.initialize(context.getApplicationContext(), appId, appKey);

    // 消息处理 handler
    AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new LCLKMessageHandler(context));

    // 与网络相关的 handler
    AVIMClient.setClientEventHandler(LCLKClientEventHandler.getInstance());

    // 和 Conversation 相关的事件的 handler
    AVIMMessageManager.setConversationEventHandler(LCLKConversationHandler.getInstance());

    // 默认设置为离线消息仅推送数量
    AVIMClient.setOfflineMessagePush(true);

    AVIMMessageManager.registerAVIMMessageType(LCLKIMMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LCLKIMBarrageMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LCLKGiftMessage.class);
    AVIMMessageManager.registerAVIMMessageType(LCLKIMStatusMessage.class);
  }
  public void setProfileProvider(LCLiveKitProvider profileProvider) {
    this.liveKitProvider = profileProvider;
  }

  /**
   * 获取当前的用户体系
   *
   * @return
   */
  public LCLiveKitProvider getProfileProvider() {
    return liveKitProvider;
  }

  /**
   * 设置签名工厂
   *
   * @param signatureFactory
   */
  public void setSignatureFactory(SignatureFactory signatureFactory) {
    AVIMClient.setSignatureFactory(signatureFactory);
  }

  /**
   * 开启实时聊天
   *
   * @param userId
   * @param callback
   */
  public void open(final String userId, final AVIMClientCallback callback) {
    if (TextUtils.isEmpty(userId)) {
      throw new IllegalArgumentException("userId can not be empty!");
    }
    if (null == callback) {
      throw new IllegalArgumentException("callback can not be null!");
    }

    AVIMClient.getInstance(userId).open(new AVIMClientCallback() {
      @Override
      public void done(final AVIMClient avimClient, AVIMException e) {
        if (null == e) {
          currentUserId = userId;
        }
        callback.internalDone(avimClient, e);
      }
    });
  }

  /**
   * 关闭实时聊天
   *
   * @param callback
   */
  public void close(final AVIMClientCallback callback) {
    AVIMClient.getInstance(currentUserId).close(new AVIMClientCallback() {
      @Override
      public void done(AVIMClient avimClient, AVIMException e) {
        currentUserId = null;
        if (null != callback) {
          callback.internalDone(avimClient, e);
        }
      }
    });
  }

  /**
   * 获取当前的实时聊天的用户
   *
   * @return
   */
  public String getCurrentUserId() {
    return currentUserId;
  }

  /**
   * 获取当前的 AVIMClient 实例
   *
   * @return
   */
  public AVIMClient getClient() {
    if (!TextUtils.isEmpty(currentUserId)) {
      return AVIMClient.getInstance(currentUserId);
    }
    return null;
  }
}
