# Generics, objects, and extensions

1. Introduction
Over the decades, programmers devised several programming language features to help you write better code—expressing the same idea with less code, abstraction to express complex ideas, and writing code that prevents other developers from accidentally making mistakes are just a few examples. The Kotlin language is no exception, and there are a number of features intended to help developers write more expressive code.

Unfortunately, these features can make things tricky if this is your first time programming. While they might sound useful, the extent of their usefulness and the problems they solve may not always be apparent. Chances are you've already seen some features used in Compose and other libraries.

While there's no substitute for experience, this codelab exposes you to several Kotlin concepts that help you structure larger apps:

Generics
Different kinds of classes (enum classes and data classes)
Singleton and companion objects
Extension properties and functions
Scope functions
By the end of this codelab, you should have a deeper knowledge of the code you've already seen in this course, and learn some examples of when you'll encounter or use these concepts in your own apps.

Prerequisites
Familiarity with object-oriented programming concepts, including inheritance.
How to define and implement interfaces.
What you'll learn
How to define a generic type parameter for a class.
How to instantiate a generic class.
When to use enum and data classes.
How to define a generic type parameter that must implement an interface.
How to use scope functions to access class properties and methods.
How to define singleton objects and companion objects for a class.
How to extend existing classes with new properties and methods.
What you'll need
A web browser with access to the Kotlin Playground.


# 2. Make a reusable class with generics
Let's say you're writing an app for an online quiz, similar to the quizzes you've seen in this course. There are often multiple types of quiz questions, such as fill-in-the-blank, or true or false. An individual quiz question can be represented by a class, with several properties.

The question text in a quiz can be represented by a string. Quiz questions also need to represent the answer. However, different question types—such as true or false—may need to represent the answer using a different data type. Let's define three different types of questions.

Fill-in-the-blank question: The answer is a word represented by a String.
True or false question: The answer is represented by a Boolean.
Math problems: The answer is a numeric value. The answer for a simple arithmetic problem is represented by an Int.
In addition, quiz questions in our example, regardless of the type of question, will also have a difficulty rating. The difficulty rating is represented by a string with three possible values: "easy", "medium", or "hard".

Define classes to represent each type of quiz question:

Navigate to the Kotlin playground.
Above the main() function, define a class for fill-in-the-blank questions named FillInTheBlankQuestion, consisting of a String property for the questionText, a String property for the answer, and a String property for the difficulty.

```kt
class FillInTheBlankQuestion(
    val questionText: String,
    val answer: String,
    val difficulty: String
)
```

Below the FillInTheBlankQuestion class, define another class named TrueOrFalseQuestion for true or false questions, consisting of a String property for the questionText, a Boolean property for the answer, and a String property for the difficulty.

```kt
class TrueOrFalseQuestion(
    val questionText: String,
    val answer: Boolean,
    val difficulty: String
)
```

Finally, below the other two classes, define a NumericQuestion class, consisting of a String property for the questionText, an Int property for the answer, and a String property for the difficulty.

```kt
class NumericQuestion(
    val questionText: String,
    val answer: Int,
    val difficulty: String
)
```

Take a look at the code you wrote. Do you notice the repetition?

```kt
class FillInTheBlankQuestion(
    val questionText: String,
    val answer: String,
    val difficulty: String
)

class TrueOrFalseQuestion(
    val questionText: String,
    val answer: Boolean,
    val difficulty: String
)
class NumericQuestion(
    val questionText: String,
    val answer: Int,
    val difficulty: String
)
```

All three classes have the exact same properties: the questionText, answer, and difficulty. The only difference is the data type of the answer property. You might think that the obvious solution is to create a parent class with the questionText and difficulty, and each subclass defines the answer property.

However, using inheritance has the same problem as above. Every time you add a new type of question, you have to add an answer property. The only difference is the data type. It also looks strange to have a parent class Question that doesn't have an answer property.

