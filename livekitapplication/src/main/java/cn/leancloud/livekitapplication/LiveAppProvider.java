package cn.leancloud.livekitapplication;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.leancloudlivekit.LCLKProfilesCallBack;
import cn.leancloud.leancloudlivekit.LCLiveKitProvider;
import cn.leancloud.leancloudlivekit.LCLKUser;

/**
 * Created by wli on 16/8/5.
 */
public class LiveAppProvider implements LCLiveKitProvider {

  private static LiveAppProvider customUserProvider;

  public synchronized static LiveAppProvider getInstance() {
    if (null == customUserProvider) {
      customUserProvider = new LiveAppProvider();
    }
    return customUserProvider;
  }

  private LiveAppProvider() {
  }

  private static List<LCLKUser> partUsers = new ArrayList<LCLKUser>();

  // 此数据均为 fake，仅供参考
  static {
    partUsers.add(new LCLKUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
    partUsers.add(new LCLKUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
    partUsers.add(new LCLKUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
    partUsers.add(new LCLKUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
    partUsers.add(new LCLKUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
  }


  @Override
  public void fetchProfiles(List<String> userIdList, LCLKProfilesCallBack profilesCallBack) {
    List<LCLKUser> userList = new ArrayList<LCLKUser>();
    for (String userId : userIdList) {
      for (LCLKUser user : partUsers) {
        if (user.getUserId().equals(userId)) {
          userList.add(user);
          break;
        }
      }
    }
    profilesCallBack.done(userList, null);
  }
}