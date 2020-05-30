package com.easymap.geometry.operator.Import;

import com.easymap.geometry.GeometryCursor;
import com.easymap.geometry.GeometryType;
import com.easymap.geometry.error.GeometryOperatorError;
import com.easymap.geometry.exception.EasyMapBaseException;
import com.easymap.geometry.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class OperatorImportFromGeoJsonLocal extends OperatorImportFromGeoJson {
    Logger logger = LoggerFactory.getLogger(OperatorImportFromGeoJsonLocal.class);

    /**
     * 执行导入
     *
     * @param geometryType  几何类型
     * @param geoJsonString GeoJSON的字符串表示
     * @return
     */
    @Override
    public GeometryCursor execute(GeometryType geometryType, String geoJsonString) throws EasyMapBaseException {
        // 借助GeooTools读取GeoJOSN吧....
        if (StringUtils.isEmpty(geoJsonString)) {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0003);
        }
        try {
            return new GeoJsonImportGeometryCursor(geoJsonString);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return null;
    }

    /**
     * 以GeoJSON流的方式读取
     *
     * @param geoJsonInputStream
     * @return
     * @throws EasyMapBaseException
     */
    @Override
    public GeometryCursor execute(InputStream geoJsonInputStream) throws EasyMapBaseException {
        if (geoJsonInputStream == null) {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0003);
        }
        try {
            return new GeoJsonImportGeometryCursor(geoJsonInputStream);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return null;
    }
}


