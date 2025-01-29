# Create an interactive Dice Roller app

1. Before you begin
In this codelab, you create an interactive Dice Roller app that lets users tap a Button composable to roll a dice. The outcome of the roll is shown with an Image composable on the screen.

You use Jetpack Compose with Kotlin to build your app layout and then write business logic to handle what happens when the Button composable is tapped.

## Prerequisites
Ability to create and run a basic Compose app in Android Studio.
Familiarity with how to use the Text composable in an app.
Knowledge of how to extract text into a string resource to make it easier to translate your app and reuse strings.
Knowledge of Kotlin programming basics.
What you'll learn
How to add a Button composable to an Android app with Compose.
How to add behavior to a Button composable in an Android app with Compose.
How to open and modify the Activity code of an Android app.
What you'll build
An interactive Android app called Dice Roller that lets users roll a dice and shows them the result of the roll.
What you'll need
A computer with Android Studio installed.
Here's what the app look likes when you complete this codelab:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/3e9a9f44c6c84634_856.png)


# 2. Establish a baseline
Create a project
In Android Studio, click File > New > New Project.
In the New Project dialog, select Empty Activity and then click Next.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/39373040e14f9c59_856.png)

In the Name field, enter Dice Roller.
In the Minimum SDK field, select a minimum API level of 24 (Nougat) from the menu and then click Finish.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/8fd6db761068ca04_856.png)


# 3. Create the layout infrastructure
Preview the project
To preview the project:

Click Build & Refresh in the Split or Design pane.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/9f1e18365da2f79c_856.png)

Now you should see a preview in the Design pane. If it looks small, don't worry because it changes when you modify the layout.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/b5c9dece74200185_856.png)


# Restructure the sample code
You need to change some of the generated code to more closely resemble the theme of a dice roller app.

As you saw in the screenshot of the final app, there's an image of a dice and a button to roll it. You will structure the composable functions to reflect this architecture.

To restructure the sample code:

Remove the GreetingPreview() function.
Create a DiceWithButtonAndImage() function with the @Composable annotation.
This composable function represents the UI components of the layout and also holds the button-click and image-display logic.

Remove the Greeting(name: String, modifier: Modifier = Modifier) function.
Create a DiceRollerApp() function with the @Preview and @Composable annotations.
Because this app only consists of a button and an image, think of this composable function as the app itself. That's why it's called the DiceRollerApp() function.

MainActivity.kt

```kt
@Preview
@Composable
fun DiceRollerApp() {

}

@Composable
fun DiceWithButtonAndImage() {

}
```

Because you removed the Greeting() function, the call to Greeting("Android") in the DiceRollerTheme() lambda body is highlighted red. That's because the compiler can't find a reference to that function anymore.

Delete all of the code inside the setContent{} lambda found in the onCreate() method.
In the setContent{} lambda body, call the DiceRollerTheme{} lambda and then inside the DiceRollerTheme{} lambda, call the DiceRollerApp() function.
MainActivity.kt

```kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        DiceRollerTheme {
            DiceRollerApp()
        }
    }
}
```

In the DiceRollerApp() function, call the DiceWithButtonAndImage() function.
MainActivity.kt

```kt
@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage()
}
```

## Add a modifier
Compose uses a Modifier object, which is a collection of elements that decorate or modify the behavior of Compose UI elements. You use this to style the UI components of the Dice Roller app's components.

To add a modifier:

Modify the DiceWithButtonAndImage() function to accept a modifier argument of type Modifier and assign it a default value of Modifier.
MainActivity.kt

```kt
@Composable 
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
}
```

The previous code snippet may confuse you, so let's break it down. The function allows a modifier parameter to be passed in. The default value of the modifier parameter is a Modifier object, hence the = Modifier piece of the method signature. The default value of a parameter lets anyone who calls this method in the future decide whether to pass a value for the parameter. If they pass their own Modifier object, they can customize the behavior and decoration of the UI. If they choose not to pass a Modifier object, it assumes the value of the default, which is the plain Modifier object. You can apply this practice to any parameter. For more information about default arguments, see Default arguments.

