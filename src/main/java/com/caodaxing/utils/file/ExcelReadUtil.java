package com.caodaxing.utils.file;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReadUtil {

    public static final String EXCEL_07=".xlsx";

    public static final String EXCEL_97=".xls";

    /**
     * 校验Excel97
     */
    public static boolean excel97FileType(String fileName, InputStream inputStream)throws IOException {
        String[] customTypes={FileValidateType.FILE_TYPE_OFFICE_97_03};
        if(fileName.endsWith(EXCEL_97)&& FileValidateType.validataType(inputStream,customTypes)){
            return true;
        }
        return false;
    }

    /**
     * 校验Excel07
     */
    public static boolean excel07FileType(String fileName, InputStream inputStream)throws IOException {
        String[] customTypes={FileValidateType.FILE_TYPE_OFFICE_07};
        if(fileName.endsWith(EXCEL_07)&& FileValidateType.validataType(inputStream,customTypes)){
            return true;
        }
        return false;
    }

}
