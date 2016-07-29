package com.daweibayu.livedemo.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by wli on 16/7/22.
 */
@AVIMMessageType(type = LCLiveIMMessage.LIVE_MESSAGE_TYPE)
public class LCLiveIMMessage extends AVIMTypedMessage {

  public static final int LIVE_MESSAGE_TYPE = 1001;

  public static final String LIVE_MESSAGE_AVATAR_KEY = "avatar";
  public static final String LIVE_MESSAGE_NAME_KEY = "name";
  public static final String LIVE_MESSAGE_CONTENT_KEY = "message_content";

  @AVIMMessageField(name = LIVE_MESSAGE_AVATAR_KEY)
  private String avatar;

  @AVIMMessageField(name = LIVE_MESSAGE_NAME_KEY)
  private String name;

  @AVIMMessageField(name = LIVE_MESSAGE_CONTENT_KEY)
  private String messageContent;

  public static final Creator<LCLiveIMMessage> CREATOR = new AVIMMessageCreator<LCLiveIMMessage>(LCLiveIMMessage.class);

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