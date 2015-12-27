package com.simonwoo.injector.application;

import com.google.inject.Inject;
import com.simonwoo.injector.services.MessageService;

public class MyApplication {
    private MessageService messageService;

    @Inject
    public MyApplication(MessageService messageService) {
        this.messageService = messageService;
    }

    // @Inject
    // public void setMessageService(MessageService messageService) {
    // this.messageService = messageService;
    // }

    public boolean sendMessage(String msg, String receiver) {
        // here are some logic to manipulate msg
        return messageService.sendMessage(msg, receiver);
    }
}
