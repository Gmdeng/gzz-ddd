package com.gzz.retail.application.assembler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class TreeSelectDto {
    private String title;
    private Long value;
    private List<TreeSelectDto>  children;
    public TreeSelectDto(String title, Long value){
        this.title = title;
        this.value = value;
    }
}
