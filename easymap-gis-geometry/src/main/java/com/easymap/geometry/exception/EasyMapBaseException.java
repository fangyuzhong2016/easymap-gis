package com.easymap.geometry.exception;

/**
 * 定义异常基类
 */
public class EasyMapBaseException extends Exception {
    private final ErrorCode code;
    private final String originalMessage;

    /**
     * 初始化基本异常
     *
     * @param code 异常代码对象
     */
    public EasyMapBaseException(ErrorCode code) {
        super(code.getCode() + ":" + code.getMessage());
        this.code = code;
        originalMessage = null;
    }

    /**
     * @param code
     * @param extraInfo
     */
    public EasyMapBaseException(ErrorCode code, String extraInfo) {
        super(code.getCode() + ":" + code.getMessage() + " - " + extraInfo);
        this.code = code;
        originalMessage = extraInfo;
    }

    /**
     * @param code
     * @param cause
     */
    public EasyMapBaseException(ErrorCode code, Throwable cause) {
        super(code.getCode() + ":" + code.getMessage(), cause);
        this.code = code;
        originalMessage = null;
    }

    /**
     * @param code
     * @param extraInfo
     * @param cause
     */
    public EasyMapBaseException(ErrorCode code, String extraInfo, Throwable cause) {
        super(code.getCode() + ":" + code.getMessage() + " - " + extraInfo, cause);
        this.code = code;
        originalMessage = extraInfo;
    }

    /**
     * @return
     */
    public ErrorCode getErrorCode() {
        return code;
    }

    /**
     * @return
     */
    public String getOriginalMessage() {
        return originalMessage;
    }
}

