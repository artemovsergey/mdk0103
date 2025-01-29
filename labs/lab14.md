# Calculate a custom tip


1. Before you begin
In this codelab, you use the solution code from the Intro to state in Compose codelab to build an interactive tip calculator that can automatically calculate and round a tip amount when you enter the bill amount and tip percentage. You can see the final app in this image:

d8e768525099378a.png

Prerequisites
The Intro to state in Compose codelab.
Ability to add Text and TextField composables to an app.
Knowledge of the remember() function, state, state hoisting, and the difference between stateful and stateless composable functions.
What you'll learn
How to add an action button to a virtual keyboard.
What a Switch composable is and how to use it.
Add leading icons to the text fields.
What you'll build
A Tip Time app that calculates tip amounts based on the user's inputted bill amount and tip percentage.
What you'll need
The latest version of Android Studio
The solution code from the Intro to state in Compose codelab

# 3. Starter app overview
This codelab begins with the Tip Time app from the previous codelab Intro to state in Compose, which provides the user interface needed to calculate a tip with a fixed tip percentage. The Bill amount text box lets the user enter the cost of the service. The app calculates and displays the tip amount in a Text composable.

Run the Tip Time App
Open the Tip Time project in Android Studio and run the app on an emulator or a device.
Enter a bill amount. The app automatically calculates and displays the tip amount.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/b6bd5374911410ac_856.png)

In the current implementation, the percentage of the tip is hardcoded to 15%. In this codelab, you extend this feature with a text field that lets the app calculate a custom tip percentage and round the tip amount.

Add necessary string resources
In the Project tab, click res > values > strings.xml.
In between the strings.xml file's ```<resources>``` tags, add these string resources:

```xml
<string name="how_was_the_service">Tip Percentage</string>
<string name="round_up_tip">Round up tip?</string>
```

The strings.xml file should look like this code snippet, which includes the strings from the previous codelab:

strings.xml
```xml
<resources>
    <string name="app_name">Tip Time</string>
    <string name="calculate_tip">Calculate Tip</string>
    <string name="bill_amount">Bill Amount</string>
    <string name="how_was_the_service">Tip Percentage</string>
    <string name="round_up_tip">Round up tip?</string>
    <string name="tip_amount">Tip Amount: %s</string>
</resources>
```

# 4. Add a tip-percentage text field
A customer may want to tip more or less based on the quality of the service provided and various other reasons. To accommodate this, the app should let the user calculate a custom tip. In this section, you add a text field for the user to enter a custom tip percentage as you can see in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/391b4b1a090687ef_856.png)

You already have a Bill Amount text field composable in your app, which is the stateless EditNumberField() composable function. In the previous codelab, you hoisted the amountInput state from the EditNumberField() composable to the TipTimeLayout() composable, which made the EditNumberField() composable stateless.

To add a text field, you can reuse the same EditNumberField() composable, but with a different label. To make this change, you need to pass in the label as a parameter, rather than hard coded it inside the EditNumberField() composable function.

Make the EditNumberField() composable function reusable:

In the MainActivity.kt file in the EditNumberField() composable function's parameters, add a label string resource of Int type:

```kt
@Composable
fun EditNumberField(
    label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
)
```

> Note: The best practice is to provide a default Modifier parameter to all composable functions, which increases reusability. You should add it as the first optional parameter after all required parameters.

In the function body, replace the hardcoded string resource ID with the label parameter:

```kt
@Composable
fun EditNumberField(
    //...
) {
     TextField(
         //...
         label = { Text(stringResource(label)) },
         //...
     )
}
```

To denote that the label parameter is expected to be a string resource reference, annotate the function parameter with the @StringRes annotation:

```kt
@Composable
fun EditNumberField(
    @StringRes label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) 
```

> Note: An example of a string resource reference is the R.string.bill_amount.

Import the following:

```kt
import androidx.annotation.StringRes
```

> Note: The @StringRes annotation is a type-safe way to use string resources. It indicates that the integer to be passed is a string resource from the values/strings.xml file. These annotations are useful to developers who work on your code and for code-inspection tools like lint in Android Studio. You learn more about lint in a future codelab.

In the TipTimeLayout() composable function's EditNumberField() function call, set the label parameter to the R.string.bill_amount string resource:

