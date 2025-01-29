# Simple Animation with Jetpack Compose

1. Before you begin
In this codelab, you learn how to add a simple animation to your Android app. Animations can make your app more interactive, interesting, and easier for users to interpret. Animating individual updates on a screen full of information can help the user see what changed.

There are many types of animations that can be used in an app's user interface. Items can fade in as they appear and fade out as they disappear, they can move on or off of the screen, or they can transform in interesting ways. This helps make the app's UI expressive and easy to use.

Animations can also add a polished look to your app, which gives it an elegant look and feel, and also helps the user at the same time.

Prerequisites
Knowledge of Kotlin, including functions, lambdas, and stateless composables.
Basic knowledge of how to build layouts in Jetpack Compose.
Basic knowledge of how to create lists in Jetpack Compose.
Basic knowledge of Material Design.
What you'll learn
How to build a simple spring animation with Jetpack Compose.
What you'll build
You will build on the Woof app from the Material Theming with Jetpack Compose codelab, and add a simple animation to acknowledge the user's action.
What you'll need
The latest stable version of Android Studio.
Internet connection to download starter code.

# 2. App Overview
In the Material Theming with Jetpack Compose codelab, you created the Woof app using Material Design which displays a list of dogs and their information.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/36c6cabd93421a92_856.png)

In this codelab, you will add animation to the Woof app. You'll add hobby information which will display when you expand the list item. You'll also add a spring animation to animate the list item being expanded.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/c0d0a52463332875.gif)

# 3. Add expand more icon
In this section you will add the Expand More 30c384f00846e69b.png and Expand Less f88173321938c003.png icons to your app.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/def59d71015c0fbe_856.png)

Icons
Icons are symbols that can help users understand a user interface by visually communicating the intended function. They often take inspiration from objects in the physical world that a user is expected to have experienced. Icon design often reduces the level of detail to the minimum required to be familiar to a user. For example, a pencil in the physical world is used for writing, so its icon counterpart usually indicates create or edit.

<div style="display:flex;">
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/1c71e91aa04d3837_856.jpeg"/>
    </div>
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/b718af58f961d2b4_856.png"/>
    </div>
</div>

Material Design provides a number of icons, arranged in common categories, for most of your needs.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/254688426772346f_856.png)

## Add Gradle dependency
Add the material-icons-extended library dependency to your project. You will use the Icons.Filled.ExpandLess 30c384f00846e69b.png and Icons.Filled.ExpandMore f88173321938c003.png icons from this library.

>Note on Gradle dependency: To add a dependency to your project, specify a dependency configuration such as implementation in the dependencies block of your module's build.gradle.kts file. When you build your app, the build system compiles the library module and packages the resulting compiled contents in the app.

You will learn more about adding libraries to your app in later units.

In the Project pane, open Gradle Scripts > build.gradle.kts (Module :app).
Scroll to the end of the build.gradle.kts (Module :app) file. In the dependencies{} block, add the following line:

```
implementation("androidx.compose.material:material-icons-extended")
```

> Tip: Whenever you modify the Gradle files, Android Studio may have to import or update libraries and run some background tasks. Android Studio displays a pop-up that asks you to sync your project. Click Sync Now.
![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/772f8f274e7d3f44_856.png)


### Add the icon composable
Add a function to display the Expand More icon from the Material icons library and use it as a button.

In MainActivity.kt, after the DogItem() function, create a new composable function called DogItemButton().
Pass in a Boolean for the expanded state, a lambda expression for the button onClick handler, and an optional Modifier as follows:

```kt
@Composable
private fun DogItemButton(
   expanded: Boolean,
   onClick: () -> Unit,
   modifier: Modifier = Modifier
) {
 

}
```

Inside the DogItemButton() function, add an IconButton() composable that accepts an onClick named parameter, a lambda using trailing lambda syntax, that is invoked when this icon is pressed and an optional modifier. Set the IconButton's onClick and modifier value parameters equal to the ones passed in to DogItemButton.

```kt
@Composable
private fun DogItemButton(
   expanded: Boolean,
   onClick: () -> Unit,
   modifier: Modifier = Modifier
){
   IconButton(
       onClick = onClick,
       modifier = modifier
   ) {

   }
}
```

