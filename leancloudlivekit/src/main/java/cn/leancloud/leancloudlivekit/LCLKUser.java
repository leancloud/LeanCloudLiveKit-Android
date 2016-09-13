package cn.leancloud.leancloudlivekit;

/**
 * Created by wli on 16/8/4.
 * * LeanCloudLiveKit 中的用户类，仅包含三个变量，暂不支持继承扩展
 */
public final class LCLKUser {

  /**
   * 用户 id
   */
  private String userId;

  /**
   * 用户头像地址
   */
  private String avatarUrl;

  /**
   * 用户别名
   */
  private String name;

  public LCLKUser(String userId, String userName, String avatarUrl) {
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
