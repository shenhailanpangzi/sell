//package com.lanpang.sell.dataobject.elasticsearch;
//
//import com.lanpang.sell.enums.ProductStatusEnum;
//import lombok.Data;
//import org.springframework.data.elasticsearch.annotations.Document;
//
//import javax.persistence.Id;
//import java.io.Serializable;
//import java.math.BigDecimal;
//
///**
// * 商品
// * Created by 杨浩
// * 2018-05-09 11:30
// */
//@Data
////@Document(indexName="mysell",type="ProductInfo",shards=5,replicas=1,indexStoreType="fs",refreshInterval="-1")
//@Document(indexName="mysell",type="ProductInfo")
//public class EsProductInfo implements Serializable {
//    private static final long serialVersionUID = 551589397625941750L;
//
//    @Id
//    private String productId;
//
//    /** 名字. */
//    private String productName;
//
//    /** 单价. */
//    private BigDecimal productPrice;
//
//    /** 库存. */
//    private Integer productStock;
//
//    /** 描述. */
//    private String productDescription;
//
//    /** 小图. */
//    private String productIcon;
//
//    /** 状态, 0正常1下架. */
//    private Integer productStatus = ProductStatusEnum.UP.getCode();
//
//    /** 类目编号. */
//    private Integer categoryType;
//
//}
