package com.ftc.shp2osm;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.junit.jupiter.api.Test;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

class ReadShpTests {

    @Test
    @SneakyThrows(Exception.class)
    void readShp() {

        TimeInterval timer = DateUtil.timer();

        //1.声明ShapeFile文件
        String shapeFilePath = "D:\\Code\\Projects\\graphhopper\\graphhopper-test-data\\albania-latest-free.shp\\gis_osm_roads_free_1.shp";
        File shapeFile = new File(shapeFilePath);

        //2.设置编码并读取文件
        ShapefileDataStore store = new ShapefileDataStore(shapeFile.toURI().toURL());
        store.setCharset(StandardCharsets.UTF_8);
        SimpleFeatureSource source = store.getFeatureSource();

        //3.获取每个文件项并遍历
        SimpleFeatureIterator features = source.getFeatures().features();
        while (features.hasNext()) {

            //4.获取文件项
            SimpleFeature feature = features.next();

            //5.转为可读Json
            FeatureJSON featureJSON = new FeatureJSON();
            StringWriter writer = new StringWriter();
            featureJSON.writeFeature(feature, writer);
            JSONObject item = JSONUtil.parseObj(writer.toString());

            //6.输出属性
            System.out.println(item.getJSONObject("properties"));
        }

        System.out.println("数据导入完成，共耗时" + timer.interval() + "ms");
    }
}
