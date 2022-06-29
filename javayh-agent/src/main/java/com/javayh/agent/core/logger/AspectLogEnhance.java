package com.javayh.agent.core.logger;

import com.javayh.agent.core.constant.AgentConstant;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
public class AspectLogEnhance {


    public static void enhance() {
        try {
            CtClass cc = null;
            try {
                ClassPool pool = ClassPool.getDefault();
                // 导入自定义logback增强类 LogbackBytesEnhance
                pool.importPackage(AgentConstant.PACKAGE_NAME);
                // 增强的类，ch.qos.logback.classic.Logger
                cc = pool.get(AgentConstant.LOGGER_NAME);
                if (cc != null) {
                    // 增强的方法
                    CtMethod ctMethod = cc.getDeclaredMethod("buildLoggingEventAndAppend");
                    // 增强后的方法体，LogbackBytesEnhance.enhance自己实现的
                    ctMethod.setBody("{return LogbackBytesEnhance.enhance($1, $2, $3, $4, $5, $6, this);}");
                    cc.toClass();
                    System.out.println("JavaYh Agent Logger");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

