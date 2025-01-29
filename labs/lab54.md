# Advanced WorkManager and Testing

# 1. Introduction
In the Background Work with WorkManager codelab, you learned how to execute work in the background (not on the main thread) using WorkManager. In this codelab, you will continue learning about WorkManager functionality for ensuring unique work, tagging work, canceling work, and work constraints. The codelab will finish with you learning how to write automated tests to verify that your workers function properly and return the expected results. You will also learn how to use the Background Task Inspector, provided by Android Studio, to inspect queued workers.

What you'll build
In this codelab, you will ensure unique work, tagging work, canceling work, and implementing work constraints. You will then learn how to write automated UI tests for the Blur-O-Matic app that verify the functionality of the three workers created in the Background Work with WorkManager codelab:

BlurWorker
CleanupWorker
SaveImageToFileWorker
What you'll learn
Ensuring unique work.
How to cancel work.
How to define work constraints.
How to write automated tests to verify Worker functionality.
The basics of inspecting queued workers with the Background Task Inspector.
What you'll need
The latest stable version of Android Studio
Completion of the Background Work with WorkManager codelab
An Android device or emulator

# 2. Getting set up
Download the Code
Click the following link to download all the code for this codelab:

file_downloadDownload zip

Or if you prefer, you can clone the code from GitHub:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-workmanager.git
$ cd basic-android-kotlin-compose-training-workmanager
$ git checkout intermediate
```

# 3. Ensure unique work
Now that you know how to chain workers, it's time to tackle another powerful feature of WorkManager: unique work sequences.

Sometimes, you only want one chain of work to run at a time. For example, perhaps you have a work chain that syncs your local data with the server. You probably want the first data sync to complete before starting a new one. To do this, you use beginUniqueWork() instead of beginWith(), and you provide a unique String name. This input names the entire chain of work requests so that you can refer to and query them together.

You also need to pass in an ExistingWorkPolicy object. This object tells the Android OS what happens if the work already exists. Possible ExistingWorkPolicy values are REPLACE, KEEP, APPEND, or APPEND_OR_REPLACE.

In this app, you want to use REPLACE because if a user decides to blur another image before the current one finishes, you want to stop the current one and start blurring the new image.

You also want to ensure that if a user clicks Start when a work request is already enqueued, then the app replaces the previous work request with the new request. It does not make sense to continue working on the previous request because the app replaces it with the new request anyway.

In the data/WorkManagerBluromaticRepository.kt file, inside the applyBlur() method, complete the following steps:

Remove the call to beginWith() function and add a call to the beginUniqueWork() function.
For the first parameter to the beginUniqueWork() function, pass in the constant IMAGE_MANIPULATION_WORK_NAME.
For the second parameter, the existingWorkPolicy parameter, pass in ExistingWorkPolicy.REPLACE.
For the third parameter, create a new OneTimeWorkRequest for the CleanupWorker.

data/WorkManagerBluromaticRepository.kt
```kt
import androidx.work.ExistingWorkPolicy
import com.example.bluromatic.IMAGE_MANIPULATION_WORK_NAME
...
// REPLACE THIS CODE:
// var continuation = workManager.beginWith(OneTimeWorkRequest.from(CleanupWorker::class.java))
// WITH
var continuation = workManager
    .beginUniqueWork(
        IMAGE_MANIPULATION_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        OneTimeWorkRequest.from(CleanupWorker::class.java)
    )
...
```

Blur-O-Matic now only blurs one image at a time.


# 4. Tag and update the UI based on Work status
The next change you make is to what the app shows when the Work executes. Information returned about the enqueued works determines how the UI needs to change.

This table shows three different methods that you can call to get work information:

|Type|WorkManager Method|Description|
|:---|:-----------------|:----------|
|Get work using id|getWorkInfoByIdLiveData()|This function returns a single LiveData<WorkInfo> for a specific WorkRequest by its ID.|
|Get work using unique chain name|getWorkInfosForUniqueWorkLiveData()|This function returns LiveData<List<WorkInfo>> for all work in a unique chain of WorkRequests.|
|Get work using a tag|getWorkInfosByTagLiveData()|This function returns the LiveData<List<WorkInfo>> for a tag.|

> Note: WorkManager exposes some APIs as LiveData. We use the LiveData APIs but convert and use them as a flow. If the APIs change in the future, we can update the codelab accordingly.


> A WorkInfo object contains details about the current state of a WorkRequest, including:

Whether the work is BLOCKED, CANCELLED, ENQUEUED, FAILED, RUNNING, or SUCCEEDED.
If the WorkRequest is finished and any output data from the work.
These methods return LiveData. LiveData is a lifecycle aware observable data holder. We convert it into a Flow of WorkInfo objects by calling .asFlow().

Because you are interested in when the final image saves, you add a tag to the SaveImageToFileWorker WorkRequest so that you can get its WorkInfo from the getWorkInfosByTagLiveData() method.

Another option is to use the getWorkInfosForUniqueWorkLiveData() method, which returns information about all three WorkRequests (CleanupWorker, BlurWorker, and SaveImageToFileWorker). The downside to this method is that you need additional code to specifically find the necessary SaveImageToFileWorker information.

Tag the work request
Tagging the work is done in the data/WorkManagerBluromaticRepository.kt file inside the applyBlur() function.

When you create the SaveImageToFileWorker work request, tag the work by calling the addTag() method and passing in the String constant TAG_OUTPUT.

data/WorkManagerBluromaticRepository.kt
```kt
import com.example.bluromatic.TAG_OUTPUT
...
val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
    .addTag(TAG_OUTPUT) // <- Add this
    .build()
