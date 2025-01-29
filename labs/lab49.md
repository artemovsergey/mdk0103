# Read and update data with Room

# 1. Before you begin
You learned in the previous codelabs how to use a Room persistence library, an abstraction layer on top of a SQLite database, to store the app data. In this codelab, you'll add more features to the Inventory app and learn how to read, display, update, and delete data from the SQLite database using Room. You will use a LazyColumn to display the data from the database and automatically update the data when the underlying data in the database changes.

Prerequisites
Ability to create and interact with the SQLite database using the Room library.
Ability to create an entity, DAO, and database classes.
Ability to use a data access object (DAO) to map Kotlin functions to SQL queries.
Ability to display list items in a LazyColumn.
Completion of the previous codelab in this unit, Persist data with Room.
What you'll learn
How to read and display entities from a SQLite database.
How to update and delete entities from a SQLite database using the Room library.
What you'll build
An Inventory app that displays a list of inventory items and can update, edit, and delete items from the app database using Room.
What you'll need
A computer with Android Studio

# 2. Starter app overview
This codelab uses the Inventory app solution code from the previous codelab, Persist data with Room as the starter code. The starter app already saves data with the Room persistence library. The user can use the Add Item screen to add data to the app database.

> Note: The current version of the starter app doesn't display the data stored in the database.

<div style="display:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/bae9fd572d154881_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/fb1fb265e2aa93f9_856.png"/>
</div>

In this codelab, you extend the app to read and display the data, and update and delete entities on the database using a Room library.

Download the starter code for this codelab
To get started, download the starter code:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app.git
$ cd basic-android-kotlin-compose-training-inventory-app
$ git checkout room
```

# 3. Update UI state
In this task, you add a LazyColumn to the app to display the data stored in the database.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/47cc655ae260796b_856.png)

### HomeScreen composable function walkthrough
Open the ui/home/HomeScreen.kt file and look at the HomeScreen() composable.

```kt
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            // Top app with app title
        },
        floatingActionButton = {
            FloatingActionButton(
                // onClick details
            ) {
                Icon(
                    // Icon details
                )
            }
        },
    ) { innerPadding ->
     
       // Display List header and List of Items
        HomeBody(
            itemList = listOf(),  // Empty list is being passed in for itemList
            onItemClick = navigateToItemUpdate,
            modifier = modifier.padding(innerPadding)
                              .fillMaxSize()
        )
    }
```


This composable function displays the following items:

The top app bar with the app title
The floating action button (FAB) for the addition of new items to inventory 7b1535d90ee957fa.png
The HomeBody() composable function
The HomeBody()composable function displays inventory items based on the passed in list. As part of the starter code implementation, an empty list (listOf()) is passed to the HomeBody()composable function. To pass the inventory list to this composable, you must retrieve the inventory data from the repository and pass it into the HomeViewModel.

Emit UI state in the HomeViewModel
When you added methods to ItemDao to get items- getItem() and getAllItems()- you specified a Flow as the return type. Recall that a Flow represents a generic stream of data. By returning a Flow, you only need to explicitly call the methods from the DAO once for a given lifecycle. Room handles updates to the underlying data in an asynchronous manner.

Getting data from a flow is called collecting from a flow. When collecting from a flow in your UI layer, there are a few things to consider.

Lifecycle events like configuration changes, for example rotating the device, causes the activity to be recreated. This causes recomposition and collecting from your Flow all over again.
You want the values to be cached as state so that existing data isn't lost between lifecycle events.
Flows should be canceled if there's no observers left, such as after a composable's lifecycle ends.
The recommended way to expose a Flow from a ViewModel is with a StateFlow. Using a StateFlow allows the data to be saved and observed, regardless of the UI lifecycle. To convert a Flow to a StateFlow, you use the stateIn operator.

The stateIn operator has three parameters which are explained below:

scope - The viewModelScope defines the lifecycle of the StateFlow. When the viewModelScope is canceled, the StateFlow is also canceled.
started - The pipeline should only be active when the UI is visible. The SharingStarted.WhileSubscribed() is used to accomplish this. To configure a delay (in milliseconds) between the disappearance of the last subscriber and the stopping of the sharing coroutine, pass in the TIMEOUT_MILLIS to the SharingStarted.WhileSubscribed() method.
initialValue - Set the initial value of the state flow to HomeUiState().
Once you've converted your Flow into a StateFlow, you can collect it using the collectAsState() method, converting its data into State of the same type.

In this step, you'll retrieve all items in the Room database as a StateFlow observable API for UI state. When the Room Inventory data changes, the UI updates automatically.

Open the ui/home/HomeViewModel.kt file, which contains a TIMEOUT_MILLIS constant and a HomeUiState data class with a list of items as a constructor parameter.

```kt
// No need to copy over, this code is part of starter code

