# Practice:Build Bus Schedule app

# 1. Before you begin
Introduction
In the Persist Data with Room codelab, you learned how to implement a Room database in an Android app. This exercise provides the opportunity to gain more familiarity with the implementation of Room databases through an independently driven set of steps.

In this practice set, you take the concepts you learned from the Persist Data with Room codelab to complete the Bus Schedule app. This app presents the user with a list of bus stops and scheduled departures using data provided from a Room database.

The solution code is available at the end. To make the most of this learning experience, try to implement and troubleshoot as much as you can before you look at the provided solution code. It is during this hands-on time that you learn the most.

Prerequisites
Android Basics with Compose coursework through the Persist Data with Room codelab
What you'll need
A computer with internet access and Android Studio
The Bus Schedule starter code
What you'll build
In this practice set, you complete the Bus Schedule app by implementing a database and then delivering data to the UI using the database. A database file in the asset directory found in the starter code provides data for the app. You load this data into a database and make it available for read usage by the app.

After you complete the app, it shows a list of bus stops and corresponding arrival times. You can click an item in the list to trigger navigation to a detail screen that provides data for that stop.

The completed app shows this data, loaded from a Room database:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-bus-schedule-app/img/cdb6f9e79137f323_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-bus-schedule-app/img/6c59e6f57f59bd27_856.png)


# 2. Download the starter code
Starter code URL:

https://github.com/google-developer-training/basic-android-kotlin-compose-training-bus-schedule-app

Branch name with starter code: starter

In Android Studio, open the basic-android-kotlin-compose-training-bus-schedule folder.
Open the Bus Schedule app code in Android Studio.
Click the Run button 65e9e2045e2dc48b.png to build and run the app.
The app is expected to display a schedule showing one stop when built from the starter branch code.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-bus-schedule-app/img/3603c91854cada9a_856.png)


# 3. Add dependencies
Add the following dependencies to the app:

app/build.gradle.kts
```kt
implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
```

You should get the most current stable version of room from the Room documentation and add the correct version number. At this moment the latest version is:

build.gradle.kts
```kt
set("room_version", "2.5.1")
```

# 4. Create a Room entity
Convert the current Bus Schedule data class into a Room Entity.

The following image shows a sample of what the final data table looks like, including the schema and Entity property.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-bus-schedule-app/img/9587f9a5f035e552_856.png)


# 5. Create a data access object
Create a data access object (DAO) to access the database. The DAO provides a method to retrieve all the items in the database and a method to retrieve a single item with the name of the bus stop. Make sure to order the schedule by arrival time.


# 6. Create a database instance
Create a Room database that uses the Entity and your DAO. The database initializes itself with data from the assets/database/bus_schedule.db file in the starter code.


# 7. Update the ViewModel
Update the ViewModel to retrieve data from the DAO and provide it to the UI instead of supplying sample data. Make sure to leverage both of your DAO methods to supply data for the list and for individual stops.


# 8. Solution code
Solution code URL:

https://github.com/google-developer-training/basic-android-kotlin-compose-training-bus-schedule-app

Branch name with solution code: main