package cn.leancloud.leancloudlivekit.play;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.List;

import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.LCLiveKitProvider;
import cn.leancloud.leancloudlivekit.R;
import cn.leancloud.leancloudlivekit.im.LCLKIMFragment;
import cn.leancloud.leancloudlivekit.utils.LCLKConstants;

/**
 * Created by wli on 16/8/5.
 */
public class LCLKPlayActivity extends AppCompatActivity {

  LCLKIMFragment lclkimFragment;
  LCLKPlayFragment lclkPlayFragment;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lclk_play_activity);

    lclkimFragment = (LCLKIMFragment) getSupportFragmentManager().findFragmentById(R.id.lclk_im_fragment);
    lclkPlayFragment = (LCLKPlayFragment) getSupportFragmentManager().findFragmentById(R.id.lclk_play_fragment);

    final String liveId = getIntent().getStringExtra(LCLKConstants.LIVE_ID);
    initConversation(liveId);
    initPlayFragment(liveId);
  }

  public void initPlayFragment(final String liveId) {
    LCLiveKitProvider provider = LCLiveKit.getInstance().getProfileProvider();
    if (null != provider) {
      provider.fetchPlayStream(liveId, new AVCallback<String>() {
        @Override
        protected void internalDone0(String s, AVException e) {
          if (null == e && !TextUtils.isEmpty(s)) {
            lclkPlayFragment.startLive(s);
          }
        }
      });
    }
  }

  private void initConversation(String liveId) {
    AVIMClient client = LCLiveKit.getInstance().getClient();
    AVIMConversationQuery conversationQuery = client.getQuery();
    conversationQuery.whereEqualTo("name", liveId);
    conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
      @Override
      public void done(List<AVIMConversation> list, AVIMException e) {
        if (null != list && list.size() > 0) {
          lclkimFragment.setConversation(list.get(0));
        }
      }
    });
  }
}
