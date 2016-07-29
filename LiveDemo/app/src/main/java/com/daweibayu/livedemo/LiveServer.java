package com.daweibayu.livedemo;


import com.pili.API;
import com.pili.Hub;
import com.pili.PiliException;
import com.pili.Stream;
import com.qiniu.Credentials;

/**
 * Created by wli on 16/7/19.
 */
public class LiveServer {
  // Replace with your keys here
  private static final String ACCESS_KEY = "2jJdzxcQEtnO72aej3B-HG4VP8yNUmBUTaQjoGvi";
  private static final String SECRET_KEY = "wu7iqkNhj51F6ulKNJhRTs1xWO_alVDjwDzsAd3d";

  // Replace with your hub name
  private static final String HUB_NAME = "daweibayu";


  public static Stream getStream() {
    Credentials credentials = new Credentials(ACCESS_KEY, SECRET_KEY); // Credentials Object
    Hub hub = new Hub(credentials, HUB_NAME);
    try {
      Stream stream = hub.createStream();
//      hub.getStream(stream.getStreamId());
//      stream.rtmpLiveUrls().get(Stream.ORIGIN);
//      stream.hlsLiveUrls().get(Stream.ORIGIN);
//      stream.httpFlvLiveUrls().get(Stream.ORIGIN);
      stream.rtmpPublishUrl();
      return stream;
    } catch (PiliException e) {
      e.printStackTrace();
    }
    return null;
  }
//
//  public static Stream updateStream(Stream stream) {
//    String newPublishKey       = "123456";
//    String newPublishSecurity  = "dynamic";
//    boolean newDisabled        = true;
//
//    Credentials credentials = new Credentials(ACCESS_KEY, SECRET_KEY); // Credentials Object
//    try {
//      return API.updateStream(credentials, stream.getStreamId(), newPublishKey, newPublishSecurity, newDisabled);
//    } catch (PiliException e) {
//      e.printStackTrace();
//    }
//    return null;
//  }
}
