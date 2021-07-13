package com.gzz.retail.application.cqrs.goods.command;

import lombok.Data;

@Data
public class CatalogDeleteCmd {
    private Long catalogId;
    private int status;
}
