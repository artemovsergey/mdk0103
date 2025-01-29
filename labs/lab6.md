# Use nullability in Kotlin

## Before you begin

This codelab teaches you about nullability and the importance of null safety. Nullability is a concept commonly found in many programming languages. It refers to the ability of variables to have an absence of value. In Kotlin, nullability is intentionally treated to achieve null safety.

Prerequisites
Knowledge of Kotlin programming basics, including variables, accessing methods and properties from a variable and the println() and main() functions
Familiarity with Kotlin conditionals, including if/else statements and Boolean expressions
What you'll learn
What null is.
The difference between nullable and non-nullable types.
What null safety is, its importance, and how Kotlin achieves null safety.
How to access methods and properties of nullable variables with the ?. safe call operator and !! non-null assertion operator.
How to perform null checks with if/else conditionals.
How to convert a nullable variable to a non-nullable type with if/else expressions.
How to provide a default value when a nullable variable is null with the if/else expression or the ?: Elvis operator.
What you'll need
A web browser with access to Kotlin Playground


# 2. Use nullable variables

What is null?
In Unit 1, you learned that when you declare a variable, you need to assign it a value immediately. For example, when you declare a favoriteActor variable, you may assign it a "Sandra Oh" string value immediately.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/47ed434c7a79a3f1_856.png)


However, what if you don't have a favorite actor? You might want to assign the variable a "Nobody" or "None" value. This isn't a good approach because your program interprets the favoriteActor variable to have a "Nobody" or "None" value rather than no value at all. In Kotlin, you can use null to indicate that there's no value associated with the variable.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/8dd4081dcf8c2610_856.png)


To use null in code, follow these steps:

In Kotlin Playground, replace the content in the body of the main() function with a favoriteActor variable set to null:

```kt
fun main() {
    val favoriteActor = null
}
```

Print the value of the favoriteActor variable with the println() function and then run this program:

```kt
fun main() {
    val favoriteActor = null
    println(favoriteActor)
}   
```

The output looks like this code snippet:

```null```

## Variable reassignments with null
Previously, you learned that you can reassign variables defined with the var keyword to different values of the same type. For example, you can reassign a name variable that's declared with one name to another name as long as the new name is of String type.

```kt
var favoriteActor: String = "Sandra Oh"
favoriteActor = "Meryl Streep"
```

There are occasions after you declare a variable when you may want to assign the variable to null. For example, after you declare your favorite actor, you decide that you don't want to reveal your favorite actor at all. In this case, it's useful to assign the favoriteActor variable to null.

Understand non-nullable and nullable variables
To reassign the favoriteActor variable to null, follow these steps:

Change the val keyword to a var keyword, and then specify that the favoriteActor variable is a String type and assign it to the name of your favorite actor:

```kt
fun main() {
    var favoriteActor: String = "Sandra Oh"
    println(favoriteActor)
}
```

Remove the println() function:

```kt
fun main() {
    var favoriteActor: String = "Sandra Oh"
}   
```

Reassign the favoriteActor variable to null and then run this program:

```kt
fun main() {
    var favoriteActor: String = "Sandra Oh"
    favoriteActor = null
}
```

You get this error message:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/20fa3d758a5de502_856.png)

In Kotlin, there's a distinction between nullable and non-nullable types:

Nullable types are variables that can hold null.
Non-null types are variables that can't hold null.
A type is only nullable if you explicitly let it hold null. As the error message says, the String data type is a non-nullable type, so you can't reassign the variable to null.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/c3bbad8de6afdbe9_856.png)

To declare nullable variables in Kotlin, you need to add a ? operator to the end of the type. For example, a String? type can hold either a string or null, whereas a String type can only hold a string. To declare a nullable variable, you need to explicitly add the nullable type. Without the nullable type, the Kotlin compiler infers that it's a non-nullable type.

Change the favoriteActor variable type from a String data type to a String? data type:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"
    favoriteActor = null
}
```

Print the favoriteActor variable before and after the null reassignment, and then run this program:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"
    println(favoriteActor)

    favoriteActor = null
    println(favoriteActor)
}
```

The output looks like this code snippet:

```
Sandra Oh
null
```

