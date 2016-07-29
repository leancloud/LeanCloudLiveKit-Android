package com.daweibayu.livedemo.im;

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
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.daweibayu.livedemo.IMPlayActivity;
import com.daweibayu.livedemo.play.LiveGiftMessage;
import com.daweibayu.livedemo.R;
import com.daweibayu.livedemo.im.barrage.BarrageLayout;
import com.daweibayu.livedemo.im.bottomdialog.LiveGiftClickEvent;
import com.daweibayu.livedemo.im.bottomdialog.LiveGiftDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 16/7/26.
 */
public class LiveIMFragment extends Fragment {

  /**
   * recyclerView 对应的 Adapter
   */
  protected LCLiveChatAdapter itemAdapter;
  protected LinearLayoutManager layoutManager;
  protected AVIMConversation imConversation;

  @Bind(R.id.live_im_personal_avatar_view)
  ImageView avatarView;

  @Bind(R.id.live_im_personal_name_view)
  TextView nameView;

  @Bind(R.id.live_im_personal_close_view)
  TextView closeView;

  @Bind(R.id.live_im_gift_barrage_view)
  BarrageLayout giftLayout;

  @Bind(R.id.live_im_barrage_barrage_view)
  BarrageLayout barrageLayout;

  @Bind(R.id.live_im_input_input_view)
  EditText inputView;

  @Bind(R.id.live_im_input_send_view)
  TextView sendButton;

  @Bind(R.id.live_im_input_gift_view)
  TextView giftView;

  @Bind(R.id.live_im_input_barrage_switch)
  SwitchCompat barrageSwitch;

  @Bind(R.id.fragment_chat_rv_chat)
  RecyclerView recyclerView;

  @Bind(R.id.fragment_chat_new_message_tip_view)
  TextView newMessageTipView;

  private boolean isBottom = true;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.live_im_fragment, container, false);
    ButterKnife.bind(this, view);
    EventBus.getDefault().register(this);

    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    return view;
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
    if (getActivity().getIntent().hasExtra(IMPlayActivity.LIVE_CONVERSATION_ID_KEY)) {
      String conversationId = getActivity().getIntent().getStringExtra(IMPlayActivity.LIVE_CONVERSATION_ID_KEY);
      imConversation = LCChatKit.getInstance().getClient().getConversation(conversationId);
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
    String clientId = LCChatKit.getInstance().getCurrentUserId();
    LCChatKit.getInstance().getProfileProvider().fetchProfiles(Arrays.asList(clientId), new LCChatProfilesCallBack() {
      @Override
      public void done(List<LCChatKitUser> userList, Exception exception) {
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

    itemAdapter = new LCLiveChatAdapter();
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

  @OnClick(R.id.live_im_input_send_view)
  public void onSendClick(View view) {
    if (barrageSwitch.isChecked()) {
      sendBarrage(inputView.getText().toString());
    } else {
      sendText(inputView.getText().toString());
    }
  }

  @OnClick(R.id.live_im_input_gift_view)
  public void onGiftClick(View view) {
    LiveGiftDialogFragment giftDialogFragment = new LiveGiftDialogFragment();
    giftDialogFragment.show(getFragmentManager(), "dialog");
  }

  @OnClick(R.id.fragment_chat_new_message_tip_view)
  public void onNewMessageClick(View view) {
    scrollToBottom();
  }

  /**
   * 处理推送过来的消息
   * 同理，避免无效消息，此处加了 conversation id 判断
   */
  public void onEvent(LCIMIMTypeMessageEvent messageEvent) {
    if (null != imConversation && null != messageEvent &&
      imConversation.getConversationId().equals(messageEvent.conversation.getConversationId())) {
      if (messageEvent.message instanceof LiveBarrageMessage) {
        addBarrage((LiveBarrageMessage) messageEvent.message);
      } else if (messageEvent.message instanceof LiveGiftMessage) {
        addGiftBarrage((LiveGiftMessage)messageEvent.message);
      } else if (messageEvent.message instanceof LCLiveIMMessage) {
        itemAdapter.addDataList(Arrays.asList((LCLiveIMMessage) messageEvent.message));
        itemAdapter.notifyDataSetChanged();
        if (isBottom) {
          scrollToBottom();
        } else {
          newMessageTipView.setVisibility(View.VISIBLE);
        }
      }
    }
  }



  public void onEvent(LiveGiftClickEvent giftClickEvent) {
    if (null != imConversation) {
      sendGift(giftClickEvent.giftMessage.getMessageContent());
    }
  }

  /**
   * 滚动 recyclerView 到底部
   */
  private void scrollToBottom() {
    layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
  }

  private void sendGift(final String content) {
    LCChatKit.getInstance().getProfileProvider().fetchProfiles(
      Arrays.asList(LCChatKit.getInstance().getCurrentUserId()), new LCChatProfilesCallBack() {
        @Override
        public void done(List<LCChatKitUser> userList, Exception exception) {
          if (exception == null && null != userList && userList.size() > 0) {
            final LiveGiftMessage message = new LiveGiftMessage();
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
    LCChatKit.getInstance().getProfileProvider().fetchProfiles(
      Arrays.asList(LCChatKit.getInstance().getCurrentUserId()), new LCChatProfilesCallBack() {
        @Override
        public void done(List<LCChatKitUser> userList, Exception exception) {
          if (exception == null && null != userList && userList.size() > 0) {
            final LiveBarrageMessage message = new LiveBarrageMessage();
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

  private void addBarrage(LiveBarrageMessage message) {
    View view = getActivity().getLayoutInflater().inflate(R.layout.live_barrage_item, null);
    ImageView avatarView = (ImageView) view.findViewById(R.id.live_barrage_item_avatar_view);
    TextView nameView = (TextView)view.findViewById(R.id.live_barrage_item_name_view);
    TextView contentView = (TextView) view.findViewById(R.id.live_barrage_item_content_view);

    Picasso.with(getContext()).load(message.getAvatar()).into(avatarView);
    nameView.setText(message.getName());
    contentView.setText(message.getMessageContent());
    barrageLayout.sendView(view);
  }

  private void addGiftBarrage(LiveGiftMessage message) {
    View view = getActivity().getLayoutInflater().inflate(R.layout.live_barrage_gift_item, null);
    ImageView avatarView = (ImageView) view.findViewById(R.id.live_barrage_gift_icon_view);
    TextView nameView = (TextView)view.findViewById(R.id.live_barrage_gift_name_view);
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
    LCChatKit.getInstance().getProfileProvider().fetchProfiles(
      Arrays.asList(LCChatKit.getInstance().getCurrentUserId()), new LCChatProfilesCallBack() {
        @Override
        public void done(List<LCChatKitUser> userList, Exception exception) {
          if (exception == null && null != userList && userList.size() > 0) {
            LCLiveIMMessage message = new LCLiveIMMessage();
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

  @OnClick(R.id.live_im_personal_close_view)
  public void onCloseClick(View view) {
    getActivity().finish();
  }
}
