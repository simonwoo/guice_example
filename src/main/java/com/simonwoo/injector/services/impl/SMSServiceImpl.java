package com.simonwoo.injector.services.impl;

import com.google.inject.Singleton;
import com.simonwoo.injector.services.MessageService;

@Singleton
public class SMSServiceImpl implements MessageService {
    public boolean sendMessage(String msg, String receiver) {
        System.out.println("SMS sent to " + receiver + " with Message= " + msg);
        return true;
    }

}
