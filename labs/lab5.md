# Write conditionals in Kotlin

1. Before you begin
Conditionals are one of the most important foundations of programming. Conditionals are commands in programming languages that handle decisions. With conditionals, code is dynamic, which means that it can behave differently given a different condition.

This codelab teaches you how to use the if/else and when statements and expressions to write conditionals in Kotlin.

Prerequisites
Knowledge of Kotlin programming basics, including variables, and the println() and main() functions
What you'll learn
How to write boolean expressions.
How to write if/else statements.
How to write when statements.
How to write if/else expressions.
How to write when expressions.
How to use commas to define common behavior for multiple branches in when conditionals.
How to use the in range to define common behavior for a range of branches in when conditionals.
How to use the is keyword to write when conditional statements.
What you'll need
A web browser with access to Kotlin Playground



# 2. Use if/else statements to express conditions

In life, it's common to do things differently based on the situation that you face. For example, if the weather is cold, you wear a jacket, whereas if the weather is warm, you don't wear a jacket.


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/c3945d8ebcbf8380_856.png)

Decision-making is also a fundamental concept in programming. You write instructions about how a program should behave in a given situation so that it can act or react accordingly when the situation occurs. In Kotlin, when you want your program to perform different actions based on a condition, you can use an if/else statement. In the next section, you write an if statement.

Write if conditions with boolean expressions
Imagine that you build a program that tells drivers what they should do when they're at a traffic light. Focus on the first condition: a red traffic light. What do you do at a red traffic light? Stop!

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/974353fbafc8cad_856.png)

In Kotlin, you can express this condition with an if statement. Take a look at the anatomy of an if statement:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/f450ef5b02967486_856.png)


To use if statements, you need to use the if keyword followed by the condition that you want to evaluate. You need to express the condition with a boolean expression. Expressions combine values, variables, and operators that return a value. Boolean expressions return a boolean value.

Previously, you learned about assignment operators, such as:

```kt
val number = 1
```

The = assignment operator assigns the number variable a 1 value.

In contrast, boolean expressions are constructed with comparison operators, which compare values or variables on both sides of the equation. Take a look at a comparison operator.

```
1 == 1
```

The == comparison operator compares the values to each other. Which boolean value do you think this expression returns?

Find the boolean value of this expression:

Use Kotlin Playground to run your code.
In the function body, add a println() function and then pass it the 1 == 1 expression as an argument:

```kt
fun main() {
    println(1 == 1)
}
```

Run the program and then view the output:

```
true
```
The first 1 value is equal to the second 1 value, so the boolean expression returns a true value, which is a boolean value.

Try it
Besides the == comparison operator, there are additional comparison operators that you can use to create boolean expressions:

Less than: <
Greater than: >
Less than or equal to: <=
Greater than or equal to: >=
Not equal to: !=
Practice the use of comparison operators with simple expressions:

In the argument, replace the == comparison operator with the < comparison operator:

```kt
fun main() {
    println(1 < 1)
}
```

Run the program and then view the output:
The output returns a false value because the first 1 value isn't less than the second 1 value.

```
false
```

Repeat the first two steps with the other comparison operators and numbers.

Write a simple if statement
Now that you saw a few examples of how to write boolean expressions, you can write your first if statement. The syntax for an if statement is as follows:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/f19b3393376d45e9_856.png)

An if statement starts with the if keyword followed by a condition, which is a boolean expression inside parentheses and a set of curly braces. The body is a series of statements or expressions that you put inside a pair of curly braces after the condition. These statements or expressions only execute when the condition is met. In other words, the statements within the curly braces only execute when a boolean expression in the if branch returns a true value.

Write an if statement for the red traffic-light condition:

Inside the main() function, create a trafficLightColor variable and assign it a "Red" value:

```kt
fun main() {
    val trafficLightColor = "Red"
}
```

Add an if statement for the red traffic-light condition and then pass it a trafficLightColor == "Red" expression:

```kt
fun main() {
    val trafficLightColor = "Red"

    if (trafficLightColor == "Red") {
        
    } 
}
```

In the body of the if statement, add a println() function and then pass it a "Stop" argument:

```kt
fun main() {
    val trafficLightColor = "Red"

    if (trafficLightColor == "Red") {
        println("Stop")
    } 
}
```