When you want a property to have differing data types, subclassing is not the answer. Instead, Kotlin provides something called generic types that allow you to have a single property that can have differing data types, depending on the specific use case.

## What is a generic data type?
Generic types, or generics for short, allow a data type, such as a class, to specify an unknown placeholder data type that can be used with its properties and methods. What exactly does this mean?

In the above example, instead of defining an answer property for each possible data type, you can create a single class to represent any question, and use a placeholder name for the data type of the answer property. The actual data type—String, Int, Boolean, etc.—is specified when that class is instantiated. Wherever the placeholder name is used, the data type passed into the class is used instead. The syntax for defining a generic type for a class is shown below:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/67367d9308c171da_856.png)

A generic data type is provided when instantiating a class, so it needs to be defined as part of the class signature. After the class name comes a left-facing angle bracket (<), followed by a placeholder name for the data type, followed by a right-facing angle bracket (>).

The placeholder name can then be used wherever you use a real data type within the class, such as for a property.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/81170899b2ca0dc9_856.png)

This is identical to any other property declaration, except the placeholder name is used instead of the data type.

How would your class ultimately know which data type to use? The data type that the generic type uses is passed as a parameter in angle brackets when you instantiate the class.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/9b8fce54cac8d1ea_856.png)

After the class name comes a left-facing angle bracket (<), followed by the actual data type, String, Boolean, Int, etc., followed by a right-facing bracket (>). The data type of the value that you pass in for the generic property must match the data type in the angle brackets. You'll make the answer property generic so that you can use one class to represent any type of quiz question, whether the answer is a String, Boolean, Int, or any arbitrary data type.

> Note: The generic types passed in when instantiating a class are also called "parameters", although they're part of a separate parameter list than the property values placed inside the parentheses.


> Note: Like the example above, you'll often see a generic type named T (short for type), or other capital letters if the class has multiple generic types. However, there is definitely not a rule and you're welcome to use a more descriptive name for generic types.

## Refactor your code to use generics
Refactor your code to use a single class named Question with a generic answer property.

Remove the class definitions for FillInTheBlankQuestion, TrueOrFalseQuestion, and NumericQuestion.
Create a new class named Question.

```kt
class Question()
```

After the class name, but before the parentheses, add a generic type parameter using left- and right-facing angle brackets. Call the generic type T.

```kt
class Question<T>()
```

Add the questionText, answer, and difficulty properties. The questionText should be of type String. The answer should be of type T because its data type is specified when instantiating the Question class. The difficulty property should be of type String.

```kt
class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: String
)
```

To see how this works with multiple question types—fill-in-the-blank, true or false, etc.— create three instances of the Question class in main(), as shown below.

```kt
fun main() {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", "medium")
    val question2 = Question<Boolean>("The sky is green. True or false", false, "easy")
    val question3 = Question<Int>("How many days are there between full moons?", 28, "hard")
}
```

Run your code to make sure everything works. You should now have three instances of the Question class—each with different data types for the answer—instead of three different classes, or instead of using inheritance. If you want to handle questions with a different answer type, you can reuse the same Question class.

# 3. Use an enum class

In the previous section, you defined a difficulty property with three possible values: "easy", "medium", and "hard". While this works, there are a couple of problems.

If you accidentally mistype one of the three possible strings, you could introduce bugs.
If the values change, for example, "medium" is renamed to "average", then you need to update all usages of the string.
There's nothing stopping you or another developer from accidentally using a different string that isn't one of the three valid values.
The code is harder to maintain if you add more difficulty levels.
Kotlin helps you address these problems with a special type of class called an enum class. An enum class is used to create types with a limited set of possible values. In the real world, for example, the four cardinal directions—north, south, east, and west—could be represented by an enum class. There's no need, and the code shouldn't allow, for the use of any additional directions. The syntax for an enum class is shown below.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/f4bddb215eb52392_856.png)