> Note: The import androidx.compose.ui.Modifier statement imports the androidx.compose.ui.Modifier package, which lets you reference the Modifier object.

Now that the DiceWithButtonAndImage() composable has a modifier parameter, pass a modifier when the composable is called. Because the method signature for the DiceWithButtonAndImage() function changed, a Modifier object with the desired decorations should be passed in when it's called. The Modifier class is responsible for the decoration of, or the addition of behavior to, a composable in the DiceRollerApp() function. In this case, there are some important decorations to add to the Modifier object that's passed to the DiceWithButtonAndImage() function.
You might wonder why you should bother to pass a Modifier argument at all when there's a default. The reason is because composables can undergo recomposition, which essentially means that the block of code in the @Composable method executes again. If a Modifier object is created in a block of code, it could potentially be recreated and that isn't efficient. Recomposition is covered later in this codelab.

MainActivity.kt

```kt
DiceWithButtonAndImage(modifier = Modifier)
```

Chain a fillMaxSize() method onto the Modifier object so that the layout fills the entire screen.
This method specifies that the components should fill the space available. Earlier in this codelab, you saw a screenshot of the final UI of the Dice Roller app. A notable feature is that the dice and button are centered on the screen. The wrapContentSize() method specifies that the available space should at least be as large as the components inside of it. However, because the fillMaxSize() method is used, if the components inside of the layout are smaller than the available space, an Alignment object can be passed to wrapContentSize() method that specifies how the components should align within the available space.

MainActivity.kt

```kt
DiceWithButtonAndImage(modifier = Modifier
    .fillMaxSize()
)
```

> Note: The import statements for fillMaxSize() and wrapContentSize() are import androidx.compose.foundation.layout.fillMaxSize and import androidx.compose.foundation.layout.wrapContentSize, respectively.

Chain the wrapContentSize() method onto the Modifier object and then pass Alignment.Center as an argument to center the components. Alignment.Center specifies that a component centers both vertically and horizontally.
MainActivity.kt

```kt
DiceWithButtonAndImage(modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center)
)
```

> Note: The import statement for the Alignment object is import androidx.compose.ui.Alignment.


# 4. Create a vertical layout
In Compose, vertical layouts are created with the Column() function.

The Column() function is a composable layout that places its children in a vertical sequence. In the expected app design, you can see that the dice image displays vertically above the roll button:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/7d70bb14948e3cc1_856.png)

To create a vertical layout:

In the DiceWithButtonAndImage() function, add a Column() function.

> Note: The import statement for the Column composable is import androidx.compose.foundation.layout.Column.

Pass the modifier argument from the DiceWithButtonAndImage() method signature to the Column()'s modifier argument.
The modifier argument ensures that the composables in the Column() function adhere to the constraints called on the modifier instance.

Pass a horizontalAlignment argument to the Column() function and then set it to a value of Alignment.CenterHorizontally.
This ensures that the children within the column are centered on the device screen with respect to the width.

MainActivity.kt

```kt
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {}
}
```

# 5. Add a button
In the strings.xml file, add a string and set it to a Roll value.
res/values/strings.xml

```xml
<string name="roll">Roll</string>
```

In the Column()'s lambda body, add a Button() function.

> Note: The import statement for the Button composable is import androidx.compose.material3.Button.

In the MainActivity.kt file, add a Text() function to the Button() in the lambda body of the function.
Pass the string resource ID of the roll string to the stringResource() function and pass the result to the Text composable.
MainActivity.kt

```kt
Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Button(onClick = { /*TODO*/ }) {
        Text(stringResource(R.string.roll))
    }
}
```

> Note: If you autocomplete the Button() function, the onClick = { /*TODO*/ } argument appears. If you don't autocomplete it or Android Studio doesn't let you do so, you can implement this argument on your own as a placeholder.

