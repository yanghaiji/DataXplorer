
<h1 align="center">
  <a href="https://github.com/yanghaiji/DataXplorer.git"><img src="https://github.com/yanghaiji/logger-agent/blob/main/doc/img/new_logo_01.png" alt="Standard - javayh-logger-agent" width="500"></a>
</h1>
<p align="center">
    <a href="https://spring.io/projects"><img src='https://img.shields.io/badge/license-Apache%202-borightgreen' alt='License'/></a>
    <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-2.6.3-brightgreen)" alt="npm version"></a>
    <a href="https://standardjs.com"><img src="https://img.shields.io/badge/code_style-standard-brightgreen.svg" alt="Standard - Java Style Guide"></a>
</p>

# 源代码地址

| **仓库**   | **URL**                                                      |
| ---------- | ------------------------------------------------------------ |
| **Github** | **[https://github.com/yanghaiji/DataXplorer](https://github.com/yanghaiji/DataXplorer)** |
| **Gitee**  | **[https://gitee.com/YangHaiJi/DataXplorer](https://gitee.com/YangHaiJi/DataXplorer)** |


github上为最新版本，gitee上会略微滞后一些 如果对您有所帮助可以给个star， ✨不迷路，您的star是我们编写的动力 🙏


#

`DataXplorer`是一个非常有价值的工具，它的核心任务是解决分布式系统中日志收集和分析的挑战。该工具的设计理念基于"数据"和"探索"，旨在提供一种高度可定制且不侵入应用代码的方式来实现对日志数据的采集和分析。

`DataXplorer`基于Java技术，借助`javassist`和`Spring`框架的集成，能够在不干扰应用程序正常运行的前提下，捕获和记录关键的日志信息，特别是Web请求。这使得它成为了一种非常灵活和可扩展的框架，能够适应各种复杂的系统架构和业务需求。

这个工具的出现解决了多个服务之间缺乏一致性日志收集方法的问题，摒弃了传统AOP方法的限制。它的优点在于其即插即用的特性，不需要对现有项目代码进行任何修改，从而降低了集成和维护的复杂性。

此外，`DataXplorer`还支持将收集到的日志数据发送至`data-xplorer-server`，进行高级的数据处理和持久化。这一特性为日志数据的集中化分析和用户行为建模提供了便利，
有助于实现更深入的业务洞察和决策支持。综合而言，`DataXplorer`在分布式系统中的日志管理方面提供了一种先进且高度可定制的解决方案，为数据驱动的决策提供了强大支持。




# Dashboard

> 由于本人是前端小白，只做了一个简单的页面，提供了几个简单的报表， 启动项目后访问 http://ip:9090/admin/api/index
>
>用户名密码都是 DataXplorer
>

<h1 align="center">
  <a href="https://github.com/yanghaiji/DataXplorer.git"><img src="https://github.com/yanghaiji/logger-agent/blob/main/doc/img/Dashboard.png" alt="Dashboard" width="800"></a>
</h1>

# 原理

<h1 align="center">
  <a href="https://github.com/yanghaiji/DataXplorer.git"><img src="https://github.com/yanghaiji/logger-agent/blob/main/doc/img/theory.png" alt="Standard - javayh-logger-agent" width="500"></a>
</h1>

# 源码编译

```
git clone https://github.com/yanghaiji/DataXplorer.git

mvn clean package install

```


# Agent 配置

## 方式一

- 将上一步骤编译好的`jdata-xplorer-agent-1.0-SNAPSHOT.jar`放在指定的位置，如`\usr\local\agent`
- 在启动脚本上加入`-javaagent:\usr\local\agent\data-xplorer-agent-1.0-SNAPSHOT.jar`的命令参数

## 方式二

```
<dependency>
    <groupId>com.javayh.agent</groupId>
    <artifactId>data-xplorer-agent-boot-starter</artifactId>
</dependency>
```

无论采用哪种方式都需要在需要监听的服务内添加如下配置,可根据项目实际场景进行修改
```
data:
  xplorer:
    host: 127.0.0.1
    port: 9098
    show-log: true
    inbound-transfer-rate:
      data-throughput: 100
      period: 45
      initial-delay: 30 

```

# 日志

## 收集方式

### 自动收集

进行agent的配置进行自动的日志收集

```
22:17:05.317 [nioEventLoopGroup-3-3] INFO com.javayh.agent.server.handler.AgentServerHandler - 客户端地址: /127.0.0.1:60163
22:17:05.317 [nioEventLoopGroup-3-3] INFO com.javayh.agent.server.handler.AgentServerHandler - 客户端发送消息是: {"actionTime":7596,"appName":"agent-example","body":"sss=sss","createBy":"javayh-agent","createTime":123,"ip":"0:0:0:0:0:0:0:1","method":"GET","sourceType":0,"traceId":"b4d071fb-54c2-40f8-b025-f4362232f7d6","type":1,"url":"/agent/example/test/agent"}

```

### 手动埋点

通过使用`LoggerReceived`进行自定义的埋点
```
LoggerReceived.received("test自定义埋点", 1, "haiji", null);
```

```
22:17:05.316 [nioEventLoopGroup-3-3] INFO com.javayh.agent.server.handler.AgentServerHandler - 客户端地址: /127.0.0.1:60163
22:17:05.317 [nioEventLoopGroup-3-3] INFO com.javayh.agent.server.handler.AgentServerHandler - 客户端发送消息是: {"appName":"agent-example","body":"test自定义埋点","createBy":"haiji","createTime":123,"query":"test自定义埋点","sourceType":0,"traceId":"b4d071fb-54c2-40f8-b025-f4362232f7d6","type":1}

```


## 日志格式

目前只收集了一些基本信息，大家可以在`LoggerCollector`进行扩展，丰富自己的日志格式

```metadata json
{
    "actionTime": 23,
    "appName": "agent-example",
    "body": "",
    "createBy": "javayh-agent",
    "createTime": null,
    "ip": "0:0:0:0:0:0:0:1",
    "method": "GET",
    "traceId": "bd861b19-242a-4846-838f-62519fd180b4",
    "type": 1,
    "url": "/agent/example/test/agent"
}
```


```
2023-08-19 [http-nio-8080-exec-2] INFO  o.s.web.servlet.DispatcherServlet - Completed initialization in 1 ms
2023-08-19 [http-nio-8080-exec-2] INFO  c.j.a.example.web.ExampleController - ewohefo
2023-08-19 [http-nio-8080-exec-2] INFO  c.j.a.c.i.AgentLogInterception - 埋点日志 : {"actionTime":23,"appName":"agent-example","body":"","createBy":"javayh-agent","createTime":null,"ip":"0:0:0:0:0:0:0:1","method":"GET","traceId":"bd861b19-242a-4846-838f-62519fd180b4","type":1,"url":"/agent/example/test/agent"}

```