class HomeViewModel : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val itemList: List<Item> = listOf())
```

Inside the HomeViewModel class, declare a val called homeUiState of the type StateFlow<HomeUiState>. You will resolve the initialization error shortly.

```kt
val homeUiState: StateFlow<HomeUiState> 
```

Call getAllItemsStream() on itemsRepository and assign it to homeUiState you just declared.

```kt
val homeUiState: StateFlow<HomeUiState> =
    itemsRepository.getAllItemsStream()
```

You now get an error - Unresolved reference: itemsRepository. To resolve the Unresolved reference error, you need to pass in the ItemsRepository object to the HomeViewModel.

Add a constructor parameter of the type ItemsRepository to the HomeViewModel class.

```kt
import com.example.inventory.data.ItemsRepository

class HomeViewModel(itemsRepository: ItemsRepository): ViewModel() {
```

In the ui/AppViewModelProvider.kt file, in the HomeViewModel initializer, pass the ItemsRepository object as shown.

```kt
initializer {
    HomeViewModel(inventoryApplication().container.itemsRepository)
}
```

Go back to the HomeViewModel.kt file. Notice the type mismatch error. To resolve this, add a transformation map as shown below.

```kt
val homeUiState: StateFlow<HomeUiState> =
    itemsRepository.getAllItemsStream().map { HomeUiState(it) }
```

Android Studio still shows you a type mismatch error. This error is because homeUiState is of the type StateFlow and getAllItemsStream() returns a Flow.

Use the stateIn operator to convert the Flow into a StateFlow. The StateFlow is the observable API for UI state, which enables the UI to update itself.

```kt
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

val homeUiState: StateFlow<HomeUiState> =
    itemsRepository.getAllItemsStream().map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
```

Build the app to make sure there are no errors in the code. There will not be any visual changes.

# 4. Display the Inventory data
In this task, you collect and update the UI state in the HomeScreen.

In the HomeScreen.kt file, in the HomeScreen composable function, add a new function parameter of the type HomeViewModel and initialize it.

```kt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.ui.AppViewModelProvider


@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
```

In the HomeScreen composable function, add a val called homeUiState to collect the UI state from the HomeViewModel. You use collectAsState(), which collects values from this StateFlow and represents its latest value via State.

```kt
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

val homeUiState by viewModel.homeUiState.collectAsState()
```

Update the HomeBody() function call and pass in homeUiState.itemList to the itemList parameter.

```kt
HomeBody(
    itemList = homeUiState.itemList,
    onItemClick = navigateToItemUpdate,
    modifier = modifier.padding(innerPadding)
)
```

Run the app. Notice that the inventory list displays if you saved items in your app database. If the list is empty, add some inventory items to the app database.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/47cc655ae260796b_856.png)

# 5. Test your database
Previous codelabs discuss the importance of testing your code. In this task, you add some unit tests to test your DAO queries, and then you add more tests as you progress through the codelab.

The recommended approach for testing your database implementation is writing a JUnit test that runs on an Android device. Because these tests don't require creating an activity, they are faster to execute than your UI tests.

In the build.gradle.kts (Module :app) file, notice the following dependencies for Espresso and JUnit.

```
// Testing
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
androidTestImplementation("androidx.test.ext:junit:1.1.5")
```

Switch to Project view and right-click on src > New > Directory to create a test source set for your tests.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/9121189f4a0d2613_856.png)

Select androidTest/kotlin from the New Directory popup.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/fba4ed57c7589f7f_856.png)

Create a Kotlin class called ItemDaoTest.kt.
Annotate the ItemDaoTest class with @RunWith(AndroidJUnit4::class). Your class now looks something like the following example code:

```kt
package com.example.inventory

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
}
```

Inside the class, add private var variables of the type ItemDao and InventoryDatabase.

```kt
import com.example.inventory.data.InventoryDatabase
import com.example.inventory.data.ItemDao

