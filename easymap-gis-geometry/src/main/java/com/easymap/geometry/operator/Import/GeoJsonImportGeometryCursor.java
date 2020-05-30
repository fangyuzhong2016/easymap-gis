package com.easymap.geometry.operator.Import;

import com.easymap.geometry.GeometryCursor;
import com.easymap.geometry.error.GeometryOperatorError;
import com.easymap.geometry.exception.EasyMapBaseException;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.easymap.geometry.error.GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0004;

public class GeoJsonImportGeometryCursor extends GeometryCursor {
    Logger logger = LoggerFactory.getLogger(GeoJsonImportGeometryCursor.class);
    private FeatureIterator<SimpleFeature> featureFeatureIterator;
    private InputStream inputStream;
    private Geometry currentGeom = null;

    public GeoJsonImportGeometryCursor(String geoJsonString) {
        try {
            this.inputStream = new ByteArrayInputStream(geoJsonString.getBytes("UTF-8"));
            this.featureFeatureIterator = new FeatureJSON().streamFeatureCollection(inputStream);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    public GeoJsonImportGeometryCursor(InputStream inputStream) {
        try {
            this.inputStream = inputStream;
            this.featureFeatureIterator = new FeatureJSON().streamFeatureCollection(inputStream);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    /**
     * 获取下个Geometry，为NULL，表示没有了
     *
     * @return
     */
    @Override
    public Geometry next() throws EasyMapBaseException {
        if (this.featureFeatureIterator == null)
            return null;
        if (hasNext()) {
            SimpleFeature feature = this.featureFeatureIterator.next();
            currentGeom = (Geometry) feature.getAttribute("geometry");
            if (!currentGeom.isValid()) {
                throw new EasyMapBaseException(GEOMETRY_OPERATOR_ERROR_0004);
            }
            Map<String, Object> featureDatas = new HashMap<>();
            for (Property attribute : feature.getProperties()) {
                String filed = attribute.getName().toString();
                if (filed.equals("geometry")) continue;
                featureDatas.put(filed, attribute.getValue());
            }
            currentGeom.setUserData(featureDatas);// 把这个Feature的其他属性塞给Geometry的UserData
            return currentGeom;
        } else {
            try {
                if (this.inputStream != null) {
                    this.inputStream.close();
                }
            } catch (Exception ex) {
                logger.error(ex.toString());
            }
            return null;
        }
    }

    /**
     * 是否有下一条记录
     *
     * @return
     */
    @Override
    public boolean hasNext() {
        if (this.featureFeatureIterator != null)
            return this.featureFeatureIterator.hasNext();
        return false;
    }

    /**
     * 获取Geometry的ID，如果有的话
     *
     * @return
     */
    @Override
    public int getGeometryID() throws EasyMapBaseException {
        throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0001);
    }

    /**
     * 获取Geometry的其他自定义信息
     *
     * @return
     */
    @Override
    public Object getUserData() throws EasyMapBaseException {
        throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0001);
    }
}
