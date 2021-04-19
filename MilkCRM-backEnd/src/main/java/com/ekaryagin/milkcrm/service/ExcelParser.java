package com.ekaryagin.milkcrm.service;

import com.ekaryagin.milkcrm.entity.Demand;
import com.ekaryagin.milkcrm.entity.employee.Manager;
import com.ekaryagin.milkcrm.entity.products.Product;
import com.ekaryagin.milkcrm.entity.products.ProductGroup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelParser {

    private ExcelParser() {
    }

    public static List<Product> getProductsFromExcel(ProductGroup group, Manager author, MultipartFile excelFile) throws IOException{

        List<Product> newProducts = new ArrayList<>();
        XSSFWorkbook myExcelBook = new XSSFWorkbook(excelFile.getInputStream());

        XSSFSheet myExcelSheet = myExcelBook.getSheet("Sheet1");
        Iterator<Row> ri = myExcelSheet.rowIterator();
        ri.next();

        Product nullProduct = new Product (group, author, "article", "Не получено ни одной строки", "шт", 0.0,
               1.0,50.0);
        while(ri.hasNext()) {
            XSSFRow row = (XSSFRow) ri.next();
            if (row.getCell(0) == null || row.getCell(1) == null){
                if (newProducts.isEmpty()){
                    newProducts.add(nullProduct);
                }
                return newProducts;
            }
            Object[] arr = {"артикул", "наименование", "шт", 0.0 /*цена*/, 1.0 /*квант*/, 50.0 /*abnormalUnit*/};
            rowParser(arr, row);
            newProducts.add(new Product(group, author, arr[0].toString(), arr[1].toString(), arr[2].toString(),
                    (double) arr[3], (double) arr[4], (double) arr[5]));
        }
        return newProducts;
    }

    private static void rowParser(Object[] arr, XSSFRow row){
        for (int i = 0; i < 6; i++) {
            XSSFCell cell = row.getCell(i);
            arr[i] = cellParser(arr[i], cell, i);
        }
    }

    private static Object cellParser(Object obj, XSSFCell cell, int cellNumber) {

        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (cellNumber < 2) {
                double d = cell.getNumericCellValue();
                obj = String.valueOf((int) d);
            } else {
                obj = cell.getNumericCellValue();
            }
        } else if(cell != null) {
            if(cellNumber > 2) {
                try {
                    obj = Double.parseDouble(cell.getStringCellValue());
                } catch (java.lang.NumberFormatException e){
                    // do nothing, will be the default value
                }
            } else {
                obj = cell.getStringCellValue();
            }
        }
        return obj;
    }

    public static void exportDemand(Demand demand) throws IOException{
        XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(demand.getGroup().getTemplatePath()));
        XSSFSheet myExcelSheet = myExcelBook.getSheet(demand.getGroup().getSheetName());

        Date date = getOrderDate();
        SimpleDateFormat formatForDateOrder = new SimpleDateFormat("dd.MM.yyyy"+" г.");
        String dateOrder = formatForDateOrder.format(date);

        myExcelSheet.getRow(demand.getGroup().getAddressRow()).getCell(demand.getGroup().getAddressCell()).setCellValue(demand.getShop().toString());
        myExcelSheet.getRow(demand.getGroup().getDateRow()).getCell(demand.getGroup().getDateCell()).setCellValue(dateOrder);

        for (Map.Entry<Product, Double> pair : demand.getListing().entrySet()) {

            String article = pair.getKey().getArticle();
            double count = pair.getValue();

            Iterator<Row> ri = myExcelSheet.rowIterator();
            while(ri.hasNext()){

                XSSFRow row = (XSSFRow) ri.next();
                if (row.getCell(demand.getGroup().getArticleColumn()).getStringCellValue().equals(article)){
                    row.getCell(demand.getGroup().getCountColumn()).setCellValue(count);
                    break;
                }
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(" ddMMyy ");
        String dateForFile = dateFormat.format(date);

        String destination = "src\\main\\resources\\Бланк "+demand.getGroup().getTitleForFile()+dateForFile+demand.getShop().getDescription()+
                " ("+demand.getShop().getAddress()+") "+ demand.getShop().getLegalEntity()+".xlsx";
        myExcelBook.write(new FileOutputStream(destination));
    }

    private static Date getOrderDate(){
        Date date = new Date();

        SimpleDateFormat weekDay = new SimpleDateFormat("u");
        int dayOfWeek = Integer.parseInt(weekDay.format(date));

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        if (dayOfWeek > 4){
            calendar.add(Calendar.DATE, 10-dayOfWeek);
        } else if(dayOfWeek > 2){
            calendar.add(Calendar.DATE, 8-dayOfWeek);
        } else{
            calendar.add(Calendar.DATE, 5-dayOfWeek);
        }

        return calendar.getTime();

    }

}