private lateinit var itemDao: ItemDao
private lateinit var inventoryDatabase: InventoryDatabase
```

Add a function to create the database and annotate it with @Before so that it can run before every test.
Inside the method, initialize itemDao.

```kt
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.Before

@Before
fun createDb() {
    val context: Context = ApplicationProvider.getApplicationContext()
    // Using an in-memory database because the information stored here disappears when the
    // process is killed.
    inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
        // Allowing main thread queries, just for testing.
        .allowMainThreadQueries()
        .build()
    itemDao = inventoryDatabase.itemDao()
}
```

In this function, you use an in-memory database and do not persist it on the disk. To do so, you use the inMemoryDatabaseBuilder() function. You do this because the information need not be persisted, but rather, needs to be deleted when the process is killed.You are running the DAO queries in the main thread with .allowMainThreadQueries(), just for testing.

Add another function to close the database. Annotate it with @After to close the database and run after every test.

```kt
import org.junit.After
import java.io.IOException

@After
@Throws(IOException::class)
fun closeDb() {
    inventoryDatabase.close()
}
```

Declare items in the class ItemDaoTest for the database to use, as shown in the following code example:

```kt
import com.example.inventory.data.Item

private var item1 = Item(1, "Apples", 10.0, 20)
private var item2 = Item(2, "Bananas", 15.0, 97)
```

Add utility functions to add one item, and then two items, to the database. Later, you use these functions in your test. Mark them as suspend so they can run in a coroutine.

```kt
private suspend fun addOneItemToDb() {
    itemDao.insert(item1)
}

private suspend fun addTwoItemsToDb() {
    itemDao.insert(item1)
    itemDao.insert(item2)
}
```

Write a test for inserting a single item into the database, insert(). Name the test daoInsert_insertsItemIntoDB and annotate it with @Test.

```kt
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


