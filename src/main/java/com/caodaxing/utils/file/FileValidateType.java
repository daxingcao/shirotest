package com.caodaxing.utils.file;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


/**
 *
 * 文件类型校验 Spring3及以上 依赖Spring-core.jar和commons-io-xx.jar
 * @author zhaoliang.fu
 */
public class FileValidateType {
    /**
     * 文件头信息，十六进制信息，取前4位
     * 50 4b 03 04 office 2007+
     * d0 cf 11 e0 office 97-03
     * 25 50 44 46 pdf
     * ff d8 ff e0 jpg,部分png与jpg文件前4为一样
     * ff d8 ff e1 jpg,一种不同的jpg头文件   www.2cto.con
     * 89 50 4e 47 png
     * 47 49 46 38 gif
     * 49 49 2A 00 tif
     * 42 4D bmp
     * 38 42 50 53 psd
     * 41 43 31 30 dwg
     * 252150532d41646f6265 ps
     * 0D0A0D0A txt
     * 0D0A2D2D txt
     * 0D0AB4B4 txt
     * B4B4BDA8 txt,文件头部为汉字
     * 73646673 txt,文件头部为英文字母
     * 32323232 txt,文件头部内容为数字
     * 0D0A09B4 txt,文件头部内容为数字
     * 3132330D txt,文件头部内容为数字
     * 7B5C727466 rtf 日记本
     *
     * //视频或音频类
     * 3026B275 wma
     * 57415645 wav
     * 41564920 avi
     * 4D546864 mid
     * 2E524D46 rm
     * 000001BA mpg
     * 000001B3 mpg
     * 6D6F6F76 mov
     * 3026B2758E66CF11 asf
     *
     * 压缩包
     * 52617221 rar
     * 1F8B08 gz
     *
     * 程序文件
     * 3C3F786D6C xml
     * 68746D6C3E html
     * 7061636B java
     * 3C254020 jsp
     * 4D5A9000 exe
     *
     *
     * 44656C69766572792D646174653A eml
     *  邮件 5374616E64617264204A mdb
     * //Access数据库文件
     *
     * 46726F6D mht 4D494D45 mhtml
     */

    /**
     * office 97-03
     */
    public static final String FILE_TYPE_OFFICE_97_03 = "d0cf11e0";

    /**
     * office 2007+
     */
    public static final String FILE_TYPE_OFFICE_07 = "504b0304";

    public static final String FILE_TYPE_OFFICE_PDF = "25504446";

    public static final String FILE_TYPE_OFFICE_JPG1 = "ffd8ffe0";

    public static final String FILE_TYPE_OFFICE_JPG2 = "ffd8ffe1";


    /**
     *
     * @param inputStream  文件魔数校验
     * @param costomTypes
     * @return
     * @throws IOException
     */
    public static boolean validataType(InputStream inputStream, String[] costomTypes)throws IOException {
        byte[] bt=FileValidateType.readInputStream(inputStream);
        String Types = Arrays.toString(costomTypes).substring(1,
                Arrays.toString(costomTypes).length() - 1);
        Types = Types.replaceAll(", ", " ");
        if (bt != null) {
            int size = bt.length;
            String hex = null;
            StringBuilder contentType = new StringBuilder();
            for (int i = 0; i < size; i++) {
                hex = Integer.toHexString(bt[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                contentType.append(hex);
                if (i > 2) {
                    break;
                }
                if (Types.indexOf(contentType.toString()) > -1) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /*
     * private static boolean validateType(String path,String[] costomTypes){
     * boolean flag=false; try { flag =
     * FileValidateType.validataType(FileUtils.readFileToByteArray
     * (ResourceUtils.getFile("classpath:"+path)), costomTypes); } catch
     * (IOException e) { e.printStackTrace(); } return flag; }
     */
    /**
     * 从输入流获取数据
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inputStream)throws IOException
             {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        return outputStream.toByteArray();
    }
}