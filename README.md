
<h1 align="center">
  <a href="https://github.com/yanghaiji/logger-agent.git"><img src="https://github.com/yanghaiji/logger-agent/blob/main/doc/img/new_logo.png" alt="Standard - logger-agent" width="500"></a>
</h1>
<p align="center">
    <a href="https://spring.io/projects"><img src='https://img.shields.io/badge/license-Apache%202-borightgreen' alt='License'/></a>
    <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-2.6.3-brightgreen)" alt="npm version"></a>
    <a href="https://standardjs.com"><img src="https://img.shields.io/badge/code_style-standard-brightgreen.svg" alt="Standard - Java Style Guide"></a>
</p>

#

一款基于`javassist`无侵入的日志收集工具，主要用于日志的收集，通过`javassist`与`spring`的联动实现对web请求的记录，实现真正的可扩展与可自定开发的框架

# 源码编译

```
git https://github.com/yanghaiji/logger-agent.git

mvn clean package install

```


# Agent 配置

- 将上一步骤编译好的`javayh-agent-1.0-SNAPSHOT.jar`放在指定的位置，如`\usr\local\agent`
- 在启动脚本上加入`-javaagent:\usr\local\agent\javayh-agent-1.0-SNAPSHOT.jar`的命令参数


# 日志格式

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
2023-08-19 16:19:46,874 [http-nio-8080-exec-2] INFO  o.s.web.servlet.DispatcherServlet - Completed initialization in 1 ms
2023-08-19 16:19:46,937 [http-nio-8080-exec-2] INFO  c.j.a.example.web.ExampleController - ewohefo
2023-08-19 16:19:52,916 [http-nio-8080-exec-2] INFO  c.j.a.c.i.AgentLogInterception - 埋点日志 : {"actionTime":23,"appName":"agent-example","body":"","createBy":"javayh-agent","createTime":null,"ip":"0:0:0:0:0:0:0:1","method":"GET","traceId":"bd861b19-242a-4846-838f-62519fd180b4","type":1,"url":"/agent/example/test/agent"}

```

