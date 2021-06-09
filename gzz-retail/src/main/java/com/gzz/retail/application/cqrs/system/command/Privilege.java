package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Data
public class Privilege {
    private Long moduleId;
    @Valid
    @Size(min=1, message = "至先选中一项")
    private int[] values;
}