```

Instead of a WorkManager ID, you use a tag to label your work because if your user blurs multiple images, all of the save image WorkRequests have the same tag but not the same ID.

Get the WorkInfo
You use the WorkInfo information from the SaveImageToFileWorker work request in the logic to decide which composables to display in the UI based on the BlurUiState.

The ViewModel consumes this information from the repository's outputWorkInfo variable.

Now that you have tagged the SaveImageToFileWorker work request, you can complete the following steps to retrieve its information:

In the data/WorkManagerBluromaticRepository.kt file, call the workManager.getWorkInfosByTagLiveData() method to populate the outputWorkInfo variable.
Pass in the TAG_OUTPUT constant for the method's parameter.

data/WorkManagerBluromaticRepository.kt
```kt
...
override val outputWorkInfo: Flow<WorkInfo?> =
    workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
...
```

The call of the getWorkInfosByTagLiveData() method returns LiveData. LiveData is a lifecycle aware observable data holder. The .asFlow() function converts it to a Flow.

Chain a call to the .asFlow() function to convert the method to a Flow. You convert the method so the app can work with a Kotlin Flow instead of LiveData.

data/WorkManagerBluromaticRepository.kt
```kt
import androidx.lifecycle.asFlow
...
override val outputWorkInfo: Flow<WorkInfo?> =
    workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow()
...
```

Chain a call to the .mapNotNull() transform function to ensure the Flow contains values.
For the transform rule, if the element is not empty, select the first item in the collection. Otherwise, return a null value. The transform function will then remove them if they are a null value.

data/WorkManagerBluromaticRepository.kt
```kt
import kotlinx.coroutines.flow.mapNotNull
...
    override val outputWorkInfo: Flow<WorkInfo?> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }
...
```

Because the .mapNotNull() transform function guarantees that a value exists, you can safely remove the ? from the Flow type as it no longer needs to be a nullable type.

data/WorkManagerBluromaticRepository.kt
```kt
...
    override val outputWorkInfo: Flow<WorkInfo> =
...
```

You also need to remove the ? from the BluromaticRepository interface.

data/BluromaticRepository.kt
```kt
...
interface BluromaticRepository {
//    val outputWorkInfo: Flow<WorkInfo?>
    val outputWorkInfo: Flow<WorkInfo>
...
```

The WorkInfo information is emitted as a Flow from the repository. The ViewModel then consumes it.

Update the BlurUiState
The ViewModel uses the WorkInfo emitted by the repository from the outputWorkInfo Flow to set the value of the blurUiState variable.

The UI code uses the blurUiState variable value to determine which composables to display.

Complete the following steps to perform the blurUiState update:

Populate the blurUiState variable with the outputWorkInfo Flow from the repository.

ui/BlurViewModel.kt
```kt
// ...
// REMOVE
// val blurUiState: StateFlow<BlurUiState> = MutableStateFlow(BlurUiState.Default)

// ADD
val blurUiState: StateFlow<BlurUiState> = bluromaticRepository.outputWorkInfo
// ...
```

You then need to map the values in the Flow to the BlurUiState states, depending on the status of the work.

When the work is finished, set the blurUiState variable to BlurUiState.Complete(outputUri = "").

When the work is cancelled, set the blurUiState variable to BlurUiState.Default.

Otherwise, set the blurUiState variable to BlurUiState.Loading.

ui/BlurViewModel.kt
```kt
import androidx.work.WorkInfo
import kotlinx.coroutines.flow.map
// ...

