package cn.com.doc.bean.service;

import cn.com.doc.model.DocBugmessage;
import java.util.List;
import java.util.Set;

public abstract interface IDocBugService
{
  public abstract Set<String> getFilePaths(String paramString1, String paramString2)
    throws Exception;

  public abstract DocBugmessage queryBugMessage(String paramString);

  public abstract String saveBugMessage(DocBugmessage paramDocBugmessage);

  public abstract DocBugmessage updateBugMessage(DocBugmessage paramDocBugmessage);

  public abstract List<DocBugmessage> updateBugMessages(List<DocBugmessage> paramList);

  public abstract String calrealPath(String paramString, List<String> paramList, DocBugmessage paramDocBugmessage);
}