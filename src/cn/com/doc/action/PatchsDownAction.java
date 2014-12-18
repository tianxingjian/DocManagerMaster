package cn.com.doc.action;

import cn.com.doc.bean.service.IDocBugService;
import cn.com.doc.util.ZipFileUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class PatchsDownAction extends ActionSupport
{
  private static final long serialVersionUID = 1L;
  private String bugId;
  private String filename;
  private String errorMsg;
  private Set<String> absolutePath = null;
  private IDocBugService docBugService;

  public String getErrorMsg()
  {
    return this.errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getFilename() {
    return this.filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getBugId() {
    return this.bugId;
  }

  public void setBugId(String bugId) {
    this.bugId = bugId;
  }

  public IDocBugService getDocBugService() {
    return this.docBugService;
  }

  public void setDocBugService(IDocBugService docBugService) {
    this.docBugService = docBugService;
  }

  public String execute()
  {
    String rootDir = getText("rootDir");
    try {
      this.absolutePath = this.docBugService.getFilePaths(this.bugId, rootDir);
    } catch (Exception e) {
      setErrorMsg(e.getMessage());
      return "error";
    }

    if ((this.absolutePath != null) && (this.absolutePath.size() > 0)) {
      if (this.absolutePath.size() == 1) {
        setFilename((String)this.absolutePath.iterator().next());
      } else {
        String[] paths = (String[])this.absolutePath.toArray(new String[0]);
        File[] files = new File[paths.length];
        for (int i = 0; i < paths.length; i++) {
          files[i] = new File(paths[i]);
        }
        String tmpDir = getText("tmpDir");

        this.filename = (tmpDir + File.separator + this.bugId + ".zip");
        ZipFileUtil.compressFiles2Zip(files, getFilename());
      }
      return "success";
    }
    setErrorMsg("未找到对应的附件！");
    return "error";
  }
}