package com.proud.exception;


public class ConsumerException extends Exception {
    private ConsumerException(String msg) {
        super(msg);
    }

    public static ConsumerException illegalMobileNumber(String mobileCode, String mobileNumber) {
       return new ConsumerException("IllegalMobile: " + mobileCode + " " + mobileNumber);
    }

    public static ConsumerException illegalPassword() {
        return new ConsumerException("The login password is invalid");
    }

    public static ConsumerException verificationCodeError() {
        return new ConsumerException("Verification code error");
    }
}