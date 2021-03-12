package com.gzz.retail.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
public class Person {
    @Length(max = 20, message = "姓名长度不能大于20")
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @Range(min = 0, max = 1, message = "性别只能输入只能输入0或1")
    private Integer gender;
    private Integer age;
    private CodeType codeType;

    public enum CodeType {
        SPU_NO,
        SPU_BAR,
        SKU_NO,
        SKU_BAR
    }
}
