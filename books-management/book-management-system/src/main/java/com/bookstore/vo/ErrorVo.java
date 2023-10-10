package com.bookstore.vo;

public class ErrorVo {
    private String entity;
    private String errMessage;

    public ErrorVo(String entity, String errMessage) {
        this.entity = entity;
        this.errMessage = errMessage;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
