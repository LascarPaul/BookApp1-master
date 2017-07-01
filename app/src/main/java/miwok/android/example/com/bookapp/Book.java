package miwok.android.example.com.bookapp;

/**
 * Created by Paul on 30-Jun-17.
 */

public class Book {

    private String mtitle;

    private String mAuthor;

    private int mnumberOfPages;

    private String mUrl;

    private String mBookCover;

    public Book(String mtitle, String mAuthor, int mnumberOfPages, String mUrl, String mBookCover) {
        this.mtitle = mtitle;
        this.mAuthor = mAuthor;
        this.mnumberOfPages = mnumberOfPages;
        this.mUrl = mUrl;
        this.mBookCover = mBookCover;
    }

    public String getMtitle() {
        return mtitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public int getMnumberOfPages() {
        return mnumberOfPages;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmBookCoverUrl() {
        return mBookCover;
    }
}