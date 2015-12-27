package com.simonwoo.injector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.simonwoo.injector.application.MyApplication;
import com.simonwoo.injector.services.MessageService;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private Injector injector;

    // @BeforeClass
    // // executed only once before all tests
    // public void setUp() {
    //
    // }

    @Before
    // executed before each test
    public void setUp() {
        injector = Guice.createInjector(new AbstractModule() {

            @Override
            protected void configure() {
                bind(MessageService.class).to(MockMessageServiceImpl.class);
            }
        });
    }

    @Test
    public void test() {
        MyApplication app = injector.getInstance(MyApplication.class);
        Assert.assertTrue(app.sendMessage("hello", "zhang san"));
    }

}
