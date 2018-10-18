package com.lanpang.sell.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: mysell
 * @description:
 * @author: yanghao
 * @create: 2018-10-18 08:53
 **/
@Document(indexName="artindex",type="arttype")
@Data
@Getter
@Setter
@AllArgsConstructor
public class Article implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 551589397625941750L;
    @Id
    private Long id;
    /**标题*/
//     @Field(index= FieldIndex.not_analyzed,store=true,type=FieldType.String)
    private String title;
    /**摘要*/
    private String abstracts;
    /**内容*/
    private String content;
    /**发表时间*/
    @Field(format=DateFormat.date_time,index=FieldIndex.not_analyzed,store=true,type= FieldType.Object)
    private Date postTime;
    /**点击率*/
    private Long clickCount;


}


