package cn.leancloud.leancloudlivekit.handler;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;

import java.util.List;


/**
 * Created by wli on 16/8/4.
 */
public class LCLKConversationHandler extends AVIMConversationEventHandler {

  private static LCLKConversationHandler eventHandler;

  public static synchronized LCLKConversationHandler getInstance() {
    if (null == eventHandler) {
      eventHandler = new LCLKConversationHandler();
    }
    return eventHandler;
  }

  private LCLKConversationHandler() {
  }

  @Override
  public void onOfflineMessagesUnread(AVIMClient client, AVIMConversation conversation, int unreadCount) {
  }

  @Override
  public void onMemberLeft(AVIMClient client, AVIMConversation conversation, List<String> members, String kickedBy) {
    // 因为不同用户需求不同，此处暂不做默认处理，如有需要，用户可以通过自定义 Handler 实现
  }

  @Override
  public void onMemberJoined(AVIMClient client, AVIMConversation conversation, List<String> members, String invitedBy) {
  }

  @Override
  public void onKicked(AVIMClient client, AVIMConversation conversation, String kickedBy) {
  }

  @Override
  public void onInvited(AVIMClient client, AVIMConversation conversation, String operator) {
  }
}
