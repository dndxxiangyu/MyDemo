package com.seu.rplugin

import com.android.build.api.transform.JarInput
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile

public class MyInject {


    private static Set<String> routSet = new TreeSet<String>()
    private static final ClassPool sClassPool = ClassPool.getDefault()

    static void collectRouterMap(JarInput jarInput, Project project) {
        //可以优化，通过固定类型的报名+类名来从jar中获取
        //ClassPool.getDefault().getCtClass("com.seu.roter.AnnotationRoute$$feed");
        JarFile jarFile = new JarFile(jarInput.file)
        Enumeration<JarEntry> enumeration = jarFile.entries()
        sClassPool.insertClassPath(jarInput.file.absolutePath)

        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement()
            String entryName = entry.getName()
            if (!entryName.endsWith(".class")) {
                continue
            }
            if (!entryName.contains("AnnotationRoute")) {
                continue
            }

            String className = entryName.substring(0, entryName.length() - 6).replaceAll("/", ".")
            routSet.add(className)
        }
    }

    /**
     * 把每个routermap，注入到SmartRouter：api中。
     * @param jarInput
     */
    static void insertRouterMap(JarInput jarInput) {
        sClassPool.importPackage('android.content.Context')
        sClassPool.makeClass('android.content.Context')
        JarFile jarFile = new JarFile(jarInput.file)
        Enumeration<JarEntry> enumeration = jarFile.entries()
        sClassPool.insertClassPath(jarInput.file.absolutePath)

        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement()
            String entryName = entry.getName()
            if (!entryName.endsWith(".class")) {
                continue
            }

            if (!entryName.contains("SmartRouter")) {
                continue
            }

            println("entryName: " + entryName)
            String className = entryName.substring(0, entryName.length() - 6).replaceAll("/", ".")

            CtClass ctClass = sClassPool.getCtClass(className)
            // 解冻
            if (ctClass.isFrozen()) {
                ctClass.defrost()
            }
            CtMethod ctMethod = ctClass.getDeclaredMethod("init")
            for (String routerClassName : routSet) {
                //当前测试写入字符串
                ctMethod.insertBefore("System.out.println(\"${routerClassName}\");")
            }
            //会在root目录保存当前class
//            ctClass.writeFile()
            byte[] b = ctClass.toBytecode()
            JarHandler jarHandler = new JarHandler()
            jarHandler.replaceJarFile(jarInput, b, entryName)
        }
    }

    /**
     * 针对class，进行插入
     * @param path
     * @param project
     */
    static void injectToast(String path, Project project) {
        // 加入当前路径
        sClassPool.appendClassPath(path)
        // project.android.bootClasspath 加入android.jar，不然找不到android相关的所有类
        sClassPool.appendClassPath(project.android.bootClasspath[0].toString())
        // 引入android.os.Bundle包，因为onCreate方法参数有Bundle
        sClassPool.importPackage('android.os.Bundle')

        File dir = new File(path)
        if (dir.isDirectory()) {
            // 遍历文件夹
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                //demo,现在暂时没用
                if (file.name == 'MainActivity.class') {
                    // 获取Class
                    // 这里的MainActivity就在app模块里
//                    CtClass ctClass = sClassPool.getCtClass('com.apm.windseeker.MainActivity')
//                    println("ctClass: $ctClass")
//
//                    // 解冻
//                    if (ctClass.isFrozen()) {
//                        ctClass.defrost()
//                    }
//
//                    // 获取Method
//                    CtMethod ctMethod = ctClass.getDeclaredMethod('onCreate')
//                    println("ctMethod: $ctMethod")
//
//                    String toastStr = """ android.widget.Toast.makeText(this,"我是被插入的Toast代码~!!",android.widget.Toast.LENGTH_SHORT).show();
//                                      """
//
//                    // 方法尾插入
//                    ctMethod.insertAfter(toastStr)
//                    ctClass.writeFile(path)
//                    ctClass.detach() //释放
                }
            }
        }
    }
}
