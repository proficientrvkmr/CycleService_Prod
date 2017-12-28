/*
 *  Copyright 2015 OROBIND (P) Limited . All Rights Reserved.
 *  OROBIND PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 03-Mar-2015
 *  @author shubhanshu
 */
package com.orobind.services.sms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

@Service("smsExotelSender")
public class SmsExotelSenderImpl implements ISmsExotelSender {

    private static final Logger LOG = LoggerFactory.getLogger(SmsExotelSenderImpl.class);
    
    @Override
    public void sendSms(String to, String from, String body, String sid, String token, String type) {
      DefaultHttpClient hc = new DefaultHttpClient();
      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
      postParameters.add(new BasicNameValuePair("From", from));
      postParameters.add(new BasicNameValuePair("To", to));
      LOG.info("Trying to send out message : " + type + ", To : " + to + ", Body : " + body);
      String out = body;
      try {
          out = new String(out.getBytes("UTF-8"), "ISO-8859-1");
      } catch (UnsupportedEncodingException e1) {
          return;
      }
      postParameters.add(new BasicNameValuePair("Body", out));
      
      HttpPost post = new HttpPost("https://twilix.exotel.in/v1/Accounts/orobind/Sms/send");
      String creds = sid+ ":" + token;
      post.addHeader("Authorization", "Basic " + new BASE64Encoder().encode(creds.getBytes()));     
      try {
          post.setEntity(new UrlEncodedFormEntity(postParameters));
          post.setHeader("referer", "orobind");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      try {
          HttpResponse response = hc.execute(post);
          int httpStatusCode = response.getStatusLine().getStatusCode();         
          LOG.info("SMS Send Status : " + " status : " + httpStatusCode + " type : " + type + ", To : " + to + ", Body : " + body);
          //System.out.println(httpStatusCode + " is the status code");
          HttpEntity entity = response.getEntity();
          //System.out.println(EntityUtils.toString(entity));
      } catch (Exception e) {
          e.printStackTrace();
      }

    }

}
