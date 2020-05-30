package com.easymap.geometry;

import com.easymap.geometry.exception.EasyMapBaseException;
import org.locationtech.jts.geom.Geometry;

/**
 * 几何游标抽象类
 */
public abstract class GeometryCursor {

    /**
     * 获取下个Geometry，为NULL，表示没有了
     *
     * @return
     */
    public abstract Geometry next() throws EasyMapBaseException;

    /**
     * 是否有下一条记录
     *
     * @return
     */
    public abstract boolean hasNext();

    /**
     * 获取Geometry的ID，如果有的话
     *
     * @return
     */
    public abstract int getGeometryID() throws EasyMapBaseException;

    /**
     * 获取Geometry的其他自定义信息
     *
     * @return
     */
    public abstract Object getUserData() throws EasyMapBaseException;
}
