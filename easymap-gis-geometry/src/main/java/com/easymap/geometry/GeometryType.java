package com.easymap.geometry;

/**
 * 定义Geometry的类型，和OGC的标准一致吧
 */
public enum GeometryType {
    Unkown(0, "Unkown"),
    Point(1, "Point"),
    MultiPoint(2, "MultiPoint"),
    LineString(3, "LineString"),
    MultiLineString(4, "MultiLineString"),
    Polygon(5, "Polygon"),
    MultiPolygon(6, "MultiPolygon");

    private int code;
    private String name;

    GeometryType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static GeometryType geometryTypeByName(String geoTypeName) {
        for (GeometryType geometryType : values()) {
            if (geometryType.getName().equalsIgnoreCase(geoTypeName)) {
                return geometryType;
            }
        }
        return GeometryType.Unkown;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