Each possible value of an enum is called an enum constant. Enum constants are placed inside the curly braces separated by commas. The convention is to capitalize every letter in the constant name.

You refer to enum constants using the dot operator.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/f3cfa84c3f34392b_856.png)

## Use an enum constant
Modify your code to use an enum constant, instead of a String, to represent the difficulty.

Below the Question class, define an enum class called Difficulty.

```kt
enum class Difficulty {
    EASY, MEDIUM, HARD
}
```

In the Question class, change the data type of the difficulty property from String to Difficulty.

```kt
class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)
```

When initializing the three questions, pass in the enum constant for the difficulty.

```kt
val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)
```


# 4. Use a data class
Many of the classes you've worked with so far, such as subclasses of Activity, have several methods to perform different actions. These classes don't just represent data, but also contain a lot of functionality.

Classes like the Question class, on the other hand, only contain data. They don't have any methods that perform an action. These can be defined as a data class. Defining a class as a data class allows the Kotlin compiler to make certain assumptions, and to automatically implement some methods. For example, toString() is called behind the scenes by the println() function. When you use a data class, toString() and other methods are implemented automatically based on the class's properties.

To define a data class, simply add the data keyword before the class keyword.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/e7cd946b4ad216f4_856.png)

## Convert Question to a data class
First, you'll see what happens when you try to call a method like toString() on a class that isn't a data class. Then, you'll convert Question into a data class, so that this and other methods will be implemented by default.

In main(), print the result of calling toString() on question1.

```kt
fun main() {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)
    println(question1.toString())
}
```

Run your code. The output only shows the class name and a unique identifier for the object.

```Question@37f8bb67```

Make Question into a data class using the data keyword.

```kt
data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)
```

Run your code again. By marking this as a data class, Kotlin is able to determine how to display the class's properties when calling toString().

```
Question(questionText=Quoth the raven ___, answer=nevermore, difficulty=MEDIUM)
```

When a class is defined as a data class, the following methods are implemented.

- equals()
- hashCode(): you'll see this method when working with certain collection types.
- toString()
- componentN(): component1(), component2(), etc.
- copy()

> Note: A data class needs to have at least one parameter in its constructor, and all constructor parameters must be marked with val or var. A data class also cannot be abstract, open, sealed, or inner.


# 5. Use a singleton object
There are many scenarios where you want a class to only have one instance. For example:

Player stats in a mobile game for the current user.
Interacting with a single hardware device, like sending audio through a speaker.
An object to access a remote data source (such as a Firebase database).
Authentication, where only one user should be logged in at a time.
In the above scenarios, you'd probably need to use a class. However, you'll only ever need to instantiate one instance of that class. If there's only one hardware device, or only one user logged in at once, there would be no reason to create more than a single instance. Having two objects that access the same hardware device simultaneously could lead to some really strange and buggy behavior.

You can clearly communicate in your code that an object should have only one instance by defining it as a singleton. A singleton is a class that can only have a single instance. Kotlin provides a special construct, called an object, that can be used to make a singleton class.

Define a singleton object


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/645e8e8bbffbb5f9_856.png)


The syntax for an object is similar to that of a class. Simply use the object keyword instead of the class keyword. A singleton object can't have a constructor as you can't create instances directly. Instead, all the properties are defined within the curly braces and are given an initial value.

Some of the examples given earlier might not seem obvious, especially if you haven't worked with specific hardware devices or dealt with authentication yet in your apps. However, you'll see singleton objects come up as you continue learning Android development. Let's see it in action with a simple example using an object for user state, in which only one instance is needed.

For a quiz, it would be great to have a way to keep track of the total number of questions, and the number of questions the student answered so far. You'll only need one instance of this class to exist, so instead of declaring it as a class, declare it as a singleton object.

Create an object named StudentProgress.

```kt
object StudentProgress {
}
```

