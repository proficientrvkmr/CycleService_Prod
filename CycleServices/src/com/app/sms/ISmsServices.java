package com.app.sms;

public interface ISmsServices {

    void sendSessionFeedbackSMS(String userNumber, String coachName, String userName);

    void sendRequestACallSMS(String userNumber, String userName);

    void sendSessionBookingSMS(String userNumber, String userName);

    void sendInvoiceGeneratedSMS(String userNumber, String userName, Integer amount);

    void sendPaymentReceivedSMS(String userNumber, String userName, Integer amount);

    void sendInvoiceGeneratedSMSWithPromo(String userNumber, String userName, Integer amount, String promo);

    void sendOntheWaySMSToUser(String userNumber, String userName, String coachName);

    void sendCheckInSMSToUser(String userNumber, String userName, String coachName);

    void sendFreeSessionConfirmationSms(String userNumber, String userName, String dateStr, String coachName, String detailedNumber);

    void sendTwoHourPriorSmsToSession(String userName, String userNumber, String dateStr, String detailedNumber);

    void sendOnMyWaySms(String userName, String userNumber);

    void sendAfterFreeSessionSMS(String userName, String userNumber, String detailedNumber);

    void sendRescheduleRequestSMS(String userName, String userNumber, String dateStr1, String dateStr2, String number);

    void sendRescheduleRequestAcceptSMS(String userName, String userNumber, String dateStr1, String dateStr2);

    void sendRescheduleRequestRejectSMS(String userName, String userNumber, String dateStr1);

    void sendFreeSessionTrainerSms(String trainerNumber, String userName, String dateStr, String timings, String userAddress, String userMobile);

    void sendCallNotPickedUpRequest(String userName, String userNumber);

    void sendAdminRescheduleRequest(String userName, String userNumber);

    void sendSessionCoachCancelSMS(String userName, String coachName, String userNumber, String dateStr1);

    void sendSessionCancelSMS(String userName, String coachName, String userNumber, String dateStr1);

    void sendRescheduleRequestCoachAcceptSMS(String coachName, String userName, String coachNumber, String dateStr1, String dateStr2);

    void sendCancelRequestSMS(String userName, String userNumber, String dateStr);

    void sendUserAccessCreatedSMS(String userName, String userNumber, String dateStr);

    void sendUserAccessSubscriptionRenewalOptions(String userName, String userNumber, String dateStr, String defaultEmail, String expiryDate, Integer amount);

    void sendUserAccessSubscriptionRenewalOptionsFree(String userName, String userNumber, String dateStr, String defaultEmail, String expiryDate, Integer amount);

    void sendFeedbackAccessSessionSMS(String userName, String userNumber);

    void sendRsdAccessSessionSMS(String userName, String userNumber, String dateStr1, String dateStr2);

    void sendCancelAccessSessionSMS(String userName, String userNumber, String dateStr);
 
    void sendMobileOnlyCampaignSMS(String userNumber);

    void sendPaymentReminderSMS(String userNumber);
} 
