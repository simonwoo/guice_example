Guice是google的依赖注入框架，它能够帮助我们自动的实现应用的依赖注入的过程。在本篇中我们继续上一篇的例子来学习如何通过Guice来完成依赖注入。
## 使用maven创建project
1. 使用命令行来创建一个project(将命令行中的mycompany.app和my-app替换为你自己组织和项目的名字):

	`mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`

2. 将创建的project导入Eclipse
3. 增加maven dependency在pom.xml中

	```
	<dependency>
	    <groupId>com.google.inject</groupId>
	    <artifactId>guice</artifactId>
	    <version>3.0</version>
	</dependency>
	```

我的project的目录结构如下：

### Service classes:
MessageService.java

```java
package com.simonwoo.injector.services;

public interface MessageService {
    public boolean sendMessage(String msg, String receiver);
}

```
EmailServiceImpl.java

```java
package com.simonwoo.injector.services.impl;

import com.google.inject.Singleton;
import com.simonwoo.injector.services.MessageService;

@Singleton
public class EmailServiceImpl implements MessageService {

    public boolean sendMessage(String msg, String receiver) {
        System.out.println("Email sent to " + receiver + " with Message= " + msg);
        return true;
    }

}
```
SMSServiceImpl.java
```
package com.simonwoo.injector.services.impl;

import com.google.inject.Singleton;
import com.simonwoo.injector.services.MessageService;

@Singleton
public class SMSEmailServiceImpl implements MessageService {
    public boolean sendMessage(String msg, String receiver) {
        System.out.println("SMS sent to " + receiver + " with Message= " + msg);
        return true;
    }

}
```
注意在`EmailServiceImpl`和`SMSServiceImpl`含有@Singleton标签。由于所有的Service是通过Injector创建，这个标签是用来告诉injector这两个Service是单例。

### Application
MyApplication.java

```java
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

```
Guice同时支持setter和构造器的方式进行依赖注入。但是在该应用中，我推荐使用构造器的方式，因为MyApplication在没有Service的情况下无法运行下去。

### 实现注入
Guice不知道哪个Service将会被注入，所以我们需要扩展`AbstractModule`并实现configure方法来告诉如果进行注入。

```java
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
```
在该例子中，我们将`EmailService`绑定为`MessageService`的实现。

### Client Code
APP.java

```
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
```
首先我们创通过`Guice.createInjector(new AppInjector());`建一个Injector, 然后我们使用该Injector来初始化我们的Application。
### Junit test
由于我们只是想测试`MyApplication`，所以我们没有必要创建真实地Service。我们可以增加一个简单的Mock service实现。

```java
package com.simonwoo.injector;

import com.simonwoo.injector.services.MessageService;

public class MockMessageServiceImpl implements MessageService {

    public boolean sendMessage(String msg, String receiver) {
        return true;
    }

}
```

```java
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
```
