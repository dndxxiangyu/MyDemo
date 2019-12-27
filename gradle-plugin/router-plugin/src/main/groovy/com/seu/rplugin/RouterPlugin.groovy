package com.seu.rplugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 *
 */
public class RouterPlugin implements Plugin<Project> {

    void apply(Project project) {

        def android = project.extensions.getByType(AppExtension)
        System.out.println("start plugin --------------------------")

        // 不能在子module中使用
//        def android = project.extensions.findByType(AppExtension.class)
        android.registerTransform(new RouterTransform(project))
    }
}
