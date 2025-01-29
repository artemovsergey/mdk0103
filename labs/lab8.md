# Use function types and lambda expressions in Kotlin

1. Introduction
This codelab teaches you about function types, how to use function types, and syntax specific to lambda expressions.

In Kotlin, functions are considered first-class constructs. This means that functions can be treated as a data type. You can store functions in variables, pass them to other functions as arguments, and return them from other functions.

Like other data types that you can express with literal values—such as an Int type of a 10 value and a String type of a "Hello" value—you can also declare function literals, which are called lambda expressions or lambdas for short. You use lambda expressions extensively in Android development and more generally in Kotlin programming.

Prerequisites
Familiarity with Kotlin programming, including functions, if/else statements, and nullability
What you'll learn
How to define a function with lambda syntax.
How to store functions in variables.
How to pass functions as arguments to other functions.
How to return functions from other functions.
How to use nullable function types.
How to make lambda expressions more concise.
What a higher-order function is.
How to use the repeat() function.
What you'll need
A web browser with access to Kotlin Playground

# 2. Watch the code-along video (Optional)
If you'd like to watch one of the course instructors complete the codelab, play the below video.

It's recommended to expand the video to full screen (with this icon This symbol shows 4 corners on a square highlighted, to indicate full screen mode. in the corner of the video) so you can see the Kotlin Playground and the code more clearly.

This step is optional. You can also skip the video and start the codelab instructions right away.


# 3. Store a function in a variable
So far, you learned how to declare functions with the fun keyword. A function declared with the fun keyword can be called, which causes the code in the function body to execute.

As a first-class construct, functions are also data types, so you can store functions in variables, pass them to functions, and return them from functions. Perhaps you want the ability to change the behavior of a piece of your app at runtime or nest composable functions to build layouts like you did in previous codelabs. All this is made possible by lambda expressions.

You can see this in action with some trick-or-treating, which refers to the Halloween tradition in many countries during which children dressed in costumes go from door to door and ask, "Trick or treat," usually in exchange for candy.

Store a function in a variable:

Navigate to Kotlin Playground.
After the main() function, define a trick() function with no parameters and no return value that prints "No treats!". The syntax is the same as that of other functions that you saw in previous codelabs.

```kt
fun main() {
    
}

fun trick() {
    println("No treats!")
}
```

In the body of the main() function, create a variable called trickFunction and set it equal to trick. You don't include the parentheses after trick because you want to store the function in a variable, not call the function.

```kt
fun main() {
    val trickFunction = trick
}

fun trick() {
    println("No treats!")
}
```

Run your code. It produces an error because the Kotlin compiler recognizes trick as the name of the trick() function, but expects you to call the function, rather than assign it to a variable.

``` 
Function invocation 'trick()' expected
```

You tried to store trick in the trickFunction variable. However, to refer to a function as a value, you need to use the function reference operator (::). The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/a9a9bfa88485ec67_856.png)

To refer to the function as a value, reassign trickFunction to ::trick.

```kt
fun main() {
    val trickFunction = ::trick
}

fun trick() {
    println("No treats!")
}
```

Run your code to verify that there are no more errors. You see a warning that trickFunction is never used, but that's fixed in the next section.

## Redefine the function with a lambda expression
Lambda expressions provide a concise syntax to define a function without the fun keyword. You can store a lambda expression directly in a variable without a function reference on another function.

Before the assignment operator (=), you add the val or var keyword followed by the name of the variable, which is what you use when you call the function. After the assignment operator (=) is the lambda expression, which consists of a pair of curly braces that form the function body. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/5e25af769cc200bc_856.png)

When you define a function with a lambda expression, you have a variable that refers to the function. You can also assign its value to other variables like any other type and call the function with the new variable's name.

Update the code to use a lambda expression:

Rewrite the trick() function with a lambda expression. The name trick now refers to the name of a variable. The function body in the curly braces is now a lambda expression.

```kt
fun main() {
    val trickFunction = ::trick
}

val trick = {
    println("No treats!")
}
```

