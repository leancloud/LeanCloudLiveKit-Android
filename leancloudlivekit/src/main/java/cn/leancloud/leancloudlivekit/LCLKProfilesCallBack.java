package cn.leancloud.leancloudlivekit;

import java.util.List;

/**
 * Created by wli on 16/8/4.
 */
public interface LCLKProfilesCallBack {
  public void done(List<LCLKUser> userList, Exception exception);
}
