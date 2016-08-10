package cn.leancloud.leancloudlivekit.im;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import cn.leancloud.leancloudlivekit.LCLiveKit;
import cn.leancloud.leancloudlivekit.LCLKProfilesCallBack;
import cn.leancloud.leancloudlivekit.LCLKUser;
import cn.leancloud.leancloudlivekit.R;
import cn.leancloud.leancloudlivekit.barrage.BarrageLayout;
import cn.leancloud.leancloudlivekit.utils.LiveKitConstants;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 16/8/4.
 */
public class LCLKIMFragment extends Fragment {

  /**
   * recyclerView 对应的 Adapter
   */
  protected LCLKChatAdapter itemAdapter;
  protected LinearLayoutManager layoutManager;
  protected AVIMConversation imConversation;

  ImageView avatarView;
  TextView nameView;
  TextView closeView;
  BarrageLayout giftLayout;
  BarrageLayout barrageLayout;
  EditText inputView;
  TextView sendButton;
  TextView giftView;
  LinearLayout inputLayout;
  SwitchCompat barrageSwitch;
  RecyclerView recyclerView;
  TextView newMessageTipView;

  private boolean isBottom = true;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.live_im_fragment, container, false);
    initView(view);
    EventBus.getDefault().register(this);

    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    return view;
  }

  private void initView(View view) {
    avatarView = (ImageView) view.findViewById(R.id.live_im_personal_avatar_view);
    nameView = (TextView) view.findViewById(R.id.live_im_personal_name_view);
    closeView = (TextView) view.findViewById(R.id.live_im_personal_close_view);
    giftLayout = (BarrageLayout) view.findViewById(R.id.live_im_gift_barrage_view);
    barrageLayout = (BarrageLayout) view.findViewById(R.id.live_im_barrage_barrage_view);
    inputView = (EditText) view.findViewById(R.id.live_im_input_input_view);
    sendButton = (TextView) view.findViewById(R.id.live_im_input_send_view);
    giftView = (TextView) view.findViewById(R.id.live_im_input_gift_view);
    inputLayout = (LinearLayout) view.findViewById(R.id.live_im_input_layout);
    barrageSwitch = (SwitchCompat) view.findViewById(R.id.live_im_input_barrage_switch);
    recyclerView = (RecyclerView) view.findViewById(R.id.fragment_chat_rv_chat);
    newMessageTipView = (TextView) view.findViewById(R.id.fragment_chat_new_message_tip_view);

    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onSendClick();
      }
    });

    giftView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onGiftClick();
      }
    });

    newMessageTipView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onNewMessageClick();
      }
    });

    closeView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onCloseClick();
      }
    });
  }

  @Override
  public void onDestroyView() {
    EventBus.getDefault().unregister(this);
    super.onDestroyView();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initAnchorInfo();
    initIMView();

    initConversation();

    inputView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
          barrageSwitch.setVisibility(View.INVISIBLE);
          giftView.setVisibility(View.VISIBLE);
        } else {
          barrageSwitch.setVisibility(View.VISIBLE);
          giftView.setVisibility(View.INVISIBLE);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
  }

  private void initConversation() {
    if (getActivity().getIntent().hasExtra(LiveKitConstants.LIVE_CONVERSATION_KEY)) {
      String conversationId = getActivity().getIntent().getStringExtra(LiveKitConstants.LIVE_CONVERSATION_KEY);
      imConversation = LCLiveKit.getInstance().getClient().getConversation(conversationId);
      imConversation.join(new AVIMConversationCallback() {
        @Override
        public void done(AVIMException e) {

        }
      });
    } else {
      getActivity().finish();
    }
  }

  private void initAnchorInfo() {
    String clientId = LCLiveKit.getInstance().getCurrentUserId();
    LCLiveKit.getInstance().getProfileProvider().fetchProfiles(Arrays.asList(clientId), new LCLKProfilesCallBack() {
      @Override
      public void done(List<LCLKUser> userList, Exception exception) {
        if (null == exception && null != userList && userList.size() > 0) {
          final String avatar = userList.get(0).getAvatarUrl();
          if (!TextUtils.isEmpty(avatar)) {
            Picasso.with(getContext()).load(avatar).into(avatarView);
          }
          nameView.setText(userList.get(0).getUserName());
        }
      }
    });

  }

  private void initIMView() {
    layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);

    itemAdapter = new LCLKChatAdapter();
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (null != layoutManager && layoutManager.getItemCount() > 0) {
          isBottom = (layoutManager.getItemCount() == layoutManager.findLastVisibleItemPosition() + 1);
          if (isBottom) {
            newMessageTipView.setVisibility(View.GONE);
          }
        }
      }
    });
    recyclerView.setAdapter(itemAdapter);
  }

  private void onSendClick() {
    if (barrageSwitch.isChecked()) {
      sendBarrage(inputView.getText().toString());
    } else {
      sendText(inputView.getText().toString());
    }
  }

  private void onGiftClick() {
    LCLKGiftDialogFragment giftDialogFragment = new LCLKGiftDialogFragment();
    View giftItem = getActivity().getLayoutInflater().inflate(R.layout.live_gift_item, null);
    giftItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendGift("Gift");
      }
    });
    giftDialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialog) {
        inputLayout.setVisibility(View.VISIBLE);
      }
    });
    inputLayout.setVisibility(View.INVISIBLE);
    giftDialogFragment.addView(giftItem);
    giftDialogFragment.show(getFragmentManager(), "dialog");
  }

  private void onNewMessageClick() {
    scrollToBottom();
  }

  /**
   * 处理推送过来的消息
   * 同理，避免无效消息，此处加了 conversation id 判断
   */
  public void onEvent(LCLKIMMessageEvent messageEvent) {
    if (null != imConversation && null != messageEvent &&
      imConversation.getConversationId().equals(messageEvent.conversation.getConversationId())) {
      if (messageEvent.message instanceof LCLKIMBarrageMessage) {
        addBarrage((LCLKIMBarrageMessage) messageEvent.message);
      } else if (messageEvent.message instanceof LCLKGiftMessage) {
        addGiftBarrage((LCLKGiftMessage) messageEvent.message);
      } else if (messageEvent.message instanceof LCLKIMMessage) {
        itemAdapter.addDataList(Arrays.asList((LCLKIMMessage) messageEvent.message));
        itemAdapter.notifyDataSetChanged();
        if (isBottom) {
          scrollToBottom();
        } else {
          newMessageTipView.setVisibility(View.VISIBLE);
        }
      }
    }
  }

  /**
   * 滚动 recyclerView 到底部
   */
  private void scrollToBottom() {
    layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
  }

  private void sendGift(final String content) {
    LCLiveKit.getInstance().getProfileProvider().fetchProfiles(
      Arrays.asList(LCLiveKit.getInstance().getCurrentUserId()), new LCLKProfilesCallBack() {
        @Override
        public void done(List<LCLKUser> userList, Exception exception) {
          if (exception == null && null != userList && userList.size() > 0) {
            final LCLKGiftMessage message = new LCLKGiftMessage();
            message.setMessageContent(content);
            message.setName(userList.get(0).getUserName());
            message.setAvatar(userList.get(0).getAvatarUrl());

            imConversation.sendMessage(message, new AVIMConversationCallback() {
              @Override
              public void done(AVIMException e) {
                addGiftBarrage(message);
              }
            });
          }
        }
      }
    );
  }

  private void sendBarrage(final String content) {
    LCLiveKit.getInstance().getProfileProvider().fetchProfiles(
      Arrays.asList(LCLiveKit.getInstance().getCurrentUserId()), new LCLKProfilesCallBack() {
        @Override
        public void done(List<LCLKUser> userList, Exception exception) {
          if (exception == null && null != userList && userList.size() > 0) {
            final LCLKIMBarrageMessage message = new LCLKIMBarrageMessage();
            message.setMessageContent(content);
            message.setName(userList.get(0).getUserName());
            message.setAvatar(userList.get(0).getAvatarUrl());
            imConversation.sendMessage(message, new AVIMConversationCallback() {
              @Override
              public void done(AVIMException e) {
                addBarrage(message);
              }
            });
          }
        }
      }
    );
  }

  private void addBarrage(LCLKIMBarrageMessage message) {
    View view = getActivity().getLayoutInflater().inflate(R.layout.live_barrage_item, null);
    ImageView avatarView = (ImageView) view.findViewById(R.id.live_barrage_item_avatar_view);
    TextView nameView = (TextView) view.findViewById(R.id.live_barrage_item_name_view);
    TextView contentView = (TextView) view.findViewById(R.id.live_barrage_item_content_view);

    Picasso.with(getContext()).load(message.getAvatar()).into(avatarView);
    nameView.setText(message.getName());
    contentView.setText(message.getMessageContent());
    barrageLayout.sendView(view);
  }

  private void addGiftBarrage(LCLKGiftMessage message) {
    View view = getActivity().getLayoutInflater().inflate(R.layout.live_barrage_gift_item, null);
    ImageView avatarView = (ImageView) view.findViewById(R.id.live_barrage_gift_icon_view);
    TextView nameView = (TextView) view.findViewById(R.id.live_barrage_gift_name_view);
    TextView numberView = (TextView) view.findViewById(R.id.live_barrage_gift_number_view);

    avatarView.setImageResource(R.mipmap.gift_demo);
    nameView.setText(message.getName());
    if (message.getNumber() > 0) {
      numberView.setText(message.getNumber() + "");
      numberView.setVisibility(View.VISIBLE);
    }
    giftLayout.sendView(view);
  }

  /**
   * 发送文本消息
   *
   * @param content
   */
  protected void sendText(final String content) {
    LCLiveKit.getInstance().getProfileProvider().fetchProfiles(
      Arrays.asList(LCLiveKit.getInstance().getCurrentUserId()), new LCLKProfilesCallBack() {
        @Override
        public void done(List<LCLKUser> userList, Exception exception) {
          if (exception == null && null != userList && userList.size() > 0) {
            LCLKIMMessage message = new LCLKIMMessage();
            message.setMessageContent(content);
            message.setName(userList.get(0).getUserName());
            message.setAvatar(userList.get(0).getAvatarUrl());
            itemAdapter.addDataList(Arrays.asList(message));
            itemAdapter.notifyDataSetChanged();
            scrollToBottom();
            imConversation.sendMessage(message, new AVIMConversationCallback() {
              @Override
              public void done(AVIMException e) {
                itemAdapter.notifyDataSetChanged();
              }
            });
          }
        }
      });
  }

  private void onCloseClick() {
    getActivity().finish();
  }
}