    val blurUiState: StateFlow<BlurUiState> = bluromaticRepository.outputWorkInfo
        .map { info ->
            when {
                info.state.isFinished -> {
                    BlurUiState.Complete(outputUri = "")
                }
                info.state == WorkInfo.State.CANCELLED -> {
                    BlurUiState.Default
                }
                else -> BlurUiState.Loading
            }
        }

// ...
```

Because you are interested in a StateFlow, convert the Flow by chaining a call to the .stateIn() function.
The call to the .stateIn() function requires three arguments:

For the first parameter, pass viewModelScope, which is the coroutine scope tied to the ViewModel.
For the second parameter, pass SharingStarted.WhileSubscribed(5_000). This parameter controls when sharing starts and stops.
For the third parameter, pass BlurUiState.Default, which is the initial value of the state flow.

ui/BlurViewModel.kt
```kt
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
// ...

    val blurUiState: StateFlow<BlurUiState> = bluromaticRepository.outputWorkInfo
        .map { info ->
            when {
                info.state.isFinished -> {
                    BlurUiState.Complete(outputUri = "")
                }
                info.state == WorkInfo.State.CANCELLED -> {
                    BlurUiState.Default
                }
                else -> BlurUiState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BlurUiState.Default
        )

// ...
```

The ViewModel exposes the UI state information as a StateFlow through the blurUiState variable. The flow converts from a cold Flow to a hot StateFlow by calling the stateIn() function.

Update the UI
In the ui/BluromaticScreen.kt file, you get the UI state from the ViewModel's blurUiState variable and update the UI.

A when block controls the app's UI. This when block has a branch for each of the three BlurUiState states.

The UI updates in the BlurActions composable inside its Row composable. Complete the following steps:

Remove the Button(onStartClick) code inside the Row Composable and replace it with a when block with blurUiState as its argument.

ui/BluromaticScreen.kt
```kt
...
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        // REMOVE
        // Button(
        //     onClick = onStartClick,
        //     modifier = Modifier.fillMaxWidth()
        // ) {
        //     Text(stringResource(R.string.start))
        // }
        // ADD
        when (blurUiState) {
        }
    }
...
```

When the app opens, it is at its default state. This state in code is represented as BlurUiState.Default.

Inside the when block, create a branch for this state as shown in the following code example:

ui/BluromaticScreen.kt
```kt
...
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        when (blurUiState) {
            is BlurUiState.Default -> {}
        }
    }
...
```

For the default state, the app shows the Start button.

For the onClick parameter in the BlurUiState.Default state, pass the onStartClick variable, which is being passed to the composable.
For the stringResourceId parameter, pass the string resource id of R.string.start.

ui/BluromaticScreen.kt
```kt
...
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        when (blurUiState) {
            is BlurUiState.Default -> {
                Button(
                    onClick = onStartClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.start))
                }
        }
    }
...
```

When the app is actively blurring an image, that is the BlurUiState.Loading state. For this state, the app shows the Cancel Work button and a circular progress indicator.

For the button's onClick parameter in the BlurUiState.Loading state, pass onCancelClick variable, which is being passed to the composable.
For the button's stringResourceId parameter, pass the string resource id of R.string.cancel_work.

ui/BluromaticScreen.kt
```kt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
...
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        when (blurUiState) {
            is BlurUiState.Default -> {
                Button(onStartClick) { Text(stringResource(R.string.start)) }
            }
            is BlurUiState.Loading -> {
               FilledTonalButton(onCancelClick) { Text(stringResource(R.string.cancel_work)) }
               CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }
        }
    }
...
```

> Note: You don't use the Cancel Work button right now but still create it. You configure it in an upcoming step.

The last state to configure is the BlurUiState.Complete state, which occurs after an image blurs and saves. At this time, the app only displays the Start button.

For its onClick parameter in the BlurUiState.Complete state, pass the onStartClick variable.
For its stringResourceId parameter, pass the string resource id of R.string.start.

ui/BluromaticScreen.kt
```kt
...
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        when (blurUiState) {
            is BlurUiState.Default -> {
                Button(onStartClick) { Text(stringResource(R.string.start)) }
            }
            is BlurUiState.Loading -> {
                FilledTonalButton(onCancelClick) { Text(stringResource(R.string.cancel_work)) }
                CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            }
            is BlurUiState.Complete -> {
                Button(onStartClick) { Text(stringResource(R.string.start)) }
            }
        }
    }
