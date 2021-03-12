package com.gzz.core.validation.custom;

/*
调用方法：

1， 实现验证接口CustomValidator

@Component
public class DemoValidator implements CustomValidator<String> {

    @Autowired
    private PhotoUrlChecker photoUrlChecker;

    @Override
    public void check(String logo) {
        // 业务验证
        if(photoUrlChecker.invalid(logo)){
            throw new BusinessException("品牌Logo的URL不合法");
        }
    }
}
2， 在model中属性上加上注解
 @CustomValid(value=DemoValidator.class, message="")
 private String message;

*/
