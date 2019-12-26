package com.ann.apt;

import com.ann.Router;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import static com.ann.apt.Constants.MODULE_NAME;

/**
 * 创建时间：2019-12-26
 * author: wuxiangyu.lc
 * describe:
 */
@AutoService(Processor.class)
public class SmartProcessor extends AbstractProcessor {
    private Filer filer;
    private Map<String, String> activityMap = new TreeMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(Router.class.getCanonicalName());
        return set;
    }

    /**
     * element代表一个元素，可以是：包，类/接口，属性，方法等。
     * TypeMirror，元素的类型信息。
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    //该方法返回true表示该注解已经被处理, 后续不会再有其他处理器处理; 返回false表示仍可被其他处理器处理.
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 准备在gradle的控制台打印信息
        Messager messager = processingEnv.getMessager();
        Map<String, String> options = processingEnv.getOptions();
        String moduleName = options.get(MODULE_NAME);
        if (moduleName == null || moduleName.isEmpty()) {
            moduleName = "default";
        }

        messager.printMessage(Diagnostic.Kind.NOTE, "start: --------------" + moduleName);
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Router.class);

        messager.printMessage(Diagnostic.Kind.NOTE, "moduleName: " + moduleName +
                "; elementsSize: " + elements.size());
        parseActivityRouter(elements, moduleName);
        return true;
    }

    /**
     * 解析当前所有Router对应的类信息。
     * <p>
     * - VariableElement //一般代表成员变量
     * - ExecutableElement //一般代表类中的方法
     * - TypeElement //一般代表代表类
     * - PackageElement //一般代表Package
     *
     * @param set
     */
    private void parseActivityRouter(Set<? extends Element> set, String moduleName) {
        if (set == null || set.isEmpty()) {
            return;
        }
        activityMap.clear();
        for (Element element : set) {

            if (!(element instanceof TypeElement)) {
                throw new IllegalStateException("");
            }
            TypeElement typeElement = (TypeElement) element;

            String activityName = typeElement.getQualifiedName().toString();
            String key = typeElement.getAnnotation(Router.class).name();
            activityMap.put(key, activityName);
        }

        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("routeMap = new $T<>()", HashMap.class);
        for (String key : activityMap.keySet()) {
            String name = activityMap.get(key);
            constructorBuilder.addStatement("routeMap.put(\"$N\", \"$N\")", key, name);
        }

        //generate getActivityRouteName method
        MethodSpec routeName = MethodSpec.methodBuilder("getActivityName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(String.class, "routeName")
                .beginControlFlow("if (null != routeMap && !routeMap.isEmpty())")
                .addStatement("return (String)routeMap.get(routeName)")
                .endControlFlow()
                .addStatement("return \"\"")
                .build();
        //generate class
        TypeSpec typeSpec = TypeSpec.classBuilder("AnnotationRoute$$" + moduleName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructorBuilder.build())
                .addMethod(routeName)
                .addField(HashMap.class, "routeMap", Modifier.PRIVATE)
                .build();

        JavaFile javaFile = JavaFile.builder(Constants.PACKAGE_NAME_GENERATOR, typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
