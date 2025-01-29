# Add Compose to a View-based app

# 1. Before you begin
From the start, Jetpack Compose was designed with View interoperability, which means that Compose and the View system can share resources and work together side by side to display the UI. This functionality lets you add Compose to your existing View-based app. That means Compose and Views can co-exist in your codebase until your entire app is fully in Compose.

In this codelab, you change the view-based list item in the Juice Tracker app to Compose. You can convert the rest of Juice Tracker's views on your own, if you wish.

If you have an app with a View-based UI, you might not want to rewrite its entire UI all at once. This codelab helps you convert a single view in a view-based UI to a Compose element.

Prerequisites
Familiarity with a View-based UI.
Knowledge of how to build an app using a View-based UI.
Experience with the Kotlin syntax, including lambdas.
Knowledge of how to build an app in Jetpack Compose.
What you'll learn
How to add Compose to an existing screen that was built with Android views.
How to preview a Composable added to your View-based app.
What you'll build
You convert a View-based list item to Compose in the Juice Tracker app.


# 2. Starter app overview
This codelab uses the Juice Tracker app solution code from the Build an Android App with Views as the starter code. The starter app already saves data using the Room persistence library. The user can add juice info to the app database, like juice name, description, color, and rating.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/36bd5542e97fee2e_856.png){style="width:400px"}

In this codelab, you convert the view-based list item to Compose.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/9aa691f45f5a880d_856.png){style="width:400px"}

Download the starter code for this codelab

To get started, download the starter code:

file_downloadDownload zip

Alternatively, you can clone the GitHub repository for the code:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-juice-tracker.git
$ cd basic-android-kotlin-compose-training-juice-tracker
$ git checkout views
```

> Note: The starter code is in the views branch of the downloaded repository.


# 3. Add Jetpack Compose library
Recollect, Compose and Views can exist together on a given screen; you can have some UI elements in Compose and others in the View system. For example, you can have only the list in Compose, while the rest of the screen is in the View system.

Complete the following steps to add the Compose library to the Juice Tracker app.

Open the Juice Tracker in Android Studio.
Open the app-level build.gradle.kts.
Inside the buildFeatures block, add a compose = true flag.

```
buildFeatures {
    //...
    // Enable Jetpack Compose for this module
    compose = true
}
```

This flag enables Android Studio to work with Compose. You have not done this step in the previous codelabs because Android Studio automatically generates this code when you create a new Android Studio Compose template project.

Below the buildFeatures, add the composeOptions block.
Inside the block, set kotlinCompilerExtensionVersion to "1.5.1" to set the Kotlin compiler version.

```
composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
}
```

In the dependencies section, add Compose dependencies. You need the following dependencies to add Compose to a View based app. These dependencies help integrate Compose with the Activity, add the Compose design components library, support Compose Jetpack theming, and provide tools for better IDE support.

```
dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    // other dependencies 
    // Compose
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.accompanist:accompanist-themeadapter-material3:0.28.0")


    debugImplementation("androidx.compose.ui:ui-tooling")
}
```

Add ComposeView
A ComposeView is an Android View that can host Jetpack Compose UI content. Use setContent to supply the content composable function for the view.

Open the layout/list_item.xml and view the preview in the Split tab.

By the end of this codelab, you will replace this view with a composable.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/7a2df616fde1ec56_856.png){style="width:400px"}

In JuiceListAdapter.kt, remove ListItemBinding from everywhere. In the JuiceListViewHolder class, replace binding.root with composeView.

```kt
import androidx.compose.ui.platform.ComposeView

