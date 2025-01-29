# Practice:Build Sports app

1. Before you begin
Congratulations! In this pathway, you learned how to make your app adaptive by using the WindowWidthSize class and the canonical layout. Now it is time to put what you learned into practice.

In this practice set, you will build on the concepts you learned in this pathway by creating a Sports app. The starter app is fully functional for a mobile layout. Your task is to make it adaptive for large screens.

The solution code is available at the end, but try to solve the exercises before you check it. Work through the problems at a pace that's comfortable for you. There are durations listed, but you do not have to adhere to them because they're only estimates. You're encouraged to take as much time as you need to solve each problem thoughtfully.

Prerequisites
Complete Android Basics in Compose coursework through the Build an adaptive app with dynamic navigation and Build an adaptive app with adaptive layout codelabs.
What you'll need
A computer with internet access and Android Studio installed
Access to GitHub
What you'll build
A Sports app that adapts to a large screen. The finished app looks like the following image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-sports-app/img/1ce365f97570965e_856.png)



# 2. Get started
Download the starter code
In Android Studio, open the basic-android-kotlin-compose-training-sports folder.

Starter code URL:

https://github.com/google-developer-training/basic-android-kotlin-compose-training-sports/tree/starter

Branch name with starter code: starter


# 3. Plan to adapt Sports app for large screens
To adapt the Sports app for large screens, you first want to establish the type of layout that best displays the app on large screens.

Start reviewing the current layouts available on the compact screen size. There are two screens — namely the list screen, which corresponds to the SportsList composable, and the detail screen, which corresponds to the SportsDetail composable.

<div style="display:flex">
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-sports-app/img/87ecbf0695393421_856.png"/>
    </div>
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-sports-app/img/6021d2078a2225ad_856.png"/>
    </div>
</div>

Review the canonical layout to determine the layout that best fits the Sports app use case.
Sketch out a possible expanded screen layout. Use either a simple visual design software or a piece of paper to visualize how the screens fit together in the expanded layout.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-sports-app/img/bb59e5ec7da56f7a_856.png)


# 4. Create the expanded screen in code
Now that you have a clear view of how the expanded layout looks, let's translate that into code.

Create a composable for the expanded screen that shows both the list and the details screen. You can name it SportsListAndDetails and place it in the SportsScreens.kt file.
Create a composable preview to simplify verification of the UI for the SportsListAndDetails composable.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-sports-app/img/678504d0ec535896_856.png)


Ensure that the back button behavior is appropriate for the expanded screen. When the user presses the back button, you want them to exit the app when the main screen appears. You can change this behavior by passing the appropriate lambda to the SportsDetails composable. Hint: you can have access to the app's Activity from (LocalContext.current as Activity).


# 5. Make app change to different layout based on window size
To add the expanded screen composable to the app, complete the following tasks:

Add androidx.compose.material3:material3-window-size-class to the app's build.gradle.kts.
Calculate windowSizeClass in the MainActivity and pass the widthSizeClass to the SportsApp composable.
Create a new directory named utils and create a new file name WindowStateUtils.kt.
Add an enum class in WindowStateUtils to denote two different contentType. You can name them ListOnly and ListAndDetail types.
In the SportsApp composable, determine the contentType, based on the widthSizeClass.
Display the SportsListAndDetails composable when contentType is ListAndDetail and keep the previous behavior when the contentType is ListOnly.
For the SportsAppBar composable, change the behavior so that the back button doesn't appear and the app bar shows Sports when the screen expands in the list page.
Verify the UI and navigation capabilities for both compact and expanded screens using the resizable emulator.


# 6. Get the solution code
To download the code for the finished codelab, you can use this git command:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-sports.git
```
Alternatively, you can download the repository as a zip file, unzip it, and open it in Android Studio.
