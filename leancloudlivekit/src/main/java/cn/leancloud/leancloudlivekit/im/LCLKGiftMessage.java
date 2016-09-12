package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;

/**
 * Created by wli on 16/7/26.
 */
@AVIMMessageType(type = LCLKIMMessage.LIVE_IM_GIFT_MESSAGE_TYPE)
public class LCLKGiftMessage extends LCLKIMMessage {

  public static final String LIVE_GIFT_MESSAGE_NUMBER_KEY = "number";
  public static final String LIVE_GIFT_MESSAGE_INDEX_KEY = "index";

  @AVIMMessageField(name = LIVE_GIFT_MESSAGE_NUMBER_KEY)
  private int number;

  @AVIMMessageField(name = LIVE_GIFT_MESSAGE_INDEX_KEY)
  private int giftIndex;

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


  public int getGiftIndex() {
    return giftIndex;
  }

  public void setGiftIndex(int giftIndex) {
    this.giftIndex = giftIndex;
  }
}
