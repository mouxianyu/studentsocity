package com.mouxianyu.studentsociety.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @description: TODO
 * @author: kingsme@yeah.net
 */
@Getter
@Setter
@NoArgsConstructor
public class Result{
    /**
     * 条数
     */
    private int row;
    /**
     * 开始
     */
    private int start;
    /**
     * 总条数
     */
    private int total;
    /**
     * 返回结果
     */
    private Object data;

    public static Result success(Object data){
        Result result = new Result();
        result.setData(data);
        return result;
    }
}
