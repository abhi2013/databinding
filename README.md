## Android MVVM with DI+Volley demo
A demo app demonstrating an MVVM implementation on Android. The app displays the current hot submissions from the Android subreddit.

## Libraries
The app uses the following libraries :
### Dagger for Dependency Injection
### Volley for Networking
### Moshi for JSON serialisation
### Mockito for Unit testing

## App design
The app is composed of the following components:
### DataService
A singleton used for initialising the networking stack.
### ConnectivityService
A singleton used for observing the device connectivity.
### Data Models
Post - a data class to represent a Reddit post.
### Repository
PostsRepository - A singleton that talks to the DataService and maintains an in-memory cache of the posts.
### ViewModel
PostsViewModel - maintains a view representation of the posts data
### Fragments (Views)
PostsListFragment - displays the list of hot posts
PostDetailFragment - displays the details of a hot post
### Activity
MainActivity - this is the main app Activity in this single Activity application
### Tests
PostsListViewModelTest - tests the view model state

## Future Considerations
The demo app has no data persistency across app restarts. Consider integrating Room to add data persistency.<br />
The demo app has no integrated crash reporting/memory leak detection.
The reddit API does not require any OAuth authentication to fetch the Android subreddits - consider adding an Authentication Service for authenticating API requests.
