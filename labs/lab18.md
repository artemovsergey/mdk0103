# Use collections in Kotlin

# 1. Introduction
In many apps, you've probably seen data displayed as a list: contacts, settings, search results, etc.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/46df844b170f4272_856.png)

However, in the code you've written so far, you've mostly worked with data consisting of a single value, like a number or piece of text displayed on the screen. To build apps involving arbitrary amounts of data, you need to learn how to use collections.

Collection types (sometimes called data structures) let you store multiple values, typically of the same data type, in an organized way. A collection might be an ordered list, a grouping of unique values, or a mapping of values of one data type to values of another. The ability to effectively use collections enables you to implement common features of Android apps, such as scrolling lists, as well as solve a variety of real-life programming problems that involve arbitrary amounts of data.

This codelab discusses how to work with multiple values in your code, and introduces a variety of data structures, including arrays, lists, sets, and maps.

Prerequisites
Familiarity with object-oriented programming in Kotlin, including classes, interfaces, and generics.
What you'll learn
How to create and modify arrays.
How to use List and MutableList.
How to use Set and MutableSet.
How to use Map and MutableMap.
What you'll need
A web browser with access to the Kotlin Playground.


# 2. Arrays in Kotlin
What is an array?
An array is the simplest way to group an arbitrary number of values in your programs.

Like a grouping of solar panels is called a solar array, or how learning Kotlin opens up an array of possibilities for your programming career, an Array represents more than one value. Specifically, an array is a sequence of values that all have the same data type.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/33986e4256650b8b_856.png)

An array contains multiple values called elements, or sometimes, items.
The elements in an array are ordered and are accessed with an index.
What's an index? An index is a whole number that corresponds to an element in the array. An index tells the distance of an item from the starting element in an array. This is called zero-indexing. The first element of the array is at index 0, the second element is at index 1, because it's one place from the first element, and so on.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/bb77ec7506ac1a26_856.png)

In the device's memory, elements in the array are stored next to each other. While the underlying details are beyond the scope of this codelab, this has two important implications:

Accessing an array element by its index is fast. You can access any random element of an array by its index and expect it to take about the same amount of time to access any other random element. This is why it's said that arrays have random access.
An array has a fixed size. This means that you can't add elements to an array beyond this size. Trying to access the element at index 100 in a 100 element array will throw an exception because the highest index is 99 (remember that the first index is 0, not 1). You can, however, modify the values at indexes in the array.

> Note: In this codelab, memory refers to the short-term Random Access Memory (RAM) of the device, not the long-term persistent storage. It's called "Random Access" because it allows for fast access to any arbitrary location in memory.

To declare an array in code, you use the arrayOf() function.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/69e283b32d35f799_856.png)

The arrayOf() function takes the array elements as parameters, and returns an array of the type matching the parameters passed in. This might look a little different from other functions you've seen because arrayOf() has a varying number of parameters. If you pass in two arguments to arrayOf(), the resulting array contains two elements, indexed 0 and 1. If you pass in three arguments, the resulting array will have 3 elements, indexed 0 through 2.

Let's see arrays in action with a small exploration of the solar system!

Navigate to the Kotlin Playground.
In main(), create a rockPlanets variable. Call arrayOf(), passing in the type String, along with four strings—one for each of the rock planets in the solar system.

```kt
val rockPlanets = arrayOf<String>("Mercury", "Venus", "Earth", "Mars")
```

Because Kotlin uses type inference, you can omit the type name when calling arrayOf(). Below the rockPlanets variable, add another variable gasPlanets, without passing a type into the angle brackets.

```kt
val gasPlanets = arrayOf("Jupiter", "Saturn", "Uranus", "Neptune")
```

You can do some cool things with arrays. For example, just like the numeric types Int or Double, you can add two arrays together. Create a new variable called solarSystem, and set it equal to the result of rockPlanets and gasPlanets, using the plus (+) operator. The result is a new array containing all the elements of the rockPlanets array and the elements of the gasPlanets array.

```kt
val solarSystem = rockPlanets + gasPlanets
```

Run your program to verify that it works. You shouldn't see any output yet.

## Access an element in an array
You can access an element of an array by its index.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/1f8398eaee30c7b0_856.png)

This is called subscript syntax. It consists of three parts:

The name of the array.
An opening ([) and closing (]) square bracket.
The index of the array element in the square brackets.
Let's access the elements of the solarSystem array by their indices.

