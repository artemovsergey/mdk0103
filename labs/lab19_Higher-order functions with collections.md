# Higher-order functions with collections

1. Introduction
In the Use function types and lambda expressions in Kotlin codelab, you learned about higher-order functions, which are functions that take other functions as parameters and/or return a function, such as repeat(). Higher-order functions are especially relevant to collections as they help you perform common tasks, like sorting or filtering, with less code. Now that you have a solid foundation working with collections, it's time to revisit higher-order functions.

In this codelab, you'll learn about a variety of functions that can be used on collection types, including forEach(), map(), filter(), groupBy(), fold(), and sortedBy(). In the process, you'll get additional practice working with lambda expressions.

Prerequisites
Familiarity with function types and lambda expressions.
Familiarity with trailing lambda syntax, such as with the repeat() function.
Knowledge of various collection types in Kotlin, such as List.
What you'll learn
How to embed lambda expressions into strings.
How to use various higher-order functions with the List collection, including forEach(), map(), filter(), groupBy(), fold(), and sortedBy().
What you'll need
A web browser with access to the Kotlin Playground.

# 2. forEach() and string templates with lambdas
Starter code
In the following examples, you'll take a List representing a bakery's cookie menu (how delicious!), and use higher-order functions to format the menu in different ways.

Start by setting up the initial code.

Navigate to the Kotlin Playground.
Above the main() function, add the Cookie class. Each instance of Cookie represents an item on the menu, with a name, price, and other information about the cookie.

```kt
class Cookie(
    val name: String,
    val softBaked: Boolean,
    val hasFilling: Boolean,
    val price: Double
)

fun main() {

}
```

Below the Cookie class, outside of main(), create a list of cookies as shown. The type is inferred to be ```List<Cookie>```.

```kt
class Cookie(
    val name: String,
    val softBaked: Boolean,
    val hasFilling: Boolean,
    val price: Double
)

val cookies = listOf(
    Cookie(
        name = "Chocolate Chip",
        softBaked = false,
        hasFilling = false,
        price = 1.69
    ),
    Cookie(
        name = "Banana Walnut", 
        softBaked = true, 
        hasFilling = false, 
        price = 1.49
    ),
    Cookie(
        name = "Vanilla Creme",
        softBaked = false,
        hasFilling = true,
        price = 1.59
    ),
    Cookie(
        name = "Chocolate Peanut Butter",
        softBaked = false,
        hasFilling = true,
        price = 1.49
    ),
    Cookie(
        name = "Snickerdoodle",
        softBaked = true,
        hasFilling = false,
        price = 1.39
    ),
    Cookie(
        name = "Blueberry Tart",
        softBaked = true,
        hasFilling = true,
        price = 1.79
    ),
    Cookie(
        name = "Sugar and Sprinkles",
        softBaked = false,
        hasFilling = false,
        price = 1.39
    )
)

fun main() {

}
```

## Loop over a list with forEach()
The first higher-order function that you learn about is the forEach()function. The forEach() function executes the function passed as a parameter once for each item in the collection. This works similarly to the repeat() function, or a for loop. The lambda is executed for the first element, then the second element, and so on, until it's executed for each element in the collection. The method signature is as follows:

```kt
forEach(action: (T) -> Unit)
```

forEach() takes a single action parameter—a function of type (T) -> Unit.

T corresponds to whatever data type the collection contains. Because the lambda takes a single parameter, you can omit the name and refer to the parameter with it.

Use the forEach() function to print the items in the cookies list.

In main(), call forEach() on the cookies list, using trailing lambda syntax. Because the trailing lambda is the only argument, you can omit the parentheses when calling the function.

```kt
fun main() {
    cookies.forEach {
        
    }
}
```

In the lambda body, add a println() statement that prints it.

```kt
fun main() {
    cookies.forEach {
        println("Menu item: $it")
    }
}
```

Run your code and observe the output. All that prints is the name of the type (Cookie), and a unique identifier for the object, but not the contents of the object.

```
Menu item: Cookie@5a10411
Menu item: Cookie@68de145
Menu item: Cookie@27fa135a
Menu item: Cookie@46f7f36a
Menu item: Cookie@421faab1
Menu item: Cookie@2b71fc7e
Menu item: Cookie@5ce65a89
```