class JuiceListViewHolder(
    private val onEdit: (Juice) -> Unit,
    private val onDelete: (Juice) -> Unit
): RecyclerView.ViewHolder(composeView) 
```

In the onCreateViewHolder() folder, update the return() function to match the following code:

```kt
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuiceListViewHolder {
   return JuiceListViewHolder(
       ComposeView(parent.context),
       onEdit,
       onDelete
   )
}
```

In the JuiceListViewHolder class, delete all the private variables and remove all the code from the bind() function. Your JuiceListViewHolder class now looks like the following code:

```kt
class JuiceListViewHolder(
    private val onEdit: (Juice) -> Unit,
    private val onDelete: (Juice) -> Unit
) : RecyclerView.ViewHolder(composeView) {

   fun bind(juice: Juice) {

   }
}
```

At this point, you can delete com.example.juicetracker.databinding.ListItemBinding and android.view.LayoutInflater imports.

```kt
// Delete
import com.example.juicetracker.databinding.ListItemBinding
import android.view.LayoutInflater
```

Delete the layout/list_item.xml file.
Select OK in the Delete dialog.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/2954ed44c5827571_856.png){style="width:400px"}


# 4. Add composable function
Next, you create a composable that emits the list item. The Composable takes Juice and two callback functions to edit and to delete the list item.

In JuiceListAdapter.kt, after the JuiceListAdapter class definition, create a composable function called ListItem().
Make the ListItem() function accept the Juice object and a lambda callback for delete.

```kt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ListItem(
    input: Juice,
    onDelete: (Juice) -> Unit,
    modifier: Modifier = Modifier
) {
}
```

Take a look at the preview of the list item you want to create. Notice that it has a juice icon, juice details, and a delete button icon. You will be implementing these components shortly.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/4ec7f82371c6bc15_856.png){style="width:400px"}


Create Juice icon composable
In JuiceListAdapter.kt, after the ListItem() composable, create another composable function called JuiceIcon() that takes a color and a Modifier.

```kt
@Composable
fun JuiceIcon(color: String, modifier: Modifier = Modifier) {

}
```

Inside the JuiceIcon() function, add variables for the color and the content description as shown in the following code:

```kt
@Composable
fun JuiceIcon(color: String, modifier: Modifier = Modifier) {
   val colorLabelMap = JuiceColor.values().associateBy { stringResource(it.label) }
   val selectedColor = colorLabelMap[color]?.let { Color(it.color) }
   val juiceIconContentDescription = stringResource(R.string.juice_color, color)

}
```

Using colorLabelMap and selectedColor variables you will retrieve the color resource associated with the user selection.

Add a Box layout to display two icons ic_juice_color and ic_juice_clear on top of each other. The ic_juice_color icon has a tint and is aligned to the center.

```kt
import androidx.compose.foundation.layout.Box


Box(
   modifier.semantics {
       contentDescription = juiceIconContentDescription
   }
) {
   Icon(
       painter = painterResource(R.drawable.ic_juice_color),
       contentDescription = null,
       tint = selectedColor ?: Color.Red,
       modifier = Modifier.align(Alignment.Center)
   )
   Icon(painter = painterResource(R.drawable.ic_juice_clear), contentDescription = null)
}
```

Since you are familiar with the composable implementation, details about how it is implemented are not provided.

Add a function to preview JuiceIcon(). Pass the color as Yellow.

```kt
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun PreviewJuiceIcon() {
    JuiceIcon("Yellow")
}
```

Create juice details composables
In the JuiceListAdapter.kt, you need to add another composable function to display the juice details. You also need a column layout to display two Text composables for the name and description, and a rating indicator. To do so, complete the following steps:

Add a composable function called JuiceDetails() that takes a Juice object and a Modifier, and a text composable for juice name and a composable for juice description as shown in the following code:

```kt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight


@Composable
fun JuiceDetails(juice: Juice, modifier: Modifier = Modifier) {
   Column(modifier, verticalArrangement = Arrangement.Top) {
       Text(
           text = juice.name,
           style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
       )
       Text(juice.description)
       RatingDisplay(rating = juice.rating, modifier = Modifier.padding(top = 8.dp))
   }
}
```

To resolve the unresolved reference error, create a composable function called RatingDisplay().

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/536030e2ecb01a4e_856.png){style="width:400px"}


In the View system, you have a RatingBar to display the following rating bar. Compose doesn't have a rating bar composable so you need to implement this element from scratch.

Define the RatingDisplay() function to display the stars as per the rating. This composable function displays the number of stars based on the rating.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/842de5707f1a9396_856.png){style="width:400px"}

![](import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource


@Composable
fun RatingDisplay(rating: Int, modifier: Modifier = Modifier) {
   val displayDescription = pluralStringResource(R.plurals.number_of_stars, count = rating)
   Row(
       // Content description is added here to support accessibility
       modifier.semantics {
           contentDescription = displayDescription
       }
   ) {
       repeat(rating) {
           // Star [contentDescription] is null as the image is for illustrative purpose
           Image(
               modifier = Modifier.size(32.dp),
               painter = painterResource(R.drawable.star),
               contentDescription = null
           )
       }
   }
})

To create the star drawable in Compose, you need to create the star vector asset.

In the Project pane, right click on drawable > New > Vector Asset.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/201431ca3d212113_856.png){style="width:400px"}

In the Asset Studio dialog, search for a star icon. Select the filled-in star icon.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/9956ed24371f61ac_856.png){style="width:400px"}

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/5a79bac6f3982b72_856.png){style="width:400px"}

Change the color value of the star to 625B71.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/44d4bdfa93bc369a_856.png){style="width:400px"}

Click Next > Finish.
Notice that a drawable appears in the res/drawable folder.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/64bb8d9f05019229_856.png){style="width:400px"}

Add a preview composable to preview the JuiceDetails composable.

```kt
@Preview
@Composable
fun PreviewJuiceDetails() {
    JuiceDetails(Juice(1, "Sweet Beet", "Apple, carrot, beet, and lemon", "Red", 4))
}
```

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/19fe7df886c6d2b6_856.png){style="width:400px"}


Create delete button composable
In the JuiceListAdapter.kt, add another composable function called DeleteButton() that takes a lambda callback function and a Modifier.
Set the lambda to the onClick argument and pass in the Icon() as shown in the following code:

```kt
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton


