package com.gzz.retail.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 *
 *
 * springboot 2.0 配置 spring.jackson.date-format 不生效
 *
 * 原因分析：
 *
 *        拦截器继承的 WebMvcConfigurationSupport ！
 *        以前是用 WebMvcConfigurerAdapter ，
 *        springboot 2.0 建议使用 WebMvcConfigurationSupport 。
 *        但是在添加拦截器并继承 WebMvcConfigurationSupport
 *        后会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置！
 *        从而导致所有的Date返回都变成时间戳！
 *
 * 解决方法：
 *        实现 WebMvcConfigurer。
 *        将：  extends WebMvcConfigurationSupport
 *        改为：implements WebMvcConfigurer
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Cors 跨域设置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                // .exposedHeaders()
                .allowCredentials(true)
                .maxAge(3600);

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    /**
     * Spring Boot 2.0建议使用WebMvcConfigurationSupport
     * 总结
     *
     * implements WebMvcConfigurer ： 不会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
     * @EnableWebMvc + implements WebMvcConfigurer ： 会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
     * extends WebMvcConfigurationSupport ：会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
     * extends DelegatingWebMvcConfiguration ：会覆盖@EnableAutoConfiguration关于WebMvcAutoConfiguration的配置
     *
     *
     * 使用此方法, 以下 spring-boot: jackson时间格式化 配置 将会失效
     * spring.jackson.time-zone=GMT+8
     * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
     * 原因: 会覆盖 @EnableAutoConfiguration 关于 WebMvcAutoConfiguration 的配置
     * */
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.forEach(System.out::println);
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = converter.getObjectMapper();
//        // 生成JSON时,将所有Long转换成String
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        // 时间格式化
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        // 设置格式化内容
//        converter.setObjectMapper(objectMapper);
//        converters.add(0, converter);
//    }
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
// converters.forEach(System.out::println);
//    }
//
//    private boolean isJson(String str) {
//        return !StringUtils.isEmpty(str) && (str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}');
//    }
//
//    /**
//     * 自定义FastJsonHttpMessageConverter处理api中的枚举
//     */
//    @Configuration
//    @ConditionalOnClass({FastJsonHttpMessageConverter.class})
//    @ConditionalOnProperty(
//            name = {"spring.http.converters.preferred-json-mapper"},
//            havingValue = "fastjson",
//            matchIfMissing = true
//    )
//    public class FastJsonHttpMessageConvertersConfiguration {
//
//        @Bean
//        @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
//        public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//            FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//
//            FastJsonConfig fastJsonConfig = new FastJsonConfig();
//            fastJsonConfig.setSerializerFeatures(
//                    SerializerFeature.PrettyFormat,
//                    SerializerFeature.WriteEnumUsingToString
//            );
//            List<MediaType> mediaTypes = new ArrayList<>();
//            mediaTypes.add(MediaType.APPLICATION_JSON);
//            converter.setSupportedMediaTypes(mediaTypes);
//
//            ValueFilter valueFilter = (o, s, o1) -> {
//                if (o1 instanceof Enum) {
//                    String str = String.valueOf(o1);
//                    if (isJson(str)) {
//                        o1 = JSONObject.parseObject(String.valueOf(o1), Map.class);
//                    }
//                }
//                return o1;
//            };
//            fastJsonConfig.setSerializeFilters(valueFilter);
//
//            converter.setFastJsonConfig(fastJsonConfig);
//
//            return converter;
//        }
//
//    }
}