For this example, we'll assume there are ten total questions, and that three of them are answered so far. Add two Int properties: total with a value of 10, and answered with a value of 3.

```kt
object StudentProgress {
    var total: Int = 10
    var answered: Int = 3
}
```

## Access a singleton object
Remember how you can't create an instance of a singleton object directly? How then are you able to access its properties?

Because there's only one instance of StudentProgress in existence at one time, you access its properties by referring to the name of the object itself, followed by the dot operator (.), followed by the property name.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/1b610fd87e99fe25_856.png)

Update your main() function to access the properties of the singleton object.

In main(), add a call to println() that outputs the answered and total questions from the StudentProgress object.

```kt
fun main() {
    ...
    println("${StudentProgress.answered} of ${StudentProgress.total} answered.")
}
```

Run your code to verify that everything works.

```
...
3 of 10 answered.
```

## Declare objects as companion objects
Classes and objects in Kotlin can be defined inside other types, and can be a great way to organize your code. You can define a singleton object inside another class using a companion object. A companion object allows you to access its properties and methods from inside the class, if the object's properties and methods belong to that class, allowing for more concise syntax.

To declare a companion object, simply add the companion keyword before the object keyword.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/68b263904ec55f29_856.png)

You'll create a new class called Quiz to store the quiz questions, and make StudentProgress a companion object of the Quiz class.

Below the Difficulty enum, define a new class named Quiz.

```kt
class Quiz {
}
```

Move question1, question2, and question3 from main() into the Quiz class. You also need to remove println(question1.toString()) if you haven't already.

```kt
class Quiz {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

}
```

Move the StudentProgress object into the Quiz class.

```kt
class Quiz {
    val question1 = Question<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }
}
```

Mark the StudentProgress object with the companion keyword.

```kt
companion object StudentProgress {
    var total: Int = 10
    var answered: Int = 3
}
```

Update the call to println() to reference the properties with Quiz.answered and Quiz.total. Even though these properties are declared in the StudentProgress object, they can be accessed with dot notation using only the name of the Quiz class.

```kt
fun main() {
    println("${Quiz.answered} of ${Quiz.total} answered.")
}
```

Run your code to verify the output.

```3 of 10 answered.```


# 6. Extend classes with new properties and methods
When working with Compose, you may have noticed some interesting syntax when specifying the size of UI elements. Numeric types, such as Double, appear to have properties like dp and sp specifying dimensions.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/a25c5a0d7bb92b60_856.png)

Why would the designers of the Kotlin language include properties and functions on built-in data types, specifically for building Android UI? Were they able to predict the future? Was Kotlin designed to be used with Compose even before Compose existed?

Of course not! When you're writing a class, you often don't know exactly how another developer will use it, or plans to use it, in their app. It's not possible to predict all future use cases, nor is it wise to add unnecessary bloat to your code for some unforeseen use case.

What the Kotlin language does, is give other developers the ability to extend existing data types, adding properties and methods that can be accessed with dot syntax, as if they were part of that data type. A developer who didn't work on the floating point types in Kotlin, for example, such as someone building the Compose library, might choose to add properties and methods specific to UI dimensions.

Since you've seen this syntax when learning Compose in the first two units, it's about time for you to learn how this works under the hood. You'll add some properties and methods to extend existing types.

Add an extension property
To define an extension property, add the type name and a dot operator (.) before the variable name.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/1e8a52e327fe3f45_856.png)

You'll refactor the code in the main() function to print the quiz progress with an extension property.

Below the Quiz class, define an extension property of Quiz.StudentProgress named progressText of type String.

```kt
val Quiz.StudentProgress.progressText: String
```

Define a getter for the extension property that returns the same string used before in main().

```kt
val Quiz.StudentProgress.progressText: String
    get() = "${answered} of ${total} answered"
```

Replace the code in the main() function with code that prints progressText. Because this is an extension property of the companion object, you can access it with dot notation using the name of the class, Quiz.

