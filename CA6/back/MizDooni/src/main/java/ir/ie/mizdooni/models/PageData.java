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
        if (pageNumber <= 0 || limit <= 0 || dataList.isEmpty()) {
            return new PageData(false, dataType, 0, 0, new ArrayList<>());
        }
        int totalItems = dataList.size();
        int totalPages = (int) Math.ceil((double) totalItems / limit);

        if (pageNumber > totalPages) {
            return new PageData(false, dataType, totalPages, 0, new ArrayList<>());
        }

        int startIndex = (pageNumber - 1) * limit;
        int endIndex = Math.min(startIndex + limit, dataList.size());

        List<Object> pageList = new ArrayList<>(dataList.subList(startIndex, endIndex));
        boolean hasNextPage = endIndex < dataList.size();

        return new PageData(hasNextPage, dataType, totalPages, pageNumber, new ArrayList<>(pageList));
    }
}
