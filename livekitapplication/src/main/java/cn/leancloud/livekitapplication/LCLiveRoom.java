package cn.leancloud.livekitapplication;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by wli on 16/8/10.
 */
@AVClassName("live")
public class LCLiveRoom extends AVObject {

  private final String LC_LIVEROOM_TOPIC = "topic";
  private final String LC_LIVEROOM_TITLE = "title";
  private final String LC_LIVEROOM_LIVEID = "liveId";
  private final String LC_LIVEROOM_ANCHORID = "AnchorId";
  private final String LC_LIVEROOM_COVER = "conver";

  public LCLiveRoom(){
    super();
  }

  public LCLiveRoom(Parcel in){
    super(in);
  }

  public void setTopic(String topic) {
    this.put(LC_LIVEROOM_TOPIC, topic);
  }

  public String getTopic() {
    return this.getString(LC_LIVEROOM_TOPIC);
  }

  public void setTitle(String title) {
    this.put(LC_LIVEROOM_TITLE, title);
  }

  public String getTitle() {
    return this.getString(LC_LIVEROOM_TITLE);
  }

  public void setLiveId(String liveId) {
    this.put(LC_LIVEROOM_LIVEID, liveId);
  }

  public String getLiveId() {
    return this.getString(LC_LIVEROOM_LIVEID);
  }

  public void setAnchorId(String anchorId) {
    this.put(LC_LIVEROOM_ANCHORID, anchorId);
  }

  public String getAnchorId() {
    return this.getString(LC_LIVEROOM_ANCHORID);
  }

  public void setConver(AVFile file) {
    this.put(LC_LIVEROOM_COVER, file);
  }

  public AVFile getConver() {
    return this.getAVFile(LC_LIVEROOM_COVER);
  }

  public static final Creator CREATOR = AVObjectCreator.instance;
}