Inside the IconButton() lambda block, add in an Icon composable and set the imageVector value-parameter to Icons.Filled.ExpandMore. This is what will display at the end of the list item f88173321938c003.png. Android Studio shows you a warning for the Icon() composable parameters that you will fix in the next step.

```kt
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

IconButton(
   onClick = onClick,
   modifier = modifier
) {
   Icon(
       imageVector = Icons.Filled.ExpandMore
   )
}
```

Add the value parameter tint, and set the color of the icon to MaterialTheme.colorScheme.secondary. Add the named parameter contentDescription, and set it to the string resource R.string.expand_button_content_description.

```kt
IconButton(
   onClick = onClick,
   modifier = modifier
){
   Icon(
       imageVector = Icons.Filled.ExpandMore,
       contentDescription = stringResource(R.string.expand_button_content_description),
       tint = MaterialTheme.colorScheme.secondary
   )
}
```

### Display the icon
Display the DogItemButton() composable by adding it to the layout.

At the beginning of DogItem(), add a var to save the expanded state of the list item. Set the initial value to false.

```kt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

var expanded by remember { mutableStateOf(false) }
```

> Refresher on remember() and mutableStateOf():
    Use the mutableStateOf() function so Compose observes any changes to the state value, and triggers a recomposition to update the UI. Wrap the mutableStateOf() function call with the remember() function to store the value in the Composition during initial composition, and the stored value is returned during recomposition.


Display the icon button within the list item. In the DogItem() composable, at the end of the Row block, after the call to DogInformation(), add DogItemButton(). Pass in the expanded state and an empty lambda for the callback. You will define the onClick action in a later step.

```kt
Row(
   modifier = Modifier
       .fillMaxWidth()
       .padding(dimensionResource(R.dimen.padding_small))
) {
   DogIcon(dog.imageResourceId)
   DogInformation(dog.name, dog.age)
   DogItemButton(
       expanded = expanded,
       onClick = { /*TODO*/ }
   )
}
```

Check out WoofPreview() in the Design pane.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/5bbf09cd2828b6_856.png)

Notice the expand more button is not aligned to the end of the list item. You will fix that in the next step.

### Align the expand more button
To align the expand more button to the end of the list item, you need to add a spacer in the layout with the Modifier.weight() attribute.

> Note: Modifier.weight() sets the UI element's width/height proportionally to the element's weight, relative to its weighted siblings (other child elements in the row or column).
Example: Consider three child elements in a row with weights 1f, 1f, and 2f. All child elements have assigned weights in this case. The available space for the row is divided proportionally to the specified weight value, with more available space going to children with higher weight values. The child elements will distribute the weight as shown below:
![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/197842fd06abf239_856.png)
In the above row, the first child composable has ¼ of the row's width, the second also has ¼ of the row's width, and the third has ½ of the row's width.
If the children don't have assigned weights, (weight is an optional parameter), then the child composable's height/width would default to wrap content (wrapping the contents of what's inside the UI element).

> Note on Float values: Float values in Kotlin are decimal numbers, represented with an f or F at the end of the number.

In the Woof app, each list item row contains a dog image, dog information, and an expand more button. You will add a Spacer composable before the expand more button with weight 1f to properly align the button icon. Since the spacer is the only weighted child element in the row, it will fill the space remaining in the row after measuring the other unweighted child elements' width.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/733f6d9ef2939ab5_856.png)

Add the spacer to the list item row
In DogItem(), between DogInformation() and DogItemButton(), add a Spacer. Pass in a Modifier with weight(1f). The Modifier.weight() causes the spacer to fill the space remaining in the row.

```kt
import androidx.compose.foundation.layout.Spacer

Row(
   modifier = Modifier
       .fillMaxWidth()
       .padding(dimensionResource(R.dimen.padding_small))
) {
   DogIcon(dog.imageResourceId)
   DogInformation(dog.name, dog.age)
   Spacer(modifier = Modifier.weight(1f))
   DogItemButton(
       expanded = expanded,
       onClick = { /*TODO*/ }
   )
}
```

Check out WoofPreview() in the Design pane. Notice that the expand more button is now aligned to the end of the list item.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/8df42b9d85a5dbaa_856.png)


# 4. Add Composable to display hobby
In this task, you'll add Text composables to display the dog's hobby information.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/bba8146c6332cc37_856.png)