```kt
fun main() {
    println(Quiz.progressText)
}
```

Run your code to verify it works.

```3 of 10 answered.```

> Note: Extension properties can't store data, so they must be get-only.

## Add an extension function
To define an extension function, add the type name and a dot operator (.) before the function name.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/879ff2761e04edd9_856.png)


You'll add an extension function to output the quiz progress as a progress bar. Since you can't actually make a progress bar in the Kotlin playground, you'll print out a retro-style progress bar using text!

Add an extension function to the StudentProgress object called printProgressBar(). The function should take no parameters and have no return value.

```kt
fun Quiz.StudentProgress.printProgressBar() {
}
```

Print out the ▓ character, answered number of times, using repeat(). This dark-shaded portion of the progress bar represents the number of questions answered. Use print() because you don't want a new line after each character.

```kt
fun Quiz.StudentProgress.printProgressBar() {
    repeat(Quiz.answered) { print("▓") }
}
```

Print out the ▒ character, the number of times equal to the difference between total and answered, using repeat(). This light-shaded portion represents the remaining questions in the process bar.

```kt
fun Quiz.StudentProgress.printProgressBar() {
    repeat(Quiz.answered) { print("▓") }
    repeat(Quiz.total - Quiz.answered) { print("▒") }
}
```

Print a new line using println() with no arguments, and then print progressText.

```kt
fun Quiz.StudentProgress.printProgressBar() {
    repeat(Quiz.answered) { print("▓") }
    repeat(Quiz.total - Quiz.answered) { print("▒") }
    println()
    println(Quiz.progressText)
}
```

Update the code in main() to call printProgressBar().

```kt
fun main() {
    Quiz.printProgressBar()
}
```

Run your code to verify the output.

```
▓▓▓▒▒▒▒▒▒▒
3 of 10 answered.
```

Is it mandatory to do any of this? Certainly not. However, having the option of extension properties and methods gives you more options to expose your code to other developers. Using dot syntax on other types can make your code easier to read, both for yourself and for other developers.


# 7. Rewrite extension functions using interfaces
On the previous page, you saw how to add properties and methods to the StudentProgress object without adding code to it directly, using extension properties and extension functions. While this is a great way to add functionality to one class that's already defined, extending a class isn't always necessary if you have access to the source code. There are also situations where you don't know what the implementation should be, only that a certain method or property should exist. If you need multiple classes to have the same additional properties and methods, perhaps with differing behavior, you can define these properties and methods with an interface.

For example, in addition to quizzes, let's say you also have classes for surveys, steps in a recipe, or any other ordered data that could use a progress bar. You can define something called an interface that specifies the methods and/or properties that each of these classes must include.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/eeed58ed687897be_856.png)

An interface is defined using the interface keyword, followed by a name in UpperCamelCase, followed by opening and closing curly braces. Within the curly braces, you can define any method signatures or get-only properties that any class conforming to the interface must implement.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/6b04a8f50b11f2eb_856.png)

An interface is a contract. A class that conforms to an interface is said to extend the interface. A class can declare that it would like to extend an interface using a colon (:), followed by a space, followed by the name of the interface.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-generics/img/78af59840c74fa08_856.png)

In return, the class must implement all properties and methods specified in the interface. This lets you easily ensure that any class that needs to extend the interface implements the exact same methods with the exact same method signature. If you modify the interface in any way, such as add or remove properties or methods or change a method signature, the compiler requires you to update any class that extends the interface, keeping your code consistent and easier to maintain.

Interfaces allow for variation in the behavior of classes that extend them. It's up to each class to provide the implementation.

Let's see how you can rewrite the progress bar to use an interface, and make the Quiz class extend that interface.

Above the Quiz class, define an interface named ProgressPrintable. We've chosen the name ProgressPrintable because it makes any class that extends it able to print a progress bar.

```kt
interface ProgressPrintable {
}
```