@Test
@Throws(Exception::class)
fun daoInsert_insertsItemIntoDB() = runBlocking {
    addOneItemToDb()
    val allItems = itemDao.getAllItems().first()
    assertEquals(allItems[0], item1)
}
```

In this test, you use the utility function addOneItemToDb()to add one item to the database. Then, you read the first item in the database. With assertEquals(), you compare the expected value with the actual value. You run the test in a new coroutine with runBlocking{}. This setup is the reason you mark the utility functions as suspend.

Run the test and make sure it passes.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/2f0ddde91781d6bd_856.png)

Write another test for getAllItems() from the database. Name the test daoGetAllItems_returnsAllItemsFromDB.

```kt
@Test
@Throws(Exception::class)
fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
    addTwoItemsToDb()
    val allItems = itemDao.getAllItems().first()
    assertEquals(allItems[0], item1)
    assertEquals(allItems[1], item2)
}
```

In the above test, you add two items to the database inside a coroutine. Then you read the two items and compare them with the expected values.


# 6. Display item details
In this task, you read and display the entity details on the Item Details screen. You use the item UI state, such as name, price, and quantity from the inventory app database and display them on the Item Details screen with the ItemDetailsScreen composable. The ItemDetailsScreen composable function is prewritten for you and contains three Text composables that display the item details.

ui/item/ItemDetailsScreen.kt
This screen is part of the starter code and displays the details of the items, which you see in a later codelab. You do not work on this screen in this codelab. The ItemDetailsViewModel.kt is the corresponding ViewModel for this screen.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/de7761a894d1b2ab_856.png)

In the HomeScreen composable function, notice the HomeBody() function call. navigateToItemUpdate is being passed to the onItemClick parameter, which gets called when you click on any item in your list.

```kt
// No need to copy over 
HomeBody(
    itemList = homeUiState.itemList,
    onItemClick = navigateToItemUpdate,
    modifier = modifier
        .padding(innerPadding)
        .fillMaxSize()
)
```

Open ui/navigation/InventoryNavGraph.kt and notice the navigateToItemUpdate parameter in the HomeScreen composable. This parameter specifies the destination for navigation as the item details screen.

```kt
// No need to copy over 
HomeScreen(
    navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
    navigateToItemUpdate = {
        navController.navigate("${ItemDetailsDestination.route}/${it}")
   }
```

This part of the onItemClick functionality is already implemented for you. When you click the list item, the app navigates to the item details screen.

Click any item in the inventory list to see the item details screen with empty fields.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/fc38a289ccb8a947_856.png)

To fill the text fields with item details, you need to collect the UI state in ItemDetailsScreen().

In UI/Item/ItemDetailsScreen.kt, add a new parameter to the ItemDetailsScreen composable of the type ItemDetailsViewModel and use the factory method to initialize it.

```kt
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.ui.AppViewModelProvider

@Composable
fun ItemDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
```

Inside the ItemDetailsScreen() composable, create a val called uiState to collect the UI state. Use collectAsState() to collect uiState StateFlow and represent its latest value via State. Android Studio displays an unresolved reference error.

```kt
import androidx.compose.runtime.collectAsState

val uiState = viewModel.uiState.collectAsState()
```

To resolve the error, create a val called uiState of the type StateFlow<ItemDetailsUiState> in the ItemDetailsViewModel class.
Retrieve the data from the item repository and map it to ItemDetailsUiState using the extension function toItemDetails(). The extension function Item.toItemDetails() is already written for you as part of the starter code.

```kt
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

val uiState: StateFlow<ItemDetailsUiState> =
         itemsRepository.getItemStream(itemId)
             .filterNotNull()
             .map {
                 ItemDetailsUiState(itemDetails = it.toItemDetails())
             }.stateIn(
                 scope = viewModelScope,
                 started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                 initialValue = ItemDetailsUiState()
             )
```

Pass ItemsRepository into the ItemDetailsViewModel to resolve the Unresolved reference: itemsRepository error.

```kt
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
    ) : ViewModel() {
```

In ui/AppViewModelProvider.kt, update the initializer for ItemDetailsViewModel as shown in the following code snippet:

```kt
initializer {
    ItemDetailsViewModel(
        this.createSavedStateHandle(),
        inventoryApplication().container.itemsRepository
    )
}
```

Go back to the ItemDetailsScreen.kt and notice the error in the ItemDetailsScreen() composable is resolved.
In the ItemDetailsScreen() composable, update the ItemDetailsBody() function call and pass in uiState.value to itemUiState argument.

```kt
ItemDetailsBody(
    itemUiState = uiState.value,
    onSellItem = {  },
    onDelete = { },
    modifier = modifier.padding(innerPadding)
)
```

Observe the implementations of ItemDetailsBody() and ItemInputForm(). You are passing the current selected item from ItemDetailsBody() to ItemDetails().

```kt
// No need to copy over

@Composable
private fun ItemDetailsBody(
    itemUiState: ItemUiState,
    onSellItem: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
       //...
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        ItemDetails(
             item = itemDetailsUiState.itemDetails.toItem(), modifier = Modifier.fillMaxWidth()
         )

      //...
    }
```

Run the app. When you click any list element on the Inventory screen, the Item Details screen displays.
Notice that the screen is not blank anymore. It displays the entity details retrieved from the inventory database.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/b0c839d911d5c379_856.png)


Tap the Sell button. Nothing happens!
In the next section, you implement the functionality of the Sell button.

7. Implement Item details screen
ui/item/ItemEditScreen.kt
The Item edit screen is already provided to you as part of the starter code.

This layout contains text field composables to edit the details of any new inventory item.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/53bf6bada41dad50_856.png)

The code for this app still isn't fully functional. For example, in the Item Details screen, when you tap the Sell button, the Quantity in Stock does not decrease. When you tap the Delete button, the app does prompt you with a confirmation dialog. However, when you select the Yes button, the app does not actually delete the item.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/d8e76897bd8f253a_856.png)

Lastly, the FAB button aad0ce469e4a3a12.png opens an empty Edit Item screen.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/cdccb3a8931b4a3_856.png)

In this section, you implement the functionalities of Sell, Delete and the FAB buttons.

# 8. Implement sell item
In this section, you extend the features of the app to implement the sell functionality. This update involves the following tasks:

Add a test for the DAO function to update an entity.
Add a function in the ItemDetailsViewModel to reduce the quantity and update the entity in the app database.
Disable the Sell button if the quantity is zero.
In ItemDaoTest.kt, add a function called daoUpdateItems_updatesItemsInDB() with no parameters. Annotate with @Test and @Throws(Exception::class).

```kt
@Test
@Throws(Exception::class)
fun daoUpdateItems_updatesItemsInDB()
```

Define the function and create a runBlocking block. Call addTwoItemsToDb() inside it.

```kt
fun daoUpdateItems_updatesItemsInDB() = runBlocking {
    addTwoItemsToDb()
}
```

Update the two entities with different values, calling itemDao.update.

```kt
itemDao.update(Item(1, "Apples", 15.0, 25))
itemDao.update(Item(2, "Bananas", 5.0, 50))
```

Retrieve the entities with itemDao.getAllItems(). Compare them to the updated entity and assert.

```kt
val allItems = itemDao.getAllItems().first()
assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
```

Make sure the completed function looks like the following:

```kt
@Test
@Throws(Exception::class)
fun daoUpdateItems_updatesItemsInDB() = runBlocking {
    addTwoItemsToDb()
    itemDao.update(Item(1, "Apples", 15.0, 25))
    itemDao.update(Item(2, "Bananas", 5.0, 50))

    val allItems = itemDao.getAllItems().first()
    assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
    assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
}
```

Run the test and make sure it passes.
Add a function in the ViewModel
In ItemDetailsViewModel.kt, inside the ItemDetailsViewModel class, add a function called reduceQuantityByOne() with no parameters.

```kt
fun reduceQuantityByOne() {
}
```

Inside the function, start a coroutine with viewModelScope.launch{}.

> Note: You must run database operations inside a coroutine.

```kt
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope


viewModelScope.launch {
}
```

Inside the launch block, create a val called currentItem and set it to uiState.value.toItem().

```kt
val currentItem = uiState.value.toItem()
```

The uiState.value is of the type ItemUiState. You convert it to the Item entity type with the extension function toItem().

Add an if statement to check if the quality is greater than 0.
Call updateItem() on itemsRepository and pass in the updated currentItem. Use copy() to update the quantity value so that the function looks like the following:

```kt
fun reduceQuantityByOne() {
    viewModelScope.launch {
        val currentItem = uiState.value.itemDetails.toItem()
        if (currentItem.quantity > 0) {
    itemsRepository.updateItem(currentItem.copy(quantity = currentItem.quantity - 1))
       }
    }
}
```

Go back to ItemDetailsScreen.kt.
In the ItemDetailsScreen composable, go to the ItemDetailsBody() function call.
In the onSellItem lambda, call viewModel.reduceQuantityByOne().

```kt
ItemDetailsBody(
    itemUiState = uiState.value,
    onSellItem = { viewModel.reduceQuantityByOne() },
    onDelete = { },
    modifier = modifier.padding(innerPadding)
)
```

Run the app.
On the Inventory screen, click a list element. When the Item Details screen displays, tap the Sell and notice that the quantity value decreases by one. 

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/3aac7e2c9e7a04b6_856.png)

In the Item Details screen, continuously tap the Sell button until the quantity is zero.

Tip: To save time, you might want to use an item for this task with a low quantity. If none of your items have a low quantity, you can create a new one with a low quantity.

After the quantity reaches zero, tap Sell again. There is no visual change because the function reduceQuantityByOne() checks if the quantity is greater than zero before updating the quantity.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/dbd889a1ac1f3be4_856.png)

To give users better feedback, you might want to disable the Sell button when there is no item to sell.

In the ItemDetailsViewModel class, set outOfStock value based on the it.quantity in the map transformation.

```kt
val uiState: StateFlow<ItemDetailsUiState> =
    itemsRepository.getItemStream(itemId)
        .filterNotNull()
        .map {
            ItemDetailsUiState(outOfStock = it.quantity <= 0, itemDetails = it.toItemDetails())
        }.stateIn(
            //...
        )
```

Run your app. Notice that the app disables the Sell button when the quantity in stock is zero.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/48f2748adfe30d47_856.png)


Congratulations on implementing the Sell item feature in your app!

Delete item entity
As with the previous task, you must extend the features of your app further by implementing delete functionality This feature is much easier to implement than the sell feature. The process involves the following tasks:

Add a test for the delete DAO query.
Add a function in the ItemDetailsViewModel class to delete an entity from the database.
Update the ItemDetailsBody composable.
Add DAO test
In ItemDaoTest.kt, add a test called daoDeleteItems_deletesAllItemsFromDB().

```kt
@Test
@Throws(Exception::class)
fun daoDeleteItems_deletesAllItemsFromDB()
```

Launch a coroutine with runBlocking {}.

```kt
fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
}
```

Add two items to the database and call itemDao.delete() on those two items to delete them from the database.

```kt
addTwoItemsToDb()
itemDao.delete(item1)
itemDao.delete(item2)
```

Retrieve the entities from the database and check that the list is empty. The completed test should look like the following:

```kt
import org.junit.Assert.assertTrue

