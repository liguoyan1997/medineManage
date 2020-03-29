package com.it.cn.util.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {
    public static String exportExcel(String fileName,ExportParams exportParams,Class<?> fieldType,List<?> list) {
//        ExportParams exportParams = new ExportParams();
//        exportParams.setSheetName("谷粒网站分析");
        FileOutputStream fos = null;
        try {
            // 生成workbook 并导出
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, fieldType,list);
            File savefile = new File("D://medicineManager//");
            if (!savefile.exists()) {
                boolean result = savefile.mkdirs();
                System.out.println("目录不存在，创建" + result);
            }
            fos = new FileOutputStream(fileName);
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}
