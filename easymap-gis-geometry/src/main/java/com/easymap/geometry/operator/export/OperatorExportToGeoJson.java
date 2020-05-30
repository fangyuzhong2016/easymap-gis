package com.easymap.geometry.operator.export;

import com.easymap.geometry.operator.Operator;
import com.easymap.geometry.operator.OperatorFactoryLocal;
import com.easymap.geometry.operator.OperatorType;
import org.geotools.feature.SchemaException;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.util.List;

public abstract class OperatorExportToGeoJson extends Operator {

    public static OperatorExportToGeoJson local() {
        return (OperatorExportToGeoJson) OperatorFactoryLocal.getInstance()
                .getOperator(OperatorType.ExportToGeoJson);
    }

    @Override
    public OperatorType getType() {
        return OperatorType.ExportToGeoJson;
    }


    /**
     * 将几何的集合导出GeoJOSN字符串
     *
     * @param geometrys 几何集合
     * @return
     */
    public abstract String execute(List<Geometry> geometrys, String name, int srid) throws SchemaException, IOException;
}