> Note: The import statement for the stringResource function is import androidx.compose.ui.res.stringResource.

# 6. Add an image

Another essential component of the app is the dice image, which displays the result when the user taps the Roll button. You add the image with an Image composable, but it requires an image resource, so first you need to download some images provided for this app.

Download the dice images
Open this URL to download a zip file of dice images to your computer and then wait for the download to complete.
Locate the file on your computer. It's likely in your Downloads folder.

Unpack the zip file to create a new dice_images folder that contains six dice image files with dice values from 1 to 6.

Add the dice images to your app
In Android Studio, click View > Tool Windows > Resource Manager.
Click + > Import Drawables to open a file browser.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/12f17d0b37dd97d2_856.png)

Find and select the six dice image folder and proceed to upload them.
The uploaded images will appear as follows.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/4f66c8187a2c58e2_856.png)

Click Next.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/688772df9c792264_856.png)


The Import Drawables dialog appears and shows where the resource files go in the file structure.

Click Import to confirm that you want to import the six images.
The images should appear in the Resource Manager pane.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/c2f08e5311f9a111_856.png)

> Important! You can refer to these images in your Kotlin code with their resource IDs:

R.drawable.dice_1
R.drawable.dice_2
R.drawable.dice_3
R.drawable.dice_4
R.drawable.dice_5
R.drawable.dice_6


Nice work! In the next task, you use these images in your app.

Add an Image composable
The dice image should appear above the Roll button. Compose inherently places UI components sequentially. In other words, whichever composable is declared first displays first. This could mean that the first declaration displays above, or before, the composable declared after it. Composables inside of a Column composable will appear above / below each other on the device. In this app, you use a Column to stack Composables vertically, therefore, whichever Composable is declared first inside the Column() function displays before the composable declared after it in the same Column() function.

To add an Image composable:

In the Column() function body, create an Image() function before the Button() function.
MainActivity.kt

```kt
Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Image()
    Button(onClick = { /*TODO*/ }) {
      Text(stringResource(R.string.roll))
    }
}
```

> Note: The import statement for the Image composable is import androidx.compose.foundation.Image.

Pass the Image() function a painter argument, and then assign it a painterResource value that accepts a drawable resource id argument. For now, pass the following resource id: R.drawable.dice_1 argument.
MainActivity.kt

```kt
Image(
    painter = painterResource(R.drawable.dice_1)
)
```

> Note: The import statement for the painterResource function is import androidx.compose.ui.res.painterResource.

> Note: Later on, you will change the value passed for the resource id. For now, it should be passed a default so that the code will compile for preview purposes.


Any time you create an Image in your app, you should provide what is called a "content description." Content descriptions are an important part of Android development. They attach descriptions to their respective UI components to increase accessibility. For more information about content descriptions, see Describe each UI element. You can pass a content description to the image as a parameter.
MainActivity.kt

```kt
Image(
    painter = painterResource(R.drawable.dice_1),
    contentDescription = "1"
)
```

> Note: The above content description is a placeholder for the time being. This will be updated in a later section of this codelab.

Now all the necessary UI components are present. But the Button and the Image are crowding each other a bit.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/54b27140071ac2fa_856.png)


To fix that, add a Spacer composable between the Image and the Button composables. A Spacer takes a Modifier as a parameter. In this case, the Image is above the Button, so there needs to be a vertical space between them. Therefore, the Modifier's height can be set to apply to the Spacer. Try setting the height to 16.dp. Typically, dp dimensions are changed in increments of 4.dp.
MainActivity.kt

```kt
Spacer(modifier = Modifier.height(16.dp))
```

> Note: The imports for the Spacer composable, modifier height, and dp are:

import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.Spacer

import androidx.compose.ui.unit.dp


In the Preview pane, click Build & Refresh.
You should see something similar to this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/73eea4c166f7e9d2_856.png)


# 7. Build the dice-roll logic

