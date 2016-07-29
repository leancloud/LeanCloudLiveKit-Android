package com.daweibayu.livedemo.im;

import android.content.Context;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;

import de.greenrobot.event.EventBus;

/**
 * Created by zhangxiaobo on 15/4/20.
 * AVIMTypedMessage 的 handler，socket 过来的 AVIMTypedMessage 都会通过此 handler 与应用交互
 * 需要应用主动调用 AVIMMessageManager.registerMessageHandler 来注册
 * 当然，自定义的消息也可以通过这种方式来处理
 */
public class LCIMMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

  private Context context;

  public LCIMMessageHandler(Context context) {
    this.context = context.getApplicationContext();
  }

  @Override
  public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    if (message == null || message.getMessageId() == null) {
      return;
    }

    if (LCChatKit.getInstance().getCurrentUserId() == null) {
      client.close(null);
    } else {
      if (!client.getClientId().equals(LCChatKit.getInstance().getCurrentUserId())) {
        client.close(null);
      } else {
        if (!message.getFrom().equals(client.getClientId())) {
          sendEvent(message, conversation);
        }
      }
    }
  }

  @Override
  public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    super.onMessageReceipt(message, conversation, client);
  }

  /**
   * 发送消息到来的通知事件
   *
   * @param message
   * @param conversation
   */
  private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
    LCIMIMTypeMessageEvent event = new LCIMIMTypeMessageEvent();
    event.message = message;
    event.conversation = conversation;
    EventBus.getDefault().post(event);
  }
}
