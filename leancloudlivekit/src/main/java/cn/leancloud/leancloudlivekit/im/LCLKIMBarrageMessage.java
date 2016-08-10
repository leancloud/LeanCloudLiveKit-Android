package cn.leancloud.leancloudlivekit.im;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageType;

/**
 * Created by wli on 16/7/26.
 */
@AVIMMessageType(type = LCLKIMBarrageMessage.LIVE_BARRAGE_MESSAGE_TYPE)
public class LCLKIMBarrageMessage extends LCLKIMMessage {

  public static final int LIVE_BARRAGE_MESSAGE_TYPE = 5001;

  public LCLKIMBarrageMessage() {
    super();
  }

  public static final Creator<LCLKIMBarrageMessage> CREATOR = new AVIMMessageCreator<LCLKIMBarrageMessage>(LCLKIMBarrageMessage.class);
}
