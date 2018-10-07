# AwareNews
News app using Guardian's API

This Android app was created for the purpose of and assignment in the frame of Android Basics Udacity and Google's course. 

News feed devoted to Travel is regularly updated by parsing responses from Guardian's API - [here](https://content.guardianapis.com/).
While articles are being loaded an empty view is displayed.

![awarenews](https://user-images.githubusercontent.com/36896406/45706008-6c786080-bb7b-11e8-9114-4337a6f9da68.png)

![emptyview](https://user-images.githubusercontent.com/36896406/45706016-74d09b80-bb7b-11e8-9001-35f399320116.png)

## API
Please be advised that student's api-key will expire after a certain number of requests sent.
You can change this parameter at NewsActivity line #101 
`uriBuilder.appendQueryParameter("api-key", "8420c23a-........");`

## Other features
User can narrow-down the selection using preference screen.

![preference_screen_by_category](https://user-images.githubusercontent.com/36896406/45706019-74d09b80-bb7b-11e8-9d3a-2b383e62afae.png)

![preference_screen_sort_by](https://user-images.githubusercontent.com/36896406/45706765-64212500-bb7d-11e8-8a20-daca42cd08c2.png)

User can consult the original article using a browser.

![readmore_intent](https://user-images.githubusercontent.com/36896406/45706766-64b9bb80-bb7d-11e8-91d2-1e9529285f27.png)

## Covered materials:
* Connecting to an API
* Parsing the JSON response
* Using an AsyncTask
