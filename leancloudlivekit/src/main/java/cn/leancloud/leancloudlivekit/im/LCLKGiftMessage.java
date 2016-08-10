package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;

/**
 * Created by wli on 16/7/26.
 */
@AVIMMessageType(type = LCLKGiftMessage.LIVE_GIFT_MESSAGE_TYPE)
public class LCLKGiftMessage extends LCLKIMMessage {

  public static final int LIVE_GIFT_MESSAGE_TYPE = 5002;

  public static final String LIVE_GIFT_MESSAGE_NUMBER_KEY = "number";

  @AVIMMessageField(name = LIVE_GIFT_MESSAGE_NUMBER_KEY)
  private int number;

  public LCLKGiftMessage() {
    super();
  }

  public static final Creator<LCLKGiftMessage> CREATOR = new AVIMMessageCreator<LCLKGiftMessage>(LCLKGiftMessage.class);

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
