package cn.com.doc.dao.util;

import java.util.List;

public class PageResults<T>
{
  private int pageNo;
  private int currentPage;
  private int pageSize;
  private int totalCount;
  private int pageCount;
  private List<T> results;

  public int getPageCount()
  {
    return this.pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public int getPageNo() {
    if (this.pageNo <= 0) {
      return 1;
    }
    return this.pageNo > this.pageCount ? this.pageCount : this.pageNo;
  }

  public void setPageNo(int pageNo)
  {
    this.pageNo = pageNo;
  }

  public List<T> getResults() {
    return this.results;
  }

  public void setResults(List<T> results) {
    this.results = results;
  }

  public int getCurrentPage() {
    return this.currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = (pageSize <= 0 ? 10 : pageSize);
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public void resetPageNo() {
    this.pageNo = (this.currentPage + 1);
    this.pageCount = (this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize : 
      this.totalCount / this.pageSize + 1);
  }
}