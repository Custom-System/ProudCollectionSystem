package com.proud.exception;


public class ConsumerException extends Exception {
    private ConsumerException(String msg) {
        super(msg);
    }

    public static ConsumerException IllegalMobileNumber(String mobileCode, String mobileNumber) {
       return new ConsumerException("IllegalMobile: " + mobileCode + " " + mobileNumber);
    }

    public static ConsumerException IllegalPassword() {
        return new ConsumerException("The login password is invalid");
    }
}