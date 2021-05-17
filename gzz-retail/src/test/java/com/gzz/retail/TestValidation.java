package com.gzz.retail;

import com.gzz.core.validation.ValidationResult;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.domain.Person;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestValidation {

    @Test
    public void testValidation() {
        Person person = new Person();
//        person.setAge(12);
//        person.setGender(5);
//        person.setName("李智龙");
        System.out.println(Person.CodeType.SKU_BAR);
        ValidationResult result = ValidationUtils.validate(person, "gender");
        Map<String, String> map = result.getErrors();
        boolean isError = result.isHasError();
        System.out.println("isError: " + isError);
        System.out.println(map);
        // System.out.println(Optional.ofNullable(map).orElse());
    }

}
