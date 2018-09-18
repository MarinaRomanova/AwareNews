package com.example.android.awarenews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Marina on 14.05.2018.
 */

class NewsLoader extends AsyncTaskLoader<List<News>> {

    //URL for news query
    private String newsUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        newsUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (newsUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news articles.
        return QueryUtils.fetchNewsData(newsUrl);
    }
}
