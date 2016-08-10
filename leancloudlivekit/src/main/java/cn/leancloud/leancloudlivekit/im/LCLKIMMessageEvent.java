package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by wli on 16/8/4.
 */
public class LCLKIMMessageEvent {
  public AVIMTypedMessage message;
  public AVIMConversation conversation;
}