In main(), access and print each element of the solarSystem array. Note how the first index is 0 and the last index is 7.

```
println(solarSystem[0])
println(solarSystem[1])
println(solarSystem[2])
println(solarSystem[3])
println(solarSystem[4])
println(solarSystem[5])
println(solarSystem[6])
println(solarSystem[7])
```

Run your program. The elements are in the same order you listed them when calling arrayOf().

```
Mercury
Venus
Earth
Mars
Jupiter
Saturn
Uranus
Neptune
```

You can also set the value of an array element by its index.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/9469e321ed79c074_856.png)

Accessing the index is the same as before—the name of the array, followed by an opening and closing square bracket containing the index. This is then followed by the assignment operator (=) and a new value.

Let's practice modifying values on the solarSystem array.

Let's give Mars a new name for its future human settlers. Access the element at index 3 and set it equal to "Little Earth".

```kt
solarSystem[3] = "Little Earth"
```

Print the element at index 3.

```kt
println(solarSystem[3])
```

Run your program. The fourth element of the array (at index 3) is updated.

```
...
Little Earth
```

Now let's say scientists made a discovery that there's a ninth planet beyond Neptune called Pluto. Earlier, we mentioned that you can't resize an array. What would happen if you tried? Let's try adding Pluto to the solarSystem array. Add Pluto at index 8 as this is the 9th element in the array.

```
solarSystem[8] = "Pluto"
```

Run your code. It throws an ArrayIndexOutOfBounds exception. Because the array already had 8 elements, as expected, you can't simply add a ninth element.

```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 8 out of bounds for length 8
```

Remove the addition of Pluto to the array.


Code to remove
```kt
solarSystem[8] = "Pluto"
```

If you want to make an array larger than it already is, you need to create a new array. Define a new variable called newSolarSystem as shown. This array can store nine elements, instead of eight.

```kt
val newSolarSystem = arrayOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto")
```

Now try to print the element at index 8.

```kt
println(newSolarSystem[8])
```

Run your code and observe that it runs without any exceptions.

```kt
...
Pluto
```

Great job! With your knowledge of arrays, you can do almost anything with collections.

Wait, not so fast! While arrays are one of the fundamental aspects of programming, using an array for tasks that require adding and removing elements, uniqueness in a collection, or mapping objects to other objects isn't exactly simple or straightforward, and your app's code would quickly become a mess.

This is why most programming languages, including Kotlin, implement special collection types to handle situations that commonly occur in real-world apps. In the following sections, you'll learn about three common collections: List, Set, and Map. You'll also learn the common properties and methods, and the situations in which to use these collection types.


# 3. Lists
A list is an ordered, resizable collection, typically implemented as a resizable array. When the array is filled to capacity and you try to insert a new element, the array is copied to a new bigger array.


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/a4970d42cd1d2b66_856.png)

With a list, you can also insert new elements between other elements at a specific index.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/a678d6a41e6afd46_856.png)

This is how lists are able to add and remove elements. In most cases, it takes the same amount of time to add any element to a list, regardless of how many elements are in the list. Every once in a while, if adding a new element would put the array above its defined size, the array elements might have to move to make room for new elements. Lists do all of this for you, but behind the scenes, it's just an array that gets swapped out for a new array when needed.


## List and MutableList
The collection types you'll encounter in Kotlin implement one or more interfaces. As you learned in the Generics, objects, and extensions codelab earlier in this unit, interfaces provide a standard set of properties and methods for a class to implement. A class that implements the List interface provides implementations for all the properties and methods of the List interface. The same is true for MutableList.

So what do List and MutableList do?

List is an interface that defines properties and methods related to a read-only ordered collection of items.
MutableList extends the List interface by defining methods to modify a list, such as adding and removing elements.
These interfaces only specify the properties and methods of a List and/or MutableList. It's up to the class that extends them to determine how each property and method is implemented. The array-based implementation described above is what you'll use most, if not all of the time, but Kotlin allows other classes to extend List and MutableList.

## The listOf() function
Like arrayOf(), the listOf() function takes the items as parameters, but returns a List rather than an array.

Remove the existing code from main().
In main(), create a List of planets called solarSystem by calling listOf().

```kt
fun main() {
    val solarSystem = listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
}
```

List has a size property to get the number of elements in the list. Print the size of the solarSystem list.

