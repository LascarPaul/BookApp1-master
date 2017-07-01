package miwok.android.example.com.bookapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10&orderBy=newest&q=";

    private static final int BOOK_LOADER_ID = 1;
    TextView mEmptyStateTextView;
    private BookAdapter mBookAdapter;
    private SearchView searchView;
    private ListView booksListView;
    private String mQuery;
    private ProgressBar mloadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView) findViewById(R.id.search_view);

        booksListView = (ListView) findViewById(R.id.list);

        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());

        booksListView.setAdapter(mBookAdapter);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book currentBook = mBookAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentBook.getmUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(webIntent);
            }
        });

        mloadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(mEmptyStateTextView);

        if (isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        } else {
            mloadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.noInternet);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (isConnected()) {
                    booksListView.setVisibility(View.INVISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    mloadingIndicator.setVisibility(View.VISIBLE);
                    mQuery = searchView.getQuery().toString();
                    mQuery = mQuery.replace(" ", "+");
                    Log.v(LOG_TAG, mQuery);
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    searchView.clearFocus();
                } else {
                    booksListView.setVisibility(View.INVISIBLE);
                    mloadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText(R.string.noInternet);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        String requestUrl = "";
        if (mQuery != null && mQuery != "") {
            requestUrl = BOOK_REQUEST_URL + mQuery;
        } else {
            String defaultQuery = "android";
            requestUrl = BOOK_REQUEST_URL + defaultQuery;
        }
        return new BookLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        mEmptyStateTextView.setText(R.string.error);
        mloadingIndicator.setVisibility(View.GONE);
        mBookAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mBookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }

}