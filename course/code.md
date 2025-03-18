# Lesson12. Material

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-woof.git
$ cd basic-android-kotlin-compose-training-woof
$ git checkout material
```


# Lesson13 Simple Animation

```
git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-woof.git
```

# Решения для практической работы Основы Kotlin

# 9. Код решения

## Мобильные уведомления
Решение использует оператор if/else для печати соответствующего сводного сообщения в зависимости от количества полученных уведомлений:

```kt
fun main() {
    val morningNotification = 51
    val eveningNotification = 135
    
    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)
}


fun printNotificationSummary(numberOfMessages: Int) {
    if (numberOfMessages < 100) {
        println("You have ${numberOfMessages} notifications.")
    } else {
        println("Your phone is blowing up! You have 99+ notifications.")
    }
}
```

## Цена билета в кино
Решение использует выражение when для возврата соответствующей цены билета в зависимости от возраста кинозрителя. Также используется простое выражение if/else для одной из ветвей выражения when, чтобы добавить дополнительное условие для стандартного ценообразования на билеты.

Цена билета в ветке else возвращает значение -1, что указывает на то, что установленная цена недействительна для ветки else. Лучшая реализация - это ветвь else, которая выбрасывает исключение. Об обработке исключений вы узнаете в следующих разделах.


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
    return when(age) {
        in 0..12 -> 15
        in 13..60 -> if (isMonday) 25 else 30
        in 61..100 -> 20
        else -> -1
    }
}
```

## Конвертер температуры
Решение требует передать функцию в качестве параметра функции printFinalTemperature(). Наиболее лаконичное решение предполагает передачу лямбда-выражений в качестве аргументов, использование ссылки на параметр it вместо имен параметров и использование синтаксиса лямбды в конце.


```kt
fun main() {    
        printFinalTemperature(27.0, "Celsius", "Fahrenheit") { 9.0 / 5.0 * it + 32 }
        printFinalTemperature(350.0, "Kelvin", "Celsius") { it - 273.15 }
        printFinalTemperature(10.0, "Fahrenheit", "Kelvin") { 5.0 / 9.0 * (it - 32) + 273.15 }
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

## Каталог песен
Решение содержит класс Song с конструктором по умолчанию, который принимает все необходимые параметры. Класс Song также имеет свойство isPopular, использующее пользовательскую функцию получения, и метод, печатающий описание самого себя. Вы можете создать экземпляр класса в функции main() и вызвать его методы, чтобы проверить правильность реализации. При написании больших чисел, таких как 1_000_000, можно использовать подчеркивания, чтобы сделать их более читабельными.


```kt
fun main() {    
    val brunoSong = Song("We Don't Talk About Bruno", "Encanto Cast", 2022, 1_000_000)
    brunoSong.printDescription()
    println(brunoSong.isPopular)
}


class Song(
    val title: String, 
    val artist: String, 
    val yearPublished: Int, 
    val playCount: Int
){
    val isPopular: Boolean
        get() = playCount >= 1000

    fun printDescription() {
        println("$title, performed by $artist, was released in $yearPublished.")
    }   
}
```

Когда вы вызываете функцию println() в методах экземпляра, программа может вывести этот вывод:


```
We Don't Talk About Bruno, performed by Encanto Cast, was released in 2022.
true
```

## Интернет-профиль
Решение содержит проверки нуля в различных операторах if/else для вывода различного текста в зависимости от того, являются ли различные свойства класса нулевыми:


```kt
fun main() {    
    val amanda = Person("Amanda", 33, "play tennis", null)
    val atiqah = Person("Atiqah", 28, "climb", amanda)
    
    amanda.showProfile()
    atiqah.showProfile()
}


class Person(val name: String, val age: Int, val hobby: String?, val referrer: Person?) {
    fun showProfile() {
        println("Name: $name")
        println("Age: $age")
        if(hobby != null) {
            print("Likes to $hobby. ")
        }
        if(referrer != null) {
            print("Has a referrer named ${referrer.name}")
            if(referrer.hobby != null) {
                print(", who likes to ${referrer.hobby}.")
            } else {
                print(".")
            }
        } else {
            print("Doesn't have a referrer.")
        }
        print("\n\n")
    }
}
```

## Складные телефоны
Чтобы класс Phone стал родительским классом, необходимо сделать его открытым, добавив перед именем класса ключевое слово open. Чтобы переопределить метод switchOn() в классе FoldablePhone, нужно сделать метод в классе Phone открытым, добавив перед методом ключевое слово open.

Решение содержит класс FoldablePhone с конструктором по умолчанию, который содержит аргумент по умолчанию для параметра isFolded. Класс FoldablePhone также имеет два метода для изменения свойства isFolded на значение true или false. Он также переопределяет метод switchOn(), унаследованный от класса Phone.

Вы можете создать экземпляр класса в функции main() и вызвать его методы, чтобы проверить правильность реализации.


```kt
open class Phone(var isScreenLightOn: Boolean = false){
    open fun switchOn() {
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

class FoldablePhone(var isFolded: Boolean = true): Phone() {
    override fun switchOn() {
        if (!isFolded) {
            isScreenLightOn = true
        }
    }
    
    fun fold() {
        isFolded = true
    }
    
    fun unfold() {
        isFolded = false
    }
}

fun main() {    
    val newFoldablePhone = FoldablePhone()
    
    newFoldablePhone.switchOn()
    newFoldablePhone.checkPhoneScreenLight()
    newFoldablePhone.unfold()
    newFoldablePhone.switchOn()
    newFoldablePhone.checkPhoneScreenLight()
}
```

The output is the following:

```
The phone screen's light is off.
The phone screen's light is on.
```

## Специальный аукцион
Решение использует оператор ?. safe call и оператор ?: Elvis для возврата правильной цены:

```kt
fun main() {
    val winningBid = Bid(5000, "Private Collector")
    
    println("Item A is sold at ${auctionPrice(winningBid, 2000)}.")
    println("Item B is sold at ${auctionPrice(null, 3000)}.")
}

class Bid(val amount: Int, val bidder: String)

fun auctionPrice(bid: Bid?, minimumPrice: Int): Int {
    return bid?.amount ?: minimumPrice
}
```


## Lesson15. ViewModel and State in Compose

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-unscramble.git
$ cd basic-android-kotlin-compose-training-unscramble
$ git checkout viewmodel
```

## Lesson 17. Адаптация под разные размеры экранов

https://github.com/google-developer-training/basic-android-kotlin-compose-training-reply-app

## Lesson 19. Корутины. Практика

git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-race-tracker.git 
cd basic-android-kotlin-compose-training-race-tracker

## Lesson 20. Retrofit

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-mars-photos.git
$ cd basic-android-kotlin-compose-training-mars-photos
$ git checkout repo-starter
```

## Практическая работа. Строим земноводных

```
https://github.com/google-developer-training/basic-android-kotlin-compose-training-amphibians
```
Название ветки: main


## Практическая работа. Получение и отображение данных из интернета

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-mars-photos.git
```


## Лекция: Загрузка и отображение данных из интернета

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-mars-photos.git
$ cd basic-android-kotlin-compose-training-mars-photos
$ git checkout coil-starter
```