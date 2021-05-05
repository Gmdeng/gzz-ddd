package com.gzz.retail.infra.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoEvent {
    private String eventNo;
    //事件注册

    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
    }
    //解除注册

    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

}