@Composable
fun DeleteButton(onDelete: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { onDelete() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete),
            contentDescription = stringResource(R.string.delete)
        )
    }
}
```

Add a preview function to preview the delete button.

```kt
@Preview
@Composable
fun PreviewDeleteIcon() {
    DeleteButton({})
}
```

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/30e00883e38f39b0_856.png)


# 5. Implement ListItem function
Now that you have all the required composables to display the list item, you can arrange them in a layout. Notice the ListItem() function you defined in the previous step.

```kt
@Composable
fun ListItem(
   input: Juice,
   onEdit: (Juice) -> Unit,
   onDelete: (Juice) -> Unit,
   modifier: Modifier = Modifier
) {
}
```

In the JuiceListAdapter.kt, complete the following steps to implement the ListItem() function.

Add a Row layout inside Mdc3Theme {} lambda.

```kt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import com.google.accompanist.themeadapter.material3.Mdc3Theme

Mdc3Theme {
   Row(
       modifier = modifier,
       horizontalArrangement = Arrangement.SpaceBetween
   ) {

   }
}
```

Inside the Row lambda, call the three composables JuiceIcon, JuiceDetails, DeleteButton you created as child elements.

```kt
JuiceIcon(input.color)
JuiceDetails(input, Modifier.weight(1f))
DeleteButton({})
```

Passing the Modifier.weight(1f) to the JuiceDetails() composable ensures the juice details take up the horizontal space remaining after measuring unweighted child elements.

Pass in the onDelete(input) lambda and modifier with top alignment as parameters to the DeleteButton composable.

```kt
DeleteButton(
   onDelete = {
       onDelete(input)
   },
   modifier = Modifier.align(Alignment.Top)
)
```

Write a preview function to preview the ListItem composable.

```kt
@Preview
@Composable
fun PreviewListItem() {
   ListItem(Juice(1, "Sweet Beet", "Apple, carrot, beet, and lemon", "Red", 4), {})
}
```

![](https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/fdb313f1157ff4e5_856.png)

Bind the ListItem composable to the view holder. Call onEdit(input) inside the clickable() lambda function to open the edit dialog when the list item is clicked.
In the JuiceListViewHolder class, inside the bind() function, you need to host the composable. You use ComposeView, which is an Android View that can host Compose UI content using its setContent method.

```kt
fun bind(input: Juice) {
    composeView.setContent {
        ListItem(
            input,
            onDelete,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onEdit(input)
                }
                .padding(vertical = 8.dp, horizontal = 16.dp),
       )
   }
}
```

Run the app. Add your favorite juice. Notice the shiny compose list item.

<div style="display:flex">
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/aadccf32ab952d0f_856.png"/>
    </div>
        <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-training-compose-add-compose-to-a-view-based-app/img/8aa751f4cf63bf98_856.png"/>
    </div>
</div>

Congratulations! You just created your first Compose interoperability app that uses Compose elements in a view-based app.


# 6. Get the solution code
To download the code for the finished codelab, you can use these git commands:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-juice-tracker.git
$ cd basic-android-kotlin-compose-training-juice-tracker
$ git checkout views-with-compose
```