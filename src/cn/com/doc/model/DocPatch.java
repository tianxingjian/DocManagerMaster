package cn.com.doc.model;

import java.util.Date;

/**
 * DocPatch entity. @author MyEclipse Persistence Tools
 */
public class DocPatch implements java.io.Serializable {

	private long id;
	private DocBugmessage docBugmessage;
	private String filename;
	private Date ts;
	// Constructors

	/** default constructor */
	public DocPatch() {
	}

	/** full constructor */
	public DocPatch(DocBugmessage docBugmessage, String filename, Date ts) {
		this.docBugmessage = docBugmessage;
		this.filename = filename;
		this.ts = ts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DocBugmessage getDocBugmessage() {
		return docBugmessage;
	}

	public void setDocBugmessage(DocBugmessage docBugmessage) {
		this.docBugmessage = docBugmessage;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

}
