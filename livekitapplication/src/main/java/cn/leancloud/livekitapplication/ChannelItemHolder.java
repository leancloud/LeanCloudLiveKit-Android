package cn.leancloud.livekitapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.squareup.picasso.Picasso;

import cn.leancloud.leancloudlivekit.LCLKUser;
import cn.leancloud.leancloudlivekit.im.LCLKCommonViewHolder;
import cn.leancloud.leancloudlivekit.im.LCLKRoundCornerImageView;
import cn.leancloud.leancloudlivekit.utils.LCLKProfileCache;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 16/8/5.
 */
public class ChannelItemHolder extends LCLKCommonViewHolder<LCLiveRoom> {

  private ImageView backgroudView;
  private TextView nameView;
  private LCLKRoundCornerImageView avatarView;
  private TextView statusView;
  private TextView descriptionView;

  private LCLiveRoom liveRoom;

  public ChannelItemHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.live_channel_item);
    backgroudView = (ImageView)itemView.findViewById(R.id.live_channle_item_bg_view);
    nameView = (TextView)itemView.findViewById(R.id.live_channle_item_name_view);
    avatarView = (LCLKRoundCornerImageView)itemView.findViewById(R.id.live_channle_item_avatar_view);
    statusView = (TextView)itemView.findViewById(R.id.live_channle_item_status_view);
    descriptionView = (TextView)itemView.findViewById(R.id.live_channle_item_description_view);

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EventBus.getDefault().post(new LiveRoomItemClickEvent(liveRoom));
      }
    });
  }

  @Override
  public void bindData(LCLiveRoom liveRoom) {
    this.liveRoom = liveRoom;
    Picasso.with(getContext()).load(liveRoom.getConver().getUrl()).into(backgroudView);
    LCLKProfileCache.getInstance().getCachedUser(liveRoom.getAnchorId(), new AVCallback<LCLKUser>() {
      @Override
      protected void internalDone0(LCLKUser lclkUser, AVException e) {
        nameView.setText(lclkUser.getUserName());
        Picasso.with(getContext()).load(lclkUser.getAvatarUrl()).into(avatarView);
      }
    });
    descriptionView.setText(liveRoom.getTitle() + " #" + liveRoom.getTopic());
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<ChannelItemHolder>() {
    @Override
    public ChannelItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new ChannelItemHolder(parent.getContext(), parent);
    }
  };
}
