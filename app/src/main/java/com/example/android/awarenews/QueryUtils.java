package com.example.android.awarenews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marina on 14.05.2018.
 */

public final class QueryUtils {

    //Tag for the log messages
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and return a list of news articles
        List<News> newsList = extractFeatureFromJson(jsonResponse);
        return newsList;
    }

    //Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    //Convert the {@link InputStream} into a String which contains the whole JSON response from the server.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Return a list of news objects that has been built up from parsing the given JSON response.

    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        List<News> newsList = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray jsonResultsArray = response.getJSONArray("results");

            for (int i = 0; i < jsonResultsArray.length(); i++) {
                // Get a single earthquake at position i within the list of earthquakes
                JSONObject results = jsonResultsArray.getJSONObject(i);

                // Extract from "results" JSONArray the value for the keys: webPublicationDate, webTitle, webUrl
                String title = results.optString("webTitle");
                String url = results.optString("webUrl");
                String date = results.optString("webPublicationDate");
                String dateFormat;
                if (date == null) {
                    dateFormat = " ";
                } else {
                    dateFormat = date.substring(8, 10);
                    dateFormat += "." + date.substring(5, 7);
                    dateFormat += "." + date.substring(0, 4);
                }

                //Extract from "fields" JSONArray the value for the keys: trailText, thumbnail
                JSONObject fields = results.getJSONObject("fields");
                String trailText = fields.optString("trailText");
                trailText = stripHtml(trailText);
                String imageUrl = fields.optString("thumbnail");

                InputStream inputStream = new java.net.URL(imageUrl).openStream();
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                //Extract from "tags" JSONArray the value for the key: webTitle
                JSONArray tagsArray = results.getJSONArray("tags");
                String author = null;
                if (!tagsArray.isNull(0)) {
                    JSONObject contributorTag = (JSONObject) tagsArray.get(0);

                    //Check if there is first name and store otherwise set it to null
                    if (!contributorTag.isNull("firstName")) {
                        author = contributorTag.getString("webTitle");
                    } else {
                        author = null;
                    }
                }

                // Create a news object with the information from JSON response and add it to the list.
                News newsArticle = new News(title, trailText, author, dateFormat, url, imageBitmap);
                newsList.add(newsArticle);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private static String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return String.valueOf(Html.fromHtml(html));
        }
    }
}



