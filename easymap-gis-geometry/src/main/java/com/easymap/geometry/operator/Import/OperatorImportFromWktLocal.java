package com.easymap.geometry.operator.Import;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatorImportFromWktLocal extends OperatorImportFromWkt {
    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
    private static final WKTReader reader = new WKTReader(geometryFactory);
    Logger logger = LoggerFactory.getLogger(OperatorImportFromWktLocal.class);

    /**
     * 读取WKT，返回Geometry
     *
     * @param wkt_string 几何的WKT文本表示
     * @return
     */
    @Override
    public Geometry execute(String wkt_string) {
        try {
            return reader.read(wkt_string);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return null;
    }
}
