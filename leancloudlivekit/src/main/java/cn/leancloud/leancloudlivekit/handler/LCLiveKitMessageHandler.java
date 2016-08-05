package cn.leancloud.leancloudlivekit.handler;

import android.content.Context;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;

import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.im.LCLiveKitIMMessageEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 16/8/4.
 */
public class LCLiveKitMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

  private Context context;

  public LCLiveKitMessageHandler(Context context) {
    this.context = context.getApplicationContext();
  }

  @Override
  public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
    if (message == null || message.getMessageId() == null) {
      return;
    }

    if (LCLiveKit.getInstance().getCurrentUserId() == null) {
      client.close(null);
    } else {
      if (!client.getClientId().equals(LCLiveKit.getInstance().getCurrentUserId())) {
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
    LCLiveKitIMMessageEvent event = new LCLiveKitIMMessageEvent();
    event.message = message;
    event.conversation = conversation;
    EventBus.getDefault().post(event);
  }
}
