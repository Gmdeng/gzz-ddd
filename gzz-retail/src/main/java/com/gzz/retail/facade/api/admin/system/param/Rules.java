package com.gzz.retail.facade.api.admin.system.param;

import lombok.Data;

import java.util.List;

@Data
public class Rules {
    private String listName;
    private Integer[] age;
    private Rule person;
    private List<Rule> rules;
}