## Embed expressions in strings
When you were first introduced to string templates, you saw how the dollar symbol ($) could be used with a variable name to insert it into a string. However, this doesn't work as expected when combined with the dot operator (.) to access properties.

In the call to forEach(), modify the lambda's body to insert $it.name into the string.

```kt
cookies.forEach {
    println("Menu item: $it.name")
}
```

Run your code. Notice that this inserts the name of the class, Cookie, and a unique identifier for the object followed by .name. The value of the name property isn't accessed.

```
Menu item: Cookie@5a10411.name
Menu item: Cookie@68de145.name
Menu item: Cookie@27fa135a.name
Menu item: Cookie@46f7f36a.name
Menu item: Cookie@421faab1.name
Menu item: Cookie@2b71fc7e.name
Menu item: Cookie@5ce65a89.name
```

To access properties and embed them in a string, you need an expression. You can make an expression part of a string template by surrounding it with curly braces.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/2c008744cee548cc_856.png)

The lambda expression is placed between the opening and closing curly braces. You can access properties, perform math operations, call functions, etc., and the return value of the lambda is inserted into the string.

Let's modify the code so that the name is inserted into the string.

Surround it.name in curly braces to make it a lambda expression.

```kt
cookies.forEach {
    println("Menu item: ${it.name}")
}
```

Run your code. The output contains the name of each Cookie.

```
Menu item: Chocolate Chip
Menu item: Banana Walnut
Menu item: Vanilla Creme
Menu item: Chocolate Peanut Butter
Menu item: Snickerdoodle
Menu item: Blueberry Tart
Menu item: Sugar and Sprinkles
```


# map()
The map() function lets you transform a collection into a new collection with the same number of elements. For example, map() could transform a List<Cookie> into a List<String> only containing the cookie's name, provided you tell the map() function how to create a String from each Cookie item.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/e0605b7b09f91717_856.png)

Let's say you are writing an app that displays an interactive menu for a bakery. When the user navigates to the screen that shows the cookie menu, they might want to see the data presented in a logical manner, such as the name followed by the price. You can create a list of strings, formatted with the relevant data (name and price), using the map() function.

Remove all the previous code from main(). Create a new variable called fullMenu, and set it equal to the result of calling map() on the cookies list.

```kt
val fullMenu = cookies.map {
    
}
```

In the lambda's body, add a string formatted to include the name and price of it.

```kt
val fullMenu = cookies.map {
    "${it.name} - $${it.price}"
}
```

> Note: There's a second $ used before the expression. The first is treated as the dollar sign character ```($)``` since it's not followed by a variable name or lambda expression.

Print the contents of fullMenu. You can do this using forEach(). The fullMenu collection returned from map() has type List<String> rather than List<Cookie>. Each Cookie in cookies corresponds to a String in fullMenu.

```kt
println("Full menu:")
fullMenu.forEach {
    println(it)
}
```

Run your code. The output matches the contents of the fullMenu list.

```
Full menu:
Chocolate Chip - $1.69
Banana Walnut - $1.49
Vanilla Creme - $1.59
Chocolate Peanut Butter - $1.49
Snickerdoodle - $1.39
Blueberry Tart - $1.79
Sugar and Sprinkles - $1.39
```

# filter()

The filter() function lets you create a subset of a collection. For example, if you had a list of numbers, you could use filter() to create a new list that only contains numbers divisible by 2.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/d4fd6be7bef37ab3_856.png)


Whereas the result of the map() function always yields a collection of the same size, filter() yields a collection of the same size or smaller than the original collection. Unlike map(), the resulting collection also has the same data type, so filtering a ```List<Cookie>``` will result in another ```List<Cookie>```.

Like map() and forEach(), filter() takes a single lambda expression as a parameter. The lambda has a single parameter representing each item in the collection and returns a Boolean value.

For each item in the collection:

If the result of the lambda expression is true, then the item is included in the new collection.
If the result is false, the item is not included in the new collection.
This is useful if you want to get a subset of data in your app. For example, let's say the bakery wants to highlight its soft-baked cookies in a separate section of the menu. You can first filter() the cookies list, before printing the items.

