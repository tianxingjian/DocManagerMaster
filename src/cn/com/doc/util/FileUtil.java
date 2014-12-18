package cn.com.doc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class FileUtil
{
  public boolean isFileExists(String rootPath, String fileName)
  {
    File file = new File(rootPath, fileName);
    boolean result = false;

    if (file.exists()) {
      result = true;
    }

    return result;
  }

  public boolean isFileNorDirExists(String parentPath, String fileName)
  {
    File file = new File(parentPath, fileName);
    boolean result = false;

    if ((file.exists()) && (file.isFile())) {
      result = true;
    }

    return result;
  }

  public InputStream getInputStream(String parentPath, String fileName) throws IOException
  {
    InputStream stream = null;

    if (isFileExists(parentPath, fileName)) {
      File file = new File(parentPath, fileName);
      stream = new FileInputStream(file);
    }

    return stream;
  }

  public InputStream getInputStream(File file) throws IOException
  {
    InputStream stream = null;
    stream = new FileInputStream(file);

    return stream;
  }

  public Set<String> getAbsoluteFilePath(String parentPath, Set<String> fileNames)
  {
    Set filePath = new HashSet();
    for (String fileName : fileNames) {
      if (isFileNorDirExists(parentPath, fileName)) {
        filePath.add(parentPath + File.separator + fileName);
      }
    }

    return filePath;
  }

  public InputStream getZipInputStream(Set<String> absolutePath) throws IOException
  {
    Vector vector = new Vector();

    for (String filePath : absolutePath) {
      vector.addElement(new FileInputStream(filePath));
    }

    Enumeration e = vector.elements();

    Object ins = new SequenceInputStream(e);
    return (InputStream)ins;
  }
}