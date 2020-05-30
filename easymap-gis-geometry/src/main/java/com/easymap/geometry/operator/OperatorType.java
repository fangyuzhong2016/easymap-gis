package com.easymap.geometry.operator;

/**
 * 定义Geometry的操作相关枚举
 */
public enum OperatorType {
    /**
     * 投影操作
     */
    Project,
    //--------------------空间关系运算-----------------------------------------------------//
    /**
     * 空间关系运算-相等
     */
    Equals,
    /**
     * 空间关系运算-相离
     */
    Disjoint,
    /**
     * 空间关系运算-相交
     */
    Intersects,
    /**
     * 空间关系运算-包含于
     */
    Within,
    /**
     * 空间关系运算-包含
     */
    Contains,
    /**
     * 空间关系运算-交叉(穿越)
     */
    Crosses,
    /**
     * 空间关系运算-相邻
     */
    Touches,
    /**
     * 空间关系运算-重叠
     */
    Overlaps,

    //-----------------------空间分析----------------------------------------------------//
    /**
     * 空间分析--合并分析
     */
    Union,
    /**
     * 空间分析-求差分析
     */
    Difference,
    /**
     * 空间分析-缓冲区分析
     */
    Buffer,
    /**
     * 空间分析-距离计算
     */
    Distance,
    /**
     * 空间分析-求交分析
     */
    Intersection,
    /**
     * 空间分析-裁剪分析
     */
    Clip,
    /**
     * 空间分析-线面分割
     */
    Cut,
    /**
     * 空间分析-对称差异分析
     */
    SymmetricDifference,
    /**
     * 空间分析-查找几何的最近点分析
     */
    Proximity2D,
    /**
     * 空间分析-几何关联分析(使用编码为字符串的DE-9IM矩阵在两个几何之间执行关联操作)
     */
    Relate,
    /**
     * 空间分析-计算几何体的中心点
     */
    Centroid2D,
    /**
     * 空间分析-长度增密(按长度对MultiPath几何图形进行密集化处理，以使任何段都不超过给定的阈值)
     */
    DensifyByLength,
    /**
     * 空间分析-角度增密
     */
    DensifyByAngle,
    /**
     * 空间分析-获取几何体的标注点
     */
    LabelPoint,

    /**
     * 空间分析-大地球面缓冲分析
     */
    GeodesicBuffer,
    /**
     * 空间分析-大地球面增密
     */
    GeodeticDensifyByLength,
    /**
     * 空间分析-形状致密化(通过长度和/或偏差将给定空间参考中的节段的形状致密化以保留段的形状。
     * 所得线段的椭圆弧长度不超过给定的最大长度，并且线段将比给定的最大偏差更接近原始线段曲线和连接的椭圆弧)
     */
    ShapePreservingDensify,
    /**
     * 空间分析-计算大地球面距离
     */
    GeodeticLength,
    /**
     * 空间分析-计算大地球面面积
     */
    GeodeticArea,
    /**
     * 空间分析-简化几何图形
     */
    Simplify,
    /**
     * 空间分析-简化几何图形OGC版
     */
    SimplifyOGC,
    /**
     * 空间分析-几何偏移操作
     */
    Offset,
    /**
     * 空间分析-概化操作(使用指定的最大偏移容差来简化输入要素。输出要素将包含原始输入折点的子集)
     */
    Generalize,

    /**
     * 凸包操作
     */
    ConvexHull,
    /**
     * 计算几何体的边界
     */
    Boundary,

    //----------------Geometry的导入导出转换操作----------------------//
    /**
     * 导出Geometry为WKB格式
     */
    ExportToWkb,
    /**
     * 读取WKB格式的Geometry
     */
    ImportFromWkb,
    /**
     * 导出Geometry为WKT格式
     */
    ExportToWkt,
    /**
     * 读取WKT格式的Geometry
     */
    ImportFromWkt,
    /**
     * 读取GeoJson格式的Geometry
     */
    ImportFromGeoJson,
    /**
     * 导出Geometry为GeoJson格式
     */
    ExportToGeoJson,
    /**
     * 导出Geometry为Json格式
     */
    ExportToJson,
    /**
     * 从Json格式读取Geometry
     */
    ImportFromJson,
    /**
     * 导出Geometry为ESRI Shape格式
     */
    ExportToESRIShape,
    /**
     * 读取ShapeFile格式的Geometry
     */
    ImportFromESRIShape
}