The favoriteActor variable originally held a string and is then reassigned to null.

Try it
Now that you can use the nullable String? type, can you initialize a variable with an Int value and reassign it to null?

Write a nullable Int value
Remove all the code in the main() function:

```kt
fun main() {
    
}
```

Create a number variable of a nullable Int type and then assign it a 10 value:

```kt
fun main() {
    var number: Int? = 10
}
```

Print the number variable and then run this program:

```kt
fun main() {
    var number: Int? = 10
    println(number)
}
```

The output is as expected:

```10```

Reassign the number variable to null to confirm that the variable is nullable:

```kt
fun main() {
    var number: Int? = 10
    println(number)
    
    number = null
}
```

Add another println(number) statement as the final line of the program and then run it:

```kt
fun main() {
    var number: Int? = 10
    println(number)
    
    number = null
    println(number)
}
```

The output is as expected:

```kt
10
null
```

>Note: While you should use nullable variables for variables that can carry null, you should use non-nullable variables for variables that can never carry null because the access of nullable variables requires more complex handling. You learn about various techniques to handle nullable variables in the next section.

# 3. Handle nullable variables

Previously, you learned to use the . operator to access methods and properties of non-nullable variables. In this section, you learn how to use it to access methods and properties of nullable variables.

To access a property of the non-nullable favoriteActor variable, follow these steps:

Remove all the code in the main() function, and then declare a favoriteActor variable of String type and assign it to the name of your favorite actor:

```kt
fun main() {
    var favoriteActor: String = "Sandra Oh"
}
```

Print the number of characters in the favoriteActor variable value with the length property and then run this program:

```kt
fun main() {
    var favoriteActor: String = "Sandra Oh"
    println(favoriteActor.length)
}
```

The output is as expected: 9

There are nine characters in the value of the favoriteActor variable, which includes spaces. The number of characters in your favorite actor's name might be different.

Access a property of a nullable variable
Imagine that you want to make the favoriteActor variable nullable so that people who don't have a favorite actor can assign the variable to null.

To access a property of the nullable favoriteActor variable, follow these steps:

Change the favoriteActor variable type to a nullable type and then run this program:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"
    println(favoriteActor.length)
}
```

You get this error message:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/5c5e60b58c31d162_856.png)

This error is a compile error. As mentioned in a previous codelab, a compile error happens when Kotlin isn't able to compile the code due to a syntax error in your code.

Kotlin intentionally applies syntactic rules so that it can achieve null safety, which refers to a guarantee that no accidental calls are made on potentially null variables. This doesn't mean that variables can't be null. It means that if a member of a variable is accessed, the variable can't be null.

This is critical because if there's an attempt to access a member of a variable that's null - known as null reference - during the running of an app, the app crashes because the null variable doesn't contain any property or method. This type of crash is known as a runtime error in which the error happens after the code has compiled and runs.

Due to the null safety nature of Kotlin, such runtime errors are prevented because the Kotlin compiler forces a null check for nullable types. Null check refers to a process of checking whether a variable could be null before it's accessed and treated as a non-nullable type. If you wish to use a nullable value as its non-nullable type, you need to perform a null check explicitly. You learn about this in the Use if/else conditionals section later in this codelab.

In this example, the code fails at compile time because the direct reference to the length property for the favoriteActor variable isn't allowed because there's a possibility that the variable is null.

Next, you learn various techniques and operators to work with nullable types.

## Use the ?. safe call operator
You can use the ?. safe call operator to access methods or properties of nullable variables.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/a09b732437a133aa_856.png)

To use the ?. safe call operator to access a method or property, add a ? symbol after the variable name and access the method or property with the . notation.

The ?. safe call operator allows safer access to nullable variables because the Kotlin compiler stops any attempt of member access to null references and returns null for the member accessed.

To safely access a property of the nullable favoriteActor variable, follow these steps:

In the println() statement, replace the . operator with the ?. safe call operator:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"
    println(favoriteActor?.length)
}
```

Run this program and then verify that the output is as expected: 9

The number of characters of your favorite actor's name might differ.

Reassign the favoriteActor variable to null and then run this program:

```kt
fun main() {
    var favoriteActor: String? = null
    println(favoriteActor?.length)
}
```