Create a new composable function called DogHobby() that takes in a dog's hobby string resource ID and an optional Modifier.

```kt
@Composable
fun DogHobby(
   @StringRes dogHobby: Int,
   modifier: Modifier = Modifier
) {
}
```

Inside the DogHobby() function, create a Column and pass in the modifier passed into DogHobby().

```kt
@Composable
fun DogHobby(
   @StringRes dogHobby: Int,
   modifier: Modifier = Modifier
){
   Column(
       modifier = modifier
   ) { 

   }
}
```

Inside the Column block, add two Text composables – one to display the About text above the hobby information, and another to display the hobby information.
Set the first one's text to the about from the strings.xml file and set the style as labelSmall. Set the second one's text to dogHobby which is passed in and set the style to bodyLarge.

```kt
Column(
   modifier = modifier
) {
   Text(
       text = stringResource(R.string.about),
       style = MaterialTheme.typography.labelSmall
   )
   Text(
       text = stringResource(dogHobby),
       style = MaterialTheme.typography.bodyLarge
   )
}
```

In DogItem(), the DogHobby() composable will go below the Row that contains the DogIcon(), DogInformation(), Spacer() and DogItemButton(). To do this, wrap the Row with a Column so that the hobby can be added below the Row.

```kt
Column() {
   Row(
       modifier = Modifier
           .fillMaxWidth()
           .padding(dimensionResource(R.dimen.padding_small))
   ) {
       DogIcon(dog.imageResourceId)
       DogInformation(dog.name, dog.age)
       Spacer(modifier = Modifier.weight(1f))
       DogItemButton(
           expanded = expanded,
           onClick = { /*TODO*/ }
       )
   }
}
```

Add DogHobby() after the Row as a second child of the Column. Pass in dog.hobbies which contains the unique hobby of the dog passed in and a modifier with the padding for the DogHobby() composable.

```kt
Column() {
   Row() {
      ...
   }
   DogHobby(
       dog.hobbies,
       modifier = Modifier.padding(
           start = dimensionResource(R.dimen.padding_medium),
           top = dimensionResource(R.dimen.padding_small),
           end = dimensionResource(R.dimen.padding_medium),
           bottom = dimensionResource(R.dimen.padding_medium)
       )
   )
}
```

<div style="    
    margin-bottom: 1rem;
    padding: 1rem;
    border-left: 5px solid green;
    width: auto;
    background-color: white;">

<strong style="color:green">Note: </strong> on dog hobbies: Dog hobby information for all the dogs is already provided for you as part of the starter code. To see this in your code, open data/Dog.kt file, observe the dogs list prefilled with the dogs information.
</div>


The complete DogItem() function should look like this:

```kt
@Composable
fun DogItem(
   dog: Dog,
   modifier: Modifier = Modifier
) {
   var expanded by remember { mutableStateOf(false) }
   Card(
       modifier = modifier
   ) {
       Column() {
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(dimensionResource(R.dimen.padding_small))
           ) {
               DogIcon(dog.imageResourceId)
               DogInformation(dog.name, dog.age)
               Spacer(Modifier.weight(1f))
               DogItemButton(
                   expanded = expanded,
                   onClick = { /*TODO*/ },
               )
           }
           DogHobby(
               dog.hobbies, 
               modifier = Modifier.padding(
                   start = dimensionResource(R.dimen.padding_medium),
                   top = dimensionResource(R.dimen.padding_small),
                   end = dimensionResource(R.dimen.padding_medium),
                   bottom = dimensionResource(R.dimen.padding_medium)
               )
           )
       }
   }
}
```

Check out WoofPreview() in the Design pane. Notice the dog's hobby displays.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/2704a4ef191ef458_856.png)

# 5. Show or hide the hobby on button click
Your app has an Expand More button for every list item, but it doesn't do anything yet! In this section, you will add the option to hide or reveal the hobby information when the user clicks the expand more button.

In the DogItem() composable function, in the DogItemButton() function call, define the onClick() lambda expression, change the expanded boolean state value to true when the button is clicked, and change it back to false if the button is clicked again.

```kt
DogItemButton(
   expanded = expanded,
   onClick = { expanded = !expanded }
)
```

