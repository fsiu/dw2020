package net.darkwire.example.widgets.adapter;

/**
 * Created by fsiu on 4/19/14.
 */
public interface NumericPagination {
    public long getMaxPages();
    public void setMaxPages(final long maxPages);

    public long getCurrentPage();
    public void setCurrentPage(final long currentPage);

    public int getResultsPerPage();
    public void setResultsPerPage(final int resultsPerPage);

    public void incrementPage();
    public void decrementPage();

    public boolean hasMorePages();
}