In the main() function, remove the function reference operator (::) because trick now refers to a variable, rather than a function name.

```kt
fun main() {
    val trickFunction = trick
}

val trick = {
    println("No treats!")
}
```

Run your code. There are no errors and you can refer to the trick() function without the function reference operator (::). There's no output because you still haven't called the function.
In the main() function, call the trick() function, but this time include the parentheses like you'd do when you call any other function.

```kt
fun main() {
    val trickFunction = trick
    trick()
}

val trick = {
    println("No treats!")
}
```

Run your code. The body of the lambda expression is executed.

```kt
No treats!
```

In the main() function, call the trickFunction variable as if it were a function.

```kt
fun main() {
    val trickFunction = trick
    trick()
    trickFunction()
}

val trick = {
    println("No treats!")
}
```

Run your code. The function is called twice, once for the trick()function call and a second time for the trickFunction() function call.

```kt
No treats!
No treats!
```

With lambda expressions, you can create variables that store functions, call these variables like functions, and store them in other variables that you can call like functions.


# 4. Use functions as a data type

You learned in a previous codelab that Kotlin has type inference. When you declare a variable, you often don't need to explicitly specify the type. In the previous example, the Kotlin compiler was able to infer that the value of trick was a function. However, if you want to specify the type of a function parameter or a return type, you need to know the syntax for expressing function types. Function types consist of a set of parentheses that contain an optional parameter list, the -> symbol, and a return type. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/5608ac5e471b424b_856.png)


The data type of the trick variable that you declared earlier would be () -> Unit. The parentheses are empty because the function doesn't have any parameters. The return type is Unit because the function doesn't return anything. If you had a function that took two Int parameters and returned an Int, its data type would be (Int, Int) -> Int.

Declare another function with a lambda expression that explicitly specifies the function's type:

After the trick variable, declare a variable called treat equal to a lambda expression with a body that prints "Have a treat!".

```kt
val trick = {
    println("No treats!")
}

val treat = {
    println("Have a treat!")
}
```

Specify the treat variable's data type as () -> Unit.

```kt
val treat: () -> Unit = {
    println("Have a treat!")
}
```

In the main() function, call the treat() function.

```kt
fun main() {
    val trickFunction = trick
    trick()
    trickFunction()
    treat()
}
```

Run the code. The treat() function behaves like the trick() function. Both variables have the same data type even though only the treat variable declares it explicitly.

```
No treats!
No treats!
Have a treat!
```

## Use a function as a return type
A function is a data type, so you can use it like any other data type. You can even return functions from other functions. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/f16dd6ca0c1588f5_856.png)

Create a function that returns a function.

Delete the code from the main() function.

```kt
fun main() {
    
}
```

After the main() function, define a trickOrTreat() function that accepts an isTrick parameter of type Boolean.

```kt
fun main() {
    
}

fun trickOrTreat(isTrick: Boolean): () -> Unit {
}

val trick = {
    println("No treats!")
}

val treat = {
    println("Have a treat!")
}
```

In the body of the trickOrTreat() function, add an if statement that returns the trick() function if isTrick is true and returns the treat() function if isTrick is false.

```kt
fun trickOrTreat(isTrick: Boolean): () -> Unit {
    if (isTrick) {
        return trick
    } else {
        return treat
    }
}
```

In the main() function, create a variable called treatFunction and assign it to the result of calling trickOrTreat(), passing in false for the isTrick parameter. Then, create a second variable, called trickFunction, and assign it to the result of calling trickOrTreat(), this time passing in true for the isTrick parameter.

```kt
fun main() {
    val treatFunction = trickOrTreat(false)
    val trickFunction = trickOrTreat(true)
}
```

Call the treatFunction() and then call the trickFunction() on the next line.

```kt
fun main() {
    val treatFunction = trickOrTreat(false)
    val trickFunction = trickOrTreat(true)
    treatFunction()
    trickFunction()
}
```

Run your code. You should see the output for each function. Even though you didn't call the trick() or treat() functions directly, you could still call them because you stored the return values from each time you called the trickOrTreat() function, and called the functions with the trickFunction and treatFunction variables.

