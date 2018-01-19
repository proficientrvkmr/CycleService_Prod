package com.app.sms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

public class SmsExotelSenderImpl implements ISmsExotelSender {

	private static final Logger log = LoggerFactory.getLogger(SmsExotelSenderImpl.class);
	
	@Override
	public void sendSms(String to, String from, String body, String sid, String token, String type, String url) {
		HttpClient hc = HttpClientBuilder.create().build();
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("From", from));
		postParameters.add(new BasicNameValuePair("To", to));
		log.info("Trying to send out message : " + type + ", To : " + to + ", Body : " + body);
		
		String out = body;
		try {
			out = new String(out.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			log.error("===================**************=================");
			log.error(e1.toString());
			log.error("===================**************=================");
			return;
		}
		postParameters.add(new BasicNameValuePair("Body", out));

		HttpPost post = new HttpPost(url);
		String creds = sid + ":" + token;
		System.out.println(creds);
//		post.addHeader("Authorization", "Basic " + new BASE64Encoder().encode(creds.getBytes()));
		try {
			post.setEntity(new UrlEncodedFormEntity(postParameters));
			post.setHeader("referer", "orobind");
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
	public void sendSms(String to, String body, String otp) {
		String from = "LM-MYFITB";
		String sid = "orbis1";
		String token = "";
		String type = "";
		String url = "https://twilix.exotel.in/v1/Accounts/orobind/Sms/send";
		sendSms(to, from, body, sid, token, type, url);
	}
	
	public static void main(String arg[]){
		SmsExotelSenderImpl sender = new SmsExotelSenderImpl();
		sender.sendSms("8218182198", "Hi Dear, How are you? I'm missing you.", "9565");
	}
}
