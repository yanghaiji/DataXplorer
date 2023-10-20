package com.javayh.agent.core;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author haiji
 */
@Slf4j
@Deprecated
public class RequestInterceptionLogTransformer implements ClassFileTransformer {

    private final String packageName;

    public RequestInterceptionLogTransformer(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        if (!className.startsWith(packageName)) {
            return classfileBuffer;
        }
        CtClass ctClass;
        try {
            // 使用全称，取得字节码类<使用javassist>
            ClassPool classPool = ClassPool.getDefault();
            ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            // 获取所有的方法
            CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
            for (CtMethod ctMethod : declaredMethods) {
                // 添加变量
                ctMethod.addLocalVariable("time", CtClass.longType);
                ctMethod.insertBefore("System.out.println(\"------------ Before --------\");");
                ctMethod.insertBefore("time = System.currentTimeMillis();");
                ctMethod.insertAfter("System.out.println(\"Elapsed Time(ms): \" + (System.currentTimeMillis() - time));");
                ctMethod.insertAfter("System.out.println(\"------------- After --------\");");
            }

            return ctClass.toBytecode();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // 返回原类字节码
        return classfileBuffer;
    }
}
