# Android Views and Compose in Views

Learn the basics of building apps with Android Views and how to add a composable in an app built with Views.


# Build an Android app with Views

In this codelab, you'll learn how to use views and build your Android app UI using XML. You will also learn how to use navigation with views and navigate between different fragments that correspond to different screens.

# Build an Android App with Views


# 1. Before you begin
Introduction
So far, you learned all about building Android apps with Compose. That's a good thing! Compose is a very powerful tool that can simplify the development process. However, Android apps were not always built with declarative UIs. Compose is a very recent tool in the history of Android Apps. Android UIs were originally built with Views. As such, it's highly likely that you will encounter Views as you continue your journey as an Android developer. In this codelab, you learn the basics of how Android apps were built before Compose — with XML, Views, View Bindings, and Fragments.

Prerequisites:
Complete the Android Basics with Compose coursework through Unit 7.
What you'll need
A computer with internet access and Android Studio
A device or emulator
The starter code for the Juice Tracker app
What you'll build
In this codelab, you complete the Juice Tracker app. This app lets you keep track of notable juices by building a list consisting of detailed items. You add and modify Fragments and XML to complete the UI and the starter code. Specifically, you build the entry form for creating a new juice, including the UI and any associated logic or navigation. The result is an app with an empty list to which you can add your own juices.


<div style="display:flex">
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/d6dc43171ae62047_856.png"/>
    </div>
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/87b2ca7b49e814cb_856.png"/>
    </div>
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/2d630489477e216e_856.png"/>
    </div>
</div>


# 2. Get the starter code
Starter code URL:

https://github.com/google-developer-training/basic-android-kotlin-compose-training-juice-tracker

Branch name with starter code: views-starter

In Android Studio, open the basic-android-kotlin-compose-training-juice-tracker folder.
Open the Juice Tracker app code in Android Studio.


# 3. Create a Layout
When building an app with Views, you construct the UI inside of a Layout. Layouts are typically declared using XML. These XML layout files are located in the resources directory under res > layout. Layouts contain the components that make up the UI; these components are known as Views. XML syntax consists of tags, elements, and attributes. For more details on XML syntax, reference the Create XML layouts for Android codelab.

In this section, you build an XML layout for the "Type of juice" entry dialog pictured.


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/87b2ca7b49e814cb_856.png)

Create a new Layout Resource File in the main > res > layout directory called fragment_entry_dialog.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/331927b84e9d1a27_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/6adb279d6e74ab13_856.png)

The fragment_entry_dialog.xml layout contains the UI components that the app displays to the user.

Notice that the Root element is a ConstraintLayout. This type of layout is a ViewGroup that lets you position and size Views in a flexible way using the constraints. A ViewGroup is a type of View that contains other Views, called children or child Views. The following steps cover this topic in more detail, but you can learn more about ConstraintLayout in Build a Responsive UI with ConstraintLayout.

After you create the file, define the app name space in the ConstraintLayout.

fragment_entry_dialog.xml
```xml
<androidx.constraintlayout.widget.ConstraintLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   android:layout_width="match_parent"
   android:layout_height="match_parent">
</androidx.constraintlayout.widget.ConstraintLayout>
```

Add the following guidelines to the ConstraintLayout.

fragment_entry_dialog.xml
```xml
<androidx.constraintlayout.widget.Guideline
   android:id="@+id/guideline_left"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:orientation="vertical"
   app:layout_constraintGuide_begin="16dp" />
<androidx.constraintlayout.widget.Guideline
   android:id="@+id/guideline_middle"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:orientation="vertical"
   app:layout_constraintGuide_percent="0.5" />
<androidx.constraintlayout.widget.Guideline
   android:id="@+id/guideline_top"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:orientation="horizontal"
   app:layout_constraintGuide_begin="16dp" />
```

These Guidelines serve as padding for other views. The guidelines constrain the "Type of juice" header text.

Create a TextView element. This TextView represents the title of the detail fragment.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/110cad4ae809e600_856.png){style="width:400px"}


Set the TextView an id of header_title.
Set layout_width to 0dp. Layout constraints ultimately define this TextView's width. Therefore, defining a width only adds unnecessary calculations during the drawing of the UI; defining a width of 0dp avoids the extra calculations.
Set the TextView text attribute to @string/juice_type.
Set the textAppearance to @style/TextAppearance.MaterialComponents.Headline5.

fragment_entry_dialog.xml
```xml
<TextView
   android:id="@+id/header_title"
   android:layout_width="0dp"
   android:layout_height="wrap_content"
   android:text="@string/juice_type"
   android:textAppearance="@style/TextAppearance.MaterialComponents.Headline5" />
```

Lastly, you need to define the constraints. Unlike the Guidelines, which use dimensions as constraints, the guidelines themselves constrain this TextView. To achieve this outcome, you can reference the id of the Guideline by which you want to constrain the view.

Constrain the top of the header to the bottom of guideline_top.