...
```

Run your app
Run your app and click Start.
Refer to the Background Task Inspector window to see how the various states correspond to the UI being displayed.

> Note: The Background Task Inspector window can be found through the menus: View > Tool Windows > App Inspection then select the Background Task Inspector tab.

The SystemJobService is the component responsible for managing Worker executions.

While the workers are running, the UI shows the Cancel Work button and a circular progress indicator.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/3395cc370b580b32_856.png)


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/c5622f923670cf67_856.png){style="width:400px"}

After the workers finish, the UI updates to show the Start button as expected.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/97252f864ea042aa_856.png)


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/81ba9962a8649e70_856.png){style="width:400px"}


# 5. Show final output
In this section, you configure the app to display a button labeled See File whenever there is a blurred image ready to show.

Create the See File button
The See File button only shows when the BlurUiState is Complete.

Open the ui/BluromaticScreen.kt file and navigate to BlurActions composable.
To add space between the Start button and the See File button, add a Spacer composable within the BlurUiState.Complete block.
Add a new FilledTonalButton composable.
For the onClick parameter, pass onSeeFileClick(blurUiState.outputUri).
Add a Text composable for the Button's content parameter.
For the Text's text parameter, use the string resource id R.string.see_file.

ui/BluromaticScreen.kt
```kt
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width

// ...
is BlurUiState.Complete -> {
    Button(onStartClick) { Text(stringResource(R.string.start)) }
    // Add a spacer and the new button with a "See File" label
    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
    FilledTonalButton({ onSeeFileClick(blurUiState.outputUri) })
    { Text(stringResource(R.string.see_file)) }
}
// ...
```


Update the blurUiState
The BlurUiState state is set in the ViewModel and is dependent on the state of the work request and possibly the bluromaticRepository.outputWorkInfo variable.

In the ui/BlurViewModel.kt file, inside the map() transform, create a new variable outputImageUri.
Populate this new variable saved image's URI from the outputData data object.
You can retrieve this string with the KEY_IMAGE_URI key.

ui/BlurViewModel.kt
```kt
import com.example.bluromatic.KEY_IMAGE_URI

