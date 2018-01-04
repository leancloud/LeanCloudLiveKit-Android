package cn.leancloud.livekitapplication;

import com.avos.avoscloud.AVCallback;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.leancloudlivekit.LCLiveKitProvider;
import cn.leancloud.leancloudlivekit.LCLKUser;

/**
 * Created by wli on 16/8/5.
 */
public class LCLKAppProvider implements LCLiveKitProvider {

  private static LCLKAppProvider customUserProvider;

  public synchronized static LCLKAppProvider getInstance() {
    if (null == customUserProvider) {
      customUserProvider = new LCLKAppProvider();
    }
    return customUserProvider;
  }

  private LCLKAppProvider() {
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
  public void fetchProfiles(List<String> userIdList, AVCallback<List<LCLKUser>> profilesCallBack) {
    List<LCLKUser> userList = new ArrayList<LCLKUser>();
    for (String userId : userIdList) {
      for (LCLKUser user : partUsers) {
        if (user.getUserId().equals(userId)) {
          userList.add(user);
          break;
        }
      }
    }
    profilesCallBack.internalDone(userList, null);
  }

  @Override
  public void fetchRecordStream(String liveId, AVCallback<String> streamCallback) {
    //TODO:根据liveId获取对应的推流地址
    streamCallback.internalDone(null, null);
  }

  @Override
  public void fetchPlayStream(String liveId, AVCallback<String> streamCallback) {
    //TODO:根据liveId获取对应的直播地址
    streamCallback.internalDone("rtmp://live.hkstv.hk.lxdns.com/live/hks" , null);
  }
}