```kt
EditNumberField(
    label = R.string.bill_amount,
    value = amountInput,
    onValueChanged = { amountInput = it },
    modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
)   
```

In the Preview pane there should not be any visual changes.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/b223d5ba4a54f792_856.png)

In the TipTimeLayout() composable function after the EditNumberField() function call, add another text field for the custom tip percentage. Make a call to the EditNumberField() composable function with these parameters:

```kt
EditNumberField(
    label = R.string.how_was_the_service,
    value = "",
    onValueChanged = { },
    modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
)
```

This adds another text box for the custom tip percentage.

The app preview now shows a Tip Percentage text field as you can see in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/a5f5ef5e456e185e_856.png)

At the top of the TipTimeLayout() composable function, add a var property called tipInput for the added text field's state variable. Use mutableStateOf("") to initialize the variable and surround the call with remember function:

```kt
var tipInput by remember { mutableStateOf("") }
```

In the new EditNumberField() function call, set the value named parameter to the tipInput variable and then update the tipInput variable in the onValueChanged lambda expression:

```kt
EditNumberField(
    label = R.string.how_was_the_service,
    value = tipInput,
    onValueChanged = { tipInput = it },
    modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
)
```

In the TipTimeLayout() function after the tipInput variable's definition. Define a val named tipPercent that converts the tipInput variable to a Double type. Use an Elvis operator and return 0, if the value is null. This value could be null if the text field is empty.

```kt
val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
```

In the TipTimeLayout() function, update the calculateTip() function call, pass in the tipPercent variable as the second parameter:

```kt
val tip = calculateTip(amount, tipPercent)
```

The code for the TipTimeLayout() function should look like this code snippet now:

```kt
@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, tipPercent)
    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.bill_amount,
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            label = R.string.how_was_the_service,
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}
```

Run the app on an emulator or a device, and then enter a bill amount and the tip percentage. Does the app calculate tip amount correctly?

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/23fc1e924d9963bc_856.png)


# 5. Set an action button
In the previous codelab, you explored how to use the KeyboardOptions class to set the type of the keyboard. In this section, you explore how to set the keyboard action button with the same KeyboardOptions. A keyboard action button is a button at the end of the keyboard. You can see some examples in this table:

|Property|Action button on the keyboard|
|:-------|:----------------------------|
|ImeAction.Search. Used when the user wants to execute a search.|![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/67fd7f2efed7e677_856.png)|
|ImeAction.Send. Used when the user wants to send the text in the input field.|![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/b19be317a5574818_856.png)|
|ImeAction.Go. Used when the user wants to navigate to the target of the text in the input.|![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/ac61541c3a56b2bb_856.png)|

In this task, you set two different action buttons for the text boxes:

A Next action button for the Bill Amount text box, which indicates that the user is done with the current input and wants to move to the next text box.
A Done action button for the Tip Percentage text box, which indicates that the user finished providing input.
You can see examples of keyboards with these action buttons in these images:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/46559a252132af44_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/b8972b81b513b0a_856.png)

Add keyboard options:

In the EditNumberField() function's TextField() function call, pass the KeyboardOptions constructor an imeAction named argument set to an ImeAction.Next value. Use the KeyboardOptions.Default.copy() function to make sure you use the other default options.

```kt
import androidx.compose.ui.text.input.ImeAction


@Composable
fun EditNumberField(
    //...
) {
    TextField(
        //...
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}
```

Run the app on an emulator or a device. The keyboard now displays the Next action button as you can see in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/82574a95b658f052_856.png)

Notice the keyboard displays the same Next action button when the Tip Percentage text field is selected. However, you want two different action buttons for the text fields. You fix this problem shortly.

Examine the EditNumberField() function. The keyboardOptions parameter in the TextField() function is hardcoded. To create different action buttons for the text fields, you need to pass in the KeyboardOptions object as an argument, which you will do in the next step.

```kt
// No need to copy, just examine the code.
fun EditNumberField(
    @StringRes label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        //...
        keyboardOptions = KeyboardOptions.Default.copy(
           keyboardType = KeyboardType.Number,
           imeAction = ImeAction.Next
        )
    )
}
```

In the EditNumberField() function definition, add a keyboardOptions parameter of type KeyboardOptions type. In the function body, assign it to the TextField() function's keyboardOptions named parameter:

