package cn.leancloud.leancloudlivekit.utils;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.leancloudlivekit.LCLKUser;
import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.LCLiveKitProvider;

/**
 * Created by wli on 16/8/10.
 * 用户信息的缓存，仅仅缓存与内存中
 */
public class LCLKProfileCache {

  /**
   * userMap 最大的数量，避免数据过多导致 oom
   */
  private final int PROFILE_CACHE_MAX_SIZE = 1000;

  private Map<String, LCLKUser> userMap;

  private LCLKProfileCache() {
    userMap = new HashMap<>();
  }

  private static LCLKProfileCache profileCache;

  public static synchronized LCLKProfileCache getInstance() {
    if (null == profileCache) {
      profileCache = new LCLKProfileCache();
    }
    return profileCache;
  }

  /**
   * 根据 id 获取用户信息
   * 先从缓存中获取，若没有再调用用户回调获取
   *
   * @param id
   * @param callback
   */
  public synchronized void getCachedUser(final String id, final AVCallback<LCLKUser> callback) {
    getCachedUsers(Arrays.asList(id), new AVCallback<List<LCLKUser>>() {
      @Override
      protected void internalDone0(List<LCLKUser> lclkUsers, AVException e) {
        LCLKUser lclkUser = (null != lclkUsers && !lclkUsers.isEmpty() ? lclkUsers.get(0) : null);
        callback.internalDone(lclkUser, e);
      }
    });
  }

  /**
   * 获取多个用户的信息
   * 先从缓存中获取，若没有再调用用户回调获取
   *
   * @param idList
   * @param callback
   */
  public synchronized void getCachedUsers(List<String> idList, final AVCallback<List<LCLKUser>> callback) {
    if (null != callback) {
      if (null == idList || idList.isEmpty()) {
        callback.internalDone(null, new AVException(new Throwable("idList is empty!")));
      } else {
        final List<LCLKUser> profileList = new ArrayList<LCLKUser>();
        final List<String> unCachedIdList = new ArrayList<String>();

        for (String id : idList) {
          if (userMap.containsKey(id)) {
            profileList.add(userMap.get(id));
          } else {
            unCachedIdList.add(id);
          }
        }

        if (unCachedIdList.isEmpty()) {
          callback.internalDone(profileList, null);
        } else {
          getProfilesFromProvider(unCachedIdList, profileList, callback);
        }
      }
    }
  }

  /**
   * 根据 id 通过开发者设置的回调获取用户信息
   *
   * @param idList
   * @param callback
   */
  private void getProfilesFromProvider(List<String> idList, final List<LCLKUser> profileList,
                                       final AVCallback<List<LCLKUser>> callback) {
    LCLiveKitProvider profileProvider = LCLiveKit.getInstance().getProfileProvider();
    if (null != profileProvider) {
      profileProvider.fetchProfiles(idList, new AVCallback<List<LCLKUser>>() {
          @Override
          protected void internalDone0(List<LCLKUser> lclkUsers, AVException e) {
            if (null != lclkUsers) {
              for (LCLKUser userProfile : lclkUsers) {
                cacheUser(userProfile);
              }
            }
            profileList.addAll(lclkUsers);
            callback.internalDone(profileList, null != e ? new AVException(e) : null);
          }
        });
    } else {
      callback.internalDone(null, new AVException(new Throwable("please setProfileProvider first!")));
    }
  }

  /**
   * 内存中是否包相关 LCLKUser 的信息
   * @param id
   * @return
   */
  public synchronized boolean hasCachedUser(String id) {
    return userMap.containsKey(id);
  }

  public synchronized void cacheUser(LCLKUser userProfile) {
    if (userMap.size() > PROFILE_CACHE_MAX_SIZE) {
      userMap.clear();
    }
    userMap.put(userProfile.getUserId(), userProfile);
  }
}