```kt
println(solarSystem.size) 
```

Run your code. The size of the list should be 8.

```
8
```

# Access elements from a list
Like an array, you can access an element at a specific index from a List using subscript syntax. You can do the same using the get() method. Subscript syntax and the get() method take an Int as a parameter and return the element at that index. Like Array, ArrayList is zero-indexed, so for example, the fourth element would be at index 3.

Print the planet at index 2 using subscript syntax.

```kt
println(solarSystem[2])
```

Print the element at index 3 by calling get() on the solarSystem list.

```kt
println(solarSystem.get(3))
```

Run your code. The element at index 2 is "Earth" and the element at index 3 is "Mars".

```
...
Earth
Mars
```

In addition to getting an element by its index, you can also search for the index of a specific element using the indexOf() method. The indexOf() method searches the list for a given element (passed in as an argument), and returns the index of the first occurrence of that element. If the element doesn't occur in the list, it returns -1.

Print the result of calling indexOf() on the solarSystem list, passing in "Earth".

```kt
println(solarSystem.indexOf("Earth"))
```

Call indexOf(), passing in "Pluto", and print the result.

```kt
println(solarSystem.indexOf("Pluto"))
```

Run your code. An element matches "Earth", so the index, 2, is printed. There isn't an element that matches "Pluto", so -1 is printed.

```
...
2
-1
```

## Iterate over list elements using a for loop
When you learned about function types and lambda expressions, you saw how you could use the repeat() function to execute code multiple times.

A common task in programming is to perform a task once for each element in a list. Kotlin includes a feature called a for loop to accomplish this with a concise and readable syntax. You'll often see this referred to as looping through a list or iterating over a list.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/f11277e6af4459bb_856.png)

To loop through a list, use the for keyword, followed by a pair of opening and closing parentheses. Within the parentheses, include a variable name, followed by the in keyword, followed by the name of the collection. After the closing parenthesis comes a pair of opening and closing curly braces, where you include the code you want executed for each element in the collection. This is known as the body of the loop. Each time this code executes is known as an iteration.

The variable before the in keyword isn't declared with val or var—it's assumed to be get-only. You can name it anything you want. If a list is given a plural name, like planets, it's common to name the variable the singular form, such as planet. It's also common to name the variable item or element.

This will be used as a temporary variable corresponding to the current element in the collection—the element at index 0 for the first iteration, element at index 1 for the second iteration, and so on, and can be accessed within the curly braces.

To see this in action, you'll print out each planet name on a separate line using a for loop.

In main(), below the most recent call to println(), add a for loop. Within the parentheses, name the variable planet, and loop through the solarSystem list.

```kt
for (planet in solarSystem) {
}
```

Within the curly braces, print the value of planet using println().

```kt
for (planet in solarSystem) {
    println(planet)
}
```

Run your code. The code within the body of the loop is executed for each item in the collection.

```
...
Mercury
Venus
Earth
Mars
Jupiter
Saturn
Uranus
Neptune
```

## Add elements to a list
The ability to add, remove, and update elements in a collection is exclusive to classes that implement the MutableList interface. If you were keeping track of newly discovered planets, you'd likely want the ability to frequently add elements to a list. You need to specifically call the mutableListOf() function, instead of listOf(), when creating a list you wish to add and remove elements from.

There are two versions of the add() function:

The first add() function has a single parameter of the type of element in the list and adds it to the end of the list.
The other version of add() has two parameters. The first parameter corresponds to an index at which the new element should be inserted. The second parameter is the element being added to the list.
Let's see both in action.

Change the initialization of solarSystem to call mutableListOf() instead of listOf(). You can now call methods defined in MutableList.

```kt
val solarSystem = mutableListOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
```

Again, we might want to classify Pluto as a planet. Call the add() method on solarSystem, passing in "Pluto" as the single argument.

```kt
solarSystem.add("Pluto")
```
Some scientists theorize a planet called Theia used to exist before colliding with Earth and forming the Moon. Insert "Theia" at index 3, between "Earth" and "Mars".

```kt
solarSystem.add(3, "Theia")
```

## Update elements at a specific index
You can update existing elements with subscript syntax:

Update the value at index 3 to "Future Moon".

```kt
solarSystem[3] = "Future Moon"
```

Print the value at indexes 3 and 9 using subscript syntax.


```kt
println(solarSystem[3])
println(solarSystem[9])
```

Run your code to verify the output.

