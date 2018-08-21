package com.lanpang.sell.dataobject;

import com.lanpang.sell.enums.OrderStatusEnum;
import com.lanpang.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**订单主表
 * Created by 杨浩
 * 2017-06-11 17:08
 */
@Entity
@Data
@DynamicUpdate(false)
public class OrderMaster {

    /** 订单id. */
    @Id
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单.  NEW(0, "新订单"), FINISHED(1, "完结"), CANCEL(2, "已取消")*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. WAIT(0, "等待支付"),SUCCESS(1, "支付成功"), */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;


}
