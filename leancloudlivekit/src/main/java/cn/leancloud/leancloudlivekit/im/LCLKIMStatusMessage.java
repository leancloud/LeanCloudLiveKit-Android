package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by wli on 16/8/18.
 */
@AVIMMessageType(type = LCLKIMMessage.LIVE_IM_STATUS_MESSAGE_TYPE)
public class LCLKIMStatusMessage extends AVIMTypedMessage {

  public static final String LIVE_IM_STASUT_MESSAGE_KEY = "status_content";

  @AVIMMessageField(name = LIVE_IM_STASUT_MESSAGE_KEY)
  private String statusContent;

  public static final Creator<LCLKIMMessage> CREATOR = new AVIMMessageCreator<LCLKIMMessage>(LCLKIMMessage.class);

  public String getStatusContent() {
    return statusContent;
  }

  public void setStatusContent(String statusContent) {
    this.statusContent = statusContent;
  }
}