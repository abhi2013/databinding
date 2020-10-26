## Android Data bindings demo with MVVM architecture using DI
A demo app demonstrating the use of data bindings to capture user input in an application based on the MVVM architecture. The app shows a user input field, performs some validation on it and subsequently display some output to the user.
## Libraries
The app uses the following libraries :
### Dagger for Dependency Injection
The app uses Dagger multibindings to create a scalable ViewModelFactory.
### Mockito for Unit testing

## App design
The app is composed of the following components:
### Data Models
InputNumber - a data class to represent a user input.
### Repository
PostsRepository - A singleton that maintains an in-memory cache of user inputs.
### ViewModel
NumbersViewModel - maintains a view representation of the user input data with. This isn't affected by onfiguration changes such as recreating an activity.
### Fragments (Views)
NumbersFragment - displays the list of hot posts
### Activity
MainActivity - this is the main app Activity in this single Activity application
### Tests
NumbersViewModelTest - tests the view model

## Future Considerations
The demo app has a very basic data persistency mechanism across app restarts (using file storage instead of a database), consider integrating Room to add data persistency.<br />
The demo app has no integrated crash reporting/memory leak detection.<br />