```kt
@Composable
fun EditNumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    // ...
){
    TextField(
        //...
        keyboardOptions = keyboardOptions
    )
}
```

In the TipTimeLayout() function, update the first EditNumberField() function call, pass in the keyboardOptions named parameter for the Bill Amount text field:

![](EditNumberField(
    label = R.string.bill_amount,
    keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    ),
    // ...
))

In the second EditNumberField() function call, change the Tip Percentage text field's imeAction to ImeAction.Done. Your function should look like this code snippet:

![](EditNumberField(
    label = R.string.how_was_the_service,
    keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    ),
    // ...
))

Run the app. It displays the Next and Done action buttons as you can see in these images:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/82574a95b658f052_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/a87ef38535796bb1_856.png)

Enter any bill amount and click the Next action button, and then enter any tip percentage and click the Done action button. That closes the keypad.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/a9e3fbddfff829c8.gif)


# 6. Add a switch
A switch toggles the state of a single item on or off.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/6923dfb1101602c7_856.png)

There are two-states in a toggle that let the user select between two options. A toggle consists of a track, thumb and an optional icon as you can see in these images:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/b4f7f68b848bcc2b_856.png)

Switch is a selection control that can be used to enter decisions or declare preferences, such as settings as you can see in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/5cd8acb912ab38eb_856.png)

The user may drag the thumb back and forth to choose the selected option, or simply tap the switch to toggle. You can see another example of a toggle in this GIF in which the Visual options setting is toggled to Dark mode:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/eabf96ad496fd226.gif)

To learn more about switches, see the Switches documentation.

You use the Switch composable so that the user can choose whether to round up the tip to the nearest whole number as you can see in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/b42af9f2d3861e4_856.png)

Add a row for the Text and Switch composables:

After the EditNumberField() function, add a RoundTheTipRow() composable function and then pass in a default Modifier, as arguments similar to the EditNumberField() function:

```kt
@Composable
fun RoundTheTipRow(modifier: Modifier = Modifier) {
}
```

Implement the RoundTheTipRow() function, add a Row layout composable with the following modifier to set the child elements' width to the maximum on the screen, center the alignment, and ensure a 48dp size:

```kt
Row(
   modifier = modifier
       .fillMaxWidth()
       .size(48.dp),
   verticalAlignment = Alignment.CenterVertically
) {
}   
```

Import the following:

```kt
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
```

In the Row layout composable's lambda block, add a Text composable that uses the R.string.round_up_tip string resource to display a Round up tip? string:

```kt
Text(text = stringResource(R.string.round_up_tip))  
```

After the Text composable, add a Switch composable, and pass a checked named parameter set it to roundUp and an onCheckedChange named parameter set it to onRoundUpChanged.

```kt
Switch(
    checked = roundUp,
    onCheckedChange = onRoundUpChanged,
)
```

This table contains information about these parameters, which are the same parameters that you defined for the RoundTheTipRow() function:

|Parameter|Description|
|:--------|:----------|
|checked|Whether the switch is checked. This is the state of the Switch composable.|
|onCheckedChange|The callback to be called when the switch is clicked.|

Import the following:

```kt
import androidx.compose.material3.Switch
```

In the RoundTheTipRow() function, add a roundUp parameter of Boolean type and an onRoundUpChanged lambda function that takes a Boolean and returns nothing:

```kt
@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
)
```

This hoists the switch's state.

In the Switch composable, add this modifier to align the Switch composable to the end of the screen:

```kt
Switch(
           modifier = modifier
               .fillMaxWidth()
               .wrapContentWidth(Alignment.End),
           //...
       )
```

Import the following:

```kt
import androidx.compose.foundation.layout.wrapContentWidth
```

In the TipTimeLayout() function, add a var variable for the Switch composable's state. Create a var variable named roundUp set it to mutableStateOf(), with false as the initial value. Surround the call with remember { }.

```kt
fun TipTimeLayout() {
    //...
    var roundUp by remember { mutableStateOf(false) }

    //...
    Column(
        ...
    ) {
      //...
   }
}
```

This is the variable for the Switch composable state, and false will be the default state.

In the TipTimeLayout() function's Column block after the Tip Percentage text field. Call the RoundTheTipRow() function with the following arguments: roundUp named parameter set to a roundUp and an onRoundUpChanged named parameter set to a lambda callback that updates the roundUp value:

