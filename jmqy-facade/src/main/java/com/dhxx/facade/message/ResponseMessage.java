package com.dhxx.facade.message;

/**
 * Created by qiong on 2016/3/3.
 */
public class ResponseMessage {
    /**
     * 消息
     */
    private String message="";

    private boolean isSuccess = true;
    /**
     * 结果集
     */
    private Object data;

    public ResponseMessage() {

    }
    
    public ResponseMessage(Object data) {
        super();
        this.data = data;
    }

    public ResponseMessage(boolean isSuccess, String message) {
        super();
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public ResponseMessage(boolean isSuccess, String message, Object data) {
        super();
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
