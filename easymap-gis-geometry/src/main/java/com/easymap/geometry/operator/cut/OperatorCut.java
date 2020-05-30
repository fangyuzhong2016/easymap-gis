package com.easymap.geometry.operator.cut;

import com.easymap.geometry.exception.EasyMapBaseException;
import com.easymap.geometry.operator.Operator;
import com.easymap.geometry.operator.OperatorFactoryLocal;
import com.easymap.geometry.operator.OperatorType;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 线面裁剪算法
 */
public abstract class OperatorCut extends Operator {
    public static OperatorCut local() {
        return (OperatorCut) OperatorFactoryLocal.getInstance().getOperator(OperatorType.Cut);
    }

    @Override
    public OperatorType getType() {
        return OperatorType.Cut;
    }

    /**
     * 线面裁剪和面面裁剪---裁剪单个的面或者线
     *
     * @param cuttee 被裁剪的线或者面几何
     * @param cutter 裁剪的几何(目前只能是简单的LineString、Polygon)
     * @return 返回裁剪的几何集合
     */
    public abstract List<Geometry> execute(Geometry cuttee, Geometry cutter) throws EasyMapBaseException;

    /**
     * 线面裁剪和面面裁剪--裁剪整个数据集。
     * 注意，调用该方法之前，先需要调用setTargetGeoJsonData设置一下裁剪目标数据集
     *
     * @param cutter
     * @return
     */
    public abstract List<Geometry> execute(Geometry cutter) throws EasyMapBaseException;

    /**
     * 设置裁剪的目标数据(GeoJSON)
     *
     * @param geoJsonString 裁剪目标数据集
     */
    public abstract void setTargetGeoJsonData(String geoJsonString) throws IOException;

    /**
     * 设置裁剪的目标数据(GeoJSON)
     *
     * @param geoJsonInputStream 裁剪目标数据集流
     */
    public abstract void setTargetGeoJsonData(InputStream geoJsonInputStream) throws IOException;
}
