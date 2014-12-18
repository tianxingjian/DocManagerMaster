package cn.com.doc.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DocBugmessage entity. @author MyEclipse Persistence Tools
 */
public class DocBugmessage implements java.io.Serializable {

	private String bugId;
	private String productName;
	private String projectName;
	private String versionNo;
	private String relpath;
	private Date ts;
	private Set docPatchs = new HashSet(0);
	// Constructors

	/** default constructor */
	public DocBugmessage() {
	}

	/** full constructor */
	public DocBugmessage(String productName, String projectName,
			String versionNo, String relpath, Date ts, Set docPatchs) {
		this.productName = productName;
		this.projectName = projectName;
		this.versionNo = versionNo;
		this.relpath = relpath;
		this.ts = ts;
		this.docPatchs = docPatchs;
	}

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getRelpath() {
		return relpath;
	}

	public void setRelpath(String relpath) {
		this.relpath = relpath;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Set getDocPatchs() {
		return docPatchs;
	}

	public void setDocPatchs(Set docPatchs) {
		this.docPatchs = docPatchs;
	}
}