@Test
@Throws(Exception::class)
fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
    addTwoItemsToDb()
    itemDao.delete(item1)
    itemDao.delete(item2)
    val allItems = itemDao.getAllItems().first()
    assertTrue(allItems.isEmpty())
}
```

Add delete function in the ItemDetailsViewModel
In ItemDetailsViewModel, add a new function called deleteItem() that takes no parameters and returns nothing.
Inside the deleteItem() function, add an itemsRepository.deleteItem() function call and pass in uiState.value.toItem().

```kt
suspend fun deleteItem() {
    itemsRepository.deleteItem(uiState.value.itemDetails.toItem())
}
```

In this function, you convert the uiState from itemDetails type to Item entity type using the toItem() extension function.

In the ui/item/ItemDetailsScreen composable, add a val called coroutineScope and set it to rememberCoroutineScope(). This approach returns a coroutine scope bound to the composition where it's called (ItemDetailsScreen composable).

```kt
import androidx.compose.runtime.rememberCoroutineScope

val coroutineScope = rememberCoroutineScope()
```

Scroll to the ItemDetailsBody()function.
Launch a coroutine with coroutineScope inside the onDelete lambda.
Inside the launch block, call the deleteItem() method on viewModel.

```kt
import kotlinx.coroutines.launch

ItemDetailsBody(
    itemUiState = uiState.value,
    onSellItem = { viewModel.reduceQuantityByOne() },
    onDelete = {
        coroutineScope.launch {
           viewModel.deleteItem()
    }
    modifier = modifier.padding(innerPadding)
)
```

After deleting the item, navigate back to the inventory screen.
Call navigateBack() after the deleteItem() function call.

```kt
onDelete = {
    coroutineScope.launch {
        viewModel.deleteItem()
        navigateBack()
    }
```

Still within the ItemDetailsScreen.kt file, scroll to the ItemDetailsBody()function.
This function is part of the starter code. This composable displays an alert dialog to get the user's confirmation before deleting the item and calls the deleteItem() function when you tap Yes.

```kt
// No need to copy over

@Composable
private fun ItemDetailsBody(
    itemUiState: ItemUiState,
    onSellItem: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        /*...*/
    ) {
        //...
       
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                //...
            )
        }
    }
}
```

When you tap No, the app closes the alert dialog. The showConfirmationDialog()function displays the following alert:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/d8e76897bd8f253a_856.png)


Run the app.
Select a list element on the Inventory screen.
In the Item Details screen, tap Delete.
Tap Yes in the alert dialog, and the app navigates back to the Inventory screen.
Confirm that the entity you deleted is no longer in the app database.
Congratulations on implementing the delete feature!

<div style="display:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/5a03d33f03b4d17c_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/c0b2c57bc97325bd_856.png"/>
</div>

Edit item entity
Similar to the previous sections, in this section, you add another feature enhancement to the app that edits an item entity.

Here is a quick run through of the steps to edit an entity in the app database:

Add a test to the test get item DAO query.
Populate the text fields and the Edit Item screen with the entity details.
Update the entity in the database using Room.
Add DAO test
In ItemDaoTest.kt, add a test called daoGetItem_returnsItemFromDB().

```kt
@Test
@Throws(Exception::class)
fun daoGetItem_returnsItemFromDB()
```

Define the function. Inside the coroutine, add one item to the database.

```kt
@Test
@Throws(Exception::class)
fun daoGetItem_returnsItemFromDB() = runBlocking {
    addOneItemToDb()
}
```

Retrieve the entity from the database using the itemDao.getItem() function and set it to a val named item.

```kt
val item = itemDao.getItem(1)
```

Compare the actual value with the retrieved value and assert using assertEquals(). Your completed test looks like the following:

```kt
@Test
@Throws(Exception::class)
fun daoGetItem_returnsItemFromDB() = runBlocking {
    addOneItemToDb()
    val item = itemDao.getItem(1)
    assertEquals(item.first(), item1)
}
```

Run the test and ensure it passes.
Populate text fields
If you run the app, go to the Item Details screen, and then click the FAB, you can see that the title of the screen now is Edit Item. However, all the text fields are empty. In this step, you populate the text fields in the Edit Item screen with the entity details.


<div style="display:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/3aac7e2c9e7a04b6_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/cdccb3a8931b4a3_856.png"/>
</div>


In ItemDetailsScreen.kt, scroll to the ItemDetailsScreen composable.
In FloatingActionButton(), change the onClick argument to include uiState.value.itemDetails.id, which is the id of the selected entity. You use this id to retrieve the entity details.

```kt
FloatingActionButton(
    onClick = { navigateToEditItem(uiState.value.itemDetails.id) },
    modifier = /*...*/
)
```

In the ItemEditViewModel class, add an init block.

```kt
init {

}
```

Inside the init block, launch a coroutine with viewModelScope.launch.

```kt
import kotlinx.coroutines.launch

