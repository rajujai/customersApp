export default class Pagination {
    page;
    data;
    totalPages;
    totalElements;
    isSorted;
    isFirstPage;
    isLastPage;
    constructor({ page, data, totalPages, totalElements, sorted, firstPage, lastPage }) {
        this.page = new Page(page);
        this.items = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.isSorted = sorted;
        this.isFirstPage = firstPage;
        this.isLastPage = lastPage;
    }

}

export class Page {
    constructor({ pageNo, pageSize, sortBy, order }) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.order = order;
    }

    static newPage() {
        return new Page(0, 10, null, null);
    }
}