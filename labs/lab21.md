# Add a scrollable list

# Before you begin
In this codelab, you learn how to make a scrollable list in your app using Jetpack Compose.

You will be working with the Affirmations app, which displays a list of affirmations paired with beautiful images to bring positivity to your day!

The data is already there, all you need to do is take that data and display it in the UI.

Prerequisites
Familiarity with lists in Kotlin
Experience building layouts with Jetpack Compose
Experience running apps on a device or emulator
What you'll learn
How to create a material design card using Jetpack Compose
How to create a scrollable list using Jetpack Compose
What you'll build
You will take an existing application and add a scrollable list to the UI
The finished product will look like this:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/286f5132aa155fa6_856.png)

What you'll need
A computer with internet access, a web browser, and Android Studio
Access to GitHub
Download the starter code
In Android Studio, open the basic-android-kotlin-compose-training-affirmations folder.

> Starter code URL:

https://github.com/google-developer-training/basic-android-kotlin-compose-training-affirmations

Branch name with starter code: starter

The app is expected to display a blank screen when built from the starter branch code.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/3beea0789e2eeaba_856.png)

# 2. Create a list item data class
Create a data class for an Affirmation
In Android apps, lists are made up of list items. For single pieces of data, this could be something simple like a string or an integer. For list items that have multiple pieces of data, like an image and text, you will need a class that contains all of these properties. Data classes are a type of class that only contain properties, they can provide some utility methods to work with those properties.

Create a new package under com.example.affirmations.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/89c8d8485c685fac_856.png)

Name the new package model. The model package will contain the data model that will be represented by a data class. The data class will be comprised of properties that represent the information relevant to what will be an "Affirmation," which will consist of a string resource and an image resource. Packages are directories that contain classes and even other directories.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/b54fb6bf57de44c8_856.png)

Create a new class in the com.example.affirmations.model package.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/58510a651bd49100_856.png)

Name the new class Affirmation and make it a Data class.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/7f94b65ee3d8407f_856.png)

Each Affirmation consists of one image and one string. Create two val properties in the Affirmation data class. One should be called stringResourceId and the other imageResourceId. They should both be integers.

Affirmation.kt
```kt
data class Affirmation(
    val stringResourceId: Int,
    val imageResourceId: Int
)
```

Annotate the stringResourceId property with the @StringRes annotation and annotate the imageResourceId with the @DrawableRes annotation. The stringResourceId represents an ID for the affirmation text stored in a string resource. The imageResourceId represents an ID for the affirmation image stored in a drawable resource.

Affirmation.kt
```kt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
```

In the com.example.affirmations.data package, open the Datasource.kt file and uncomment the two import statements and the contents of the Datasource class.

Datasource.kt
```kt
import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

class Datasource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.affirmation1, R.drawable.image1),
            Affirmation(R.string.affirmation2, R.drawable.image2),
            Affirmation(R.string.affirmation3, R.drawable.image3),
            Affirmation(R.string.affirmation4, R.drawable.image4),
            Affirmation(R.string.affirmation5, R.drawable.image5),
            Affirmation(R.string.affirmation6, R.drawable.image6),
            Affirmation(R.string.affirmation7, R.drawable.image7),
            Affirmation(R.string.affirmation8, R.drawable.image8),
            Affirmation(R.string.affirmation9, R.drawable.image9),
            Affirmation(R.string.affirmation10, R.drawable.image10))
    }
}
```

> Note: The loadAffirmations() method gathers all of the data provided in the starter code and returns it as a list. You will use this later to build the scrollable list.


# 3. Add a list to your app
Create a list item card
This app is meant to display a list of affirmations. The first step in configuring the UI to display a list is to create a list item. Each affirmation item consists of an image and a string. The data for each of these items comes with the starter code, and you will create the UI component to display such an item.

The item will be comprised of a Card composable, containing an Image and a Text composable. In Compose, a Card is a surface that displays content and actions in a single container. The Affirmation card will look like this in the preview:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/4f657540712a069f_856.png)

The card shows an image with some text beneath it. This vertical layout can be achieved using a Column composable wrapped in a Card composable. You can give it a try on your own, or follow the steps below to achieve this.

Open the MainActivity.kt file.
Create a new method beneath the AffirmationsApp() method, called AffirmationCard(), and annotate it with the @Composable annotation.
MainActivity.kt

```kt
@Composable
fun AffirmationsApp() {
}

@Composable
fun AffirmationCard() {

}
```

Edit the method signature to take an Affirmation object as a parameter. The Affirmation object comes from the model package.

MainActivity.kt
```kt
import com.example.affirmations.model.Affirmation

@Composable
fun AffirmationCard(affirmation: Affirmation) {

}
```

Add a modifier parameter to the signature. Set a default value of Modifier for the parameter.
MainActivity.kt
```kt
@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {

}
```

> Note: It is a best practice to pass a modifier to every composable and set it to a default value.

Inside of the AffirmationCard method, call the Card composable. Pass in the modifier parameter.

MainActivity.kt
```kt
import androidx.compose.material3.Card

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {

    }
}
```

Add a Column composable inside of the Card composable. Items within a Column composable arrange themselves vertically in the UI. This allows you to place an image above the associated text. Conversely, a Row composable arranges its contained items horizontally.
MainActivity.kt
```kt
import androidx.compose.foundation.layout.Column

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {

        }
    }

}
```

