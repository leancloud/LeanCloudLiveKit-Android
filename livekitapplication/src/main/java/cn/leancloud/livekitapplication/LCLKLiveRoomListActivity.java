package cn.leancloud.livekitapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.leancloud.leancloudlivekit.im.LCLKCommonListAdapter;
import cn.leancloud.leancloudlivekit.play.LCLKPlayActivity;
import cn.leancloud.leancloudlivekit.utils.LCLKConstants;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 16/8/5.
 * 直播间页面
 */
public class LCLKLiveRoomListActivity extends AppCompatActivity {

  @Bind(R.id.refreshable_list_srl)
  SwipeRefreshLayout refreshLayout;

  @Bind(R.id.refreshable_list_recycler)
  RecyclerView recyclerView;

  GridLayoutManager layoutManager;
  LCLKCommonListAdapter<LCLiveRoom> itemAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lclk_live_room_list_activity);
    EventBus.getDefault().register(this);
    ButterKnife.bind(this);

    layoutManager = new GridLayoutManager(this, 2);
    recyclerView.setLayoutManager(layoutManager);

    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fetchChannels();
      }
    });

    itemAdapter = new LCLKCommonListAdapter<>(LCLKLiveRoomItemHolder.class);
    recyclerView.setAdapter(itemAdapter);

    fetchChannels();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.lclk_menu_live_create, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int menuId = item.getItemId();
    if (menuId == R.id.menu_channel_start) {
      gotoStartChannelAcitivty();
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * 跳转到创建直播间的页面
   */
  private void gotoStartChannelAcitivty() {
    Intent intent = new Intent(this, LCLKLiveRoomCreateActivity.class);
    startActivity(intent);
  }

  /**
   * 拉取直播间信息
   */
  private void fetchChannels() {
    AVQuery<LCLiveRoom> avQuery = AVQuery.getQuery(LCLiveRoom.class);
    avQuery.findInBackground(new FindCallback<LCLiveRoom>() {
      @Override
      public void done(List<LCLiveRoom> list, AVException e) {
        refreshLayout.setRefreshing(false);
        itemAdapter.setDataList(list);
        itemAdapter.notifyDataSetChanged();
      }
    });
  }

  public void onEvent(LCLKLiveRoomItemClickEvent event) {
    Intent intent = new Intent(LCLKLiveRoomListActivity.this, LCLKPlayActivity.class);
    intent.putExtra(LCLKConstants.LIVE_ID, event.liveRoom.getLiveId());
    startActivity(intent);
  }
}