You see this output: null

Notice that the program doesn't crash despite an attempt to access the length property of a null variable. The safe call expression simply returns null.

> Note: You can also use the ?. safe call operators on non-nullable variables to access a method or property. While the Kotlin compiler won't give any error for this, it's unnecessary because the access of methods or properties for non-nullable variables is always safe.


# Use the !! not-null assertion operator
You can also use the !! not-null assertion operator to access methods or properties of nullable variables.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/1a6f269bfd700839_856.png)

After the nullable variable, you need to add the !! not-null assertion operator followed by the . operator and then the method or property without any spaces.

As the name suggests, if you use the !! not-null assertion, it means that you assert that the value of the variable isn't null, regardless of whether it is or isn't.

Unlike ?. safe-call operators, the use of a !! not-null assertion operator may result in a NullPointerException error being thrown if the nullable variable is indeed null. Thus, it should be done only when the variable is always non-nullable or proper exception handling is set in place. When not handled, exceptions cause runtime errors. You learn about exception handling in later units of this course.

To access a property of the favoriteActor variable with the !! not-null assertion operator, follow these steps:

Reassign the favoriteActor variable to your favorite actor's name and then replace the ?. safe-call operator with the !! not-null assertion operator in println() statement:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"
    println(favoriteActor!!.length)
}
```

Run this program and then verify that the output is as expected: 9

The number of characters of your favorite actor's name might differ.

Reassign the favoriteActor variable to null and then run this program:

```kt
fun main() {
    var favoriteActor: String? = null
    println(favoriteActor!!.length)
}
```

You get a NullPointerException error:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/c74ab53164de0c01_856.png)

This Kotlin error shows that your program crashed during execution. As such, it's not recommended to use the !! not-null assertion operator unless you're sure that the variable isn't null.

> Note: In other programming languages that don't contain the null-safety attribute, the NullPointerException error is the frequent cause of app crashes. As such, Kotlin eliminates a huge cause of program crashes because it includes null safety in the language.

# Use the if/else conditionals
You can use the if branch in the if/else conditionals to perform null checks.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/326d68521327f229_856.png)

To perform null checks, you can check that the nullable variable isn't equal to null with the != comparison operator.

if/else statements
An if/else statement can be used together with a null check as follows:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/4cca066cf405b09c_856.png)


The null check is useful when combined with an if/else statement:

The null check of the nullableVariable != null expression is used as the if condition.
Body 1 inside the if branch assumes that the variable is not null. Therefore, in this body, you can freely access methods or properties of the variable as if it's a non-nullable variable without using a ?. safe-call operator or a !! not-null assertion operator.
Body 2 inside the else branch assumes that the variable is null. Therefore, in this body, you can add statements that should run when the variable is null. The else branch is optional. You can use only the if conditional to run a null check without providing default action when the null check fails.
The null check is more convenient to use with the if condition when there are multiple lines of code that use the nullable variable. In contrast, the ?. safe-call operator is more convenient for a single reference of the nullable variable.

To write an if/else statement with a null check for the favoriteActor variable, follow these steps:

Assign the favoriteActor variable to the name of your favorite actor again and then remove the println() statement:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"
}
```

Add an if branch with a favoriteActor != null condition:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    if (favoriteActor != null) {

    }
}
```

In the body of the if branch, add a println statement that accepts a "The number of characters in your favorite actor's name is ${favoriteActor.length}." string and then run this program:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    if (favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    }
}
```

The output is as expected.

```The number of characters in your favorite actor's name is 9.```

The number of characters in your favorite actor's name might differ.

Notice that you can access the name's length method directly with the . operator because you access the length method inside the if branch after the null check. As such, the Kotlin compiler knows that there's no possibility that the favoriteActor variable is null, so the compiler allows direct access to the property.

Optional: Add an else branch to handle a situation in which the actor's name is null:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    if (favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    } else {

    }
}
```

In the body of the else branch, add a println statement that takes a "You didn't input a name." string:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    if (favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    } else {
      println("You didn't input a name.")
    }
}
```

Assign the favoriteActor variable to null and then run this program:

