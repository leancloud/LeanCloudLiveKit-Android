package cn.leancloud.leancloudlivekit;

import java.util.List;

/**
 * Created by wli on 16/8/4.
 */
public interface LCLiveKitProfilesCallBack {
  public void done(List<LCLiveKitUser> userList, Exception exception);
}