```
Future Moon
Pluto
```

## Remove elements from a list
Elements are removed using the remove() or removeAt() method. You can either remove an element by passing it into the remove() method or by its index using removeAt().

Let's see both methods to remove an element in action.

Call removeAt() on solarSystem, passing in 9 for the index. This should remove "Pluto" from the list.

```kt
solarSystem.removeAt(9)
```

Call remove() on solarSystem, passing in "Future Moon" as the element to remove. This should search the list, and if a matching element is found, it will be removed.

```kt
solarSystem.remove("Future Moon")
```

List provides the contains() method that returns a Boolean if an element exists in a list. Print the result of calling contains() for "Pluto".

```kt
println(solarSystem.contains("Pluto"))
```

An even more concise syntax is to use the in operator. You can check if an element is in a list using the element, the in operator, and the collection. Use the in operator to check if solarSystem contains "Future Moon".

```kt
println("Future Moon" in solarSystem)
```

Run your code. Both statements should print false.

```
...
false
false
```

# 4. Sets
A set is a collection that does not have a specific order and does not allow duplicate values.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/9de9d777e6b1d265_856.png)

How is a collection like this possible? The secret is a hash code. A hash code is an Int produced by the hashCode() method of any Kotlin class. It can be thought of as a semi-unique identifier for a Kotlin object. A small change to the object, such as adding one character to a String, results in a vastly different hash value. While it's possible for two objects to have the same hash code (called a hash collision), the hashCode() function ensures some degree of uniqueness, where most of the time, two different values each have a unique hash code.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/84842b78e78f2f58_856.png)

> Note: A set uses hash codes as array indices. Of course, there can be about 4 billion different hash codes, so a Set isn't just one giant array. Instead, you can think of a Set as an array of lists.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/a44f3d05140a19bd_856.png)


The outer array—the numbers outlined in blue on the left—each correspond to a range (also known as a bucket) of possible hash codes. Each inner list—shaded in green on the right—represents the individual items in the set. Since hash collisions are relatively uncommon, even when the potential indices are limited, the inner lists at each array index will only have one or two items each, unless tens or hundreds of thousands of elements are added.

Sets have two important properties:

Searching for a specific element in a set is fast—compared with lists—especially for large collections. While the indexOf() of a List requires checking each element from the beginning until a match is found, on average, it takes the same amount of time to check if an element is in a set, whether it's the first element or the hundred thousandth.
Sets tend to use more memory than lists for the same amount of data, since more array indices are often needed than the data in the set.

> Note: Contrary to popular belief, the time it takes to check if a set contains an element is not fixed, and does in fact, depend on the amount of data in the set. However, as there will usually be few hash collisions, the number of elements that need to be checked is still orders of magnitude smaller than searching for an item in a list.

The benefit of sets is ensuring uniqueness. If you were writing a program to keep track of newly discovered planets, a set provides a simple way to check if a planet has already been discovered. With large amounts of data, this is often preferable to checking if an element exists in a list, which requires iterating over all the elements.

Like List and MutableList, there's both a Set and a MutableSet. MutableSet implements Set, so any class implementing MutableSet needs to implement both.


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/691f995fde47f1ff_856.png)

## Use a MutableSet in Kotlin
We'll use a MutableSet in the example to demonstrate how to add and remove elements.

Remove the existing code from main().
Create a Set of planets called solarSystem using mutableSetOf(). This returns a MutableSet, the default implementation of which is LinkedHashSet().

```kt
val solarSystem = mutableSetOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
```

Print the size of the set using the size property.

```kt
println(solarSystem.size)
```

Like MutableList, MutableSet has an add() method. Add "Pluto" to the solarSystem set using the add() method. It only takes a single parameter for the element being added. Elements in sets don't necessarily have an order, so there's no index!

```kt
solarSystem.add("Pluto")
```

Print the size of the set after adding the element.

```kt
println(solarSystem.size)
```

The contains() function takes a single parameter and checks if the specified element is contained in the set. If so, it returns true. Otherwise, it returns false. Call contains() to check if "Pluto" is in solarSystem.

```kt
println(solarSystem.contains("Pluto"))
```

Run your code. The size has increased and contains() now returns true.

```
8
9
true
```

> Note: Alternatively, you can use the in operator to check if an element is in a collection, for example: "Pluto" in solarSystem is equivalent to solarSystem.contains("Pluto").

