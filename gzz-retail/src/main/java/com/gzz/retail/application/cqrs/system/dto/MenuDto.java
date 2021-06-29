package com.gzz.retail.application.cqrs.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuDto {
    private Long id;
    private String code;
    private String name;
    private String icon;
    private String url;
    /**
     * 子项
     */
    private List<MenuDto> children;

    public MenuDto(Long id, String code, String name, String icon, String url) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.icon = icon;
        this.url = url;
    }
}
