package com.shangcheng.plugins

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

/**
 * 自定义切面编程插件
 * 初始化切面编程引擎
 */
public class AopPlugin implements Plugin<Project> {

    void apply(Project project) {
        println "dddd******************d"

        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants//当前module是app
        } else {
            variants = project.android.libraryVariants//当前module是library
        }

        project.dependencies {
            // TODO this should come transitively
            implementation 'org.aspectj:aspectjrt:1.8.13'//当前module添加aspect库
        }

        variants.all { variant ->
            JavaCompile javaCompile = variant.javaCompile
            //编译任务添加lastAction,当前module编译完之后会执行这个action，所以要在每个module中都要执行这个插件
            //因为project是代表当前module中的project。可以学习gradle，敬请期待
            javaCompile.doLast {
                String[] args = ["-showWeaveInfo",
                                 "-1.5",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true);
                new Main().run(args, handler);
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break;
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }
    }
}