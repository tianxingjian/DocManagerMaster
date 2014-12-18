package cn.com.doc.bean.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.com.doc.bean.service.IDocBugService;
import cn.com.doc.dao.impl.BaseDao;
import cn.com.doc.model.DocBugmessage;
import cn.com.doc.model.DocPatch;
import cn.com.doc.util.FileUtil;
import cn.com.doc.util.MD5;

public class DocBugServiceImpl extends BaseDao<DocBugmessage, String> implements
		IDocBugService {
	public Long savePatch(DocPatch patch) {
		return (Long) getSession().save(patch);
	}

	public List<Long> savePatchs(Set<DocPatch> patchs) {
		List<Long> patchIds = new ArrayList<Long>();
		for (DocPatch patch : patchs) {
			patchIds.add(savePatch(patch));
		}
		return patchIds;
	}

	public void deleteBatch(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			this.deleteById(ids[i]);
		}
	}

	public void saveBugPatchBatch(List<DocBugmessage> bugMsgs, List<String> fileName) {
		
		for(DocBugmessage bugMsg : bugMsgs){
			String prikey = (String) this.save(bugMsg);
			DocBugmessage newbugMsg = (DocBugmessage) this.get(prikey);

			Set<DocPatch> patchs = new HashSet<DocPatch>();
			for (int i = 0; i < fileName.size(); i++) {
				DocPatch patch = new DocPatch();
				patch.setFilename((String) fileName.get(i));
				patch.setDocBugmessage(newbugMsg);
				patch.setTs(new Date());
				patchs.add(patch);
			}
			List<Long> patchIds = savePatchs(patchs);
		}
		
	}

	public DocBugmessage queryBugMessage(String bugId) {
		return (DocBugmessage) get(bugId);
	}

	public String saveBugMessage(DocBugmessage bugDoc) {
		return (String) super.save(bugDoc);
	}

	public DocBugmessage updateBugMessage(DocBugmessage bugMsg) {
		super.saveOrUpdate(bugMsg);
		return (DocBugmessage) load(bugMsg.getBugId());
	}

	public List<DocBugmessage> updateBugMessages(List<DocBugmessage> bugMsgs) {
		List bugmsgs = new ArrayList();
		for (int i = 0; i < bugMsgs.size(); i++) {
			bugmsgs.add(updateBugMessage((DocBugmessage) bugMsgs.get(i)));
		}
		return bugmsgs;
	}

	public String calrealPath(String bugIds, List<String> fileFileName,
			DocBugmessage freeBugMsg) {
		String[] ids = bugIds.split(",");
		String relPath = null;
		if (ids.length == 1)
			relPath = excutecalSimrealPath(bugIds, fileFileName, freeBugMsg);
		else {
			relPath = excutecalComrealPath(ids, fileFileName, freeBugMsg);
		}
		return relPath;
	}

	private String excutecalSimrealPath(String bugId, List<String> fileName,
			DocBugmessage freeBugMsg) {
		boolean isdel = deleteById(bugId);
		String relPath = MD5.Md(
				freeBugMsg.getProductName() + freeBugMsg.getProjectName(),
				false);

		DocBugmessage bugMsg = new DocBugmessage();
		bugMsg.setBugId(bugId);
		bugMsg.setProductName(freeBugMsg.getProductName());
		bugMsg.setProjectName(freeBugMsg.getProjectName());
		bugMsg.setRelpath(relPath);
		bugMsg.setVersionNo(freeBugMsg.getVersionNo());
		bugMsg.setTs(new Date());

		String prikey = (String) save(bugMsg);
		bugMsg = (DocBugmessage) get(prikey);

		Set patchs = new HashSet();
		for (int i = 0; i < fileName.size(); i++) {
			DocPatch patch = new DocPatch();
			patch.setFilename((String) fileName.get(i));
			patch.setDocBugmessage(bugMsg);
			patch.setTs(new Date());
			patchs.add(patch);
		}

		List patchIds = savePatchs(patchs);
		return relPath;
	}

	private String excutecalComrealPath(String[] bugIds, List<String> fileName,
			DocBugmessage freeBugMsg) {

		deleteBatch(bugIds);
		String relPath = MD5.Md(
				freeBugMsg.getProductName() + freeBugMsg.getProjectName(),
				false);

		List<DocBugmessage> bugMsgs = new ArrayList<DocBugmessage>();
		for (int i = 0; i < bugIds.length; i++) {
			DocBugmessage bugMsg = new DocBugmessage();
			bugMsg.setBugId(bugIds[i]);
			bugMsg.setProductName(freeBugMsg.getProductName());
			bugMsg.setProjectName(freeBugMsg.getProjectName());
			bugMsg.setRelpath(relPath);
			bugMsg.setVersionNo(freeBugMsg.getVersionNo());
			bugMsg.setTs(new Date());
			
			bugMsgs.add(bugMsg);
		}
		saveBugPatchBatch(bugMsgs, fileName);

		return relPath;
	}

	public Set<String> getFilePaths(String bugId, String rootDir)
			throws Exception {
		DocBugmessage bugmsg = (DocBugmessage) get(bugId);
		if ((bugmsg == null) || (StringUtils.isBlank(bugmsg.getBugId()))
				|| (bugmsg.getDocPatchs() == null)) {
			throw new Exception("未找到对应的补丁！");
		}

		String parentPath = rootDir + File.separator + bugmsg.getRelpath();
		Set fileNames = new HashSet();
		Set<DocPatch> patchs = (Set<DocPatch>) bugmsg.getDocPatchs();
		for (DocPatch patch : patchs) {
			fileNames.add(patch.getFilename());
		}

		FileUtil fileUtil = new FileUtil();
		Set absolutePaths = fileUtil.getAbsoluteFilePath(parentPath, fileNames);

		if (absolutePaths.size() == 0) {
			throw new Exception("未找到对应的补丁！");
		}

		return absolutePaths;
	}
}