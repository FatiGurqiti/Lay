# Lay

**Lay** is an android project that was originally made for summer internship at Information Systems Engineering of Kocaeli University. And later improved and turned into a real application.

## Technologies

- Java
- XML
- Android Studio
- FireBase
- Sqlite
- RESTful API
- JUnit5

![Image of Thumbnail](https://github.com/FatiGurqiti/Lay/blob/develop/Thumbnail.png)

### Documentation

The app uses RESTful API provided by [RapidAPI's imdb api](https://rapidapi.com/apidojo/api/imdb8/). On the MainActivity you should be able to see a String called APItoken. Sign up for api and add your token there. The API 

es will use that String for the token.

For [FireBase](https://firebase.google.com/), You have to replace the existing _google-services.json_ and add your own. 


### Usage of the app

The app contains simple a sqlite database to work with simple tasks like: If its user's first time opening the app, in this case it will show the first time fragements. Or the status wether the user is already signed in or not. Which, in this case it directs to needed Activity. So, that the user won't have to sign in everytime. Keep in mind that sqlite only set's and get's the status of login, the datas are provided by Firebase. You may find the needed codes in _Account_ and _DatabaseHandler_ classes under the database folder.


![Image of Sqlite](https://github.com/FatiGurqiti/Lay/blob/develop/Sqlite.png)

On the opening page you should see the log in Activity.If you don't have an account you can simply click on 'Sign Up' and register on the opened page.Once you complete all the inputs,program will check if given e-mail adress does not exist on the _FireBase_,your info's will be saved to the database.
To check if the user exsits:

```
                private void ifuserExists(String email)
    {
        String TAG = "IfUserExists: ";
        DocumentReference docRef = db.collection("users").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        //This user is created before
                        canclick = false;
                    } else {
                        Log.d(TAG, "No such document");
                        //This is user isn't created before
                        canclick = true;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
```

<br>

And the if the canclick boolean is true, program can run **createAccount(String email, String password)** function. Which creates the account with Firebase. Auth and **userData(String email, String password, String username)** function. Which add's the user's data to Firebase.
Since RAPID API give you limit of 500 query per month, I have also added a quota `user.put("quota", 20);` to FireStore for purpose of prevention one user to drain all query.
This quota is being updated every day with `quotaQuery()` function on _MainLoggedin_ class

<br>
The contents that can bee seen on _My List_ fragment is getting the data from FireStore. This is done to prevent using API everytime users want to seen their saved contents. You may check the `saveMovie()` function in _MovieDetails_ class. If the user is viewing a page that hasn't been saved before it will get the data from API. You can see there is a bollean named `isSaved` which get's the status from previos page using Bundle. If the user is coming from _My List_ it will load the data from Firebase otherwise, the content isn't saved before so, it will load the data from API. 
You may view all of APIs under the folder of _api_ .

<br>

Finally, there's a 'seen' function. On _My List_ fragment there's a TextView that says "Set as Seen", this makes the user vote for the movie and collectes the votes in _Movie Rate_ document in FireStore. It also put's the movie to _SeenMovies_ document to seperate the ones that are seen from the others. This way, even if the user set's a content seen and unsaves it it will still put the content to Firebase in order to prevent voting again or marking it seen if the movie is going to be saved in future.

<br>

![Image of FireBase](https://github.com/FatiGurqiti/Lay/blob/develop/FireBase.png)