In main(), create a new variable called softBakedMenu, and set it to the result of calling filter() on the cookies list.

```kt
val softBakedMenu = cookies.filter {
}
```

In the lambda's body, add a boolean expression to check if the cookie's softBaked property is equal to true. Because softBaked is a Boolean itself, the lambda body only needs to contain it.softBaked.

```kt
val softBakedMenu = cookies.filter {
    it.softBaked
}
```

Print the contents of softBakedMenu using forEach().

```kt
println("Soft cookies:")
softBakedMenu.forEach {
    println("${it.name} - $${it.price}")
}
```

Run your code. The menu is printed as before, but only includes the soft-baked cookies.

```
...
Soft cookies:
Banana Walnut - $1.49
Snickerdoodle - $1.39
Blueberry Tart - $1.79
```

# groupBy()

The groupBy() function can be used to turn a list into a map, based on a function. Each unique return value of the function becomes a key in the resulting map. The values for each key are all the items in the collection that produced that unique return value.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/54e190b34d9921c0_856.png)

The data type of the keys is the same as the return type of the function passed into groupBy(). The data type of the values is a list of items from the original list.

> Note: The value doesn't have to be the same type of the list. There's another version of groupBy() that can transform the values into a different type. However, that version is not covered here.

This can be hard to conceptualize, so let's start with a simple example. Given the same list of numbers as before, group them as odd or even.

You can check if a number is odd or even by dividing it by 2 and checking if the remainder is 0 or 1. If the remainder is 0, the number is even. Otherwise, if the remainder is 1, the number is odd.

This can be achieved with the modulo operator (%). The modulo operator divides the dividend on the left side of an expression by the divisor on the right.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/4c3333da9e5ee352_856.png)

Instead of returning the result of the division, like the division operator (/), the modulo operator returns the remainder. This makes it useful for checking if a number is even or odd.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/4219eacdaca33f1d_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/4219eacdaca33f1d_856.png)


The groupBy() function is called with the following lambda expression: { it % 2 }.

The resulting map has two keys: 0 and 1. Each key has a value of type List<Int>. The list for key 0 contains all even numbers, and the list for key 1 contains all odd numbers.

A real-world use case might be a photos app that groups photos by the subject or location where they were taken. For our bakery menu, let's group the menu by whether or not a cookie is soft baked.

Use groupBy() to group the menu based on the softBaked property.

Remove the call to filter() from the previous step.
Code to remove

```kt
val softBakedMenu = cookies.filter {
    it.softBaked
}
println("Soft cookies:")
softBakedMenu.forEach {
    println("${it.name} - $${it.price}")
}
```

Call groupBy() on the cookies list, storing the result in a variable called groupedMenu.

```kt
val groupedMenu = cookies.groupBy {}
```

Pass in a lambda expression that returns it.softBaked. The return type will be ```Map<Boolean, List<Cookie>>```.

```kt
val groupedMenu = cookies.groupBy { it.softBaked }
```

Create a softBakedMenu variable containing the value of groupedMenu[true], and a crunchyMenu variable containing the value of groupedMenu[false]. Because the result of subscripting a Map is nullable, you can use the Elvis operator (?:) to return an empty list.

```kt
val softBakedMenu = groupedMenu[true] ?: listOf()
val crunchyMenu = groupedMenu[false] ?: listOf()
```

> Note: Alternatively, emptyList() creates an empty list and may be more readable.

Add code to print the menu for soft cookies, followed by the menu for crunchy cookies.

```kt
println("Soft cookies:")
softBakedMenu.forEach {
    println("${it.name} - $${it.price}")
}
println("Crunchy cookies:")
crunchyMenu.forEach {
    println("${it.name} - $${it.price}")
}
```

Run your code. Using the groupBy() function, you split the list into two, based on the value of one of the properties.

```
...
Soft cookies:
Banana Walnut - $1.49
Snickerdoodle - $1.39
Blueberry Tart - $1.79
Crunchy cookies:
Chocolate Chip - $1.69
Vanilla Creme - $1.59
Chocolate Peanut Butter - $1.49
Sugar and Sprinkles - $1.39
```

> Note: If you only need to split a list in two, an alternative is the partition() function.

# fold()

