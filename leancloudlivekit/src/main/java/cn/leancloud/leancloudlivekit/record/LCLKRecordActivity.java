package cn.leancloud.leancloudlivekit.record;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.R;
import cn.leancloud.leancloudlivekit.im.LCLKIMFragment;
import cn.leancloud.leancloudlivekit.utils.LCLKConstants;

/**
 * Created by wli on 16/8/2.
 */
public class LCLKRecordActivity extends AppCompatActivity {

  LCLKIMFragment lclkimFragment;
  LCLKRecordFragment lclkRecordFragment;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lclk_record_activity);
    lclkimFragment = (LCLKIMFragment) getSupportFragmentManager().findFragmentById(R.id.lclk_im_fragment);
    lclkRecordFragment = (LCLKRecordFragment) getSupportFragmentManager().findFragmentById(R.id.lclk_record_fragment);

    final String liveId = getIntent().getStringExtra(LCLKConstants.LIVE_ID);
    final AVIMClient client = LCLiveKit.getInstance().getClient();
    AVIMConversationQuery conversationQuery = client.getQuery();
    conversationQuery.whereEqualTo("name", liveId);
    conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
      @Override
      public void done(List<AVIMConversation> list, AVIMException e) {
        if (null != list && list.size() > 0) {
          lclkimFragment.setConversation(list.get(0));
        } else {
          createConversation(client, liveId);
        }
      }
    });
  }

  //TODO 根据 liveId 获取 stream
  private void getStreamInfo(String liveId) {
    lclkRecordFragment.setStream("");
  }

  private void createConversation(AVIMClient client, String liveId) {
    client.createConversation(new ArrayList<String>(), liveId, null, false, true, new AVIMConversationCreatedCallback() {
      @Override
      public void done(AVIMConversation avimConversation, AVIMException e) {
        lclkimFragment.setConversation(avimConversation);
      }
    });
  }
}
