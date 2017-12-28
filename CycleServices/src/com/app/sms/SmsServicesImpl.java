/*
 *  Copyright 2015 OROBIND (P) Limited . All Rights Reserved.
 *  OROBIND PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 03-Mar-2015
 *  @author shubhanshu
 */
package com.orobind.services.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.orobind.base.cache.CacheManager;
import com.orobind.base.utils.DateUtils;
import com.orobind.base.utils.StringUtils;
import com.orobind.services.cache.SmsTemplateCache;
import com.orobind.services.cache.SystemPropertiesCache;

@Service("smsServices")
public class SmsServicesImpl implements ISmsServices {

    @Autowired
    ISmsExotelSender smsExotelSender;

    @Autowired
    ISmsSolutionsInfiniSender smsSolutionsInfiniSender;

    @Override
    @Async
    public void sendSessionFeedbackSMS(String userNumber, String coachName, String userName) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String apiKey = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.solutionsinfini.apikey");
        String senderId = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.solutionsinfini.sender.id");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sessionFeedbackSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sessionFeedbackSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "");
        } else {
            body = body.replace("%z", contact);
        }
        if ("Y".equals(CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.is.solutionsinfini.live"))) {
            smsSolutionsInfiniSender.sendSms(userNumber, senderId, body, apiKey, "sessionFeedbackSMS");
        }
        else{
            smsExotelSender.sendSms(userNumber, from, body, sid, token, "sessionFeedbackSMS");
        }
    }

    @Override
    @Async
    public void sendSessionBookingSMS(String userNumber, String userName) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sessionBookingSMS").getBodyTemplate();
        //body.replace("${coachName}", coachName);

        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }

        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sessionBookingSMS");
    }

    @Override
    @Async
    public void sendRequestACallSMS(String userNumber, String userName) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("requestACallSMS").getBodyTemplate();
        //body.replace("${coachName}", coachName);

        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "requestACallSMS");
    }

    @Override
    //@Async
    public void sendInvoiceGeneratedSMS(String userNumber, String userName, Integer amount) {
        if (amount == null || amount <= 0) {
            return;
        }
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentInvoiceGenerated").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentInvoiceGenerated").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "");
        } else {
            body = body.replace("%z", contact);
        }
        body = body.replace("%d", amount.toString());
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "paymentInvoiceGenerated");
    }

    @Override
    //@Async
    public void sendInvoiceGeneratedSMSWithPromo(String userNumber, String userName, Integer amount, String promo) {
        if (amount == null || amount <= 0) {
            return;
        }
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentInvoiceGeneratedWithPromo").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentInvoiceGeneratedWithPromo").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "");
        } else {
            body = body.replace("%z", contact);
        }
        body = body.replace("%p", promo);
        body = body.replace("%d", amount.toString());
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "paymentInvoiceGeneratedWithPromo");
    }

    @Override
    @Async
    public void sendPaymentReceivedSMS(String userNumber, String userName, Integer amount) {
        if (amount == null || amount <= 0) {
            return;
        }
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentReceivedSuccess").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentReceivedSuccess").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "");
        } else {
            body = body.replace("%z", contact);
        }
        body = body.replace("%d", amount.toString());
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "paymentReceivedSuccess");
    }

    @Override
    @Async
    public void sendOntheWaySMSToUser(String userNumber, String userName, String coachName) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("ontheWaySMSUser").getBodyTemplate();
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(coachName)) {
            body = body.replace("%c", "Coach");
        } else {
            body = body.replace("%c", coachName);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "ontheWaySMSUser");
    }

    @Override
    @Async
    public void sendFreeSessionConfirmationSms(String userNumber, String userName, String dateStr, String coachName, String detailedNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("freeSessionConfirmUserSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("freeSessionConfirmUserSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(dateStr)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr);
        }
        //  String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        if (StringUtils.isEmpty(coachName)) {
            body = body.replace("%c", "Coach");
        } else {
            body = body.replace("%c", coachName);
        }
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "freeSessionConfirmUserSMS");
    }

    @Override
    @Async
    public void sendFreeSessionTrainerSms(String trainerNumber, String userName, String dateStr, String timings, String userAddress, String userMobile) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        // String adminMobile = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.freesession.admin.number");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("freeSessionConfirmTrainerSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("freeSessionConfirmTrainerSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");

        if (StringUtils.isEmpty(userAddress)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", userAddress);
        }
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%u", "");
        } else {
            body = body.replace("%u", userName);
        }
        if (StringUtils.isEmpty(userMobile)) {
            body = body.replace("%m", "");
        } else {
            body = body.replace("%m", userMobile);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "");
        } else {
            body = body.replace("%z", contact);
        }

        if (StringUtils.isEmpty(dateStr)) {
            body = body.replace("%d", "");
        } else {
            if (DateUtils.isSameDay(DateUtils.getCurrentDate(), DateUtils.stringToDate(dateStr, "dd/MM/yyyy"))) {
                body = body.replace("%d", timings + " on today");
            } else if (DateUtils.isSameDay(DateUtils.getCurrentDate(), org.apache.commons.lang.time.DateUtils.addDays(DateUtils.stringToDate(dateStr, "dd/MM/yyyy"), 1))) {
                body = body.replace("%d", timings + " on tomorrow");
            } else {
                body = body.replace("%d", timings + " on " + dateStr);
            }
        }
        smsExotelSender.sendSms(trainerNumber, from, body, sid, token, "freeSessionConfirmTrainerSMS");
    }

    @Override
    @Async
    public void sendCheckInSMSToUser(String userNumber, String userName, String coachName) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        // String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("checkInSMSUser").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("checkInSMSUser").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "checkInSMSUser");
    }

    @Override
    @Async
    public void sendTwoHourPriorSmsToSession(String userName, String userNumber, String dateStr, String detailedNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendTwoHoursPriorSMSUser").getBodyTemplate();

        // String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendTwoHoursPriorSMSUser").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");

        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(dateStr)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendTwoHoursPriorSMSUser");
    }

    @Override
    @Async
    public void sendOnMyWaySms(String userName, String userNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("ontheWaySMSUser").getBodyTemplate();
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "ontheWaySMSUser");
    }

    @Override
    @Async
    public void sendAfterFreeSessionSMS(String userName, String userNumber, String detailedNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("afterFreeSessionSMSUser").getBodyTemplate();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%d", "9972966515");
        } else {
            body = body.replace("%d", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "afterFreeSessionSMSUser");
    }

    @Override
    @Async
    public void sendRescheduleRequestSMS(String userName, String userNumber, String dateStr1, String dateStr2, String number) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendRescheduleRequestUserSMS").getBodyTemplate();

        //String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendRescheduleRequestUserSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(dateStr1)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr1);
        }
        if (StringUtils.isEmpty(dateStr2)) {
            body = body.replace("%b", "");
        } else {
            body = body.replace("%b", dateStr2);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendRescheduleRequestUserSMS");
    }

    @Override
    @Async
    public void sendRescheduleRequestAcceptSMS(String userName, String userNumber, String dateStr1, String dateStr2) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendRescheduleRequestAcceptUserSMS").getBodyTemplate();
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(dateStr1)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr1);
        }
        if (StringUtils.isEmpty(dateStr2)) {
            body = body.replace("%b", "");
        } else {
            body = body.replace("%b", dateStr2);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendRescheduleRequestAcceptUserSMS");
    }

    @Override
    @Async
    public void sendSessionCancelSMS(String userName, String coachName, String userNumber, String dateStr1) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendSessionCancelSMS").getBodyTemplate();

        //String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendSessionCancelSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(coachName)) {
            body = body.replace("%c", "Coach");
        } else {
            body = body.replace("%c", coachName);
        }
        if (StringUtils.isEmpty(dateStr1)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr1);
        }
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "user");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendSessionCancelSMS");
    }

    @Override
    @Async
    public void sendSessionCoachCancelSMS(String userName, String coachName, String coachNumber, String dateStr1) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendSessionCoachCancelSMS").getBodyTemplate();
        // String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendSessionCoachCancelSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");

        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(coachName)) {
            body = body.replace("%c", "Coach");
        } else {
            body = body.replace("%c", coachName);
        }
        if (StringUtils.isEmpty(dateStr1)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr1);
        }
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "user");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(coachNumber, from, body, sid, token, "sendSessionCoachCancelSMS");
    }

    @Override
    @Async
    public void sendRescheduleRequestCoachAcceptSMS(String coachName, String userName, String userNumber, String dateStr1, String dateStr2) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendRescheduleRequestCoachAcceptSMS").getBodyTemplate();
        // String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendRescheduleRequestCoachAcceptSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(coachName)) {
            body = body.replace("%c", "Coach");
        } else {
            body = body.replace("%c", coachName);
        }
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(dateStr1)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr1);
        }
        if (StringUtils.isEmpty(dateStr2)) {
            body = body.replace("%b", "");
        } else {
            body = body.replace("%b", dateStr2);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendRescheduleRequestCoachAcceptSMS");
    }

    @Override
    @Async
    public void sendRescheduleRequestRejectSMS(String userName, String userNumber, String dateStr1) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendRescheduleRequestRejectUserSMS").getBodyTemplate();
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(dateStr1)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr1);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendRescheduleRequestRejectUserSMS");
    }

    @Override
    @Async
    public void sendCallNotPickedUpRequest(String userName, String userNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("callNotPickedUpRequest").getBodyTemplate();
        //String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("callNotPickedUpRequest").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "callNotPickedUpRequest");
    }

    @Override
    @Async
    public void sendAdminRescheduleRequest(String userName, String userNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sessionRsdAdminNotification").getBodyTemplate();
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "sessionRsdAdminNotification");
    }

    @Override
    @Async
    public void sendCancelRequestSMS(String userName, String userNumber, String dateStr) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String apiKey = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.solutionsinfini.apikey");
        String senderId = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.solutionsinfini.sender.id");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendCancelRequestSMS").getBodyTemplate();
        //String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("default.admin.number");
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("sendCancelRequestSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");

        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "User");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(dateStr)) {
            body = body.replace("%a", "");
        } else {
            body = body.replace("%a", dateStr);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        if ("Y".equals(CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.is.solutionsinfini.live"))) {
            smsSolutionsInfiniSender.sendSms(userNumber, senderId, body, apiKey, "sendRescheduleRequestUserSMS");
        }
        else{
            smsExotelSender.sendSms(userNumber, from, body, sid, token, "sendRescheduleRequestUserSMS");
        }
    }

    /*@Override
    @Async
    public void sendUserAccessPoinCodeSMS(String userName, String userNumber, String accessPointName, String code) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
    <<<<<<< HEAD
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessPoinCodeSMS").getBodyTemplate();
        //body.replace("${coachName}", coachName);
        if (StringUtils.isEmpty(accessPointName) || StringUtils.isEmpty(code)) {
            return;
        }
    ||||||| merged common ancestors
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("mobileOnlyCampaignSMS").getBodyTemplate();      
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "mobileOnlyCampaignSMS");
    }
    /*
     * 
     * 
    1. Free Session Booking: SMS - Need some dev in CRM
    
    To be sent when a new user is created via the panel with an additional param like "IsTrial" or through app directly.
    
    Dear %s, We have received your request for the free session. Your Orobind coach would be there at the requested time. Details of your coach would be sent to your email id 24 hours prior to your session. You may call %d in case of any changes/queries.
    
    
    2. Free Session Confirmation: SMS + Mail  - Immediate
    
    To be Sent one day prior to session date, when the session scheduling happens.
    
    Dear %s, Your free session is confirmed for "Date" at "Time" with Orobind Coach %s. Details have been sent to your email id. Please call on %d for any changes/queries.
    
    
    3.  2 Hours Prior to Session: SMS - Immediate
    
    Dear %s, Your session is all set for "Time". Have a great one. Please call on %d for any changes/queries.
    
    
    4. On my way: P/N + SMS (Same) - Immediate (API Integration from Coach App is required)
    
    Dear %s, Your Orobind coach is on the way and would reach you shortly. Have a great workout.
    
    
    5. Running Late: P/N + SMS (Same) - Feature Development Required
    
    To be sent 8 minutes after session start time.
    
    Dear %s, It seems your coach is running late. Apologies; Let us fix it ASAP.
    
    6. Checkin: P/N (Same) - Immediate (API Integration from Coach App is required)
    
    Hey %s, Your Orobind Coach is at your doorsteps. Please call on %d if that is not the case.
    
    7. Session Finish: SMS + P/N + Mail (Buyer - Immediate
    
    To be sent immediately on session ending.
    
    Hey %s, Had a great workout? Go ahead and continue. An email has been sent to you with further details, or you may purchase it directly from the app. Please call on %d in case of any queries.
    
    8. Discount Follow up: SMS + P/N - Feature Development Required
    
    To be set 15 minutes after session end time.
    
    and one last thing, you get a 500 Rs. discount if you complete the purchase by "sessionStartTime + 5 Hours". Please let us know if you need any assistance by calling on %d.
    
    9. Purchase confirmation. SMS + P/N + Mail - Immediate
    
    Coach assignment done as accurately as possible and payment cycle completed. Or if there is an inventory failure, a dummy coach comes up, and the CTA is a booking request instead.
    
    Hey %s, Welcome aboard. Your subscription is confirmed, and starts from startDate for slotType at timeSlot. The validity of the subscription is 5n weeks. " Please give a missed call to %d whenever you need to speak with us, and we will get back real quick. "
    
    10. Diet Call Scheduling: P/N -Feature Development Required
    
    30 mins after Purchase confirmation.
    
    Please schedule your diet call with the dietitian. You may do so by completing your profile in the app and choosing a convenient time for the call.
    
    11. Session Finish (Subscription): P/N Immediate
    
    Hope you had a great workout. Please complete the session feedback and improve your next workout.
    
    12. Referral Joinee: SMS + P/N Feature Development in Progress
    
    Hey %s, Your friend %s has joined in. Thanks for making the Orobind Community bigger and stronger. Your referral discount will be applied to the next invoice.
    
    13. Payment Confirmation (Any): SMS + P/N + Mail (Invoice) - Immediate
    
    Hey %s, We have received your payment for INR %d. Thank you. Please call on %d for any queries.
    
    14. Renewal: SMS + P/N + Mail (Subscription snapshot) - Immediate
    
    Hey %s, Your subscription has been renewed. The sessions have been added to your profile. Please let us know if there are any issues by calling on %d.
    =======
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("mobileOnlyCampaignSMS").getBodyTemplate();      
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "mobileOnlyCampaignSMS");
    } 
    /*
     * 
     * 
    1. Free Session Booking: SMS - Need some dev in CRM
    
    To be sent when a new user is created via the panel with an additional param like "IsTrial" or through app directly.
    
    Dear %s, We have received your request for the free session. Your Orobind coach would be there at the requested time. Details of your coach would be sent to your email id 24 hours prior to your session. You may call %d in case of any changes/queries.
    
    
    2. Free Session Confirmation: SMS + Mail  - Immediate
    
    To be Sent one day prior to session date, when the session scheduling happens.
    
    Dear %s, Your free session is confirmed for "Date" at "Time" with Orobind Coach %s. Details have been sent to your email id. Please call on %d for any changes/queries.
    
    
    3.  2 Hours Prior to Session: SMS - Immediate
    
    Dear %s, Your session is all set for "Time". Have a great one. Please call on %d for any changes/queries.
    
    
    4. On my way: P/N + SMS (Same) - Immediate (API Integration from Coach App is required)
    
    Dear %s, Your Orobind coach is on the way and would reach you shortly. Have a great workout.
    
    
    5. Running Late: P/N + SMS (Same) - Feature Development Required
    
    To be sent 8 minutes after session start time.
    
    Dear %s, It seems your coach is running late. Apologies; Let us fix it ASAP.
    
    6. Checkin: P/N (Same) - Immediate (API Integration from Coach App is required)
    
    Hey %s, Your Orobind Coach is at your doorsteps. Please call on %d if that is not the case.
    
    7. Session Finish: SMS + P/N + Mail (Buyer - Immediate
    
    To be sent immediately on session ending.
    
    Hey %s, Had a great workout? Go ahead and continue. An email has been sent to you with further details, or you may purchase it directly from the app. Please call on %d in case of any queries.
    
    8. Discount Follow up: SMS + P/N - Feature Development Required
    
    To be set 15 minutes after session end time.
    
    and one last thing, you get a 500 Rs. discount if you complete the purchase by "sessionStartTime + 5 Hours". Please let us know if you need any assistance by calling on %d.
    
    9. Purchase confirmation. SMS + P/N + Mail - Immediate
    
    Coach assignment done as accurately as possible and payment cycle completed. Or if there is an inventory failure, a dummy coach comes up, and the CTA is a booking request instead.
    
    Hey %s, Welcome aboard. Your subscription is confirmed, and starts from startDate for slotType at timeSlot. The validity of the subscription is 5n weeks. " Please give a missed call to %d whenever you need to speak with us, and we will get back real quick. "
    
    10. Diet Call Scheduling: P/N -Feature Development Required
    
    30 mins after Purchase confirmation.
    
    Please schedule your diet call with the dietitian. You may do so by completing your profile in the app and choosing a convenient time for the call.
    
    11. Session Finish (Subscription): P/N Immediate
    
    Hope you had a great workout. Please complete the session feedback and improve your next workout.
    
    12. Referral Joinee: SMS + P/N Feature Development in Progress
    
    Hey %s, Your friend %s has joined in. Thanks for making the Orobind Community bigger and stronger. Your referral discount will be applied to the next invoice.
    
    13. Payment Confirmation (Any): SMS + P/N + Mail (Invoice) - Immediate
    
    Hey %s, We have received your payment for INR %d. Thank you. Please call on %d for any queries.
    
    14. Renewal: SMS + P/N + Mail (Subscription snapshot) - Immediate
    
    Hey %s, Your subscription has been renewed. The sessions have been added to your profile. Please let us know if there are any issues by calling on %d.
    >>>>>>> SessionGrpAndPaymentFormalV2
    
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        body = body.replace("%g", accessPointName);
        body = body.replace("%c", code);
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessPoinCodeSMS");
    }*/

    @Override
    @Async
    public void sendUserAccessSubscriptionRenewalOptions(String userName, String userNumber, String dateStr, String defaultEmail, String expiryDate, Integer amount) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSubscriptionRenewal").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSubscriptionRenewal").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");

        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        body = body.replace("%g", dateStr);
        body = body.replace("%e", defaultEmail);
        body = body.replace("%h", expiryDate);
        body = body.replace("%a", amount.toString());
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessSubscriptionRenewal");
    }

    @Override
    @Async
    public void sendUserAccessSubscriptionRenewalOptionsFree(String userName, String userNumber, String dateStr, String defaultEmail, String expiryDate, Integer amount) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSubscriptionRenewalFree").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSubscriptionRenewalFree").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");

        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        body = body.replace("%g", dateStr);
        body = body.replace("%e", defaultEmail);
        body = body.replace("%h", expiryDate);
        body = body.replace("%a", amount.toString());
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessSubscriptionRenewalFree");
    }

    @Override
    @Async
    public void sendUserAccessCreatedSMS(String userName, String userNumber, String dateStr) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessPoinCodeSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessPoinCodeSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        body = body.replace("%g", dateStr);
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessPoinCodeSMS");
    }

    @Override
    @Async
    public void sendFeedbackAccessSessionSMS(String userName, String userNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessPoinCodeSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessPoinCodeSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessPoinCodeSMS");
    }

    @Override
    @Async
    public void sendCancelAccessSessionSMS(String userName, String userNumber, String dateStr) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSessionCancelledSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSessionCancelledSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        body = body.replace("%g", dateStr);
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessSessionCancelledSMS");
    }

    @Override
    @Async
    public void sendRsdAccessSessionSMS(String userName, String userNumber, String dateStr1, String dateStr2) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSessionRsdSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("userAccessSessionRsdSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");
        if (StringUtils.isEmpty(userName)) {
            body = body.replace("%s", "There");
        } else {
            body = body.replace("%s", userName);
        }
        body = body.replace("%g", dateStr1);
        body = body.replace("%h", dateStr1);
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "userAccessSessionRsdSMS");
    }

    @Override
    @Async
    public void sendMobileOnlyCampaignSMS(String userNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("mobileOnlyCampaignSMS").getBodyTemplate();
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "mobileOnlyCampaignSMS");

    }
    
    @Override
    @Async
    public void sendPaymentReminderSMS(String userNumber) {
        String sid = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.sid");
        String token = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.token");
        String from = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms.exotel.from");
        String body = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentReminderSMS").getBodyTemplate();
        String type = CacheManager.getInstance().getCache(SmsTemplateCache.class).getSmsTemplates().get("paymentReminderSMS").getType();
        String contact = CacheManager.getInstance().getCache(SystemPropertiesCache.class).getProperty("sms." + type + ".query.contact");       
        if (StringUtils.isEmpty(contact)) {
            body = body.replace("%z", "9972966515");
        } else {
            body = body.replace("%z", contact);
        }     
        smsExotelSender.sendSms(userNumber, from, body, sid, token, "paymentReminderSMS");
    }
}