```
Have a treat!
No treats!
```

Now you know how functions can return other functions. You can also pass a function as an argument to another function. Perhaps you want to provide some custom behavior to the trickOrTreat() function to do something other than return either of the two strings. A function that takes another function as an argument lets you pass in a different function each time that it's called.

## Pass a function to another function as an argument
In some parts of the world that celebrate Halloween, children receive spare change instead of candy or they receive both. You'll modify your trickOrTreat() function to allow an additional treat represented by a function to be provided as an argument.

The function that trickOrTreat() uses as a parameter also needs to take a parameter of its own. When declaring function types, the parameters aren't labeled. You only need to specify the data types of each parameter, separated by a comma. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/8372d3b83d539fac_856.png)

When you write a lambda expression for a function that takes a parameter, the parameters are given names in the order that they occur. Parameter names are listed after the opening curly braces and each name is separated by a comma. An arrow (->) separates the parameter names from the function body. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/938d2adf25172873_856.png)


Update the trickOrTreat() function to take a function as a parameter:

After the isTrick parameter, add an extraTreat parameter of type (Int) -> String.

```kt
fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int) -> String)?): () -> Unit {
```

Modify the call to the extraTreat() function to use an if statement to only call the function if it's non-null. The trickOrTreat() function should now look like this code snippet:

```kt
fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int) -> String)?): () -> Unit {
    if (isTrick) {
        return trick
    } else {
        if (extraTreat != null) {
            println(extraTreat(5))
        }
        return treat
    }
}
```

Remove the cupcake() function and then replace the cupcake argument with null in the second call to the trickOrTreat() function.

```kt
fun main() {
    val coins: (Int) -> String = { quantity ->
        "$quantity quarters"
    }

    val treatFunction = trickOrTreat(false, coins)
    val trickFunction = trickOrTreat(true, null)
    treatFunction()
    trickFunction()
}
```

Run your code. The output should be unchanged. Now that you can declare function types as nullable, you no longer need to pass in a function for the extraTreat parameter.

```
5 quarters
Have a treat!
No treats!
```

# 5. Write lambda expressions with shorthand syntax

Lambda expressions provide a variety of ways to make your code more concise. You explore a few of them in this section because most of the lambda expressions that you encounter and write are written with shorthand syntax.

## Omit parameter name
When you wrote the coins() function, you explicitly declared the name quantity for the function's Int parameter. However, as you saw with the cupcake() function, you can omit the parameter name entirely. When a function has a single parameter and you don't provide a name, Kotlin implicitly assigns it the it name, so you can omit the parameter name and -> symbol, which makes your lambda expressions more concise. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/332ea7bade5062d6_856.png)

Update the coins() function to use the shorthand syntax for parameters:

In the coins() function, remove the quantity parameter name and -> symbol.

```kt
val coins: (Int) -> String = {
    "$quantity quarters"
}
```

Change the "$quantity quarters" string template to refer to the single parameter using $it.

```kt
val coins: (Int) -> String = {
    "$it quarters"
}
```

Run your code. Kotlin recognizes the it parameter name of the Int parameter and still prints the number of quarters.

```kt
5 quarters
Have a treat!
No treats!
```

## Pass a lambda expression directly into a function
The coins() function is currently only used in one place. What if you could simply pass a lambda expression directly into the trickOrTreat() function without the need to first create a variable?

Lambda expressions are simply function literals, just like 0 is an integer literal or "Hello" is a string literal. You can pass a lambda expression directly into a function call. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/39dc1086e2471ffc_856.png)

Modify the code so that you can remove the coins variable:

Move the lambda expression so that it's passed directly into the call to the trickOrTreat() function. You can also condense the lambda expression to a single line.

```kt
fun main() {
    val coins: (Int) -> String = {
        "$it quarters"
    }
    val treatFunction = trickOrTreat(false, { "$it quarters" })
    val trickFunction = trickOrTreat(true, null)
    treatFunction()
    trickFunction()
}
```

