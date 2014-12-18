package hibernate.test;

import java.io.File;

import cn.com.doc.util.ZipFileUtil;

public class ZipFileUtilTest
{
  public void testCompressFiles2Zip()
  {
    String fileName1 = "stzb.sql";
    String fileName2 = "查到根源数据的sql.sql";
    String path = "C:\\Users\\Administrator\\Desktop\\非信贷sql";

    File[] files = new File[2];
    files[0] = new File(path, fileName1);
    files[1] = new File(path, fileName2);

    String zipFilePath = "C:\\Users\\Administrator\\Desktop\\非信贷sql\\test.zip";

    ZipFileUtil.compressFiles2Zip(files, zipFilePath);
  }

  public void testDecompressZip()
  {
    String zipFilePath = "d:/test2/test.zip";

    String saveFileDir = "d:/test2/";

    ZipFileUtil.decompressZip(zipFilePath, saveFileDir);
  }

  public static void main(String[] args) {
    ZipFileUtilTest util = new ZipFileUtilTest();
    util.testCompressFiles2Zip();
  }
}