The fold() function is used to generate a single value from a collection. This is most commonly used for things like calculating a total of prices, or summing all the elements in a list to find an average.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/a9e11a1aad05cb2f_856.png)

The fold() function takes two parameters:

An initial value. The data type is inferred when calling the function (that is, an initial value of 0 is inferred to be an Int).
A lambda expression that returns a value with the same type as the initial value.
The lambda expression additionally has two parameters:

The first is known as the accumulator. It has the same data type as the initial value. Think of this as a running total. Each time the lambda expression is called, the accumulator is equal to the return value from the previous time the lambda was called.
The second is the same type as each element in the collection.
Like other functions you've seen, the lambda expression is called for each element in a collection, so you can use fold() as a concise way to sum all the elements.

Let's use fold() to calculate the total price of all the cookies.

In main(), create a new variable called totalPrice and set it equal to the result of calling fold() on the cookies list. Pass in 0.0 for the initial value. Its type is inferred to be Double.

```kt
val totalPrice = cookies.fold(0.0) {
}
```

You'll need to specify both parameters for the lambda expression. Use total for the accumulator, and cookie for the collection element. Use the arrow (->) after the parameter list.

```kt
val totalPrice = cookies.fold(0.0) {total, cookie ->
}
```

In the lambda's body, calculate the sum of total and cookie.price. This is inferred to be the return value and is passed in for total the next time the lambda is called.

```kt
val totalPrice = cookies.fold(0.0) {total, cookie ->
    total + cookie.price
}
```

Print the value of totalPrice, formatted as a string for readability.

```kt
println("Total price: $${totalPrice}")
```

Run your code. The result should be equal to the sum of the prices in the cookies list.

```kt
...
Total price: $10.83
```

> Note: fold() is sometimes called reduce(). The fold() function in Kotlin works the same as the reduce() function found in JavaScript, Swift, Python, etc. Note that Kotlin also has its own function called reduce(), where the accumulator starts with the first element in the collection, rather than an initial value passed as an argument.

> Note: Kotlin collections also have a sum() function for numeric types, as well as a higher-order sumOf() function.

# sortedBy()

When you first learned about collections, you learned that the sort() function could be used to sort the elements. However, this won't work on a collection of Cookie objects. The Cookie class has several properties and Kotlin won't know which properties (name, price, etc.) you want to sort by.

For these cases, Kotlin collections provide a sortedBy() function. sortedBy() lets you specify a lambda that returns the property you'd like to sort by. For example, if you'd like to sort by price, the lambda would return it.price. So long as the data type of the value has a natural sort order—strings are sorted alphabetically, numeric values are sorted in ascending order—it will be sorted just like a collection of that type.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-higher-order-functions/img/5fce4a067d372880_856.png)

You'll use sortedBy() to sort the list of cookies alphabetically.

In main(), after the existing code, add a new variable called alphabeticalMenu and set it equal to calling sortedBy() on the cookies list.

```kt
val alphabeticalMenu = cookies.sortedBy {
}
```

In the lambda expression, return it.name. The resulting list will still be of type ```List<Cookie>```, but sorted based on the name.

```kt
val alphabeticalMenu = cookies.sortedBy {
    it.name
}
```

Print the names of the cookies in alphabeticalMenu. You can use forEach() to print each name on a new line.

```kt
println("Alphabetical menu:")
alphabeticalMenu.forEach {
    println(it.name)
}
```

Run your code. The cookie names are printed in alphabetical order.

```
...
Alphabetical menu:
Banana Walnut
Blueberry Tart
Chocolate Chip
Chocolate Peanut Butter
Snickerdoodle
Sugar and Sprinkles
Vanilla Creme
```

> Note: Kotlin collections also have a ```sort()``` function if the data type has a natural sort order.


# Conclusion
Congratulations! You just saw several examples of how higher-order functions can be used with collections. Common operations, like sorting and filtering, can be performed in a single line of code, making your programs more concise and expressive.

## Summary
You can loop over each element in a collection using forEach().
Expressions can be inserted into strings.
map() is used to format the items in a collection, often as a collection of another data type.
filter() can generate a subset of a collection.
groupBy() splits a collection based on a function's return value.
fold() turns a collection into a single value.
sortedBy() is used to sort a collection by a specified property.