package com.simonwoo.injector.appInjector;

import com.google.inject.AbstractModule;
import com.simonwoo.injector.services.MessageService;
import com.simonwoo.injector.services.impl.EmailServiceImpl;

public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        // bind service to implementation class
        bind(MessageService.class).to(EmailServiceImpl.class);
    }

}
