package cn.com.doc.action;

import cn.com.doc.util.FileUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.InputStream;

public class FileDownAction extends ActionSupport
{
  private static final long serialVersionUID = 1L;
  private String filename;
  private String patchName;

  public String getFilename()
  {
    return this.filename;
  }

  public void setFilename(String filename) throws Exception {
    this.filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
  }

  public String getPatchName() {
    return this.patchName;
  }

  public void setPatchName(String patchName) {
    this.patchName = patchName;
  }

  public InputStream getDownloadFile() throws Exception
  {
    FileUtil fileUtil = new FileUtil();

    File file = new File(this.filename);

    setPatchName(file.getName());

    InputStream in = fileUtil.getInputStream(file);

    return in;
  }

  public String execute()
  {
    return "success";
  }
}