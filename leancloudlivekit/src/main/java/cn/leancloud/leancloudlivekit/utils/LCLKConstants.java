package cn.leancloud.leancloudlivekit.utils;

/**
 * Created by wli on 16/8/4.
 * LeanCloudLiveKit 常量类
 */
public class LCLKConstants {

  /**
   * LeanCloudLiveKit 常量前缀，避免与用户其他常量混淆
   */
  private static final String LEANMESSAGE_CONSTANTS_PREFIX = "cn.leancloud.livekit.";

  private static String getPrefixConstant(String str) {
    return LEANMESSAGE_CONSTANTS_PREFIX + str;
  }

  public static final String LIVE_RECORD_STREAM_KEY = getPrefixConstant("live_record_stream_key");


  public static final String LIVE_CONVERSATION_KEY = getPrefixConstant("live_conversation_key");

  public static final String LIVE_ID = getPrefixConstant("live_id");
  public static final String ANCHOR_ID = getPrefixConstant("anchor_id");
}
