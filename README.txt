Star Wars Wiki, by Ioannis Mittas

When the app is opened for the first time, the data are fetched from http://swapi.co/api.
Retrofit2 and GSON were used for the network requests.

The user can scroll the character list and click on a character to open it's detailed view.
Every list in the app was implemented with RecyclerViews and the appropriate adapters.

When the user press the favourite button of a character, the character is saved in the favourite character list.
If the user pressed the button again, he is removed from the list. From the settings menu, the user can open the Favourites screen
to see a list of his favourite users.

The app is based in MVVM design pattern. The below  diagram from the official Android Guide to App Architecture shows a rough diagram of
the app architecture: https://developer.android.com/topic/libraries/architecture/images/final-architecture.png

Room is used for local persistence.
Also, LiveData and ViewModels are widely used.

The Views(Activities, Fragments) make data requests to the appropriate ViewModels, which serve as
middlemen between the Views and a Repository. The app broadly uses a reactive mechanism and an Observer-Observable
pattern by using LiveData. Also, in ViewModels, Transformations are used to communicate with the repository.
We do that to  avoid leaking ViewModels, as we want our ViewModels to go away when their respected Views are gone, too.
A more detailed explanation can be found here: https://medium.com/google-developers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54


A Repository is used as a single point of entry for the data. The repository handles data
communication between the local persistance, which is a Room database, and the remote API (http://swapi.co/api/people).
The local database serves as the single source of truth, so even after the data are fetched from swapi,they are being saved locally in the db before
they are exposed to the presentation layer.



