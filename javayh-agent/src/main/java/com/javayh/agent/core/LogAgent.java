package com.javayh.agent.core;


import com.javayh.agent.core.logger.AspectLogEnhance;

import java.lang.instrument.Instrumentation;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
public class LogAgent {
    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     */
    public static void premain(String arg, Instrumentation instrumentation) {

        AspectLogEnhance.enhance();
        //instrumentation.addTransformer(new RequestInterceptionLogTransformer(arg));
    }

    /**
     * 若不存在 premain(String agentArgs, Instrumentation inst)，
     * 则会执行 premain(String agentArgs)
     */
    public static void premain(String arg) {

    }
}
