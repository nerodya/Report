package ru.croc;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRequestReport {

    private final static String FILE_PATH = "src/main/resources/"
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-MM")) + " Requests.xlsx";

    // названия столбцов
    private final static List<String> HEADINGS = new ArrayList<>(List.of(
            "№ п/п",
            "№ обращения",
            "Тип заявителя",
            "Фамилия",
            "Имя",
            "Отчество",
            "Услуга",
            "Дата получения обращения",
            "Время получения обращения",
            "Дата направления ответа",
            "Время направления ответа",
            "Краткое наименование КО",
            "Номер КО по КГРКО",
            "Статус"));

    public static void main(String[] args) {

        UserRequestReport userRequestReport = new UserRequestReport();
        var data = userRequestReport.readConsole();

        userRequestReport.createReport(data);
    }

    private List<List<String>> readConsole () {

        List<List<String>> data = new ArrayList<>();
        try (BufferedReader inputStream = new BufferedReader(new FileReader("src/main/resources/TestData.txt"))) {
            String line;
            while ((line = inputStream.readLine()) != null){
                data.add(Arrays.stream(line.split(";")).toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private void createReport(List<List<String>> data) {

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {

            Sheet sheet = workbook.createSheet("Отчет");
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();

            font.setBold(true);
            sheet.setDefaultColumnWidth(20);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.JUSTIFY);
            style.setFont(font);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            int row = 0;
            Row headerRow = sheet.createRow(row);
            headerRow.setHeightInPoints(60);
            for (int i = 0; i < HEADINGS.size(); i++) {
                headerRow.createCell(i);
            }
            headerRow.getCell(0).setCellStyle(style);
            headerRow.getCell(0).setCellValue("Перечень обращений за период");

            sheet.addMergedRegion(new CellRangeAddress(row++,0,0, HEADINGS.size() - 1));

            var requestFieldRow = sheet.createRow(row++);
            requestFieldRow.setHeightInPoints(40);

            // заголовки
            int col = 0;
            for (var header : HEADINGS) {
                var cell = requestFieldRow.createCell(col);
                cell.setCellValue(header);
                cell.setCellStyle(style);
                col++;
            }

            // строки
            long id = 1;
            for (var line : data) {
                var rowWithData = sheet.createRow(row);
                rowWithData.createCell(0).setCellValue(id++);
                for (col = 1; col < HEADINGS.size(); col++) {
                    rowWithData.createCell(col);
                    rowWithData.getCell(col).setCellValue(line.get(col));
                }
                row++;
            }
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}