package com.app.sms;

public interface ISmsSolutionsInfiniSender {

    void sendSms(String to, String from, String body, String apiKey, String type);

}
