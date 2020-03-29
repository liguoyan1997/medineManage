package com.it.cn.util.page.converter.restful;

/**
 * 统一消息返回
 *
 * @param <T>
 * @author i.yoo
 * @version 2018年8月1日13:49:51
 */

public class ResponseWrapperEntity<T> {
    /**
     * 返回的消息
     */
    private String code;
    /**
     * 返回的消息
     */
    private String msg;
    /**
     * 返回值，类型为T
     */
    private T data;

    /**
     * 数据总数
     */
    private long count;

    public ResponseWrapperEntity(T data) {
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
        this.data = data;
    }

    public ResponseWrapperEntity(ResponseCode returnCode, T data) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
    }

    public ResponseWrapperEntity(ResponseCode returnCode, T data, long count) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.data = data;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
