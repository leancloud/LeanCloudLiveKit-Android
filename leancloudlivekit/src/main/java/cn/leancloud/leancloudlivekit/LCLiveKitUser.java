package cn.leancloud.leancloudlivekit;

/**
 * Created by wli on 16/8/4.
 */
public final class LCLiveKitUser {
  private String userId;
  private String avatarUrl;
  private String name;

  public LCLiveKitUser(String userId, String userName, String avatarUrl) {
    this.userId = userId;
    this.avatarUrl = avatarUrl;
    this.name = userName;
  }

  public String getUserId() {
    return userId;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getUserName() {
    return name;
  }
}
