package com.example.config;

import com.example.anno.MyFilterOrder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动注册 web Filter
 */
@Slf4j
public class WebFilterAutoRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    //包名
    public static final String DEFAULT_PACKAGE = "com.example.controller.filter";

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //1.初始化类扫描器
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };

        //2.给扫描器加过滤条件，需要在类上有MyFilterOrder注解
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(MyFilterOrder.class);
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(annotationTypeFilter);

        //3.扫描指定的包，得到一系列bean定义，循环处理
        Set<BeanDefinition> candidateBeanDefinitionSet = scanner.findCandidateComponents(DEFAULT_PACKAGE);
        for (BeanDefinition beanDefinition : candidateBeanDefinitionSet) {
            if (beanDefinition instanceof ScannedGenericBeanDefinition) {
                ScannedGenericBeanDefinition annotatedBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
                AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();

                String[] interfaceNames = annotationMetadata.getInterfaceNames();

                // 带这个注解的类，必须实现 Filter 接口，否则不注册
                boolean isImplFilter = Arrays.asList(interfaceNames).contains("javax.servlet.Filter");

                if (isImplFilter) {

                    // 4. 开始注册
                    registerWebFilter(registry, annotationMetadata);

                    log.info("register web filter {} to bean factory", annotationMetadata.getClassName());
                }
            }
        }
    }


    /**
     * 生成一个 FilterRegistrationBean 并注册到 spring
     * @param registry
     * @param annotationMetadata
     */
    @SneakyThrows
    private void registerWebFilter(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata) {

        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(MyFilterOrder.class.getCanonicalName());

        Integer order = (Integer) attributes.get("value");
        String[] urlPatterns = (String[]) attributes.get("urlPatterns");

        String className = annotationMetadata.getClassName();
        try {
            // 生成 filter 实例
            Object newInstance = Class.forName(className).getConstructor().newInstance();

            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(WebFilterFactoryBean.class)
                    .addPropertyValue("filter", newInstance)
                    .addPropertyValue("order", order)
                    .addPropertyValue("name", className)
                    .addPropertyValue("urlPatterns", urlPatterns)
                    .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                    .getBeanDefinition();

            // 注册
            registry.registerBeanDefinition(className, beanDefinition);

            log.info("register WebFilter {} success", className);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            log.error("error, ", e);
        }

    }


}