```kt
fun main() {
    var favoriteActor: String? = null

    if(favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    } else {
      println("You didn't input a name.")
    }
}
```

The output is as expected:

You didn't input a name.

if/else expressions
You can also combine the null check with an if/else expression to convert a nullable variable to a non-nullable variable.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/8bbf0c7e50163906_856.png)

To assign an if/else expression to a non-nullable type:

The nullableVariable != null null check is used as the if condition.
Body 1 inside the if branch assumes that the variable is not null. Therefore, in this body, you can access methods or properties of the variable as if it's a non-nullable variable without a ?. safe call operator or a !! not-null assertion operator.
Body 2 inside the else branch assumes that the variable is null. Therefore, in this body you can add statements that should run when the variable is null.
In the final line of body 1 and 2, you need to use an expression or value that results in a non-nullable type so that it's assigned to the non-nullable variable when the null check passes or fails respectively.
To use the if/else expression to rewrite the program so that it only uses one println statement, follow these steps:

Assign the favoriteActor variable to the name of your favorite actor:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    if (favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    } else {
      println("You didn't input a name.")
    }
}
```

Create a lengthOfName variable and then assign it to the if/else expression:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = if (favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    } else {
      println("You didn't input a name.")
    }
}
```

Remove both println() statements from the if and else branches:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = if (favoriteActor != null) {
      
    } else {
      
    }
}
```

In the body of the if branch, add a favoriteActor.length expression:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = if (favoriteActor != null) {
      favoriteActor.length
    } else {
      
    }
}
```

The length property of the favoriteActor variable is accessed directly with the . operator.

In the body of the else branch, add a 0 value:

```kt
fun main() {
   var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = if (favoriteActor != null) {
      favoriteActor.length
    } else {
      0
    }
}
```

The 0 value serves as the default value when the name is null.

At the end of the main() function, add a println statement that takes a "The number of characters in your favorite actor's name is $lengthOfName." string and then run this program:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = if (favoriteActor != null) {
      favoriteActor.length
    } else {
      0
    }

    println("The number of characters in your favorite actor's name is $lengthOfName.")
}
```

The output is as expected:

```
The number of characters in your favorite actor's name is 9.
```
The number of characters of the name that you used might differ.

> Note: You can also use the == comparison operator for null checks instead of the != operator. When doing so, note that the two bodies are reversed. The body of the if branch assumes that the variable is null and the body of the else branch assumes that the variable is not null.


## Use the ?: Elvis operator
The ?: Elvis operator is an operator that you can use together with the ?. safe-call operator. With the ?: Elvis operator, you can add a default value when the ?. safe-call operator returns null. It's similar to an if/else expression, but in a more idiomatic way.

If the variable isn't null, the expression before the ?: Elvis operator executes. If the variable is null, the expression after the ?: Elvis operator executes.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/85be2b9161680ecf_856.png)

To modify your previous program to use the ?: Elvis operator, follow these steps:

Remove the if/else conditional and then set the lengthOfName variable to the nullable favoriteActor variable and use the ?. safe-call operator to call its length property:

```kt
fun main() {
   var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = favoriteActor?.length

    println("The number of characters in your favorite actor's name is $lengthOfName.")
}
```

After the length property, add the ?: Elvis operator followed by a 0 value and then run this program:

```kt
fun main() {
    var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = favoriteActor?.length ?: 0

    println("The number of characters in your favorite actor's name is $lengthOfName.")
}
```

The output is the same as the previous output:

```
The number of characters in your favorite actor's name is 9.
```

> Note: The ?: Elvis operator is named after Elvis Presley, the rock star, because it resembles an emoticon of his quiff when you view it sideways.


# 4. Conclusion
Congratulations! You learned about nullability and how to use various operators to manage it.

## Summary
A variable can be set to null to indicate that it holds no value.
Non-nullable variables cannot be assigned null.
Nullable variables can be assigned null.
To access methods or properties of nullable variables, you need to use ?. safe-call operators or !! not-null assertion operators.
You can use if/else statements with null checks to access nullable variables in non-nullable contexts.
You can convert a nullable variable to a non-nullable type with if/else expressions.
You can provide a default value for when a nullable variable is null with the if/else expression or the ?: Elvis operator.
