package com.app.sms;

public interface ISmsExotelSender {

    void sendSms(String to, String from, String body, String sid, String token, String type);
    
    void sendSms(String to, String body);

}