In the ProgressPrintable interface, define a property named progressText.

```kt
interface ProgressPrintable {
    val progressText: String
}
```

Modify the declaration of the Quiz class to extend the ProgressPrintable interface.

```kt
class Quiz : ProgressPrintable {
    ... 
}
```

In the Quiz class, add a property named progressText of type String, as specified in the ProgressPrintable interface. Because the property comes from ProgressPrintable, precede val with the override keyword.

```kt
override val progressText: String
```

Copy the property getter from the old progressText extension property.

```kt
override val progressText: String
        get() = "${answered} of ${total} answered"
```

Remove the old progressText extension property.

Code to delete:

```kt
val Quiz.StudentProgress.progressText: String
    get() = "${answered} of ${total} answered"
```

In the ProgressPrintable interface, add a method named printProgressBar that takes no parameters and has no return value.

```kt
interface ProgressPrintable {
    val progressText: String
    fun printProgressBar()
}
```

In the Quiz class, add the printProgressBar() method using the override keyword.

```kt
In the Quiz class, add the printProgressBar() method using the override keyword.
```

Move the code from the old printProgressBar() extension function into the new printProgressBar() from the interface. Modify the last line to refer to the new progressText variable from the interface by removing the reference to Quiz.

```kt
override fun printProgressBar() {
    repeat(Quiz.answered) { print("▓") }
    repeat(Quiz.total - Quiz.answered) { print("▒") }
    println()
    println(progressText)
}
```

Remove the extension function printProgressBar(). This functionality now belongs to the Quiz class that extends ProgressPrintable.

Code to delete:
```kt
fun Quiz.StudentProgress.printProgressBar() {
    repeat(Quiz.answered) { print("▓") }
    repeat(Quiz.total - Quiz.answered) { print("▒") }
    println()
    println(Quiz.progressText)
}
```

Update the code in main(). As the printProgressBar() function is now a method of the Quiz class, you need to first instantiate a Quiz object and then call printProgressBar().

```kt
fun main() {
    Quiz().printProgressBar()
}
```

Run your code. The output is unchanged, but your code is now more modular. As your codebases grow, you can easily add classes that conform to the same interface to reuse code without inheriting from a superclass.

```
▓▓▓▒▒▒▒▒▒▒
3 of 10 answered.
```

There are numerous use cases for interfaces to help structure your code and you'll start to see them used frequently in the common units. The following are some examples of interfaces you may encounter as you continue working with Kotlin.

Manual dependency injection. Create an interface defining all the properties and methods of the dependency. Require the interface as the data type of the dependency (activity, test case, etc.) so that an instance of any class implementing the interface can be used. This allows you to swap out the underlying implementations.
Mocking for automated tests. Both the mock class and the real class conform to the same interface.
Accessing the same dependencies in a Compose Multiplatform app. For example, create an interface that provides a common set of properties and methods for Android and desktop, even if the underlying implementation differs for each platform.
Several data types in Compose, such as Modifier, are interfaces. This allows you to add new modifiers without needing to access or modify the underlying source code.


# 8. Use scope functions to access class properties and methods
As you've seen already, Kotlin includes a lot of features to make your code more concise.

One such feature you'll encounter as you continue learning Android development is scope functions. Scope functions allow you to concisely access properties and methods from a class without having to repeatedly access the variable name. What exactly does this mean? Let's take a look at an example.

Eliminate repetitive object references with scope functions
Scope functions are higher-order functions that allow you to access properties and methods of an object without referring to the object's name. These are called scope functions because the body of the function passed in takes on the scope of the object that the scope function is called with. For example, some scope functions allow you to access the properties and methods in a class, as if the functions were defined as a method of that class. This can make your code more readable by allowing you to omit the object name when including it is redundant.

To better illustrate this, let's take a look at a few different scope functions that you'll encounter later in the course.

