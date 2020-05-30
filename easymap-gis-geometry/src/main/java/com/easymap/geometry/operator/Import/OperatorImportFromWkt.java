package com.easymap.geometry.operator.Import;

import com.easymap.geometry.operator.Operator;
import com.easymap.geometry.operator.OperatorFactoryLocal;
import com.easymap.geometry.operator.OperatorType;
import org.locationtech.jts.geom.Geometry;

public abstract class OperatorImportFromWkt extends Operator {

    public static OperatorImportFromWkt local() {
        return (OperatorImportFromWkt) OperatorFactoryLocal.getInstance()
                .getOperator(OperatorType.ImportFromWkt);
    }

    @Override
    public OperatorType getType() {
        return OperatorType.ImportFromWkt;
    }

    /**
     * 读取WKT，返回Geometry
     *
     * @param wkt_string 几何的WKT文本表示
     * @return
     */
    public abstract Geometry execute(String wkt_string);
}
