/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.utils;

import dsl.db.converter.Main;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.*;

import static dsl.db.converter.constant.ExcelConstant.*;

public class ExcelUtils
{
    private ExcelUtils() {}

    public static void read(String filename)
    {
        File file = new File(Main.class.getResource(String.format("/%s", filename)).getFile());
        try(Workbook workbook = WorkbookFactory.create(file))
        {
            for(int sheetIdx = 0; sheetIdx < workbook.getNumberOfSheets(); sheetIdx++)
            {
                Sheet sheet = workbook.getSheetAt(sheetIdx);

                String tableName = getTableName(sheet);
                Map<String, String> columnsMap = getDbColumns(sheet);
                List<List<Object>> data = getData(sheet);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String getTableName(Sheet sheet)
    {
        String tableName = sheet.getRow(0).getCell(0).getStringCellValue();

        System.out.println("tableName: " + tableName);

        return tableName;
    }

    private static Map<String, String> getDbColumns(Sheet sheet)
    {
        Map<String, String> columnsMap = new LinkedHashMap<>();

        Row columnNameRow = sheet.getRow(findRowIdx(sheet, COLUMN_NANE_ROW_GAP));
        Row dataTypeRow = sheet.getRow(findRowIdx(sheet, DATA_TYPE_ROW_GAP));

        for(int cellIdx = 0; cellIdx < columnNameRow.getLastCellNum(); cellIdx++)
        {
            String columnName = columnNameRow.getCell(cellIdx).getStringCellValue();
            String dataType = dataTypeRow.getCell(cellIdx).getStringCellValue();

            if(columnName.isEmpty() && dataType.isEmpty()) continue;

            columnsMap.put(columnName, dataType);
        }

        System.out.println("columnsMap: " + columnsMap);

        return columnsMap;
    }

    private static List<List<Object>> getData(Sheet sheet)
    {
        List<List<Object>> columnsData = new ArrayList<>();

        int idx = findRowIdx(sheet, DATA_ROW_GAP);

        try
        {
            if(idx == - 1) throw new Exception("START not found");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            for(int rowIdx = idx; rowIdx < sheet.getLastRowNum(); rowIdx++)
            {
                Row dataRow = sheet.getRow(rowIdx);

                List<Object> columnData = new ArrayList<>();

                for(int cellIdx = 0; cellIdx < dataRow.getLastCellNum(); cellIdx++)
                {
                    Object value = getCellValue(dataRow.getCell(cellIdx));
                    if(Objects.nonNull(value)) columnData.add(value);
                }

                if(!columnData.isEmpty()) columnsData.add(columnData);
            }
            System.out.println("columnsData: " + columnsData);
        }
        return columnsData;
    }

    private static Object getCellValue(Cell cell)
    {
        switch (cell.getCellTypeEnum())
        {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case BLANK:
            default:
                return null;
        }
    }

    private static int findRowIdx(Sheet sheet, int gap)
    {
        for(int rowIdx = 0; rowIdx < sheet.getLastRowNum(); rowIdx++)
        {
            Row row = sheet.getRow(rowIdx);
            Cell cell = row.getCell(0);

            if(cell.getCellTypeEnum() == CellType.STRING && Objects.equals(cell.getStringCellValue(), START_COLUMN))
            {
                return rowIdx + gap;
            }
        }
        return -1;
    }
}