Replace long object names using let()
The let() function allows you to refer to an object in a lambda expression using the identifier it, instead of the object's actual name. This can help you avoid using a long, more descriptive object name repeatedly when accessing more than one property. The let() function is an extension function that can be called on any Kotlin object using dot notation.

Try accessing the properties of question1, question2, and question3 using let():

Add a function to the Quiz class named printQuiz().

```kt
fun printQuiz() {
    
}
```

Add the following code that prints the question's questionText, answer, and difficulty. While multiple properties are accessed for question1, question2, and question3, the entire variable name is used each time. If the variable's name changed, you'd need to update every usage.

```kt
fun printQuiz() {
    println(question1.questionText)
    println(question1.answer)
    println(question1.difficulty)
    println()
    println(question2.questionText)
    println(question2.answer)
    println(question2.difficulty)
    println()
    println(question3.questionText)
    println(question3.answer)
    println(question3.difficulty)
    println()
}
```

Surround the code accessing the questionText, answer, and difficulty properties with a call to the let() function on question1, question2, and question3. Replace the variable name in each lambda expression with it.

```kt
fun printQuiz() {
    question1.let {
        println(it.questionText)
        println(it.answer)
        println(it.difficulty)
    }
    println()
    question2.let {
        println(it.questionText)
        println(it.answer)
        println(it.difficulty)
    }
    println()
    question3.let {
        println(it.questionText)
        println(it.answer)
        println(it.difficulty)
    }
    println()
}
```

Update the code in main() to create an instance of the Quiz class named quiz.

```kt
fun main() {
    val quiz = Quiz()
}
```
Call printQuiz().

```kt
fun main() {
    val quiz = Quiz()
    quiz.printQuiz()
}
```

Run your code to verify that everything works.

```
Quoth the raven ___
nevermore
MEDIUM

The sky is green. True or false
false
EASY

How many days are there between full moons?
28
HARD
```

## Call an object's methods without a variable using apply()
One of the cool features of scope functions is that you can call them on an object before that object has even been assigned to a variable. For example, the apply() function is an extension function that can be called on an object using dot notation. The apply() function also returns a reference to that object so that it can be stored in a variable.

Update the code in main() to call the apply() function.

Call apply() after the closing parenthesis when creating an instance of the Quiz class. You can omit the parentheses when calling apply(), and use trailing lambda syntax.

```kt
val quiz = Quiz().apply {
}
```

Move the call to printQuiz() inside the lambda expression. You no longer need to reference the quiz variable or use dot notation.

```kt
val quiz = Quiz().apply {
    printQuiz()
}
```

The apply() function returns the instance of the Quiz class, but since you're no longer using it anywhere, remove the quiz variable. With the apply() function, you don't even need a variable to call methods on the instance of Quiz.

```kt
Quiz().apply {
    printQuiz()
}
```

Run your code. Note that you were able to call this method without a reference to the instance of Quiz. The apply() function returned the objects which were stored in quiz.

```kt
Quoth the raven ___
nevermore
MEDIUM

The sky is green. True or false
false
EASY

How many days are there between full moons?
28
HARD
```

While using scope functions isn't mandatory to achieve the desired output, the above examples illustrate how they can make your code more concise and avoid repeating the same variable name.

The above code demonstrates just two examples, but you're encouraged to bookmark and refer to the Scope Functions documentation as you encounter their usage later in the course.


# 9. Summary
You just got the chance to see several new Kotlin features in action. Generics allow data types to be passed as parameters to classes, enum classes define a limited set of possible values, and data classes help automatically generate some useful methods for classes.

You also saw how to create a singleton object—which is restricted to one instance, how to make it a companion object of another class, and how to extend existing classes with new get-only properties and new methods. Finally, you saw some examples of how scope functions can provide a simpler syntax when accessing properties and methods.

You'll see these concepts throughout the later units as you learn more about Kotlin, Android development, and Compose. You now have a better understanding of how they work and how they can improve the reusability and readability of your code.