> Note: The logical NOT operator ( ! ) returns the negated value of the Boolean expression.
For example, if expanded is true, then !expanded evaluates to false.

In the DogItem() function, wrap the DogHobby() function call with an if check on the expanded boolean.

```kt
@Composable
fun DogItem(
   dog: Dog,
   modifier: Modifier = Modifier
) {
   var expanded by remember { mutableStateOf(false) }
   Card(
       ...
   ) {
       Column(
           ...
       ) {
           Row(
               ...
           ) {
               ...
           }
           if (expanded) {
               DogHobby(
                   dog.hobbies, modifier = Modifier.padding(
                       start = dimensionResource(R.dimen.padding_medium),
                       top = dimensionResource(R.dimen.padding_small),
                       end = dimensionResource(R.dimen.padding_medium),
                       bottom = dimensionResource(R.dimen.padding_medium)
                   )
               )
           }
       }
   }
}
```

Now, the dog's hobby information only displays if the value of expanded is true.

The preview can show you what the UI looks like, and you can also interact with it. To interact with the UI preview, hover above the WoofPreview text in the Design Pane, then click the Interactive Mode button 42379dbe94a7a497.png in the top-right corner of the Design pane. This starts the preview in interactive mode.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/74e1624d68fb4131_856.png)

Interact with the preview by clicking the expand more button. Notice the dog's hobby information is hidden and revealed when you click the expand more button.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/e4c793196993a9ae.gif)

> Note: To stop the interactive mode, click Stop Interactive Mode on the top-left corner of the preview pane.
![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/60829fbffe4d8302_856.png)

Notice that the expand more button icon remains the same when the list item is expanded. For a better user experience, you'll change the icon so that ExpandMore displays the downward arrow c761ef298c2aea5a.png, and ExpandLess displays the upward arrow b380f933be0b6ff4.png.

In the DogItemButton() function, add an if statement that updates the imageVector value based on the expanded state as follows:

```kt
import androidx.compose.material.icons.filled.ExpandLess


@Composable
private fun DogItemButton(
   ...
) {
   IconButton(onClick = onClick) {
       Icon(
           imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
           ...
       )
   }
}
```

Notice how you wrote if-else in the previous code snippet.

if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore

This is the same as using the curly braces { } in the following code:

if (expanded) {
`Icons.Filled.ExpandLess`
} else {
`Icons.Filled.ExpandMore`
}

The curly braces are optional if there is a single line of code for the if-else statement.

Run the app on a device or emulator, or use the interactive mode in the preview again. Notice the icon alternates between the ExpandMore c761ef298c2aea5a.png and the ExpandLess b380f933be0b6ff4.png icons.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/de5dc4a953f11e65.gif)

> Note on interactive mode: Interactive mode allows you to interact with a preview the same way you would interact on a device. However, the preview mode is not a substitute for running your app on a device for testing.

Good job updating the icon!

When you expanded the list item, did you notice the abrupt height change? The abrupt height change doesn't look like a polished app. To resolve this issue, you will next add an animation to your app.


# 6. Add animation
Animations can add visual cues that notify users about what's going on in your app. They are especially useful when the UI changes state, such as when new content loads or new actions become available. Animations can also add a polished look to your app.

In this section you will add a spring animation to animate the change in height of the list item.

Spring Animation
Spring animation is a physics-based animation driven by a spring force. With a spring animation, the value and velocity of movement are calculated based on the spring force that is applied.

For example, if you drag an app icon around the screen and then release it by lifting your finger, the icon jumps back to its original location by an invisible force.

The following animation demonstrates the spring effect. Once the finger is released from the icon, the icon jumps back, mimicking a spring.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/af35a9d327d1cab0.gif)

Spring effect

Spring force is guided by the following two properties:

Damping ratio: The bounciness of the spring.
Stiffness level: The stiffness of the spring, that is, how fast the spring moves toward the end.
Below are some examples of animations with <a href="https://developer.android.com/reference/kotlin/androidx/compose/animation/core/Spring">different damping ratios and stiffness levels</a>.

<div style="dispay:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/d8e850d6eeec30a5.gif"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/32eac117de778099.gif"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/7ba20cf822ecb900.gif"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/710c9cad92e2d7ad.gif"/>
</div>

