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

    @Test
    public void testNumber(){
        int OP_READ = 1 << 0, OP_WRTE = 1 << 2, OP_CONNECT = 1 << 3, OP_ACCEPT = 1 << 4;
        //
        System.out.println("OP_READ = "  + OP_READ);
        System.out.println("OP_WRTE = "  + OP_WRTE);
        System.out.println("OP_CONNECT = "  + OP_CONNECT);
        System.out.println("OP_ACCEPT = "  + OP_ACCEPT);

    }

}
