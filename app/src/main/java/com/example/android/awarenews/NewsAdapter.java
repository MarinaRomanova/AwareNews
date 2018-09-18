package com.example.android.awarenews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marina on 14.05.2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.thumbnail_image_view);
        imageView.setImageBitmap(currentNews.getNewsThumbnailBitmap());

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_tv);
        titleTextView.setText(currentNews.getNewsTitle());

        TextView contributorTextView = (TextView) listItemView.findViewById(R.id.contributor_tv);
        contributorTextView.setText(currentNews.getNewsContributor());

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_tv);

        dateTextView.setText(currentNews.getNewsPublicationTime());

        TextView subtitleTextView = (TextView) listItemView.findViewById(R.id.subtitle_tv);
        subtitleTextView.setText(currentNews.getNewsSubtitle());

        return listItemView;
    }

}

