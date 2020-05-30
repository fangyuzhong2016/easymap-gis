package com.easymap.geometry.operator;

import org.locationtech.jts.geom.Geometry;

/**
 * 定义几何运算操作的基类
 */
public abstract class Operator {

    /**
     * 获取操作类型
     *
     * @return 返回操作类型枚举
     */
    public abstract OperatorType getType();

    /**
     * 处理Geometry以加快对其的操作。 这里待定，可能需要在内存中为Geometry创建B+ 索引
     * 如果为给定参数构建了加速器，则该方法将立即返回。
     *
     * @param geometry 要加速的几何
     */
    public boolean accelerateGeometry(Geometry geometry) {
        // Override at specific Operator level
        return false;
    }

    /**
     * 如果可以加速几何，则返回true
     *
     * @param geometry 待判断的几何实例
     * @return 对于可加速的几何为true，否则为false
     */
    public boolean canAccelerateGeometry(Geometry geometry) {
        // Override at specific Operator level
        return false;
    }

}