Now that all the necessary composables are present, you modify the app so that a tap of the button rolls the dice.

Make the button interactive
In the DiceWithButtonAndImage() function before the Column() function, create a result variable and set it equal to a 1 value.
Take a look at the Button composable. You will notice that it is being passed an onClick parameter which is set to a pair of curly braces with the comment /*TODO*/ inside the braces. The braces, in this case, represent what is known as a lambda, the area inside of the braces being the lambda body. When a function is passed as an argument, it can also be referred to as a " callback".

```kt
Button(onClick = { /*TODO*/ })
```

A lambda is a function literal, which is a function like any other, but instead of being declared separately with the fun keyword, it is written inline and passed as an expression. The Button composable is expecting a function to be passed as the onClick parameter. This is the perfect place to use a lambda, and you will be writing the lambda body in this section.

In the Button() function, remove the /*TODO*/ comment from the value of the lambda body of the onClick parameter.
A dice roll is random. To reflect that in code, you need to use the correct syntax to generate a random number. In Kotlin, you can use the random() method on a number range. In the onClick lambda body, set the result variable to a range between 1 to 6 and then call the random() method on that range. Remember that, in Kotlin, ranges are designated by two periods between the first number in the range and the last number in the range.

```kt
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result = 1
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.dice_1),
            contentDescription = "1"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { result = (1..6).random() }) {
            Text(stringResource(R.string.roll))
        }
    }
}
```

Now the button is tappable, but a tap of the button won't cause any visual change yet because you still need to build that functionality.

Add a conditional to the dice roller app
In the previous section, you created a result variable and hard-coded it to a 1 value. Ultimately, the value of the result variable is reset when the Roll button is tapped and it should determine which image is shown.

Composables are stateless by default, which means that they don't hold a value and can be recomposed any time by the system, which results in the value being reset. However, Compose provides a convenient way to avoid this. Composable functions can store an object in memory using the remember composable.

Make the result variable a remember composable.
The remember composable requires a function to be passed.

In the remember composable body, pass in a mutableStateOf() function and then pass the function a 1 argument.
The mutableStateOf() function returns an observable. You learn more about observables later, but for now this basically means that when the value of the result variable changes, a recomposition is triggered, the value of the result is reflected, and the UI refreshes.

```kt
var result by remember { mutableStateOf(1) }
```

```kt
Note: The import androidx.compose.runtime.mutableStateOf and import androidx.compose.runtime.remember statements import the packages needed for the mutableStateOf() function and the remember composable.

The following import statements are also needed to import necessary extension functions of State:

import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
```

Now, when the button is tapped, the result variable is updated with a random number value.

The result variable can now be used to determine which image to show.

Underneath the instantiation of the result variable, create an immutable imageResource variable set to a when expression that accepts a result variable and then set each possible result to its drawable.

```kt
val imageResource = when (result) {
    1 -> R.drawable.dice_1
    2 -> R.drawable.dice_2
    3 -> R.drawable.dice_3
    4 -> R.drawable.dice_4
    5 -> R.drawable.dice_5
    else -> R.drawable.dice_6
}
```

Change the ID passed to the painterResource parameter of the Image composable from the R.drawable.dice_1 drawable to the imageResource variable.
Change the contentDescription parameter of the Image composable to reflect the value of result variable by converting the result variable to a string with toString() and passing it as the contentDescription.

```kt
Image(
   painter = painterResource(imageResource),
   contentDescription = result.toString()
)
```

Run your app.
Your Dice Roller app should fully work now!

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app/img/3e9a9f44c6c84634_856.png)


# Conclusion
You created an interactive Dice Roller app for Android with Compose!

Summary
Define composable functions.
Create layouts with Compositions.
Create a button with the Button composable.
Import drawable resources.
Display an image with the Image composable.
Make an interactive UI with composables.
Use the remember composable to store objects in a Composition to memory.
Refresh the UI with the mutableStateOf()function to make an observable.