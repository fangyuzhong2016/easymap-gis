package com.easymap.geometry.exception;

/**
 * 定义框架错误代码接口
 */
public interface ErrorCode {

    /**
     * 获取错误代码的字符串表示形式
     *
     * @return 错误代码的字符串表示形式
     */
    String getCode();

    /**
     * 获取与错误代码关联的消息。
     *
     * @return 与错误代码关联的消息。
     */
    String getMessage();
}
