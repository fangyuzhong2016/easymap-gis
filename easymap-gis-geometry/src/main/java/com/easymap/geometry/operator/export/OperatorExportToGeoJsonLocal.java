package com.easymap.geometry.operator.export;

import org.geotools.data.DataUtilities;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class OperatorExportToGeoJsonLocal extends OperatorExportToGeoJson {
    /**
     * 将几何的集合导出GeoJOSN字符串
     *
     * @param geometrys 几何集合
     * @return
     */
    @Override
    public String execute(List<Geometry> geometrys, String name, int srid) throws SchemaException, IOException {
        if (geometrys.size() <= 0) return null;
        Geometry geometry = geometrys.get(0); // 暂取第一个作为标准，要求geometrys类型和数据字段都要一直
        String geomType = geometry.getGeometryType();
        int geoSrid = geometry.getSRID();
        if (geoSrid == 0) {
            geoSrid = srid;
            geometry.setSRID(srid);
        }
        // 属性就不输出了吧，直接输出Geom了
        String typeSpec = "geometry:" + geomType + ",srid=" + geoSrid;
        SimpleFeatureType TYPE = DataUtilities.createType(name, typeSpec);
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        List<SimpleFeature> features = new ArrayList<>();
        SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
        for (Geometry geo : geometrys) {
            featureBuilder.add(geo);
            SimpleFeature feature = featureBuilder.buildFeature(null);
            features.add(feature);
        }
        StringWriter writer = new StringWriter();
        FeatureJSON fjson = new FeatureJSON();
        fjson.writeFeatureCollection(collection, writer);
        return writer.toString();
    }
}
