package com.easymap.geometry.operator.Import;

import com.easymap.geometry.GeometryCursor;
import com.easymap.geometry.GeometryType;
import com.easymap.geometry.exception.EasyMapBaseException;
import com.easymap.geometry.operator.Operator;
import com.easymap.geometry.operator.OperatorFactoryLocal;
import com.easymap.geometry.operator.OperatorType;

import java.io.InputStream;

/**
 * 定义从GeoJSON读取
 */
public abstract class OperatorImportFromGeoJson extends Operator {
    /**
     * 获取导入器
     *
     * @return
     */
    public static OperatorImportFromGeoJson local() {
        return (OperatorImportFromGeoJson) OperatorFactoryLocal.getInstance().getOperator(OperatorType.ImportFromGeoJson);
    }

    @Override
    public OperatorType getType() {
        return OperatorType.ImportFromGeoJson;
    }

    /**
     * 执行导入
     *
     * @param geometryType  几何类型
     * @param geoJsonString GeoJSON的字符串表示
     * @return
     */
    public abstract GeometryCursor execute(GeometryType geometryType, String geoJsonString)
            throws EasyMapBaseException;

    /**
     * 以GeoJSON流的方式读取
     *
     * @param geoJsonInputStream
     * @return
     * @throws EasyMapBaseException
     */
    public abstract GeometryCursor execute(InputStream geoJsonInputStream)
            throws EasyMapBaseException;
}
