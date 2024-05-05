package ir.ie.mizdooni.models;

import java.util.ArrayList;
import java.util.List;

public class PageData {
    boolean hasNextPage;
    String dataType;
    long totalPages;
    long currentPage;
    List<Object> PageList;

    public PageData(boolean hasNextPage, String dataType, long totalPages, long currentPage, List<Object> PageList) {
        this.hasNextPage = hasNextPage;
        this.dataType = dataType;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.PageList = PageList;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public String getDataType() {
        return dataType;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public List<Object> getPageList() {
        return PageList;
    }

    public static PageData paginate(List<Object> dataList, int pageNumber, int limit, String dataType) {
        int startIndex = (pageNumber - 1) * limit;
        int endIndex = Math.min(startIndex + limit, dataList.size());

        List<Object> pageList = new ArrayList<>(dataList.subList(startIndex, endIndex));
        boolean hasNextPage = endIndex < dataList.size();
        long totalPages = (long) Math.ceil((double) dataList.size() / limit);

        return new PageData(hasNextPage, dataType, totalPages, pageNumber, new ArrayList<>(pageList));
    }
}
