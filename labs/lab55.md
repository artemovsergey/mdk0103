# Practice:Water Me! app


# 1. Before you begin
Introduction
In this unit, you learned about WorkManager, which is a Jetpack library for deferrable background work. This background work is guaranteed to run even if you close its source app.

While learning about WorkManager, you learned how to define work in a Worker class, how to create a WorkRequest for the Worker, and how to enqueue and schedule work.

In this practice set, you take the concepts you learned and enhance the Water Me! app.

The solution code is available at the end. To make the most of this learning experience, try to implement and troubleshoot as much as you can before looking at the provided solution code. It is during this hands-on time that you learn the most.

Prerequisites
Android Basics with Compose coursework through the Advanced WorkManager and Testing codelab
What you'll need
A computer with internet access and Android Studio
What you'll build
In this practice set, you take the concepts you learned and enhance the Water Me! app.

The app currently displays a list of plants in a scrolling list. When you tap on a plant, the app lets you set a reminder to water the plant.

<div style="display:flex">
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-water-me-app/img/fb69d6519999f217_856.png"/>
    </div>
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-water-me-app/img/d6b68d1e9f1026c5_856.png"/>
    </div>
</div>

While you can select a reminder timeframe, the reminder notification doesn't display.

Your job is to implement the background work for the reminder notification to display.

After you complete your code, the app can then display a reminder notification after a selected time duration elapses.



![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-water-me-app/img/a8f9bceed83af5a9_856.png){style="width:400px"}


# 2. Get the starter code
To get started, download the starter code:

file_downloadDownload zip

Alternatively, you can clone the GitHub repository for the code:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-waterme.git
$ cd basic-android-kotlin-compose-training-waterme
$ git checkout starter
```

> Note: The starter code is in the starter branch of the cloned repository.

You can browse the code for the Water Me! app in this GitHub repository.

Run starter code
To familiarize yourself with the starter code, complete the following steps:

Open the project with the starter code in Android Studio.
Run the app on an Android device or emulator.
You are now ready to begin coding!

# 3. Schedule notification using WorkManager
The functionality for the Water Me! app is mostly implemented except for the ability to schedule the reminder notification.

The code to make a notification is in the WaterReminderWorker.kt file, which is in the worker package. The WaterReminderWorker class extends the CoroutineWorker class, and the code to make the notification is inside its doWork() method.

Because notifications might be a new topic for you, this code is already complete.

```kt
override suspend fun doWork(): Result {

    val plantName = inputData.getString(nameKey)

    makePlantReminderNotification(
        applicationContext.resources.getString(R.string.time_to_water, plantName),
        applicationContext
    )

    return Result.success()
}
```

Your task is to create a OneTimeWorkRequest that calls this method with the correct parameters from the WorkManagerWaterRepository.

For additional help, refer to Background Work with WorkManager.

Create work requests
To schedule the notification, you need to implement the scheduleReminder() method in the WorkManagerWaterRepository.kt file.

Create a variable called data with Data.Builder. The data needs to consist of a single string value where WaterReminderWorker.nameKey is the key and the plantName passed into scheduleReminder() is the value.
Create a one-time work request with the WaterReminderWorker class. Use the duration and unit passed into the scheduleReminder() function and set the input data to the data variable you create.
Call the workManager's enqueueUniqueWork() method. Pass in the plant name concatenated with the duration, use REPLACE as the ExistingWorkPolicy, and the work request object.

> Note: Passing in the plant name, concatenated with the duration, lets you set multiple reminders per plant. You can schedule one reminder in 5 seconds for a Peony and another reminder in 1 day for a Peony. If you only pass in the plant name, when you schedule the second reminder for a Peony, it replaces the previously scheduled reminder for the Peony.

Your app should now work as expected.

# 4. Additional challenge (optional)
For additional coding practice, change the time duration options to the following:

5 seconds
1 minute
2 minutes
3 minutes
After you finish, test each duration to confirm it works as expected.

> Note: The provided solution code does not contain the implementation of this additional challenge.


# 5. Get the solution code
To download the code for the finished codelab, you can use these commands:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-waterme.git
$ cd basic-android-kotlin-compose-training-waterme
$ git checkout main
```