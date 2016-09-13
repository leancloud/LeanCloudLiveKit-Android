package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by wli on 16/7/22.
 */
@AVIMMessageType(type = LCLKIMMessage.LIVE_IM_TEXT_MESSAGE_TYPE)
public class LCLKIMMessage extends AVIMTypedMessage {

  public static final int LIVE_IM_TEXT_MESSAGE_TYPE = 5001;
  public static final int LIVE_IM_STATUS_MESSAGE_TYPE = 5002;
  public static final int LIVE_IM_GIFT_MESSAGE_TYPE = 5003;
  public static final int LIVE_IM_BARRAGE_MESSAGE_TYPE = 5004;

  public static final String LIVE_MESSAGE_AVATAR_KEY = "avatar";
  public static final String LIVE_MESSAGE_NAME_KEY = "name";
  public static final String LIVE_MESSAGE_CONTENT_KEY = "message_content";

  @AVIMMessageField(name = LIVE_MESSAGE_AVATAR_KEY)
  private String avatar;

  @AVIMMessageField(name = LIVE_MESSAGE_NAME_KEY)
  private String name;

  @AVIMMessageField(name = LIVE_MESSAGE_CONTENT_KEY)
  private String messageContent;

  public static final Creator<LCLKIMMessage> CREATOR = new AVIMMessageCreator<LCLKIMMessage>(LCLKIMMessage.class);

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMessageContent() {
    return messageContent;
  }

  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }
}