# LocationTracking
Location Tracking Application.

An android app that tracks the location of a user and posts it to a backend server. 
The app contains an activity with 2 buttons:
1. Start Tracking
2. Stop Tracking

When "Start Tracking" is clicked, the app begins to track location coordinates of the user. 
If the distance from the last posted location exceeds the distance threshold (e.g. 100m) or if the time since the last posted location's timestamp exceeds the time threshold (e.g. 2 mins), then the location will be posted. 
The app also runs in the background, even when the devices screen is turned off. It considers real world conditions like lossy network connection, etc. (i.e. location history should not be lost) bu persisting location values in Database.
This application uses ROOM. 

"Stop Tracking" should stop tracking the user's location.
