package miwok.android.example.com.bookapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Paul on 30-Jun-17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context c, List<Book> b) {
        super (c, 0, b);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.book_title);
        titleView.setText(currentBook.getMtitle());

        TextView pagesView = (TextView) listItemView.findViewById(R.id.book_pages);
        pagesView.setText(String.valueOf(currentBook.getMnumberOfPages()) + " pg." );

        TextView authorView = (TextView) listItemView.findViewById(R.id.book_author);
        authorView.setText(currentBook.getmAuthor());

        ImageView coverImage = (ImageView) listItemView.findViewById(R.id.cover_image);

        if (currentBook.getmBookCoverUrl() != null && !currentBook.getmBookCoverUrl().isEmpty()) {
            Log.v("IMAGE", "IMAGE" + currentBook.getmBookCoverUrl());
            Picasso.with(getContext()).load(currentBook.getmBookCoverUrl()).into(coverImage);
        }

        return listItemView;
}
}