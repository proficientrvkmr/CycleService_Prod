/*
 *  Copyright 2015 OROBIND (P) Limited . All Rights Reserved.
 *  OROBIND PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 03-Mar-2015
 *  @author shubhanshu
 */
package com.orobind.services.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("smsSolutionsInfiniSender")
public class SmsSolutionsInfiniSenderImpl implements ISmsSolutionsInfiniSender {

    private static final Logger LOG = LoggerFactory.getLogger(SmsSolutionsInfiniSenderImpl.class);

    @Override
    public void sendSms(String to, String from, String body, String apiKey, String type) {

        StringBuffer urlBuffer = new StringBuffer();
urlBuffer.append("http://alerts.solutionsinfini.com/api/v3/index.php?method=sms");
        try {
            urlBuffer.append("&api_key=" + URLEncoder.encode(apiKey, "UTF-8"));
            urlBuffer.append("&to=" + URLEncoder.encode(to, "UTF-8"));
            urlBuffer.append("&sender=" + URLEncoder.encode(from, "UTF-8"));
            urlBuffer.append("&message=" + URLEncoder.encode(body, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return;
        }
        urlBuffer.append("&unicode=1");

        String url = urlBuffer.toString();

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        LOG.info("Trying to send out message : " + type + ", To : " + to + ", Body : " + body);
        LOG.info("Sending 'GET' request to URL : " + url);
        try {
            HttpResponse response = client.execute(request);
            LOG.info("SMS Send Status : " + " status : " + response.getStatusLine().getStatusCode() + " type : " + type + ", To : " + to + ", Body : " + body);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            //  System.out.println(result.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

