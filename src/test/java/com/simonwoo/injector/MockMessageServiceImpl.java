package com.simonwoo.injector;

import com.simonwoo.injector.services.MessageService;

public class MockMessageServiceImpl implements MessageService {

    public boolean sendMessage(String msg, String receiver) {
        return true;
    }

}
