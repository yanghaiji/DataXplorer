
<h1 align="center">
  <a href="https://github.com/yanghaiji/javayh-logger-agent.git"><img src="https://github.com/yanghaiji/logger-agent/blob/main/doc/img/new_logo_01.png" alt="Standard - javayh-logger-agent" width="500"></a>
</h1>
<p align="center">
    <a href="https://spring.io/projects"><img src='https://img.shields.io/badge/license-Apache%202-borightgreen' alt='License'/></a>
    <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-2.6.3-brightgreen)" alt="npm version"></a>
    <a href="https://standardjs.com"><img src="https://img.shields.io/badge/code_style-standard-brightgreen.svg" alt="Standard - Java Style Guide"></a>
</p>

#

`DataXplorer` 并不是一个常见的英语单词，而是由`Data` 和`Explorer`组合而成，可以被理解为一个探索数据或发现数据中的信息的概念。

`DataXplorer`一款基于`javassist`无侵入的日志收集工具，主要用于日志的收集，通过`javassist`与`spring`的联动实现对web请求的记录，实现真正的可扩展与可自定开发的框架。

致力于解决多个服务没有统一的日志收集工具，替代原有AOP的方式，可扩展性极强，开箱即用，对于项目代码无任何侵入。

支持将收集的日志发送给`javayh-logger-agent-server`进行最终的数据处理与持久化，方便统一的日志分析，进行用户画像。

# 原理

<h1 align="center">
  <a href="https://github.com/yanghaiji/javayh-logger-agent.git"><img src="https://github.com/yanghaiji/logger-agent/blob/main/doc/img/theory.png" alt="Standard - javayh-logger-agent" width="500"></a>
</h1>

# 源码编译

```
git clone https://github.com/yanghaiji/DataXplorer.git

mvn clean package install

```


# Agent 配置

- 将上一步骤编译好的`javayh-agent-1.0-SNAPSHOT.jar`放在指定的位置，如`\usr\local\agent`
- 在启动脚本上加入`-javaagent:\usr\local\agent\javayh-agent-1.0-SNAPSHOT.jar`的命令参数

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