In the body of the if statement, add a println() function and then pass it a "Stop" argument:

```kt
fun main() {
    val trafficLightColor = "Red"

    if (trafficLightColor == "Red") {
        println("Stop")
    } 
}
```

Run the program and then view the output:

```
Stop
```

The trafficLightColor == "Red" expression returns a true value, so the println("Stop")statement is executed, which prints the Stop message.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/6099620ed80a0b_856.png)

Add an else branch
Now you can extend the program so that it tells drivers to go when the traffic light isn't red.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/e5d85c9b3ed6a42c_856.png)

You need to add an else branch to create an if/else statement. A branch is an incomplete part of code that you can join to form statements or expressions. An else branch needs to follow an if branch.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/55b83c8dd179b45b_856.png)

After the closing curly brace of the if statement, you add the else keyword followed by a pair of curly braces. Inside the curly braces of the else statement, you can add a second body that only executes when the condition in the if branch is false.

Add an else branch to your program:

After the closing curly brace of the if statement, add the else keyword followed by another pair of curly braces:

```kt
fun main() {
    val trafficLightColor = "Red"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else {

    }
}
```

Inside the else keyword's curly braces, add a println() function and then pass it a "Go" argument:

```kt
fun main() {
    val trafficLightColor = "Red"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else {
        println("Go")
    }
}
```

Run this program and then view the output:

```kt
Stop
```

The program still behaves the same way as it did before you added the else branch, but it doesn't print a Go message.

Reassign the trafficLightColor variable to a "Green" value because you want drivers to go on green:

```kt
fun main() {
    val trafficLightColor = "Green"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else {
        println("Go")
    }
}
```

Run this program and then view the output:

```
Go
```

As you can see, now the program prints a Go message instead of a Stop message.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/4a2e560eae8ba3b0_856.png)

You reassigned the trafficLightColor variable to a "Green" value, so the trafficLightColor == "Red" expression evaluated in the if branch returns a false value because the "Green" value isn't equal to the "Red" value.

As a result, the program skips all statements in the if branch and instead executes all statements inside the else branch. This means that the println("Go") function is executed, but the println("Stop") function isn't executed.


## Add an else if branch
Typically, a traffic light also has a yellow color that tells drivers to proceed slowly. You can expand the program's decision-making process to reflect this.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/fd2497de099060b3_856.png)

You learned to write conditionals that cater to a single decision point with if/else statements that contain a single if and a single else branch. How can you handle more complex branching with multiple decision points? When you face multiple decision points, you need to create conditionals with multiple layers of conditions, which you can do when you add else if branches to if/else statements.

After the closing curly brace of the if branch, you need to add the else if keyword. Inside the parentheses of the else if keyword, you need to add a boolean expression as the condition for the else if branch followed by a body inside a pair of curly braces. The body is only executed if condition 1 fails, but condition 2 is satisfied.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/5443fb986621fe14_856.png)

The else if branch is always located after the if branch, but before the else branch. You can use multiple else if branches in a statement:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/6ac6583dafe2d642_856.png)

The if statement can also contain the if branch and else if branches without any else branch:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/f85e0566c7df174d_856.png)

Add an else if branch to your program:

After the closing curly brace of the if statement, add an else if (trafficLightColor == "Yellow") expression followed by curly braces:

```kt
fun main() {
    val trafficLightColor = "Green"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {

    } else {
        println("Go")
    }
}
```

Inside the curly braces of the else if branch, add a println() statement and then pass it a "Slow" string argument:

```kt
fun main() {
    val trafficLightColor = "Green"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else {
        println("Go")
    }
}
```

Reassign the trafficLightColor variable to a "Yellow" string value:

```kt
fun main() {
    val trafficLightColor = "Yellow"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else {
        println("Go")
    }
}
```

Run this program and then view the output:

```
Slow
```

Now the program prints a Slow message, instead of a Stop or Go message.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/8d5e322ddf1acd52_856.png)

Here's why it only prints a Slow message and not the other lines:

The trafficLightColor variable is assigned a "Yellow" value.
The "Yellow" value isn't equal to the "Red" value, so the boolean expression of the if branch (denoted as 1 in the image) returns a false value. The program skips all statements inside the if branch and doesn't print a Stop message.
Since the if branch yields a false value, the program proceeds to evaluate the boolean expression inside the else if branch.
The "Yellow" value is equal to the "Yellow" value, so the boolean expression of the else if branch (denoted as 2 in the image) returns a true value. The program executes all statements inside the else if branch and prints a Slow message.
Since the boolean expression of the else if branch returns a true value, the program skips the rest of the branches. Thus, all statements in the else branch aren't executed and the program doesn't print a Go message.
Try it
Have you noticed that the current program contains a bug?

In Unit 1, you learned about a type of bug called a compile error in which Kotlin can't compile the code due to a syntactical error in your code and the program can't run. Here, you face another type of bug called a logic error in which the program can run, but doesn't produce an output as intended.

Supposedly, you only want drivers to drive only when the traffic-light color is green. What if the traffic light is broken and turned off? Would you want the driver to drive or receive a warning that something is wrong?

Unfortunately, in the current program, if the traffic-light color is anything else other than red or yellow, the driver is still advised to go.

Fix this problem:

Reassign the trafficLightColor variable to a "Black" value to illustrate a traffic light that is turned off:

```kt
fun main() {
    val trafficLightColor = "Black"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else {
        println("Go")
    }
}
```

Run this program and then view the output:

```
Go
```

Notice that the program prints a Go message even though the trafficLightColor variable isn't assigned a "Green" value. Can you fix this program so that it reflects the correct behavior?

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/507d8fbdde8876a4_856.png)


You need to modify the program so that it prints:

A Go message only when the trafficLightColor variable is assigned a "Green" value.
An Invalid traffic-light color message when the trafficLightColor variable isn't assigned a "Red", "Yellow", or "Green" value.
Fix the else branch
The else branch is always located at the end of an if/else statement because it's a catchall branch. It automatically executes when all other conditions in the preceding branches aren't satisfied. As such, the else branch isn't suitable when you want an action to execute only when it satisfies a specific condition. In the case of the traffic light, you can use the else if branch to specify the condition for green lights.

Use the else if branch to evaluate the green traffic-light condition:

After the current else if branch, add another else if (trafficLightColor == "Green") branch:

```kt
fun main() {
    val trafficLightColor = "Black"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else if (trafficLightColor == "Green") {
        println("Go")
    }
}
```

Run this program and then view the output.
The output is empty because you don't have an else branch that executes when the previous conditions aren't satisfied.

After the last else if branch, add an else branch with a println("Invalid traffic-light color") statement inside:

```kt
fun main() {
    val trafficLightColor = "Black"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else if (trafficLightColor == "Green") {
        println("Go")
    } else {
        println("Invalid traffic-light color")
    }

}
```

Run this program and then view the output:

```
Invalid traffic-light color
```

Assign the trafficLightColor variable another value besides "Red", "Yellow", or "Green" and then rerun the program.
What's the output of the program?

It's good programming practice to have an explicit else if branch as input validation for the green color and an else branch to catch other invalid inputs. This ensures that drivers are directed to go, only when the traffic light is green. For other cases, there's an explicit message relayed that the traffic light isn't behaving as expected.


# 3. Use a when statement for multiple branches

Your trafficLightColor program looks more complex with multiple conditions, also known as branching. You may wonder whether you can simplify a program with an even larger number of branches.

In Kotlin, when you deal with multiple branches, you can use the when statement instead of the if/else statement because it improves readability, which refers to how easy it is for human readers, typically developers, to read the code. It's very important to consider readability when you write your code because it's likely that other developers need to review and modify your code throughout its lifetime. Good readability ensures that developers can correctly understand your code and don't inadvertently introduce bugs into it.

when statements are preferred when there are more than two branches to consider.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/2f7c0a1e312a2581_856.png)

A when statement accepts a single value through the parameter. The value is then evaluated against each of the conditions sequentially. The corresponding body of the first condition that's met is then executed. Each condition and body are separated by an arrow (->). Similar to if/else statements, each pair of condition and body is called a branch in when statements. Also similar to the if/else statement, you can add an else branch as your final condition in a when statement that works as a catchall branch.


Rewrite an if/else statement with a when statement
In the traffic-light program, there are already multiple branches:

Red traffic-light color
Yellow traffic-light color
Green traffic-light color
Other traffic-light color
Convert the program to use a when statement:

In the main() function, remove the if/else statement:

```kt
fun main() {
    val trafficLightColor = "Black"
}
```

Add a when statement and then pass it the trafficLightColor variable as an argument:

```kt
fun main() {
    val trafficLightColor = "Black"

    when (trafficLightColor) {
    }
}
```

In the body of the when statement, add the"Red" condition followed by an arrow and a println("Stop") body:

```kt
fun main() {
    val trafficLightColor = "Black"

    when (trafficLightColor) {
        "Red" -> println("Stop")
    }
}
```

On the next line, add the "Yellow" condition followed by an arrow and a println("Slow") body:

```kt
fun main() {
    val trafficLightColor = "Black"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow" -> println("Slow")
    }
}
```

On the next line, add the "Green" condition followed by an arrow and then a println("Go") body:

```kt
fun main() {
    val trafficLightColor = "Black"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow" -> println("Slow")
        "Green" -> println("Go")
    }
}
```

On the next line, add the else keyword followed by an arrow and then a println("Invalid traffic-light color") body:

```kt
fun main() {
    val trafficLightColor = "Black"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}
```

Reassign the trafficLightColor variable to a "Yellow" value:

```kt
fun main() {
    val trafficLightColor = "Yellow"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}
```

When you run this program, what do you think the output will be?

Run the program and then view the output:

```
Slow
```

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/7112edfc7c7b7918_856.png)

The output is a Slow message because:

The trafficLightColor variable is assigned a "Yellow" value.
The program evaluates each condition one-by-one in sequence.
The "Yellow" value isn't equal to the "Red" value, so the program skips the first body.
The "Yellow" value is equal to the "Yellow" value, so the program executes the second body and prints a Slow message.
A body was executed, so the program ignores the third and fourth branches, and leaves the when statement.

> Note: There's a variant of the when statement that doesn't take any parameters and is used as a replacement of an if/else chain. To learn more, see When expression.

Write more complex conditions in a when statement
So far you learned how to write when conditions for a single equal condition, such as when the trafficLightColor variable is assigned a "Yellow" value. Next, you learn to use the comma (,), the in keyword, and the is keyword to form more complex when conditions.

Build a program that determines whether a number between 1 and 10 is a prime number:

Open Kotlin playground in a separate window.
You return to the traffic-light program later.

Define an x variable and then assign it a 3 value:

```kt
fun main() {
    val x = 3
}
```

Add a when statement that includes multiple branches for 2,3,5 and 7 conditions and follow each with a println("x is prime number between 1 and 10.") body:

```kt
fun main() {
    val x = 3

    when (x) {
        2 -> println("x is a prime number between 1 and 10.")
        3 -> println("x is a prime number between 1 and 10.")
        5 -> println("x is a prime number between 1 and 10.")
        7 -> println("x is a prime number between 1 and 10.")
    }
}
```

Add an else branch with a println("x is not prime number between 1 and 10.") body:

