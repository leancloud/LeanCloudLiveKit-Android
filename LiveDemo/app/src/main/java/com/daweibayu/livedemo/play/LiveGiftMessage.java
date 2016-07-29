package com.daweibayu.livedemo.play;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.daweibayu.livedemo.im.LCLiveIMMessage;

/**
 * Created by wli on 16/7/26.
 */
@AVIMMessageType(type = LiveGiftMessage.LIVE_GIFT_MESSAGE_TYPE)
public class LiveGiftMessage extends LCLiveIMMessage {

  public static final int LIVE_GIFT_MESSAGE_TYPE = 5002;

  public static final String LIVE_GIFT_MESSAGE_NUMBER_KEY = "number";

  @AVIMMessageField(name = LIVE_GIFT_MESSAGE_NUMBER_KEY)
  private int number;

  public LiveGiftMessage() {
    super();
  }

  public static final Creator<LiveGiftMessage> CREATOR = new AVIMMessageCreator<LiveGiftMessage>(LiveGiftMessage.class);

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
