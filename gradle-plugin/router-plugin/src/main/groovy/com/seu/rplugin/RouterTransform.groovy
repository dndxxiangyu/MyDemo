package com.seu.rplugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project

/**
 *
 * 参考：https://www.jianshu.com/p/417589a561da
 */
public class RouterTransform extends Transform {
    private Project mProject;

    public RouterTransform(Project project) {
        mProject = project;
    }

    @Override
    String getName() {
        return "RouterTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 如果要应用在library中，需要修改此参数
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        System.out.println("start transform --------------------------")
        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        transformInvocation.inputs.each { input ->
            input.directoryInputs.each { directoryInput ->
                MyInject.injectToast(directoryInput.file.path, mProject)
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            input.jarInputs.each { jarInput ->
                // 重命名输出文件（同目录copyFile会冲突）
                MyInject.collectRouterMap(jarInput, mProject)
                def jarName = jarInput.name
                def md5Name = new DigestUtils().md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //生成输出路径
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                //将输入内容复制到输出
                FileUtils.copyFile(jarInput.file, dest)
            }
            long startTime = System.currentTimeMillis();
            input.jarInputs.each { jarInput ->

                //针对获取到的每个模块的routermap，进行整合
                MyInject.insertRouterMap(jarInput)
                def jarName = jarInput.name
                def md5Name = new DigestUtils().md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //生成输出路径
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, dest)
            }
            System.out.println("extra time: " + (System.currentTimeMillis() - startTime))
        }

    }
}
