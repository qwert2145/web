package com.womai.platform.admin.web.Util;

import com.womai.platform.api.model.CouponContentApi;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
    static private Workbook wb;
    static private Sheet sheet;
    static private Row row;

    public static void initial(InputStream is,String fileName){
        try {
            int index = fileName.indexOf(".xlsx");
            if (index > 0) {
                // 针对2007 Excel 文件
                wb = new XSSFWorkbook(is);
            } else {
                // 针对 2003 Excel 文件
                wb = new HSSFWorkbook(new POIFSFileSystem(is));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
    }
//    public static String[] readExcelTitle(InputStream is,String fileName) {
//        if(wb == null || sheet == null){
//            initial(is,fileName);
//        }
//        // 获取第一行（约定第一行是标题行）
//        row = sheet.getRow(0);
//        // 获取行的列数
//        int colNum = row.getPhysicalNumberOfCells();
//        String[] titles = new String[colNum];
//        for (int i = 0; i < titles.length; i++) {
//            titles[i] = getCellFormatValue(row.getCell(i));
//        }
//        return titles;
//    }

    public static List<CouponContentApi> readExcelContent(int activeId,InputStream is,String fileName) {
        List<CouponContentApi> list = new ArrayList<CouponContentApi>();
        try{
            if(wb == null || sheet == null){
                initial(is,fileName);
            }
            int rowNum = sheet.getLastRowNum();// 得到总行数
            row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells();
//        String titles[] = readExcelTitle(is,fileName);
            // 正文内容应该从第二行开始,第一行为标题
            for (int i = 1; i <= rowNum; i++) {
                int j = 0;
                row = sheet.getRow(i);
                String result ="";
                do {
                    String card = getCellFormatValue(row.getCell(j)).trim();
                    String pass = getCellFormatValue(row.getCell(j+1)).trim();
                    if(StringUtils.isNotBlank(card)){
                        result += card + "," + pass + ";";
                    }
                    j=j+2;
                } while (j < colNum);
                CouponContentApi couponContentApi = new CouponContentApi();
                couponContentApi.setCouponValue(result);
                couponContentApi.setCouponActiveId(activeId);
                list.add(couponContentApi);
            }
        }finally {
            wb = null;
            sheet = null;
        }
        return list;
    }

    /**
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) {
        String cellValue;
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_NUMERIC:
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 方法2：这样子的date格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(date);
                    } else {
                        // 如果是纯数字取得当前Cell的数值
                        //转换成整型
                        DecimalFormat df = new DecimalFormat("#");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRING
                case Cell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    // 默认的Cell值
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;

    }

    public static void main(String[] args) throws FileNotFoundException {
//        String file = "D:\\document\\test1.xlsx";
//        InputStream is = new FileInputStream(file);
//        int ac
//        List<String> list = ExcelReader.readExcelContent(is,file);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
    }

}