```kt
@Composable
fun TipTimeLayout() {
    //...

    Column(
        ...
    ) {
        Text(
            ...
        )
        Spacer(...)
        EditNumberField(
            ...
        )
        EditNumberField(
            ...
        )
        RoundTheTipRow(
             roundUp = roundUp,
             onRoundUpChanged = { roundUp = it },
             modifier = Modifier.padding(bottom = 32.dp)
         )
        Text(
            ...
        )
    }
}
```

This displays the Round up tip? row.

Run the app. The app displays the Round up tip? toggle.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/5225395a29022a5e_856.png)

Enter a bill amount and tip percentage, and then select the Round up tip? toggle. The tip amount isn't rounded because you still need to update the calculateTip() function, which you do in the next section.

## Update the calculateTip() function to round the tip
Modify the calculateTip() function to accept a Boolean variable to round up the tip to the nearest integer:

To round up the tip, the calculateTip() function should know the state of the switch, which is a Boolean. In the calculateTip() function, add a roundUp parameter of Boolean type:

```kt
private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundUp: Boolean
): String { 
    //...
}
```

In the calculateTip() function before the return statement, add an if() condition that checks the roundUp value. If the roundUp is true, define a tip variable and set to kotlin.math.ceil() function and then pass the function tip as argument:

```kt
if (roundUp) {
    tip = kotlin.math.ceil(tip)
}
```

> Warning. Android Studio shows you an error - val cannot be reassigned error, you need to change the tip variable to a var variable:

var tip = tipPercent / 100 * amount

> Note: The kotlin.math.ceil(x) function rounds the given value of an integer up. For example, it rounds 10.65 to 11.00. This function can take a Double or a Float number.


The completed calculateTip() function should look like this code snippet:

```kt
private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}
```

In the TipTimeLayout() function, update the calculateTip() function call and then pass in a roundUp parameter:

```kt
val tip = calculateTip(amount, tipPercent, roundUp)
```

Run the app. Now it rounds up the tip amount as you can see in these images:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/caff187fc2c8c46_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/fe592cd5eea3a7b_856.png)


# 7. Add support for landscape orientation
Android devices come in a variety of form factors—phones, tablets, foldables, and ChromeOS devices—which have a wide range of screen sizes. Your app should support both orientations portrait and landscape.

Test your app in landscape mode, turn on the Auto-rotate.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/8566fc367d5a5b2f_856.png)

Rotate your emulator or device left, notice you are not able to see the tip amount. To resolve this you will need a vertical scrollbar, that helps you scroll your app screen.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/28d23a73c2a5ea24_856.png)

Add .verticalScroll(rememberScrollState()) to the modifier to enable the column to scroll vertically. The rememberScrollState() creates and automatically remembers the scroll state.

```kt
@Composable
fun TipTimeLayout() {
    // ...
    Column(
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //...
    }
}
```

Import the following:

```kt
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
```

Run the app again. Try scrolling in landscape mode!

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/179866a0fae00401.gif)


# 8. Add leading icon to text fields (optional)

Icons can make the text field more visually appealing and provide additional information about the text field. Icons can be used to convey information about the purpose of the text field, such as what type of data is expected or what kind of input is required. For example, an icon of a phone next to a text field might indicate that the user is expected to enter a phone number.

Icons can be used to guide the user's input by providing visual cues about what is expected. For example, an icon of a calendar next to a text field might indicate that the user is expected to enter a date.

Following is an example of a text field with a search icon, indicating to enter the search term.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/9318c9a2414c4add_856.png)

Add another parameter to the EditNumberField() composable called leadingIcon of the type Int. Annotate it with @DrawableRes.

```kt
@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) 
```

Import the following:

```kt
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
```

Add the leading icon to the text field. The leadingIcon takes a composable, you will pass in the following Icon composable.

```kt
TextField(
    value = value,
    leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
    //...
)
```

Pass in the leading icon to the text fields. Icons are already present in the starter code for your convenience.

```kt
EditNumberField(
    label = R.string.bill_amount,
    leadingIcon = R.drawable.money,
    // Other arguments
)
EditNumberField(
    label = R.string.how_was_the_service,
    leadingIcon = R.drawable.percent,
    // Other arguments
)
```

Run the app.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-calculate-tip/img/bff007b9d67ede83_856.png)