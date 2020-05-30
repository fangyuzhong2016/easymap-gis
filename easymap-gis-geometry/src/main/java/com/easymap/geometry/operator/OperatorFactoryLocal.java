package com.easymap.geometry.operator;

import com.easymap.geometry.operator.Import.OperatorImportFromGeoJsonLocal;
import com.easymap.geometry.operator.Import.OperatorImportFromWktLocal;
import com.easymap.geometry.operator.cut.OperatorCutLocal;
import com.easymap.geometry.operator.export.OperatorExportToGeoJsonLocal;

import java.util.HashMap;

/**
 * 创建几何操作实例的抽象工厂类的具体实现
 */
public class OperatorFactoryLocal extends OperatorFactory {

    private static final OperatorFactoryLocal INSTANCE = new OperatorFactoryLocal();

    private static final HashMap<OperatorType, Operator> st_supportedOperators = new HashMap<OperatorType, Operator>();

    static {
        // 初始化注册所有的受支持的Geometry的操作类
        st_supportedOperators.put(OperatorType.ImportFromGeoJson,
                new OperatorImportFromGeoJsonLocal());
        st_supportedOperators.put(OperatorType.ImportFromWkt,
                new OperatorImportFromWktLocal());
        st_supportedOperators.put(OperatorType.Cut,
                new OperatorCutLocal());
        st_supportedOperators.put(OperatorType.ExportToGeoJson,
                new OperatorExportToGeoJsonLocal());
    }

    private OperatorFactoryLocal() {

    }

    public static OperatorFactoryLocal getInstance() {
        return INSTANCE;
    }

    /**
     * 如果给定的运算符存在，则返回True。
     * 该类型是Operator :: Type值或用户定义的值之一。
     *
     * @param type 几何操作类型
     * @return
     */
    @Override
    public boolean isOperatorSupported(OperatorType type) {
        return st_supportedOperators.containsKey(type);
    }

    /**
     * 返回给定类型的运算符。 如果不支持该运算符，则引发异常
     *
     * @param type 几何操作类型
     * @return 几何操作类型
     */
    @Override
    public Operator getOperator(OperatorType type) {
        if (st_supportedOperators.containsKey(type)) {
            return st_supportedOperators.get(type);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
