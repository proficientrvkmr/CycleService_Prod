/*
 *  Copyright 2015 OROBIND (P) Limited . All Rights Reserved.
 *  OROBIND PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 03-Mar-2015
 *  @author shubhanshu
 */
package com.orobind.services.sms;

public interface ISmsSolutionsInfiniSender {

    void sendSms(String to, String from, String body, String apiKey, String type);

}
