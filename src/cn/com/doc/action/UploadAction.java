package cn.com.doc.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringBufferInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.doc.bean.service.IDocBugService;
import cn.com.doc.model.DocBugmessage;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;
	private InputStream inputStream;
	private String bugIds;
	private String productName;
	private String projectName;
	private String versionNo;
	private IDocBugService docBugService;

	public IDocBugService getDocBugService() {
		return this.docBugService;
	}

	@Autowired
	public void setDocBugService(IDocBugService docBugService) {
		this.docBugService = docBugService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getBugIds() {
		return this.bugIds;
	}

	public void setBugIds(String bugIds) {
		this.bugIds = bugIds;
	}

	public List<File> getFile() {
		return this.file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return this.fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return this.fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String execute() throws Exception {
		DocBugmessage bugMsg = new DocBugmessage();
		bugMsg.setBugId(this.bugIds);
		bugMsg.setProductName(this.productName);
		bugMsg.setProjectName(this.projectName);
		bugMsg.setVersionNo(this.versionNo);
		bugMsg.setTs(new Date());

		Map<String, String> map = new HashMap<String, String>();
		String relPath = null;
		try {
			relPath = this.docBugService.calrealPath(this.bugIds,
					this.fileFileName, bugMsg);
			map.put("upload_m_code", cn.com.doc.util.DocStatus.UP_CODE_ARRAY[1]);
			for (int i = 0; i < this.file.size(); i++) {
				uploadFile(i, relPath);
			}
		} catch (Exception e) {
			map.put("upload_m_code", cn.com.doc.util.DocStatus.UP_CODE_ARRAY[0]);
		}

//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.reset();
//		response.setContentType("text/plain;charset=utf-8");
//		PrintWriter writer = response.getWriter();
//		writer.print(map.get("upload_m_code"));
//		writer.flush();
//		writer.close();
		 inputStream = new StringBufferInputStream(map.get("upload_m_code"));
		return SUCCESS;
	}

	private void uploadFile(int i, String realPath)
			throws FileNotFoundException, IOException {
		try {
			InputStream in = new FileInputStream((File) this.file.get(i));

			String rootDir = getText("rootDir") + File.separator + realPath;
			File tmpdir = new File(rootDir);
			if (!tmpdir.exists()) {
				tmpdir.mkdirs();
			}
			File uploadFile = new File(rootDir, (String) getFileFileName().get(
					i));
			OutputStream out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1048576];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}

			in.close();
			out.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}