As mentioned before, sets can't contain duplicates. Try adding "Pluto" again.

```kt
solarSystem.add("Pluto")
```

Print the size of the set again.

```kt
println(solarSystem.size)
```

Run your code again. "Pluto" isn't added as it is already in the set. The size should not increase this time.

```
...
9
```

The remove() function takes a single parameter and removes the specified element from the set.

Use the remove() function to remove "Pluto".

```kt
solarSystem.remove("Pluto")
```

> Note: Remember that sets are an unordered collection. There's no way to remove a value from a set by its index, as sets don't have indices.

Print the size of the collection and call contains() again to check if "Pluto" is still in the set.

```kt
println(solarSystem.size)
println(solarSystem.contains("Pluto"))
```

Run your code. "Pluto" is no longer in the set and the size is now 8.

```
...
8
false
```



# 5. Map collection

A Map is a collection consisting of keys and values. It's called a map because unique keys are mapped to other values. A key and its accompanying value are often called a key-value pair.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/8571494fb4a106b6_856.png)


A map's keys are unique. A map's values, however, are not. Two different keys could map to the same value. For example, "Mercury" has 0 moons, and "Venus" has 0 moons.

Accessing a value from a map by its key is generally faster than searching through a large list, such as with indexOf().

Maps can be declared using the mapOf() or mutableMapOf() function. Maps require two generic types separated by a comma—one for the keys and another for the values.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/affc23a0e1f2b223_856.png)

A map can also use type inference if it has initial values. To populate a map with initial values, each key value pair consists of the key, followed by the to operator, followed by the value. Each pair is separated by a comma.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-collections/img/2ed99c3391c74ec4_856.png)

Let's take a closer look at how to use maps, and some useful properties and methods.

Remove the existing code from main().
Create a map called solarSystem using mutableMapOf() with initial values as shown.

```kt
val solarSystem = mutableMapOf(
    "Mercury" to 0,
    "Venus" to 0,
    "Earth" to 1,
    "Mars" to 2,
    "Jupiter" to 79,
    "Saturn" to 82,
    "Uranus" to 27,
    "Neptune" to 14
)
```

Like lists and sets, Map provides a size property, containing the number of key-value pairs. Print the size of the solarSystem map.

```
println(solarSystem.size)
```

You can use subscript syntax to set additional key-value pairs. Set the key "Pluto" to a value of 5.

```
solarSystem["Pluto"] = 5
```

Print the size again, after inserting the element.

```
println(solarSystem.size)
```

You can use subscript syntax to get a value. Print the number of moons for the key "Pluto".

```kt
println(solarSystem["Pluto"])
```

You can also access values with the get() method. Whether you use subscript syntax or call get(), it's possible that the key you pass in isn't in the map. If there isn't a key-value pair, it will return null. Print the number of moons for "Theia".

```kt
println(solarSystem.get("Theia"))
```

Run your code. The number of moons for Pluto should print. However, because Theia isn't in the map, calling get() returns null.

```
8
9
5
null
```

The remove() method removes the key-value pair with the specified key. It also returns the removed value, or null, if the specified key isn't in the map.

Print the result from calling remove() and passing in "Pluto".

```kt
solarSystem.remove("Pluto")
```

To verify that the item was removed, print the size again.

```kt
println(solarSystem.size)
```

Run your code. The size of the map is 8 after removing the entry.

```
...
8
```

Subscript syntax, or the put() method, can also modify a value for a key that already exists. Use subscript syntax to update Jupiter's moons to 78 and print the new value.

```kt
solarSystem["Jupiter"] = 78
println(solarSystem["Jupiter"])
```

Run your code. The value for the existing key, "Jupiter", is updated.

```
...
78
```

# 6. Conclusion
Congratulations! You learned about one of the most foundational data types in programming, the array, and several convenient collection types built off of arrays, including List, Set, and Map. These collection types allow you to group and organize values in your code. Arrays and lists provide fast access to elements by their index, while sets and maps use hash codes to make it easier to find elements in the collection. You'll see these collection types used frequently in future apps, and knowing how to use them will benefit you in your future programming career.

Summary
Arrays store ordered data of the same type, and have a fixed size.
Arrays are used to implement many of the other collection types.
Lists are a resizable, ordered collection.
Sets are unordered collections and cannot contain duplicates.
Maps work similarly to sets and store pairs of keys and values of the specified type.