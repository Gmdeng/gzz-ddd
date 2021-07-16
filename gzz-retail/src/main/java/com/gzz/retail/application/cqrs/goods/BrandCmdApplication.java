package com.gzz.retail.application.cqrs.goods;

import com.gzz.retail.domain.goods.repo.BrandRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BrandCmdApplication {
    @Autowired
    private BrandRepo brandRepo;
}
