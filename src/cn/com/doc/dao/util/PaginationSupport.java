package cn.com.doc.dao.util;

import java.util.ArrayList;
import java.util.List;

public class PaginationSupport
{
  public static int PAGESIZE = 10;

  private int pageSize = PAGESIZE;
  private List items;
  private int totalCount;
  private int[] indexes = new int[0];

  private int startIndex = 0;
  private int lastStartIndex;

  public PaginationSupport()
  {
  }

  public PaginationSupport(List items, int totalCount)
  {
    setPageSize(PAGESIZE);
    setTotalCount(totalCount);
    setItems(items);
    setStartIndex(0);
  }

  public PaginationSupport(List items, int totalCount, int startIndex) {
    setPageSize(PAGESIZE);
    setTotalCount(totalCount);
    setItems(items);
    setStartIndex(startIndex);
  }

  public PaginationSupport(List items, int totalCount, int pageSize, int startIndex)
  {
    setPageSize(pageSize);
    setTotalCount(totalCount);
    setItems(items);
    setStartIndex(startIndex);
  }

  public List getItems() {
    return this.items;
  }

  public void setItems(List items) {
    this.items = items;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public void setTotalCount(int totalCount) {
    if (totalCount > 0) {
      this.totalCount = totalCount;
      int count = totalCount / this.pageSize;
      if (totalCount % this.pageSize > 0)
        count++;
      this.indexes = new int[count];
      for (int i = 0; i < count; i++)
        this.indexes[i] = (this.pageSize * i);
    }
    else {
      this.totalCount = 0;
    }
  }

  public int[] getIndexes() {
    return this.indexes;
  }

  public void setIndexes(int[] indexes) {
    this.indexes = indexes;
  }

  public int getStartIndex() {
    return this.startIndex;
  }

  public void setStartIndex(int startIndex) {
    if (this.totalCount <= 0)
      this.startIndex = 0;
    else if (startIndex >= this.totalCount)
      this.startIndex = this.indexes[(this.indexes.length - 1)];
    else if (startIndex < 0)
      this.startIndex = 0;
    else
      this.startIndex = this.indexes[(startIndex / this.pageSize)];
  }

  public int getNextIndex()
  {
    int nextIndex = getStartIndex() + this.pageSize;
    if (nextIndex >= this.totalCount) {
      return getStartIndex();
    }
    return nextIndex;
  }

  public int getPreviousIndex() {
    int previousIndex = getStartIndex() - this.pageSize;
    if (previousIndex < 0) {
      return 0;
    }
    return previousIndex;
  }

  public int getLastIndex() {
    int[] indexes = getIndexes();
    if ((indexes != null) && (indexes.length > 0)) {
      this.lastStartIndex = indexes[(indexes.length - 1)];
    }
    return this.lastStartIndex;
  }

  public int getPages()
  {
    if (getTotalCount() % this.pageSize == 0) {
      return getTotalCount() / this.pageSize;
    }
    return getTotalCount() / this.pageSize + 1;
  }

  public int getCurrentPage() {
    return getStartIndex() / this.pageSize + 1;
  }

  public ArrayList<Integer> page(int totalPages, int currentPage)
  {
    int adjacents = 3;
    ArrayList result = new ArrayList();
    if (totalPages < 5 + adjacents * 2) {
      writeNumberedLinks(1, totalPages, result);
    }
    else if ((totalPages - adjacents * 2 > currentPage) && (currentPage > adjacents * 2)) {
      writeNumberedLinks(1, 1, result);
      writeElipsis(result);
      writeNumberedLinks(currentPage - adjacents - 1, currentPage + adjacents, result);
      writeElipsis(result);
      writeNumberedLinks(totalPages, totalPages, result);
    } else if (currentPage < totalPages / 2) {
      writeNumberedLinks(1, 3 + adjacents * 2, result);
      writeElipsis(result);
      writeNumberedLinks(totalPages, totalPages, result);
    } else {
      writeNumberedLinks(1, 1, result);
      writeElipsis(result);
      writeNumberedLinks(totalPages - (2 + adjacents * 2), totalPages, result);
    }

    return result;
  }

  private void writeElipsis(ArrayList<Integer> result)
  {
    result.add(Integer.valueOf(-1));
  }

  private void writeNumberedLinks(int i, int lastIndex, ArrayList<Integer> result)
  {
    for (int d = i; d <= lastIndex; d++)
      result.add(Integer.valueOf(d));
  }
}