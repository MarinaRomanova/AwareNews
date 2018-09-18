package com.example.android.awarenews;

import android.graphics.Bitmap;

/**
 * Created by Marina on 14.05.2018.
 */

class News {

    //The news article title
    private String newsTitle;

    //The news article sub-title
    private String newsSubtitle;

    //The name of the author if available
    private String newsContributor;

    //Time of the publication
    private String newsPublicationTime;

    //Website URL of the news article
    private String newsUrl;

    //ImageView ID
    private Bitmap newsThumbnailBitmap;

    //Constructs a new News object

    public News(String title, String subtitle, String contributor, String publicationTime, String url, Bitmap thumbnailBitmap) {
        newsTitle = title;
        newsSubtitle = subtitle;
        newsContributor = contributor;
        newsPublicationTime = publicationTime;
        newsUrl = url;
        newsThumbnailBitmap = thumbnailBitmap;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsSubtitle() {
        return newsSubtitle;
    }

    public String getNewsContributor() {
        return newsContributor;
    }

    public String getNewsPublicationTime() {
        return newsPublicationTime;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public Bitmap getNewsThumbnailBitmap() {
        return newsThumbnailBitmap;
    }

}