Take a look at the DogHobby() function call in the DogItem() composable function. The dog's hobby information is included in the composition, based on the expanded boolean value. The height of the list item changes, depending on whether the hobby information is visible or hidden. Currently, the transition is jarring. In this section you will use the animateContentSize modifier to add a smoother transition between the expanded and not expanded states.

```kt
// No need to copy over
@Composable
fun DogItem(...) {
  ...
    if (expanded) {
       DogHobby(
          dog.hobbies, 
          modifier = Modifier.padding(
              start = dimensionResource(R.dimen.padding_medium),
              top = dimensionResource(R.dimen.padding_small),
              end = dimensionResource(R.dimen.padding_medium),
              bottom = dimensionResource(R.dimen.padding_medium)
          )
      )
   }
}
```

In MainActivity.kt, in DogItem(), add a modifier parameter to the Column layout.

```kt
@Composable
fun DogItem(
   dog: Dog, 
   modifier: Modifier = Modifier
) {
   ...
   Card(
       ...
   ) {
       Column(
          modifier = Modifier
       ){
           ...
       }
   }
}
```

Chain the modifier with the animateContentSize modifier to animate the size (list item height) change.

```kt
import androidx.compose.animation.animateContentSize

Column(
   modifier = Modifier
       .animateContentSize()
)
```

In the current implementation, you are animating the list item height in your app. But, the animation is so subtle that it is difficult to discern when you run the app. To resolve this, use an optional animationSpec parameter that lets you customize the animation.

For Woof, the animation eases in and out with no bounce. To achieve that, add the animationSpec parameter to the animateContentSize() function call. Set it to a spring animation with DampingRatioNoBouncy so that there isn't a bounce to it and a StiffnessMedium parameter to make the spring a bit stiffer.

```kt
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

Column(
   modifier = Modifier
       .animateContentSize(
           animationSpec = spring(
               dampingRatio = Spring.DampingRatioNoBouncy,
               stiffness = Spring.StiffnessMedium
           )
       )
)
```

Check out WoofPreview() in the Design pane, and use the interactive mode or run your app on an emulator or device to see your spring animation in action.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/c0d0a52463332875.gif)

You did it! Enjoy your beautiful app with animations.


# 7. (Optional) Experiment with other animations

### animate*AsState
The animate*AsState() functions are one of the simplest animation APIs in Compose for animating a single value. You only provide the end value (or target value), and the API starts animation from the current value to the specified end value.

Compose provides animate*AsState() functions for Float, Color, Dp, Size, Offset, and Int, to name a few. You can easily add support for other data types using animateValueAsState() that takes a generic type.

Try using the animateColorAsState() function to change the color when a list item is expanded.

In DogItem(), declare a color and delegate its initialization to animateColorAsState() function.

```kt
import androidx.compose.animation.animateColorAsState

@Composable
fun DogItem(
   dog: Dog,
   modifier: Modifier = Modifier
) {
   var expanded by remember { mutableStateOf(false) }
   val color by animateColorAsState()
   ...
}
```

Set the targetValue named parameter, depending on the expanded boolean value. If the list item is expanded, set the list item to tertiaryContainer color. Else, set it to primaryContainer color.

```kt
import androidx.compose.animation.animateColorAsState

@Composable
fun DogItem(
   dog: Dog,
   modifier: Modifier = Modifier
) {
   var expanded by remember { mutableStateOf(false) }
   val color by animateColorAsState(
       targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
       else MaterialTheme.colorScheme.primaryContainer,
   )
   ...
}
```

Set the color as the background modifier to the Column.

```kt
@Composable
fun DogItem(
   dog: Dog, 
   modifier: Modifier = Modifier
) {
   ...
   Card(
       ...
   ) {
       Column(
           modifier = Modifier
               .animateContentSize(
                   ...
                   )
               )
               .background(color = color)
       ) {...}
}
```
Check out how the color changes when the list item is expanded. Non expanded list items are primaryContainer color and expanded list items are tertiaryContainer color.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-woof-animation/img/b45e86eb53fd7d88_856.png)


# 9. Conclusion
Congratulations! You added a button to hide and reveal information about the dog. You enhanced the user experience using spring animations. You also learned how to use interactive mode in the Design pane.
You can also try a different type of Jetpack Compose Animation.
