package com.easymap.geometry.operator.cut;

import com.easymap.geometry.GeometryType;
import com.easymap.geometry.error.GeometryOperatorError;
import com.easymap.geometry.exception.EasyMapBaseException;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.LineStringExtracter;
import org.locationtech.jts.index.strtree.STRtree;
import org.locationtech.jts.operation.polygonize.Polygonizer;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperatorCutLocal extends OperatorCut {
    private static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
    Logger logger = LoggerFactory.getLogger(OperatorCutLocal.class);
    private STRtree stRtree = null;

    /**
     * 线面裁剪和面面裁剪---裁剪单个的面或者线
     *
     * @param cuttee 被裁剪的线或者面几何
     * @param cutter 裁剪的几何(目前只能是简单的LineString、Polygon)
     * @return 返回裁剪的几何集合
     */
    @Override
    public List<Geometry> execute(Geometry cuttee, Geometry cutter) throws EasyMapBaseException {
        GeometryType cutteeGeometryType = GeometryType.geometryTypeByName(cuttee.getGeometryType());
        GeometryType cutterGeometryType = GeometryType.geometryTypeByName(cutter.getGeometryType());
        if ((cutteeGeometryType == GeometryType.Polygon ||
                cutteeGeometryType == GeometryType.MultiPolygon) &&
                (cutterGeometryType == GeometryType.LineString ||
                        cutterGeometryType == GeometryType.MultiLineString)) {
            // 线分割面
            return splitPolygonByLine(cuttee, cutter);
        } else if ((cutteeGeometryType == GeometryType.Polygon ||
                cutteeGeometryType == GeometryType.MultiPolygon) &&
                (cutterGeometryType == GeometryType.Polygon ||
                        cutterGeometryType == GeometryType.MultiPolygon)) {
            // 面分割面
            return splitPolygonByPy(cuttee, cutter);
        } else {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0002);
        }
    }

    /**
     * 线面裁剪和面面裁剪--裁剪整个数据集。
     * 注意，调用该方法之前，先需要调用setTargetGeoJsonData设置一下裁剪目标数据集
     *
     * @param cutter
     * @return
     */
    @Override
    public List<Geometry> execute(Geometry cutter) throws EasyMapBaseException {
        if (stRtree == null) {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0005);
        }
        GeometryType cutterGeometryType = GeometryType.geometryTypeByName(cutter.getGeometryType());
        if ((cutterGeometryType == GeometryType.LineString ||
                cutterGeometryType == GeometryType.MultiLineString ||
                cutterGeometryType == GeometryType.Polygon ||
                cutterGeometryType == GeometryType.MultiPolygon)) {
            // 先将cutter 查询整个数据集，将相交的几何查询出来，再进行批量裁剪---
            // 走stRtree先查询，貌似有点问题，之前没这么做过 都是选中面再去分割的，应该走空间数据集查询，如PG数据库
            Envelope envelope = new Envelope(cutter.getCoordinate());// 构造查询框对象
            envelope.expandBy(0.00001); // 容差范围，这个参数，估计要开出去？
            List<Geometry> resultSearch = this.stRtree.query(envelope);// 这样只是按照矩形框查询出来的，这还不够，还要进行一次判断
            List<Geometry> cutResults = new ArrayList<>();
            for (Geometry geom : resultSearch) {
                if (geom.intersects(cutter)) {
                    // cutter和这个面是相交的，然后再判断起码有2个交点以上，那就裁剪了(线没画过面，咋分割？)
                    boolean isAllintersects = false;
                    try {
                        Geometry intersectionPoints = geom.intersection(cutter);
                        int count = intersectionPoints.getCoordinates().length;
                        isAllintersects = count >= 4; // 注意，相交的公共点应该有4个以上，这样才能形成面
                    } catch (Exception ex) {
                        logger.warn(ex.toString());
                    }
                    if (isAllintersects) {
                        List<Geometry> cut = execute(geom, cutter);
                        if (cut.size() != 0)
                            cutResults.addAll(cut);
                    }
                }
            }
            return cutResults;
        } else {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0002);
        }
    }

    /**
     * 设置裁剪的目标数据(GeoJSON)
     *
     * @param geoJsonString 裁剪目标数据集
     */
    @Override
    public void setTargetGeoJsonData(String geoJsonString) throws IOException {
        setTargetGeoJsonData(new ByteArrayInputStream(geoJsonString.getBytes("UTF-8")));
    }

    /**
     * 设置裁剪的目标数据(GeoJSON)
     *
     * @param geoJsonInputStream 裁剪目标数据集流
     */
    @Override
    public void setTargetGeoJsonData(InputStream geoJsonInputStream) throws IOException {
        FeatureIterator<SimpleFeature> featureIterator = new
                FeatureJSON().streamFeatureCollection(geoJsonInputStream);
        stRtree = null;
        stRtree = new STRtree(); // 读取数据在内存中创建R树，不建议这样做吧
        while (featureIterator.hasNext()) {
            SimpleFeature feature = featureIterator.next();
            Geometry geom = (Geometry) feature.getAttribute("geometry");
            // 把这个Feature的其他属性塞给Geometry的UserData
            Map<String, Object> featureDatas = new HashMap<>();
            for (Property attribute : feature.getProperties()) {
                String filed = attribute.getName().toString();
                if (filed.equals("geometry")) continue;
                featureDatas.put(filed, attribute.getValue());
            }
            geom.setUserData(featureDatas);
            stRtree.insert(geom.getEnvelopeInternal(), geom);
        }
        stRtree.build();// 构建空间索引
        geoJsonInputStream.close();
    }

    /**
     * 几何集合多边形化
     *
     * @param geometry
     * @return
     */
    private Geometry polygonize(Geometry geometry) {
        Polygonizer polygonizer = new Polygonizer();
        polygonizer.add(LineStringExtracter.getLines(geometry));
        Polygon[] polygons = GeometryFactory.toPolygonArray(polygonizer.getPolygons());
        return geometryFactory.createGeometryCollection(polygons);
    }

    /**
     * 线分割面
     *
     * @param polygon 面多边形
     * @param line    裁剪的线
     * @return
     */
    private List<Geometry> splitPolygonByLine(Geometry polygon, Geometry line) {
        // 检查原始的Geometry的UserData属性，看有没有
        Object userData = polygon.getUserData();
        HashMap<String, Object> datas = null;
        if (userData instanceof Map) {
            datas = (HashMap<String, Object>) userData;// 强制转了^_^
        }
        Geometry nodedLinework = polygon.getBoundary().union(line);
        Geometry polys = this.polygonize(nodedLinework);
        int count = polys.getNumGeometries();
        List<Geometry> outPuts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Geometry geometry = polys.getGeometryN(i);
            if (!geometry.isEmpty() && geometry.isValid()) {
                if (polygon.contains(geometry.getInteriorPoint())) {
                    if (datas != null) {
                        // 先简单的将原始的数据属性复制到新的几何中吧
                        geometry.setUserData(datas);
                    }
                    outPuts.add(geometry);
                }
            }
        }
        return outPuts;
    }

    /**
     * 面分割要素
     *
     * @param polygon
     * @param otherPolygon
     * @return
     * @throws EasyMapBaseException
     */
    private List<Geometry> splitPolygonByPy(Geometry polygon, Geometry otherPolygon) throws EasyMapBaseException {
        if (!polygon.isValid()) {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0004);
        }
        if (!otherPolygon.isValid()) {
            throw new EasyMapBaseException(GeometryOperatorError.GEOMETRY_OPERATOR_ERROR_0004);
        }
        Object userData = polygon.getUserData();
        HashMap<String, Object> datas = null;
        if (userData instanceof Map) {
            datas = (HashMap<String, Object>) userData;
        }
        Geometry intersection = polygon.intersection(otherPolygon);
        Geometry difference = polygon.difference(otherPolygon);
        List<Geometry> outPuts = new ArrayList<>();
        if (!intersection.isEmpty() && intersection.isValid()) {
            if (datas != null) {
                intersection.setUserData(datas);
            }
            outPuts.add(intersection);
        }
        if (!difference.isEmpty() && intersection.isValid()) {
            if (datas != null) {
                difference.setUserData(datas);
            }
            outPuts.add(difference);
        }
        return outPuts;
    }
}
