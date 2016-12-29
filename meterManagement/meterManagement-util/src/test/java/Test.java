import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.*;

/**
 * Created by chenfu on 2016/12/29.
 */
public class Test {

    public static void main(String[] args) {
        /*File file = new File("F:/excel/a.xls");
        ArrayList<ArrayList<Object>> result = ExcelUtil.readExcel(file);
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                System.out.println(i + "行 " + j + "列  " + result.get(i).get(j).toString());
            }
        }
        ExcelUtil.writeExcel(result, "F:/excel/bb.xls", "sheet1");*/

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("表具资料");
        HSSFRow headRow = sheet.createRow(0);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.BLUE.index);

        headRow.createCell(0).setCellValue("表具编号");
        headRow.getCell(0).setCellStyle(style);
        headRow.createCell(1).setCellValue("通讯号码");
        headRow.createCell(2).setCellValue("ICCID");
        headRow.createCell(3).setCellValue("出厂密钥");


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] f_stream = new byte[stream.size()];
        for (int i = 0; i < f_stream.length; i++) {
            f_stream[i] = stream.toByteArray()[i];
        }

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