```kt
fun main() {
    val x = 3

    when (x) {
        2 -> println("x is a prime number between 1 and 10.")
        3 -> println("x is a prime number between 1 and 10.")
        5 -> println("x is a prime number between 1 and 10.")
        7 -> println("x is a prime number between 1 and 10.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

Run the program and then verify that the output is as expected:

```
x is a prime number between 1 and 10.
```

Use a comma (,) for multiple conditions
The prime-number program contains a lot of repetition of println() statements. When you write a when statement, you can use a comma (,) to denote multiple conditions that correspond to the same body.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/4e778c4c4c044e51_856.png)

In the previous diagram, if either the first or second condition is fulfilled, the corresponding body is executed.

Rewrite the prime number program with this concept:

In the branch for the 2 condition, add 3, 5 and 7 separated by commas (,):

```kt
fun main() {
    val x = 3

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        3 -> println("x is a prime number between 1 and 10.")
        5 -> println("x is a prime number between 1 and 10.")
        7 -> println("x is a prime number between 1 and 10.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

Remove the individual branches for the 3, 5 and 7 conditions:

```kt
fun main() {
    val x = 3

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

Run the program and then verify that the output is as expected:

```x is a prime number between 1 and 10.```

Use the in keyword for a range of conditions
Besides the comma (,) symbol to denote multiple conditions, you can also use the in keyword and a range of values in when branches.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/400f940f363bd3c4_856.png)

To use a range of values, add a number that denotes the start of the range followed by two periods without any spaces and then close it with another number that denotes the end of the range.

When the value of the parameter is equal to any value in the range between start of the range and the end of the range, the first body executes.

In your prime-number program, can you print a message if the number is between 1 and 10, but not a prime number?

Add another branch with the in keyword:

After the first branch of the when statement, add a second branch with the in keyword followed by a 1..10 range and a println("x is a number between 1 and 10, but not a prime number.") body:

```kt
fun main() {
    val x = 3

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

Change the x variable to a 4 value:

```kt
fun main() {
    val x = 4

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

Run the program and then verify the output:

```x is a number between 1 and 10, but not a prime number.```

The program prints the message of the second branch, but not the message of the first or third branch.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/bde4eca467c105a2_856.png)

Here's how this program works:

The x variable is assigned a 4 value.
The program proceeds to evaluate conditions for the first branch. The 4 value isn't the 2, 3, 5, or 7 values, so the program skips the execution of the body of the first branch and proceeds to the second branch.
The 4 value is between 1 and 10, so the message of x is a number between 1 and 10, but not a prime number. body is printed.
One body is executed, so the program proceeds to leave the when statement and ignores the else branch.
Use the is keyword to check data type
You can use the is keyword as a condition to check the data type of an evaluated value.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/66841365125b37aa_856.png)

In the previous diagram, if the value of the argument is of the data type stated, the first body is executed.

In your prime-number program, can you print a message if the input is an integer number that's outside the range of 1 to 10?

Add another branch with the is keyword:

Modify x to be of type Any. This is to indicate that x can be of value other than Int type.

```kt
fun main() {
    val x: Any = 4

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

After the second branch of the when statement, add the is keyword and an Int data type with a println("x is an integer number, but not between 1 and 10.") body:

```kt
fun main() {
    val x: Any = 4

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        is Int -> println("x is an integer number, but not between 1 and 10.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}
```

In the else branch, change the body to a println("x isn't an integer number.")body:

```kt
fun main() {
    val x: Any = 4

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        is Int -> println("x is an integer number, but not between 1 and 10.")
        else -> println("x isn't an integer number.")
    }
}
```

Change the x variable to a 20 value:

```kt
fun main() {
    val x: Any = 20

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        is Int -> println("x is an integer number, but not between 1 and 10.")
        else -> println("x isn't an integer number.")
    }
}
```

Run the program and then verify the output:

```kt
x is an integer number, but not between 1 and 10.
```
The program prints the message of the third branch, but not the messages of the first, second, or fourth branches.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/1255c101845f9247_856.png)

Here's how the program works:

The x variable is assigned a 20 value.
The program proceeds to evaluate conditions for the first branch. The 20 value isn't the 2, 3, 5 or 7 values, so the program skips the execution of the body of the first branch and proceeds to the second branch.
The 20 value isn't inside the 1 to 10 range, so the program skips the execution of the body of the second branch and proceeds to the third branch.
The 20 value is of Int type, so the x is an integer number, but not between 1 and 10 body is printed.
One body is executed, so the program proceeds to leave the when statement and ignores the else branch.
Try it
Now practice what you learned in your traffic-light program.

Imagine that there's an amber traffic-light color in some countries that warns drivers the same way as a yellow traffic light in other countries. Can you modify the program so that this additional condition is covered and maintain the original conditions?

Add an additional condition with the same body
Add an additional condition to the traffic-light program:

If you still have it open, go back to the instance of Kotlin Playground with your traffic-light program.
If you closed it, open a new instance of Kotlin Playground and enter this code:

```kt
fun main() {
    val trafficLightColor = "Yellow"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}
```

In the second branch of the when statement, add a comma after the "Yellow" condition and then an "Amber" condition:

```kt
fun main() {
    val trafficLightColor = "Yellow"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow", "Amber" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}
```

Change the trafficLightColor variable to an "Amber" value:

```kt
fun main() {
    val trafficLightColor = "Amber"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow", "Amber" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}
```

Run this program and then verify the output:

```Slow```

# 4. Use if/else and when as expressions

You learned how to use if/else and when as statements. When you use conditionals as statements, you let each branch execute different actions in the body based on the conditions.

You can also use conditionals as expressions to return different values for each branch of condition. When the body of each branch appears similar, you can use conditional expressions to improve code readability compared to conditional statements.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/a6ff7ba09d3cdea3_856.png)


The syntax for conditionals as expressions is similar to statements, but the last line of bodies in each branch need to return a value or an expression, and the conditionals are assigned to a variable.

If the bodies only contain a return value or expression, you can remove the curly braces to make the code more concise.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-conditionals/img/411c2aff894a72e2_856.png)

In the next section, you take a look at if/else expressions through the traffic-light program.

## Convert an if statement to an expression
There's a lot of println() statement repetition in this if/else statement:

```kt
fun main() {
    val trafficLightColor = "Black"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else if (trafficLightColor == "Green") {
        println("Go")
    } else {
        println("Invalid traffic-light color")
    }
}
```

Convert this if/else statement to an if/else expression and remove this repetition:

In Kotlin playground, enter the previous traffic-light program.
Define a message variable and then assign it an if/else statement:

```kt
fun main() {
    val trafficLightColor = "Black"

    val message = if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else if (trafficLightColor == "Green") {
        println("Go")
    } else {
        println("Invalid traffic-light color")
    }

}
```

Remove all println() statements and their curly braces, but leave the values inside them:

```kt
fun main() {
    val trafficLightColor = "Black"

    val message = 
      if (trafficLightColor == "Red") "Stop"
      else if (trafficLightColor == "Yellow") "Slow"
      else if (trafficLightColor == "Green") "Go"
      else "Invalid traffic-light color"
}
```

Add a println() statement to the end of the program and then pass it the message variable as an argument:

```kt
fun main() {
    val trafficLightColor = "Black"

    val message = 
      if (trafficLightColor == "Red") "Stop"
      else if (trafficLightColor == "Yellow") "Slow"
      else if (trafficLightColor == "Green") "Go"
      else "Invalid traffic-light color"

    println(message)
}
```

Run this program and then view the output:

```Invalid traffic-light color```

Try it
Convert the traffic-light program to use a when expression instead of a when statement:

In Kotlin Playground, enter this code:

```kt
fun main() {
    val trafficLightColor = "Amber"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow", "Amber" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}
```

Can you convert the when statement to an expression so that you don't repeat the println() statements?

Create a message variable and assign it to the when expression:

```kt
fun main() {
    val trafficLightColor = "Amber"

    val message = when(trafficLightColor) {
        "Red" -> "Stop"
        "Yellow", "Amber" -> "Slow"
        "Green" -> "Go"
        else -> "Invalid traffic-light color"
    }
}
```

Add a println()statement as the last line of the program and then pass it the message variable as an argument:

```kt
fun main() {
    val trafficLightColor = "Amber"

    val message = when(trafficLightColor) {
        "Red" -> "Stop"
        "Yellow", "Amber" -> "Slow"
        "Green" -> "Go"
        else -> "Invalid traffic-light color"
    }
    println(message)
}
```

> Note: A when statement doesn't need the else branch to be defined. However, in most cases, a when expression requires the else branch because the when expression needs to return a value. As such, the Kotlin compiler checks whether all the branches are exhaustive. An else branch ensures that there won't be a scenario in which the variable doesn't get assigned a value.


# 5. Conclusion
Congratulations! You learned about conditionals and how to write them in Kotlin.

Summary
In Kotlin, branching can be achieved with if/else or when conditionals.
The body of an if branch in an if/else conditional is only executed when the boolean expression inside the if branch condition returns a true value.
Subsequent else if branches in an if/else conditional get executed only when previous if or else if branches return false values.
The final else branch in an if/else conditional only gets executed when all previous if or else if branches return false values.
It's recommended to use the when conditional to replace an if/else conditional when there are more than two branches.
You can write more complex conditions in when conditionals with the comma (,), in ranges, and the is keyword.
if/else and when conditionals can work as either statements or expressions.