Add an Image composable inside of the lambda body of the Column composable. Recall that an Image composable always requires a resource to display, and a contentDescription. The resource should be a painterResource passed to the painter parameter. The painterResource method will load either vector drawables or rasterized asset formats like PNGs. Also, pass a stringResource for the contentDescription parameter.

MainActivity.kt
```kt
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
            )
        }
    }
}
```

In addition to the painter and contentDescription parameters, pass a modifier and a contentScale. A contentScale determines how the image should be scaled and displayed. The Modifier object should have the fillMaxWidth attribute set and a height of 194.dp. The contentScale should be ContentScale.Crop.

MainActivity.kt
```kt
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
```

Inside of the Column, create a Text composable after the Image composable. Pass a stringResource of the affirmation.stringResourceId to the text parameter, pass a Modifier object with the padding attribute set to 16.dp, and set a text theme by passing MaterialTheme.typography.headlineSmall to the style parameter.

MainActivity.kt
```kt
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(affirmation.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
```

Preview the AffirmationCard composable
The card is the core of the UI for the Affirmations app, and you worked hard to create it! To check that the card looks correct, you can create a composable that can be previewed without launching the entire app.

Create a private method called AffirmationCardPreview(). Annotate the method with @Preview and @Composable.
MainActivity.kt
```kt
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun AffirmationCardPreview() {

}
```

Inside of the method, call the AffirmationCard composable, and pass it a new Affirmation object with the R.string.affirmation1 string resource and the R.drawable.image1 drawable resource passed into its constructor.
MainActivity.kt
```kt
@Preview
@Composable
private fun AffirmationCardPreview() {
    AffirmationCard(Affirmation(R.string.affirmation1, R.drawable.image1))
}
```

Open the Split tab and you will see a preview of the AffirmationCard. If necessary, click Build & Refresh in the Design pane to display the preview.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/924a4df2c1db236c_856.png)

Create the list
The list item component is the building block of the list. Once the list item is created, you can leverage it to make the list component itself.

Create a function called AffirmationList(), annotate it with the @Composable annotation, and declare a List of Affirmation objects as a parameter in the method signature.
MainActivity.kt

```kt
@Composable
fun AffirmationList(affirmationList: List<Affirmation>) {
    
}
```

Declare a modifier object as a parameter in the method signature with a default value of Modifier.
MainActivity.kt
```kt
@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {

}
```

In Jetpack Compose, a scrollable list can be made using the LazyColumn composable. The difference between a LazyColumn and a Column is that a Column should be used when you have a small number of items to display, as Compose loads them all at once. A Column can only hold a predefined, or fixed, number of composables. A LazyColumn can add content on demand, which makes it good for long lists and particularly when the length of the list is unknown. A LazyColumn also provides scrolling by default, without additional code. Declare a LazyColumn composable inside of the AffirmationList() function. Pass the modifier object as an argument to the LazyColumn.

MainActivity.kt
```kt
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {

    }
}
```

In the lambda body of the LazyColumn, call the items() method, and pass in the affirmationList. The items() method is how you add items to the LazyColumn. This method is somewhat unique to this composable, and is otherwise not a common practice for most composables.

MainActivity.kt
```kt
import androidx.compose.foundation.lazy.items

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) {

        }
    }
}
```

A call to the items() method requires a lambda function. In that function, specify a parameter of affirmation that represents one affirmation item from the affirmationList.
MainActivity.kt
```kt
@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->

        }
    }
}
```

For each affirmation in the list, call the AffirmationCard() composable. Pass it the affirmation and a Modifier object with the padding attribute set to 8.dp.
MainActivity.kt

```kt
@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
```

Display the list
In the AffirmationsApp composable, retrieve the current layout directions and save them in a variable. These will be used to configure the padding later.
MainActivity.kt
```kt
import com.example.affirmations.data.Datasource

@Composable
fun AffirmationsApp() {
    val layoutDirection = LocalLayoutDirection.current
}
```

Now create a Surface composable. This composable will set the padding for the AffirmationsList composable.
MainActivity.kt
```kt
import com.example.affirmations.data.Datasource

@Composable
fun AffirmationsApp() {
    val layoutDirection = LocalLayoutDirection.current
    Surface() {
    }
}
```

Pass a Modifier to the Surface composable that fills the max width and height of its parent, sets status bar padding, and sets the start and end padding to the layoutDirection. Here's an example of how to translate a LayoutDirection object into padding: WindowInsets.safeDrawing.asPaddingValues().calculateStartPadding(layoutDirection).
MainActivity.kt
```kt
import com.example.affirmations.data.Datasource

@Composable
fun AffirmationsApp() {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        Modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
            end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
        ),
    ) {
    }
}
```

In the lambda for the Surface composable, call the AffirmationList composable, and pass DataSource().loadAffirmations() to the affirmationList parameter.

> Note: The DataSource class is found in the data package.

MainActivity.kt
```kt
import com.example.affirmations.data.Datasource

@Composable
fun AffirmationsApp() {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        Modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
            end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
        ),
    ) {
        AffirmationsList(
            affirmationList = Datasource().loadAffirmations(),
        )
    }
}
```

Run the Affirmations app on a device or emulator and see the finished product!

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-training-add-scrollable-list/img/286f5132aa155fa6_856.png)

# 5. Conclusion
You now know how to create cards, list items, and scrollable lists using Jetpack Compose! Keep in mind that these are just basic tools for creating a list. You can let your creativity roam and customize list items however you like!

## Summary
Use Card composables to create list items.
Modify the UI contained within a Card composable.
Create a scrollable list using the LazyColumn composable.
Build a list using custom list items.