Remove the coins variable because it's no longer being used.

```kt
fun main() {
    val treatFunction = trickOrTreat(false, { "$it quarters" })
    val trickFunction = trickOrTreat(true, null)
    treatFunction()
    trickFunction()
}
```

Run the code. It still compiles and runs as expected.

```
5 quarters
Have a treat!
No treats!
```

## Use trailing lambda syntax
You can use another shorthand option to write lambdas when a function type is the last parameter of a function. If so, you can place the lambda expression after the closing parenthesis to call the function. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/3ee3176d612b54_856.png)

This makes your code more readable because it separates the lambda expression from the other parameters, but doesn't change what the code does.

Update the code to use trailing lambda syntax:

In the treatFunction variable, move the lambda expression {"$it quarters"} after the closing parenthesis in the call to trickOrTreat().

```kt
val treatFunction = trickOrTreat(false) { "$it quarters" }
```

Run your code. Everything still works!

```kt
5 quarters
Have a treat!
No treats!
```

> Note: The composable functions that you used to declare your UI take functions as parameters and are typically called using trailing lambda syntax.

# 6. Use the repeat() function
When a function returns a function or takes a function as an argument, it's called a higher-order function. The trickOrTreat() function is an example of a higher-order function because it takes a function of ((Int) -> String)? type as a parameter and returns a function of () -> Unit type. Kotlin provides several useful higher-order functions, which you can take advantage of with your newfound knowledge of lambdas.

The repeat() function is one such higher-order function. The repeat() function is a concise way to express a for loop with functions. You use this and other higher-order functions frequently in later units. The repeat() function has this function signature:

```kt
repeat(times: Int, action: (Int) -> Unit)
```

The times parameter is the number of times that the action should happen. The action parameter is a function that takes a single Int parameter and returns a Unit type. The action function's Int parameter is the number of times that the action has executed so far, such as a 0 argument for the first iteration or a 1 argument for the second iteration. You can use the repeat() function to repeat code a specified number of times, similar to a for loop. The syntax is illustrated in this image:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-function-types-and-lambda/img/519a2e0f5d02687_856.png)

Instead of calling the trickFunction() function only once, you can call it multiple times with the repeat() function.

Update your trick-or-treating code to see the repeat() function in action:

In the main() function, call the repeat() function in-between the calls to treatFunction() and trickFunction(). Pass in 4 for the times parameter and use trailing lambda syntax for the action function. You don't need to provide a name for the lambda expression's Int parameter.

```kt
fun main() {
    val treatFunction = trickOrTreat(false) { "$it quarters" }
    val trickFunction = trickOrTreat(true, null)
    treatFunction()
    trickFunction()
    repeat(4) {
        
    }
}
```

Move the call to the treatFunction() function into the repeat() function's lambda expression.

```kt
fun main() {
    val treatFunction = trickOrTreat(false) { "$it quarters" }
    val trickFunction = trickOrTreat(true, null)
    repeat(4) {
        treatFunction()
    }
    trickFunction()
}
```

Run your code. The "Have a treat" string should print four times.

```kt
5 quarters
Have a treat!
Have a treat!
Have a treat!
Have a treat!
No treats!
```


# 7. Conclusion
Congratulations! You learned the basics of function types and lambda expressions. Familiarity with these concepts helps you as you learn more about the Kotlin language. The use of function types, higher-order functions, and shorthand syntax also makes your code more concise and easier to read.

## Summary
Functions in Kotlin are first-class constructs and can be treated like data types.
Lambda expressions provide a shorthand syntax to write functions.
You can pass function types into other functions.
You can return a function type from another function.
A lambda expression returns the value of the last expression.
If a parameter label is omitted in a lambda expression with a single parameter, it's referred to with the it identifier.
Lambdas can be written inline without a variable name.
If a function's last parameter is a function type, you can use trailing lambda syntax to move the lambda expression after the last parenthesis when you call a function.
Higher-order functions are functions that take other functions as parameters or return a function.
The repeat() function is a higher-order function that works similarly to a for loop.