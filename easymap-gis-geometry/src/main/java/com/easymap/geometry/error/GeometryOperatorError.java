package com.easymap.geometry.error;

import com.easymap.geometry.exception.ErrorCode;

public enum GeometryOperatorError implements ErrorCode {
    /**
     * 方法暂未实现，目前不支持
     */
    GEOMETRY_OPERATOR_ERROR_0001("GEOMETRY_OPERATOR_ERROR_0001", "方法暂未实现，目前不支持"),
    /**
     * 不支持的几何操作
     */
    GEOMETRY_OPERATOR_ERROR_0002("GEOMETRY_OPERATOR_ERROR_0002", "不支持的几何操作"),
    /**
     * GeoJSON数据内容不能为空
     */
    GEOMETRY_OPERATOR_ERROR_0003("GEOMETRY_OPERATOR_ERROR_0003", "GeoJSON数据内容不能为空"),
    /**
     * 几何有拓扑错误，无法进行空间操作，请先修复拓扑错误
     */
    GEOMETRY_OPERATOR_ERROR_0004("GEOMETRY_OPERATOR_ERROR_0004", "几何有拓扑错误，无法进行空间操作，请先修复拓扑错误"),
    /**
     * 请先设置裁剪的数据集(GeoJSON文件)
     */
    GEOMETRY_OPERATOR_ERROR_0005("GEOMETRY_OPERATOR_ERROR_0005", "请先设置裁剪的数据集(GeoJSON文件)");
    private String code;
    private String mesg;

    GeometryOperatorError(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }

    /**
     * 获取错误代码的字符串表示形式
     *
     * @return 错误代码的字符串表示形式
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取与错误代码关联的消息。
     *
     * @return 与错误代码关联的消息。
     */
    @Override
    public String getMessage() {
        return mesg;
    }
}
