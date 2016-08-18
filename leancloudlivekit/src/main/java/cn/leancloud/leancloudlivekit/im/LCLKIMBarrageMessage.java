package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageType;

/**
 * Created by wli on 16/7/26.
 */
@AVIMMessageType(type = LCLKIMMessage.LIVE_IM_BARRAGE_MESSAGE_TYPE)
public class LCLKIMBarrageMessage extends LCLKIMMessage {

  public LCLKIMBarrageMessage() {
    super();
  }

  public static final Creator<LCLKIMBarrageMessage> CREATOR = new AVIMMessageCreator<LCLKIMBarrageMessage>(LCLKIMBarrageMessage.class);
}