viewModelScope.launch { }   
```

Inside the launch block, retrieve the entity details with itemsRepository.getItemStream(itemId).

```kt
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first


init {
    viewModelScope.launch {
        itemUiState = itemsRepository.getItemStream(itemId)
            .filterNotNull()
            .first()
            .toItemUiState(true)
    }
}
```

In this launch block, you add a filter to return a flow that only contains values that are not null. With toItemUiState(), you convert the item entity to ItemUiState. You pass the actionEnabled value as true to enable the Save button.

To resolve the Unresolved reference: itemsRepository error, you need to pass in the ItemsRepository as a dependency to the view model.

Add a constructor parameter to the ItemEditViewModel class.

```kt
class ItemEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
)
```


In the AppViewModelProvider.kt file, in the ItemEditViewModel initializer, add the ItemsRepository object as an argument.

```kt
initializer {
    ItemEditViewModel(
        this.createSavedStateHandle(),
        inventoryApplication().container.itemsRepository
    )
}
```

Run the app.
Go to Item Details and tap 73b88f16638608f0.png FAB.
Notice that the fields populate with the item details.
Edit the stock quantity, or any other field, and tap Save.
Nothing happens! This is because you are not updating the entity in the app database. You fix this in the next section.


<div style="display:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/3aac7e2c9e7a04b6_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/cdccb3a8931b4a3_856.png"/>
</div>

Update the entity using Room
In this final task, you add the final pieces of the code to implement the update functionality. You define the necessary functions in the ViewModel and use them in the ItemEditScreen.

It's coding time again!

In ItemEditViewModel class, add a function called updateUiState() that takes an ItemUiState object and returns nothing. This function updates the itemUiState with new values that the user enters.

```kt
fun updateUiState(itemDetails: ItemDetails) {
    itemUiState =
        ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
}
```


In this function, you assign the passed in itemDetails to the itemUiState and update the isEntryValid value. The app enables the Save button if itemDetails is true. You set this value to true only if the input that the user enters is valid.

Go to the ItemEditScreen.kt file.
In the ItemEditScreen composable, scroll down to the ItemEntryBody() function call.
Set the onItemValueChange argument value to the new function updateUiState.

```kt
ItemEntryBody(
    itemUiState = viewModel.itemUiState,
    onItemValueChange = viewModel::updateUiState,
    onSaveClick = { },
    modifier = modifier.padding(innerPadding)
)
```

Run the app.
Go to the Edit Item screen.
Make one of the entity values empty so that it is invalid. Notice how the Save button disables automatically.


<div style="display:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/d368151eb7b198cd_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/427ff7e2bf45f6ca_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/9aa8fa86a928e1a6_856.png"/>
</div>

Go back to the ItemEditViewModel class and add a suspend function called updateItem() that takes nothing. You use this function to save the updated entity to the Room database.

```kt
suspend fun updateItem() {
}

