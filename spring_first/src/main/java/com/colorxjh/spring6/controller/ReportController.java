package com.colorxjh.spring6.controller;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.policy.reference.MultiSeriesChartTemplateRenderPolicy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.*;

@RestController
public class ReportController {

    @GetMapping("/report/download")
    public void generateWordReport(HttpServletResponse response) {
        try {
            //多系列组合图表（不同类型）
            ChartMultiSeriesRenderData comb = Charts
                    .ofComboSeries("MyChart", new String[] { "x1", "x2","x3","x4" })
                    .addBarSeries("x1", new Double[] { 15.0, 6.0 ,15.0, 6.0})
                    .addBarSeries("x2", new Double[] { 223.0, 119.0,223.0, 119.0 })
                    .addLineSeries("x3", new Double[] { 223.0, 119.0,223.0, 119.0 })
                    .addLineSeries("x4", new Double[] { 323.0, 89.0,323.0, 89.0 })
                    .create();




            //多系列 同类型
            ChartMultiSeriesRenderData test2 = new ChartMultiSeriesRenderData();
            test2.setChartTitle("季度销售与增长123");
            test2.setCategories(Arrays.asList("Q1", "Q2", "Q3").toArray(new String[0]));
            List<SeriesRenderData> seriesList2 = new ArrayList<>();
            seriesList2.add(new SeriesRenderData("公司A1", Arrays.asList(120, 150, 170).toArray(new Number[0])));
            seriesList2.add(new SeriesRenderData("公司B2", Arrays.asList(100, 130, 180).toArray(new Number[0])));
            seriesList2.add(new SeriesRenderData("公司C3", Arrays.asList(105, 134, 110).toArray(new Number[0])));
            seriesList2.add(new SeriesRenderData("公司D4", Arrays.asList(110, 137, 140).toArray(new Number[0])));
            test2.setSeriesDatas(seriesList2);

            //单系列
            ChartSingleSeriesRenderData single = Charts
                    .ofSingleSeries("ChartTitle", new String[] { "美国", "中国","英国","日本","法国" })
                    .series("countries", new Integer[] { 9826675, 9596961,2826675,3826675,4826675 })
                    .create();



            // 2. 绑定数据 Map
            Map<String, Object> data = new HashMap<>();
            data.put("test_chart", comb);
            data.put("test2", test2);
            data.put("single", single);
            String mymessage="12121221\n夏锦辉colorsdasdada\n下你到那儿打了让我进去了就阿瑟驱蚊器恶趣味去恶趣味去问" +
                    "啊饿啊饿啊饿啊请ddasdadsadasdadsasdasdasd问请问请问\n";
            data.put("mymessage", mymessage);
            // 3. 注册组合图表策略
            Configure config = Configure.builder()
                    .bind("chartData", new MultiSeriesChartTemplateRenderPolicy())
                    .build();

            // 4. 加载模板（假设模板路径是 resources/templates/word_report_template.docx）
            ClassPathResource templateResource = new ClassPathResource("templates/word_report_template.docx");
            XWPFTemplate template = XWPFTemplate.compile(templateResource.getInputStream(), config)
                    .render(data);

            // 5. 设置下载响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=report.docx");

            // 6. 写入输出流
            OutputStream out = response.getOutputStream();
            template.write(out);
            out.flush();
            out.close();
            template.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
