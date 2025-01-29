# Practice:Kotlin Fundamentals


# 1. Before you begin
Now that you put in the hard work to learn the basics of Kotlin programming, it's time to put what you learned into practice.

These exercises test your understanding of the concepts that you studied. They're based on real-world use cases, some of which you probably encountered before as a user.

Follow the instructions to find a solution for each exercise in Kotlin Playground. If you get stuck, some exercises have hints that can help you. The solution code for each exercise is available at the end, but it's recommended that you solve the exercises before you check your answers.

Work through the exercises at a pace that's comfortable for you. There are duration estimates for the exercises, but they're only estimates, so you don't have to adhere to them. Take as much time as you need to solve each problem thoughtfully. The solutions are only one way to solve the exercises, so feel free to experiment however you feel comfortable.

Prerequisites
Familiarity with the Kotlin Playground
Ability to define and call functions.
Knowledge of Kotlin programming basics, including variables, and the println() and main() functions.
Familiarity with Kotlin conditionals, including if/else and when statements and expressions
Familiarity with Kotlin lambda expressions
Knowledge of how to handle nullable variables.
Knowledge of how to create Kotlin classes and objects.
Completion of the Write conditionals in Kotlin, Use nullability in Kotlin, Use classes and objects in Kotlin, and Use function types and lambda expressions in Kotlin codelabs.


# 2. Mobile notifications
Typically, your phone provides you with a summary of notifications.

In the initial code provided in the following code snippet, write a program that prints the summary message based on the number of notifications that you received. The message should include:

The exact number of notifications when there are less than 100 notifications.
99+ as the number of notifications when there are 100 notifications or more.

```kt
fun main() {
    val morningNotification = 51
    val eveningNotification = 135
    
    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}


fun printNotificationSummary(numberOfMessages: Int) {
    // Fill in the code.
}
```

Complete the printNotificationSummary() function so that the program prints these lines:

```
You have 51 notifications.
Your phone is blowing up! You have 99+ notifications.
```

# 3. Movie-ticket price

Movie tickets are typically priced differently based on the age of moviegoers.

In the initial code provided in the following code snippet, write a program that calculates these age-based ticket prices:

A children's ticket price of $15 for people 12 years old or younger.
A standard ticket price of $30 for people between 13 and 60 years old. On Mondays, discount the standard ticket price to $25 for this same age group.
A senior ticket price of $20 for people 61 years old and older. Assume that the maximum age of a moviegoer is 100 years old.
A -1 value to indicate that the price is invalid when a user inputs an age outside of the age specifications.

```kt
fun main() {
    val child = 5
    val adult = 28
    val senior = 87
    
    val isMonday = true
    
    println("The movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}.")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
    println("The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}.")
}

fun ticketPrice(age: Int, isMonday: Boolean): Int {
    // Fill in the code.
}
```

Complete the ticketPrice() function so that the program prints these lines:

```
The movie ticket price for a person aged 5 is $15.
The movie ticket price for a person aged 28 is $25.
The movie ticket price for a person aged 87 is $20.
```


# 4. Temperature converter

There are three main temperature scales used in the world: Celsius, Fahrenheit, and Kelvin.

In the initial code provided in the following code snippet, write a program that converts a temperature from one scale to another with these formulas:

Celsius to Fahrenheit: ° F = 9/5 (° C) + 32
Kelvin to Celsius: ° C = K - 273.15
Fahrenheit to Kelvin: K = 5/9 (° F - 32) + 273.15
Note that the String.format("%.2f", /* measurement */ ) method is used to convert a number into a String type with 2 decimal places.

```kt
fun main() {
    // Fill in the code.
}


fun printFinalTemperature(
    initialMeasurement: Double, 
    initialUnit: String, 
    finalUnit: String, 
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}
```

Complete the main() function so that it calls the printFinalTemperature() function and prints the following lines. You need to pass arguments for the temperature and conversion formula. Hint: you may want to use Double values to avoid Integer truncation during division operations.

```
27.0 degrees Celsius is 80.60 degrees Fahrenheit.
350.0 degrees Kelvin is 76.85 degrees Celsius.
10.0 degrees Fahrenheit is 260.93 degrees Kelvin.
```

# 5. Song catalog
Imagine that you need to create a music-player app.

Create a class that can represent the structure of a song. The Song class must include these code elements:

Properties for the title, artist, year published, and play count
A property that indicates whether the song is popular. If the play count is less than 1,000, consider it unpopular.
A method that prints a song description in this format:
"[Title], performed by [artist], was released in [year published]."


# 6. Internet profile
Oftentimes, you're required to complete profiles for online websites that contain mandatory and non-mandatory fields. For example, you can add your personal information and link to other people who referred you to sign up for the profile.

In the initial code provided in the following code snippet, write a program which prints out a person's profile details.

```kt
fun main() {    
    val amanda = Person("Amanda", 33, "play tennis", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)
    
    amanda.showProfile()
    atiqah.showProfile()
}


class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?) {
    fun showProfile() {
       // Fill in code 
    }
}
```

Complete the showProfile() function so that the program prints these lines:

```
Name: Amanda
Age: 33
Likes to play tennis. Doesn't have a referrer.

Name: Atiqah
Age: 28
Likes to climb. Has a referrer named Amanda, who likes to play tennis.
```

# 7. Foldable phones

Typically, a phone screen turns on and off when the power button is pressed. In contrast, if a foldable phone is folded, the main inner screen on a foldable phone doesn't turn on when the power button is pressed.

In the initial code provided in the following code snippet, write a FoldablePhone class that inherits from the Phone class. It should contain the following:

A property that indicates whether the phone is folded.
A different switchOn() function behavior than the Phone class so that it only turns the screen on when the phone isn't folded.
Methods to change the folding state.

```kt
class Phone(var isScreenLightOn: Boolean = false){
    fun switchOn() {
        isScreenLightOn = true
    }
    
    fun switchOff() {
        isScreenLightOn = false
    }
    
    fun checkPhoneScreenLight() {
        val phoneScreenLight = if (isScreenLightOn) "on" else "off"
        println("The phone screen's light is $phoneScreenLight.")
    }
}
```

# 8. Special auction

Typically in an auction, the highest bidder determines the price of an item. In this special auction, if there's no bidder for an item, the item is automatically sold to the auction house at the minimum price.

In the initial code provided in the following code snippet, you're given an auctionPrice() function that accepts a nullable Bid? type as an argument:

```kt
fun main() {
    val winningBid = Bid(5000, "Private Collector")
    
    println("Item A is sold at ${auctionPrice(winningBid, 2000)}.")
    println("Item B is sold at ${auctionPrice(null, 3000)}.")
}

class Bid(val amount: Int, val bidder: String)
 
fun auctionPrice(bid: Bid?, minimumPrice: Int): Int {
   // Fill in the code.
}
```

Complete the auctionPrice() function so that the program prints these lines:

```
Item A is sold at 5000.
Item B is sold at 3000.
```