fragment_entry_dialog.xml
```xml
<TextView
   android:id="@+id/header_title"
   android:layout_width="0dp"
   android:layout_height="wrap_content"
   android:text="@string/juice_type"
   android:textAppearance="@style/TextAppearance.MaterialComponents.Headline5"
   app:layout_constraintTop_toBottomOf="@+id/guideline_top" />
```

Constrain the end to the start of guideline_middle and the start to the start of guideline_left to finish the TextView placement. Keep in mind that how you constrain a given view depends entirely on how you want your UI to look.

fragment_entry_dialog.xml
```xml
<TextView
   android:id="@+id/header_title"
   android:layout_width="0dp"
   android:layout_height="wrap_content"
   android:text="@string/juice_type"
   android:textAppearance="@style/TextAppearance.MaterialComponents.Headline5"
   app:layout_constraintTop_toBottomOf="@+id/guideline_top"
   app:layout_constraintEnd_toStartOf="@+id/guideline_middle"
   app:layout_constraintStart_toStartOf="@+id/guideline_left" />
```

Try to build the rest of the UI based on the screenshots. You can find the completed fragment_entry_dialog.xml file in the <a href="https://github.com/google-developer-training/basic-android-kotlin-compose-training-juice-tracker/tree/views">solution</a>.

# 4. Create a Fragment with Views
In Compose, you build layouts declaratively using Kotlin or Java. You can access different "screens" by navigating to different Composables, typically within the same activity. When building an app with Views, a Fragment that hosts the XML layout replaces the concept of a Composable "screen."

In this section, you create a Fragment to host the fragment_entry_dialog layout and provide data to the UI.

In the juicetracker package, create a new class called EntryDialogFragment.
Make the EntryDialogFragment extend the BottomSheetDialogFragment.

EntryDialogFragment.kt
```kt
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EntryDialogFragment : BottomSheetDialogFragment() {
}
```

The DialogFragment is a Fragment that displays a floating dialog. BottomSheetDialogFragment inherits from the DialogFragment class, but displays a sheet the width of the screen pinned to the bottom of the screen. This approach matches the design pictured previously.

Rebuild the project, which causes View Binding files based on the fragment_entry_dialog layout to autogenerate. The View Bindings let you access and interact with XML declared Views, you can read more about them in the View Binding documentation.
In the EntryDialogFragment class, implement the onCreateView()function. As the name suggests, this function creates the View for this Fragment.

> Note: Like an Activity, a Fragment also has different lifecycle states, and corresponding lifecycle methods.

EntryDialogFragment.kt
```kt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

override fun onCreateView(
   inflater: LayoutInflater,
   container: ViewGroup?,
   savedInstanceState: Bundle?
): View? {
   return super.onCreateView(inflater, container, savedInstanceState)
}
```

The onCreateView() function returns a View, but right now, it does not return a useful View.

Return the View generated by inflating the FragmentEntryDialogViewBinding instead of returning super.onCreateView().

EntryDialogFragment.kt
```kt
import com.example.juicetracker.databinding.FragmentEntryDialogBinding


override fun onCreateView(
   inflater: LayoutInflater,
   container: ViewGroup?,
   savedInstanceState: Bundle?
): View? {
   return FragmentEntryDialogBinding.inflate(inflater, container, false).root
}
```

Outside of the onCreateView() function, but inside the EntryDialogFragment class, create an instance of the EntryViewModel.
Implement the onViewCreated() function.
After you inflate the View binding, you can access and modify the Views in the layout. The onViewCreated() method is called after onCreateView() in the lifecycle. The onViewCreated() method is the recommended place to access and modify the Views within the layout.

Create an instance of the view binding by calling the bind() method on FragmentEntryDialogBinding.
At this point, your code should look like the following example:

EntryDialogFragment.kt
```kt
import androidx.fragment.app.viewModels
import com.example.juicetracker.ui.AppViewModelProvider
import com.example.juicetracker.ui.EntryViewModel

class EntryDialogFragment : BottomSheetDialogFragment() {

   private val entryViewModel by viewModels<EntryViewModel> { AppViewModelProvider.Factory }

   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
       return FragmentEntryDialogBinding.inflate(inflater, container, false).root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)
    }
}
```

You can access and set Views through the binding. For example, you can set a TextView through the setText() method.

```kt
binding.name.setText("Apple juice")
```

> Note: The use of the hard-coded string "Apple juice" in the above example was done for simplicity. In production, string resources should be used.

The entry dialog UI serves as a place for a user to create a new item, but you can also use it to modify an existing item. Therefore, the Fragment needs to retrieve a clicked item. The Navigation Component facilitates navigating to the EntryDialogFragment and retrieving a clicked item.

The EntryDialogFragment is not yet complete, but don't worry! For now, move on to the next section to learn more about using the Navigation Component in an app with Views.

> 5. Modify the Navigation Component
In this section, you use the navigation component to launch the entry dialog and to retrieve an item, if applicable.

