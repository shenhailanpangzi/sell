package com.lanpang.sell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * http请求返回的最外层对象
 * Created by 杨浩
 * 2018-05-12 14:13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
