package com.simonwoo.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.simonwoo.injector.appInjector.AppInjector;
import com.simonwoo.injector.application.MyApplication;

/**
 * Client clalss
 *
 */
public class App
{
    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new AppInjector());
        MyApplication app = injector.getInstance(MyApplication.class);

        app.sendMessage("hello", "zhang san");
    }
}