// ...
.map { info ->
    val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)
    when {
// ...
```

If the worker finishes and the variable is populated, it indicates that a blurred image exists to display.
You can check if this variable is populated by calling outputImageUri.isNullOrEmpty().

Update the isFinished branch to also check that the variable is populated and then pass the outputImageUri variable into the BlurUiState.Complete data object.

ui/BlurViewModel.kt
```kt
// ...
.map { info ->
    val outputImageUri = info.outputData.getString(KEY_IMAGE_URI)
    when {
        info.state.isFinished && !outputImageUri.isNullOrEmpty() -> {
            BlurUiState.Complete(outputUri = outputImageUri)
        }
        info.state == WorkInfo.State.CANCELLED -> {
// ...
```

Create See File click event code
When a user clicks the See File button, its onClick handler calls its assigned function. This function passes as an argument in the call to the BlurActions() composable.

The purpose of this function is to display the saved image from its URI. It calls the showBlurredImage() helper function and passes in the URI. The helper function creates an intent and uses it to start a new activity to show the saved image.

Open the ui/BluromaticScreen.kt file.
In the BluromaticScreenContent() function, in the call to the BlurActions() composable function, start creating a lambda function for the onSeeFileClick parameter that takes a single parameter named currentUri. This approach stores the saved image's URI.

ui/BluromaticScreen.kt
```kt
// ...
BlurActions(
    blurUiState = blurUiState,
    onStartClick = { applyBlur(selectedValue) },
    onSeeFileClick = { currentUri ->
    },
    onCancelClick = { cancelWork() },
    modifier = Modifier.fillMaxWidth()
)
// ...
```

Inside the body of the lambda function, call the showBlurredImage() helper function.
For the first parameter, pass the context variable.
For the second parameter, pass the currentUri variable.

ui/BluromaticScreen.kt
```kt
// ...
BlurActions(
    blurUiState = blurUiState,
    onStartClick = { applyBlur(selectedValue) },
    // New lambda code runs when See File button is clicked
    onSeeFileClick = { currentUri ->
        showBlurredImage(context, currentUri)
    },
    onCancelClick = { cancelWork() },
    modifier = Modifier.fillMaxWidth()
)
// ...
```

Run your app

Run your app. You now see your new, clickable See File button, which takes you to the saved file:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/9d76d5d7f231c6b6_856.png){style="width:400px"}

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/926e532cc24a0d4f_856.png){style="width:400px"}


# 6. Cancel work

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/5cec830cc8ef647e_856.png)


Previously, you added the Cancel Work button, so now you can add the code to make it do something. With WorkManager, you can cancel work using the id, tag, and unique chain name.

In this case, you want to cancel work with its unique chain name because you want to cancel all work in the chain, not just a particular step.

Cancel the work by name
Open the data/WorkManagerBluromaticRepository.kt file.
In the cancelWork() function, call the workManager.cancelUniqueWork() function.
Pass in the unique chain name IMAGE_MANIPULATION_WORK_NAME so the call only cancels scheduled work with that name.

data/WorkManagerBluromaticRepository.kt
```kt
override fun cancelWork() {
    workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
}
```

Following the design principle of separation of concerns, the composable functions must not directly interact with the repository. The composable functions interact with the ViewModel, and the ViewModel interacts with the repository.

This approach is a good design principle to follow because changes to your repository do not require you to change your composable functions as they do not directly interact.

Open the ui/BlurViewModel.kt file.
Create a new function called cancelWork() to cancel the work.
Inside the function, on the bluromaticRepository object, call the cancelWork() method.

ui/BlurViewModel.kt
```kt
/**
 * Call method from repository to cancel any ongoing WorkRequest
 * */
fun cancelWork() {
    bluromaticRepository.cancelWork()
}
```

Setup Cancel Work click event
Open the ui/BluromaticScreen.kt file.
Navigate to the BluromaticScreen() composable function.

ui/BluromaticScreen.kt
```kt
fun BluromaticScreen(blurViewModel: BlurViewModel = viewModel(factory = BlurViewModel.Factory)) {
    val uiState by blurViewModel.blurUiState.collectAsStateWithLifecycle()
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ) {
        BluromaticScreenContent(
            blurUiState = uiState,
            blurAmountOptions = blurViewModel.blurAmount,
            applyBlur = blurViewModel::applyBlur,
            cancelWork = {},
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}
```

Inside the call to the BluromaticScreenContent composable, you want the ViewModel's cancelWork() method to run when a user clicks the button.

Assign the cancelWork parameter the value blurViewModel::cancelWork.

ui/BluromaticScreen.kt
```kt
// ...
        BluromaticScreenContent(
            blurUiState = uiState,
            blurAmountOptions = blurViewModel.blurAmount,
            applyBlur = blurViewModel::applyBlur,
            cancelWork = blurViewModel::cancelWork,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_medium))
        )
// ...
```

Run your app and cancel work
Run your app. It compiles just fine. Start blurring a picture and then click Cancel Work. The whole chain is cancelled!

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/81ba9962a8649e70_856.png){style="width:400px"}


After you cancel work, only the Start button shows because WorkInfo.State is CANCELLED. This change causes the blurUiState variable to be set to BlurUiState.Default, which resets the UI back to its initial state and shows just the Start button.

The Background Task Inspector shows the status of Cancelled which is expected.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/7656dd320866172e_856.png)


# 7. Work constraints
Last but not least, WorkManager supports Constraints. A constraint is a requirement that you must meet before a WorkRequest runs.

Some example constraints are requiresDeviceIdle() and requiresStorageNotLow().

For the requiresDeviceIdle() constraint, if it is passed a value of true, the work runs only if the device is idle.
For the requiresStorageNotLow() constraint, if it is passed a value of true, the work runs only if the storage is not low.
For Blur-O-Matic, you add the constraint that the device's battery charge level must not be low before it runs the blurWorker work request. This constraint means that your work request is deferred and only runs once the device's battery is not low.

Create the battery not low constraint
In the data/WorkManagerBluromaticRepository.kt file, complete the following steps:

Navigate to the applyBlur() method.
After the code declaring the continuation variable, create a new variable named constraints, which holds a Constraints object for the constraint being created.
Create a builder for a Constraints object by calling the Constraints.Builder() function and assign it to the new variable.

data/WorkManagerBluromaticRepository.kt
```kt
import androidx.work.Constraints

// ...
    override fun applyBlur(blurLevel: Int) {
        // ...

        val constraints = Constraints.Builder()
// ...
```

Chain the setRequiresBatteryNotLow() method to the call and pass it a value of true so that the WorkRequest only runs when the device's battery is not low.

data/WorkManagerBluromaticRepository.kt
```kt
// ...
    override fun applyBlur(blurLevel: Int) {
        // ...

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
// ...
```

Build the object by chaining a call to the .build() method.

data/WorkManagerBluromaticRepository.kt
```kt
// ...
    override fun applyBlur(blurLevel: Int) {
        // ...

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
// ...
```

To add the constraint object to the blurBuilder work request, chain a call to the .setConstraints() method and pass in the constraint object.

data/WorkManagerBluromaticRepository.kt
```kt
// ...
blurBuilder.setInputData(createInputDataForWorkRequest(blurLevel, imageUri))

blurBuilder.setConstraints(constraints) // Add this code
//...
```

Test with emulator
On an emulator, change the Charge level in the Extended Controls window to be 15% or lower to simulate a low battery scenario, Charger connection to AC charger, and Battery status to Not charging.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/9b0084cb6e1a8672_856.png){style="width:400px"}

Run the app and click Start to start blurring the image.
The emulator's battery charge level is set to low, so WorkManager does not run the blurWorker work request because of the constraint. It is enqueued but deferred until the constraint is met. You can see this deferral in the Background Task Inspector tab.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/7518cf0353d04f12_856.png){style="width:400px"}

After you confirm it did not run, slowly increase the battery charge level.
The constraint is met after the battery charge level reaches approximately 25%, and the deferred work runs. This outcome appears in the Background Task Inspector tab.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/ab189db49e7b8997_856.png){style="width:400px"}

> Note: Another good constraint to add to Blur-O-Matic is a setRequiresStorageNotLow() constraint when saving. To see a full list of constraint options, check out the Constraints.Builder reference.



# 8. Write tests for Worker implementations
How to test WorkManager
Writing tests for Workers and testing using the WorkManager API can be counterintuitive. The work done in a Worker does not have direct access to the UI—it is strictly business logic. Typically, you test business logic with local unit tests. However, you might remember from the Background Work with WorkManager codelab that WorkManger requires an Android Context to run. Context is not available in local unit tests by default. Therefore, you must test Worker tests with UI tests, even though there are no direct UI elements to test.

Set up dependencies
You need to add three gradle dependencies to your project. The first two enable JUnit and espresso for UI tests. The third dependency provides the work testing API.

app/build.gradle.kts
```
dependencies {
    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Junit
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    // Work testing
    androidTestImplementation("androidx.work:work-testing:2.8.1")
}
```

You need to use the most current stable release version of work-runtime-ktx in your app. If you change the version, make sure to click Sync Now to sync your project with the updated gradle files.

Create a test class
Create a directory for your UI tests in the app > src directory.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/a7768e9b6ea994d3_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/20cc54de1756c884_856.png)


Create a new Kotlin class in the androidTest/java directory called WorkerInstrumentationTest.
Write a CleanupWorker test
Follow the steps to write a test to verify the implementation of the CleanupWorker. Try to implement this verification on your own based on the instructions. The solution is provided at the end of the steps.

In WorkerInstrumentationTest.kt, create a lateinit variable to hold an instance of Context.
Create a setUp() method annotated with @Before.
In the setUp() method, initialize the lateinit context variable with an application context from ApplicationProvider.
Create a test function called cleanupWorker_doWork_resultSuccess().
In the cleanupWorker_doWork_resultSuccess() test, create an instance of the CleanupWorker.

WorkerInstrumentationTest.kt
```kt
class WorkerInstrumentationTest {
   private lateinit var context: Context

   @Before
   fun setUp() {
       context = ApplicationProvider.getApplicationContext()
   }

   @Test
   fun cleanupWorker_doWork_resultSuccess() {
   }
}
```

When writing the Blur-O-Matic app, you use the OneTimeWorkRequestBuilder to create workers. Testing Workers requires different work builders. The WorkManager API provides two different builders:

TestWorkerBuilder
TestListenableWorkerBuilder
Both of these builders let you test the business logic of your worker. For CoroutineWorkers, such as CleanupWorker, BlurWorker, and SaveImageToFileWorker, use TestListenableWorkerBuilder for testing because it handles the threading complexities of the coroutine.

CoroutineWorkers run asynchronously, given the use of coroutines. To execute the worker in parallel, use runBlocking. Provide it an empty lambda body to start, but you use runBlocking to instruct the worker to doWork() directly instead of queuing the worker.

WorkerInstrumentationTest.kt
```kt
class WorkerInstrumentationTest {
   private lateinit var context: Context

   @Before
   fun setUp() {
       context = ApplicationProvider.getApplicationContext()
   }

   @Test
   fun cleanupWorker_doWork_resultSuccess() {
       val worker = TestListenableWorkerBuilder<CleanupWorker>(context).build()
       runBlocking {
       }
   }
}
```

In the lambda body of runBlocking, call doWork() on the instance of CleanupWorker that you created in step 5 and save it as a value.
You might recall that the CleanupWorker deletes any PNG files saved in the Blur-O-Matic app's file structure. This process involves file input/output, which means that exceptions can be thrown while trying to delete files. For this reason, the attempt to delete files is wrapped in a try block.

CleanupWorker.kt
```kt
...
            return@withContext try {
                val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
                if (outputDirectory.exists()) {
                    val entries = outputDirectory.listFiles()
                    if (entries != null) {
                        for (entry in entries) {
                            val name = entry.name
                            if (name.isNotEmpty() && name.endsWith(".png")) {
                                val deleted = entry.delete()
                                Log.i(TAG, "Deleted $name - $deleted")
                            }
                        }
                    }
                }
                Result.success()
            } catch (exception: Exception) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_cleaning_file),
                    exception
                )
                Result.failure()
            }
```

Note that at the end of the try block, Result.success() is returned. If the code makes it to Result.success(), there is no error accessing the file directory.

Now it's time to make an assertion that indicates the worker was successful.

Assert that the result of the worker is ListenableWorker.Result.success().
Take a look at the following solution code:

WorkerInstrumentationTest.kt
```kt
class WorkerInstrumentationTest {
   private lateinit var context: Context

   @Before
   fun setUp() {
       context = ApplicationProvider.getApplicationContext()
   }

   @Test
   fun cleanupWorker_doWork_resultSuccess() {
       val worker = TestListenableWorkerBuilder<CleanupWorker>(context).build()
       runBlocking {
           val result = worker.doWork()
           assertTrue(result is ListenableWorker.Result.Success)
       }
   }
}
```

Write a BlurWorker test
Follow these steps to write a test to verify the implementation of the BlurWorker. Try to implement this verification on your own based on the instructions. The solution is provided at the end of the steps.

In WorkerInstrumentationTest.kt, create a new test function called blurWorker_doWork_resultSuccessReturnsUri().
The BlurWorker needs an image to process. Therefore, building an instance of the BlurWorker requires some input data that includes such an image.

Outside of the test function, create a mock URI input. The mock URI is a pair that contains a key and a URI value. Use the following example code for the key value pair:

KEY_IMAGE_URI to "android.resource://com.example.bluromatic/drawable/android_cupcake"
Build a BlurWorker inside of the blurWorker_doWork_resultSuccessReturnsUri() function and make sure to pass the mock URI input you create as work data through the setInputData() method.
Similar to the CleanupWorker test, you must call the implementation of the worker inside of runBlocking.

Create a runBlocking block.
Call doWork() inside of the runBlocking block.
Unlike the CleanupWorker, the BlurWorker has some output data that is ripe for testing!

To access the output data, extract the URI from the result of doWork().

WorkerInstrumentationTest.kt
```kt
@Test
fun blurWorker_doWork_resultSuccessReturnsUri() {
    val worker = TestListenableWorkerBuilder<BlurWorker>(context)
        .setInputData(workDataOf(mockUriInput))
        .build()
    runBlocking {
        val result = worker.doWork()
        val resultUri = result.outputData.getString(KEY_IMAGE_URI)
    }
}
```

Make an assertion that the worker is successful. For an example, take a look at the following code from the BlurWorker:

BlurWorker.kt
```kt
val resourceUri = inputData.getString(KEY_IMAGE_URI)
val blurLevel = inputData.getInt(BLUR_LEVEL, 1)

...
val picture = BitmapFactory.decodeStream(
    resolver.openInputStream(Uri.parse(resourceUri))
)

val output = blurBitmap(picture, blurLevel)

// Write bitmap to a temp file
val outputUri = writeBitmapToFile(applicationContext, output)

val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

Result.success(outputData)
...
```

The BlurWorker takes the URI and blur level from the input data and creates a temporary file. If the operation is successful, it returns a key-value pair containing the URI. To check that the contents of the output are correct, make an assertion that the output data contains the key KEY_IMAGE_URI.

Make an assertion that the output data contains a URI that starts with the string "file:///data/user/0/com.example.bluromatic/files/blur_filter_outputs/blur-filter-output-"

> Note: The URI from the result of doWork() might be null. If the URI is null, return false inside of the assertion.

Check your test against the following solution code:

WorkerInstrumentationTest.kt
```kt
 @Test
    fun blurWorker_doWork_resultSuccessReturnsUri() {
        val worker = TestListenableWorkerBuilder<BlurWorker>(context)
            .setInputData(workDataOf(mockUriInput))
            .build()
        runBlocking {
            val result = worker.doWork()
            val resultUri = result.outputData.getString(KEY_IMAGE_URI)
            assertTrue(result is ListenableWorker.Result.Success)
            assertTrue(result.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))
            assertTrue(
                resultUri?.startsWith("file:///data/user/0/com.example.bluromatic/files/blur_filter_outputs/blur-filter-output-")
                    ?: false
            )
        }
    }
```

Write a SaveImageToFileWorker test
True to its name, the SaveImageToFileWorker writes a file to disk. Recall that in the WorkManagerBluromaticRepository, you add the SaveImageToFileWorker to the WorkManager as a continuation after the BlurWorker. Therefore, it has the same input data. It takes the URI from the input data, creates a bitmap, and then writes that bitmap to disk as a file. If the operation is successful, the resulting output is an image URL. The test for the SaveImageToFileWorker is very similar to that of the BlurWorker test, the only difference is the output data.

See if you can write a test for the SaveImageToFileWorker on your own! When you are done, you can check the solution below. Recall the approach you took for the BlurWorker test:

Build the worker, passing the input data.
Make a runBlocking block.
Call doWork() on the worker.
Check that the result was successful.
Check the output for the correct key and value.

> Note: The URL of the image file that the SaveImageToFileWorker saves to the disk needs to start with the following string: "content://media/external/images/media/".

Here is the solution:

```kt
@Test
fun saveImageToFileWorker_doWork_resultSuccessReturnsUrl() {
    val worker = TestListenableWorkerBuilder<SaveImageToFileWorker>(context)
        .setInputData(workDataOf(mockUriInput))
        .build()
    runBlocking {
        val result = worker.doWork()
        val resultUri = result.outputData.getString(KEY_IMAGE_URI)
        assertTrue(result is ListenableWorker.Result.Success)
        assertTrue(result.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))
        assertTrue(
            resultUri?.startsWith("content://media/external/images/media/")
                ?: false
        )
    }
}
```

# 9. Debug WorkManager with Background Task Inspector
Inspect Workers
Automated tests are a great way to verify the functionality of your Workers. However, they don't provide quite as much utility when you are trying to debug a Worker. Fortunately, Android Studio has a tool that lets you visualize, monitor, and debug your Workers in real time. Background Task Inspector works for emulators and devices running API level 26 or higher.

In this section, you learn some of the features that Background Task Inspector provides to inspect the workers in Blur-O-Matic.

Launch the Blur-O-Matic app on a device or emulator.
Navigate to View > Tool Windows > App Inspection.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/798f10dfd8d74bb1_856.png)

Select the Background Task Inspector tab.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/d601998f3754e793_856.png)

If necessary, select the device and running process from the drop-down menu.
In the example images, the process is com.example.bluromatic. It might automatically select the process for you. If it selects the wrong process, you can change it.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/6428a2ab43fc42d1_856.png)

Click the Workers drop-down menu. Currently, there are no workers running, which makes sense because there hasn't been an attempt to blur an image.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/cf8c466b3fd7fed1_856.png)

In the app, select More blurred and click Start. You immediately see some content in the Workers drop-down.

> Note: Keep in mind that the Background Task Inspector isn't like the debugger in the sense that it doesn't stop the workers during their execution; the debugger provides that functionality separately. The Background Task Inspector only monitors the Workers during execution.

You now see something like this in the Workers drop-down.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/569a8e0c1c6993ce_856.png)

The Worker table shows the name of the Worker, the Service (SystemJobService in this case), the status of each, and a timestamp. In the screenshot from the previous step, notice that the BlurWorker and the CleanupWorker have successfully completed their work.

You can also cancel work using the inspector.

Select an enqueued worker and click Cancel Selected Worker 7108c2a82f64b348.png from the toolbar.
Inspect task details
Click on a worker in the Workers table.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/97eac5ad23c41127_856.png)

Doing so brings up the Task Details window.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/9d4e17f7d4afa6bd_856.png)

Review the information shown in the Task Details.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/59fa1bf4ad8f4d8d_856.png)

e details show the following categories:

Description: This section lists the Worker class name with the fully-qualified package as well as the assigned tag and the UUID of this worker.
Execution: This section shows the worker's constraints (if any), running frequency, its state, and which class created and queued this worker. Recall the BlurWorker has a constraint that prevents it from executing when the battery is low. When you inspect a Worker that has constraints, they appear in this section.
WorkContinuation: This section displays where this worker is in the work chain. To check the details of another worker in the work chain, click on its UUID.
Results: This section displays the start time, retry count, and the output data of the selected worker.

Graph view
Recall that the workers in Blur-O-Matic are chained. The Background Task Inspector offers a graph view that represents worker dependencies visually.

At the corner of the Background Task Inspector window, there are two buttons to toggle between — Show Graph View and Show List View.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/4cd96a8b2773f466_856.png)

Click Show Graph View <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/6f871bb00ad8b11a_856.png"/>:


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/ece206da18cfd1c9_856.png)


The graph view accurately indicates the Worker dependency implemented in the Blur-O-Matic app.

Click Show List View <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-verify-background-work/img/669084937ea340f5_856.png"/> to exit the graph view.
Additional features
The Blur-O-Matic app only implements Workers to complete background tasks. However, you can read more about the tools available to inspect other types of background work in the documentation for the Background Task Inspector.


# 10. Get the solution code
To download the code for the finished codelab, you can use these commands:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-workmanager.git
$ cd basic-android-kotlin-compose-training-workmanager
$ git checkout main
```

# 11. Congratulations
Congratulations! You learned about additional WorkManger functionality, wrote automated tests for the Blur-O-Matic workers, and used the Background Task Inspector to examine them. In this codelab, you learned:

Naming unique WorkRequest chains.
Tagging WorkRequests.
Updating the UI based on the WorkInfo.
Canceling a WorkRequest.
Adding constraints to a WorkRequest.
The WorkManager testing API.
How to approach testing worker implementations.
How to test CoroutineWorkers.
How to manually inspect workers and verify their functionality.