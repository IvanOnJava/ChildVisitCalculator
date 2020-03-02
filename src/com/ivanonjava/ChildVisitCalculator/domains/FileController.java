package com.ivanonjava.ChildVisitCalculator.domains;

import com.ivanonjava.ChildVisitCalculator.UI.controllers.DocumentPageControllers;
import com.ivanonjava.ChildVisitCalculator.helpers.Constants;
import com.ivanonjava.ChildVisitCalculator.pojo.PatientForDiary;
import com.ivanonjava.ChildVisitCalculator.pojo.PatientForMagazine;
import com.ivanonjava.ChildVisitCalculator.pojo.PatientForPatronage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileController {

    /**
     * save diary to file
     * @param beginDate date of begin
     * @param endDate date of end
     * @param number number of patronage
     */

    public static void writePatientsDiaryToFile(String beginDate, String endDate, int number) {
        ArrayList<Date> dates = CalendarController.getDaysWithoutWeekend(beginDate, endDate);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Дневник");
        int rowNum = 0;
        Cell cell;
        Row row;
        row = sheet.createRow(rowNum);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("№");

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("ФИО");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Причина");

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Адрес");

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Телефон");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Дата рождения");


        for (Date date : dates) {
            ArrayList<PatientForDiary> patients = DatabaseController.getDiaryForDocument(date.toString(), number);
            rowNum++;
            row = sheet.createRow(rowNum);
            cell = row.createCell(0, CellType.STRING);
            String[] repDate = date.toString().split("-");
            cell.setCellValue(String.format("%s.%s.%s", repDate[2], repDate[1], repDate[0]));
            int count = 0;
            for (PatientForDiary emp : patients) {
                rowNum++;
                row = sheet.createRow(rowNum);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(++count);
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(emp.getName());
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(emp.getReason());
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(emp.getAddress());
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(emp.getPhone());
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(emp.getBirthday());
            }
            for (int i = 0; i < 3; i++) {
                rowNum++;
                row = sheet.createRow(rowNum);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(++count);
            }
            DocumentPageControllers.update();
        }

        File file = new File(Constants.getInstance().DOCUMENT_FILE_PATH + "Дневник №" + number + ".xls");
        save(workbook, file);

    }

    /**
     * save patronage in file
     * @param begin date of begin
     * @param end date of end
     * @param number number of patronage
     */
    public static void savePatronage(String begin, String end, int number) {
        ArrayList<PatientForPatronage> list = DatabaseController.getPatientsForPatronage("birthday", true, begin, end, number);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Патронаж №" + number);
        int rowNum = 0;
        Cell cell;
        Row row;
        row = sheet.createRow(rowNum);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("ФИО");

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Адрес");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Телефон");

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Коментарий");

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Дата рождения");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Дата выписки");

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("1 день");

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("3 день");

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("14 день");

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("21 день");

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("1,5 мес");

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("2,5 мес");

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("3,5 мес");

        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("4,5 мес");

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("5,5 мес");

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("6,5 мес");

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("7,5 мес");

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("8,5 мес");

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("9,5 мес");

        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("10,5 мес");

        cell = row.createCell(20, CellType.STRING);
        cell.setCellValue("11,5 мес");

        for (PatientForPatronage emp : list) {

            row = sheet.createRow(++rowNum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(emp.getFIO());
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(emp.getAddress() + " кв. " + emp.getKv());
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(emp.getPhone());
            cell = row.createCell(3, CellType.STRING);
            String isPresent = emp.isIsPresent() ? " Выбыл" : "";
            cell.setCellValue(emp.getComment() + isPresent);
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(replaceDate(emp.getBirthday()));
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(emp.getDiscardDay());
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(emp.getOneDay());
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(emp.getThreeDay());
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue(emp.getTwoWeeks());
            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue(emp.getThreeWeeks());
            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue(emp.getOneMonth());
            cell = row.createCell(11, CellType.STRING);
            cell.setCellValue(emp.getTwoMonth());
            cell = row.createCell(12, CellType.STRING);
            cell.setCellValue(emp.getThreeMonth());
            cell = row.createCell(13, CellType.STRING);
            cell.setCellValue(emp.getFourMonth());
            cell = row.createCell(14, CellType.STRING);
            cell.setCellValue(emp.getFiveMonth());
            cell = row.createCell(15, CellType.STRING);
            cell.setCellValue(emp.getSixMonth());
            cell = row.createCell(16, CellType.STRING);
            cell.setCellValue(emp.getSevenMonth());
            cell = row.createCell(17, CellType.STRING);
            cell.setCellValue(emp.getEightMonth());
            cell = row.createCell(18, CellType.STRING);
            cell.setCellValue(emp.getNineMonth());
            cell = row.createCell(19, CellType.STRING);
            cell.setCellValue(emp.getTenMonth());
            cell = row.createCell(20, CellType.STRING);
            cell.setCellValue(emp.getElevenMonth());

        }
        File file = new File(Constants.getInstance().DOCUMENT_FILE_PATH + "Патронаж №" + number + ".xls");
        save(workbook, file);
    }

    /**
     * save file to excel
     * @param workbook file
     * @param file file path
     */
    private static void save(HSSFWorkbook workbook, File file) {
        file.getParentFile().mkdirs();
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(file);
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (outFile != null)
                outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * save magazine in file to excel
     * @param begin date of begin
     * @param end date of end
     */
    public static void saveMagazine(String begin, String end) {

        ArrayList<PatientForMagazine> list = DatabaseController.getPatientsForMagazine("birthday", true, begin, end);

        DocumentPageControllers.update();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Журнал");
        int rowNum = 0;
        Cell cell;
        Row row;
        row = sheet.createRow(rowNum);
        int a = 0;
        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("№");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Сертификат");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("ФИО");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Дата рождения");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Дата выписки");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Коментарий");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Адрес");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Пол");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Телефон");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("1 день");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("14 день");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("21 день");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Вес при рождении");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Рост при рождении");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Вес при выписке");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Диагноз");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Дата НБО");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Дата АУДИОСКРИННИНГА");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Обследование на туберкулез");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Дата БЦЖ");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Серия БЦЖ");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Дата ГЕПАТИТА");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Серия ГЕПАТИТА");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Роддом");

        cell = row.createCell(a++, CellType.STRING);
        cell.setCellValue("Кто передал");

        cell = row.createCell(a, CellType.STRING);
        cell.setCellValue("Номер участка");

        for (PatientForMagazine emp : list) {
            row = sheet.createRow(++rowNum);
            int i = 0;
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(rowNum);
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.isSertificate() ? "да" : "нет");
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getFIO());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(replaceDate(emp.getBirthday()));
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getDiscardDay());
            cell = row.createCell(i++, CellType.STRING);
            String isPresent = emp.isIsPresent() ? " Выбыл" : "";
            cell.setCellValue(emp.getComment() + isPresent);
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getAddress() + " кв. " + emp.getKv());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getGender());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getPhone());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getOneDay());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getTwoWeeks());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getThreeWeeks());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getWeightB());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getHeightB());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getWeightD());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getDiagnose());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getDateNBO());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getDateAUDIO());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.isTuber() ? "да" : "нет");
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getDateBCJ());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getSerialBCJ());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getDateGEP());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getSerialGEP());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getRoddom());
            cell = row.createCell(i++, CellType.STRING);
            cell.setCellValue(emp.getHelper());
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(emp.getNumber());
        }
        File file = new File(Constants.getInstance().DOCUMENT_FILE_PATH + "Журнал.xls");
        save(workbook, file);
    }

    private static String replaceDate(String date) {
        String result = date;
       
        if(date.contains("-")){
            String[] temp = date.split("-");
            result = String.format("%s.%s.%s", temp[2], temp[1], temp[0]);
        }
        return result;
    }

}
