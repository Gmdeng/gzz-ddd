package com.gzz.retail.domain.order.model;

import com.gzz.retail.infra.defines.state.ShipStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 发货单
 */
@Data
@Setter
@Getter
public class ShipOrderDo {
    // 编号
    private String no;
    // 时间
    private String timeOn;
    // 订单编号
    private String orderNo;
    // 收货人姓名
    private String name;
    // 收货人手机号
    private String mobile;
    // 收货人地址
    private String address;
    // 收货人地区编码
    private String areaCode;
    // 快递单号
    private String expressBillNo;
    // 快递费用
    private String expressFee;
    // 快递公司
    private String expressCorp;
    // 发货状态
    private ShipStatus status;
}