```
Inside the getUpdatedItemEntry() function, add an if condition to validate the user input by using the function validateInput().
Make a call to the updateItem() function on the itemsRepository, passing in the itemUiState.itemDetails.toItem(). Entities that can be added to the Room database need to be of the type Item. The completed function looks like the following:

```kt
suspend fun updateItem() {
    if (validateInput(itemUiState.itemDetails)) {
        itemsRepository.updateItem(itemUiState.itemDetails.toItem())
    }
}
```

Go back to the ItemEditScreen composable.You need a coroutine scope to call the updateItem() function. Create a val called coroutineScope and set it to rememberCoroutineScope().

```kt
import androidx.compose.runtime.rememberCoroutineScope

val coroutineScope = rememberCoroutineScope()
```

In the ItemEntryBody() function call, update the onSaveClick function argument to start a coroutine in the coroutineScope.
Inside the launch block, call updateItem() on the viewModel and navigate back.

```kt
import kotlinx.coroutines.launch

onSaveClick = {
    coroutineScope.launch {
        viewModel.updateItem()
        navigateBack()
    }
},
```

The completed ItemEntryBody() function call looks like the following:

```kt
ItemEntryBody(
    itemUiState = viewModel.itemUiState,
    onItemValueChange = viewModel::updateUiState,
    onSaveClick = {
        coroutineScope.launch {
            viewModel.updateItem()
            navigateBack()
        }
    },
    modifier = modifier.padding(innerPadding)
)
```

Run the app and try editing inventory items. You are now able to edit any item in the Inventory app database.

<div style="display:flex">
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/6ed9dac5d3cafeda_856.png"/>
    <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-update-data-room/img/476f37623617d192_856.png"/>
</div>

Congratulations on creating your first app that uses Room to manage the database!


# 9. Solution code
The solution code for this codelab is in the GitHub repo and the branch shown below:

Solution Code URL:

https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app