Compose affords the opportunity to render different composables simply by calling them. However, Fragments work differently. The Navigation Component coordinates Fragment "destinations," providing an easy way to move between different Fragments and the Views they contain.

Use the Navigation Component to coordinate navigation to your EntryDialogFragment.

Open the nav_graph.xml file and make sure the Design tab is selected.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/783cb5d7ff0ba127_856.png){style="width:400px"}

Click the 93401bf098936c15.png icon to add a new destination.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/d5410c90e408b973_856.png){style="width:400px"}

Select EntryDialogFragment destination. This action declares the entryDialogFragment in the nav graph, making it accessible for navigation actions.


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/418feed425072ea4_856.png){style="width:400px"}


> Note: The entryDialogFragment screen might appear in a random place in the nav_graph.xml display. You can drag the destinations to make the display more organized.

You need to launch the EntryDialogFragment from the TrackerFragment. Therefore, a navigation action needs to accomplish this task.

Drag your cursor over the trackerFragment. Selecting the gray dot and drag the line to the entryDialogFragment.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/85decb6fcddec713_856.png){style="width:400px"}


The nav_graph design view lets you declare arguments for a destination by selecting the destination and clicking the a0d73140a20e4348.png icon next to the Arguments dropdown. Use this feature to add an itemId argument of type Long to the entryDialogFragment; the default value should be 0L.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/555cf791f64f62b8_856.png){style="width:400px"}

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-app-with-views/img/840105bd52f300f7_856.png){style="width:400px"}

Note that the TrackerFragment holds a list of Juice items — if you click one of these items, the EntryDialogFragment launches.

Rebuild the project. The itemId argument is now accessible in the EntryDialogFragment.


# 6. Complete the Fragment
With the data from the navigation arguments, complete the entry dialog.

Retrieve the navArgs() in the onViewCreated() method of the EntryDialogFragment.
Retrieve the itemId from the navArgs().
Implement the saveButton to save the new/modified juice using the ViewModel.
Recall from the entry dialog UI that the default color value is red. For now, pass this as a place holder.

Pass the item id from the args when calling saveJuice().

EntryDialogFragment.kt
```kt
import androidx.navigation.fragment.navArgs
import com.example.juicetracker.data.JuiceColor


class EntryDialogFragment : BottomSheetDialogFragment() {

   //...
   var selectedColor: JuiceColor = JuiceColor.Red

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)
        val args: EntryDialogFragmentArgs by navArgs()
        val juiceId = args.itemId

        binding.saveButton.setOnClickListener {
           entryViewModel.saveJuice(
               juiceId,
               binding.name.text.toString(),
               binding.description.text.toString(),
               selectedColor.name,
               binding.ratingBar.rating.toInt()
           )
        }
    }
}
```

After the data is saved, dismiss the dialog with the dismiss() method.
EntryDialogFragment.kt
```kt
class EntryDialogFragment : BottomSheetDialogFragment() {

    //...
    var selectedColor: JuiceColor = JuiceColor.Red
    //...

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)
        val args: EntryDialogFragmentArgs by navArgs()
        binding.saveButton.setOnClickListener {
           entryViewModel.saveJuice(
               juiceId,
               binding.name.text.toString(),
               binding.description.text.toString(),
               selectedColor.name,
               binding.ratingBar.rating.toInt()
           )
           dismiss()
        }
    }
}
```

Keep in mind that the above code does not complete the EntryDialogFragment. You still need to implement a number of things, such as the population of the fields with existing Juice data (if applicable), the selection of a color from the colorSpinner, the implementation of the cancelButton, and more. However, this code is not unique to Fragments, and you are able to implement this code on your own. Try to implement the rest of the functionality. As a last resort, you can refer to the solution code for this codelab.


# 7. Launch the entry dialog
The last task is to launch the entry dialog using the Navigation Component. The entry dialog needs to launch when the user clicks the floating action button (FAB). It needs to also launch and pass the corresponding id when the user clicks an item.

In the onClickListener() for the FAB, call navigate() on the nav controller.

TrackerFragment.kt
```kt
import androidx.navigation.findNavController


//...

binding.fab.setOnClickListener { fabView ->
   fabView.findNavController().navigate(
   )
}

//...
```

In the navigate function, pass the action to navigate from the tracker to the entry dialog.
TrackerFragment.kt
```kt
//...

binding.fab.setOnClickListener { fabView ->
   fabView.findNavController().navigate(
TrackerFragmentDirections.actionTrackerFragmentToEntryDialogFragment()
   )
}

//...
```

Repeat this action in the lambda body for the onEdit() method in the JuiceListAdapter, but this time, pass the id of the Juice.
TrackerFragment.kt
```kt
//...

onEdit = { drink ->
   findNavController().navigate(
       TrackerFragmentDirections.actionTrackerFragmentToEntryDialogFragment(drink.id)
   )
},

//...
```

# 8. Get the solution code
To download the code for the finished codelab, you can use these git commands:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-juice-tracker.git
$ cd basic-android-kotlin-compose-training-juice-tracker
$ git checkout views
```