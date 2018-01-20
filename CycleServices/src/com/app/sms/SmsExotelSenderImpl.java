package com.app.sms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.util.ApplicationConstant;

public class SmsExotelSenderImpl implements ISmsExotelSender {

	private static final Logger log = LoggerFactory.getLogger(SmsExotelSenderImpl.class);
	
	@Override
	public void sendSms(String to, String from, String body, String sid, String token, String type) {
		HttpClient hc = HttpClientBuilder.create().build();
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("From", from));
		postParameters.add(new BasicNameValuePair("To", to));
		log.info("Trying to send out message : " + type + ", To : " + to + ", Body : " + body);
		
		String out = "";
		try {
			out = new String(body.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			log.error("===================**************=================");
			log.error(e1.toString());
			log.error("===================**************=================");
			return;
		}
		postParameters.add(new BasicNameValuePair("Body", out));
		
        String authStr = sid + ":" + token;
        String url = "https://" + 
                        authStr + "@twilix.exotel.in/v1/Accounts/" + 
                        sid + "/Sms/send"; 
        System.out.println(url);
		byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
        String authStringEnc = new String(authEncBytes);
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", "Basic " + authStringEnc);
        try {
            post.setEntity(new UrlEncodedFormEntity(postParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("===================**************=================");
			log.error(e.toString());
			log.error("===================**************=================");
        }
		
		
		try {
			HttpResponse response = hc.execute(post);
			int httpStatusCode = response.getStatusLine().getStatusCode();
			log.info("SMS Send Status : " + " status : " + httpStatusCode + " type : " + type + ", To : " + to
					+ ", Body : " + body);
			 System.out.println(httpStatusCode + " is the status code");
			HttpEntity entity = response.getEntity();
			 System.out.println(EntityUtils.toString(entity));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===================**************=================");
			log.error(e.toString());
			log.error("===================**************=================");
		}
	}
	
	@Override
	public void sendSms(String to, String body) {
		String from = "MYFITB";
		String sid = "orbis1";
		String token = "6805ddd2656eb9a025ac1a932c6378dc46a0b13f";
		String type = "OTP";
		sendSms(to, from, body, sid, token, type);
	}
	
	public static void main(String arg[]){
		SmsExotelSenderImpl sender = new SmsExotelSenderImpl();
		String to = "7827760202";
		String body = ApplicationConstant.smsMessageBody;
		String smsText = String.format(body, "9586");
		sender.sendSms(to, smsText);
	}
}
