package cn.com.doc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class ZipFileUtil
{
  public static void compressFiles2Zip(File[] files, String zipFilePath)
  {
    if ((files != null) && (files.length > 0) && 
      (isEndsWithZip(zipFilePath))) {
      ZipArchiveOutputStream zaos = null;
      try {
        makeDestDir(zipFilePath);
        File zipFile = new File(zipFilePath);
        zaos = new ZipArchiveOutputStream(zipFile);

        zaos.setUseZip64(Zip64Mode.AsNeeded);

        File[] arrayOfFile = files; int j = files.length; for (int i = 0; i < j; i++) { File file = arrayOfFile[i];
          if (file != null) {
            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(
              file, file.getName());
            zaos.putArchiveEntry(zipArchiveEntry);
            InputStream is = null;
            try {
              is = new FileInputStream(file);
              byte[] buffer = new byte[5120];
              int len = -1;
              while ((len = is.read(buffer)) != -1)
              {
                zaos.write(buffer, 0, len);
              }

              zaos.closeArchiveEntry();
            } catch (Exception e) {
              throw new RuntimeException(e);
            } finally {
              if (is != null) {
                is.close();
              }
            }
          }
        }
        zaos.finish();
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        try {
          if (zaos != null)
            zaos.close();
        }
        catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private static void makeDestDir(String zipFilePath)
  {
    File file = new File(zipFilePath);
    File parent = file.getParentFile();
    if (!parent.exists())
      parent.mkdirs();
  }

  public static void decompressZip(String zipFilePath, String saveFileDir)
  {
    if (isEndsWithZip(zipFilePath)) {
      File file = new File(zipFilePath);
      if (file.exists()) {
        InputStream is = null;

        ZipArchiveInputStream zais = null;
        try {
          is = new FileInputStream(file);
          zais = new ZipArchiveInputStream(is);
          ArchiveEntry archiveEntry = null;

          while ((archiveEntry = zais.getNextEntry()) != null)
          {
            String entryFileName = archiveEntry.getName();

            String entryFilePath = saveFileDir + entryFileName;
            byte[] content = new byte[(int)archiveEntry.getSize()];
            zais.read(content);
            OutputStream os = null;
            try
            {
              File entryFile = new File(entryFilePath);
              os = new FileOutputStream(entryFile);
              os.write(content);
            } catch (IOException e) {
              throw new IOException(e);
            } finally {
              if (os != null) {
                os.flush();
                os.close();
              }
            }
          }
        }
        catch (Exception e) {
          throw new RuntimeException(e);
        } finally {
          try {
            if (zais != null) {
              zais.close();
            }
            if (is != null)
              is.close();
          }
          catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
  }

  public static boolean isEndsWithZip(String fileName)
  {
    boolean flag = false;
    if ((fileName != null) && (!"".equals(fileName.trim())) && (
      (fileName.endsWith(".ZIP")) || (fileName.endsWith(".zip")))) {
      flag = true;
    }

    return flag;
  }
}