package com.gzz.retail.infra.started;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Import注解是将指定的Bean加入到IOC容器之中进行管理，
 * ImportSelector接口只有一个selectImports方法，
 * 该方法将返回一个数组，也就是类实例名称，@Import
 * 注解将会把selectImports返回的所有Bean全部加入到IOC容器中进行管理。
 *
 */
public class TestServiceImport implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //return new String[]{TestService1.class.getName(), TestService2.class.getName()};
        return null;
    }
}