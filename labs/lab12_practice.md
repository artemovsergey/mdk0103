# Практика: поведение при нажатии

1. Прежде чем начать
В этом разделе вы узнали, как добавить кнопку в приложение и как изменить приложение, чтобы оно реагировало на нажатие кнопки. Теперь пришло время применить полученные знания на практике, создав приложение.

Вы создадите приложение под названием Lemonade app. Сначала прочитайте требования к приложению Lemonade, чтобы понять, как оно должно выглядеть и вести себя. Если вы хотите испытать себя, вы можете создать приложение самостоятельно. Если вы застряли, читайте последующие разделы, чтобы получить больше подсказок и советов о том, как разбить проблему на части и решить ее шаг за шагом.

Проработайте эту практическую задачу в удобном для вас темпе. Уделите столько времени, сколько вам нужно для создания каждой части функциональности приложения. Код решения для приложения Lemonade доступен в конце, но мы рекомендуем вам попробовать собрать приложение самостоятельно, прежде чем проверять решение. Помните, что представленное решение не является единственным способом создания приложения Lemonade, поэтому вы можете создать его по-другому, если требования к приложению соблюдены.

Необходимые условия
Уметь создать простой макет пользовательского интерфейса в Compose с композитными элементами текста и изображений
Уметь создавать интерактивное приложение, реагирующее на нажатие кнопки
Базовое понимание композиции и рекомпозиции
Знакомство с основами языка программирования Kotlin, включая функции, переменные, условия и лямбды
Что вам понадобится
Компьютер с доступом в Интернет и установленной программой Android Studio.



# 2. Обзор приложения
Вы поможете нам воплотить в жизнь нашу идею создания цифрового лимонада! Цель - создать простое интерактивное приложение, которое позволит вам выжимать сок из лимонов, касаясь изображения на экране, пока у вас не получится стакан лимонада. Считайте это метафорой или просто забавным способом скоротать время!


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/dfcc3bc3eb43e4dd_856.png)


Вот как работает приложение:

Когда пользователь впервые запускает приложение, он видит лимонное дерево. Там есть ярлык, который предлагает коснуться изображения лимонного дерева, чтобы «выбрать» лимон с дерева.
После нажатия на лимонное дерево пользователь видит лимон. Ему предлагается коснуться лимона, чтобы «выжать» его и приготовить лимонад. Чтобы сжать лимон, нужно коснуться его несколько раз. Количество нажатий, необходимое для выжимания лимона, каждый раз разное и представляет собой случайно сгенерированное число от 2 до 4 (включительно).
После того как они коснутся лимона необходимое количество раз, они увидят освежающий стакан лимонада! Им предлагается коснуться стакана, чтобы «выпить» лимонад.
После того как они коснутся стакана с лимонадом, они увидят пустой стакан. Их просят коснуться пустого стакана, чтобы начать сначала.
После того как они коснутся пустого стакана, они увидят лимонное дерево и смогут начать процесс заново. Больше лимонада, пожалуйста!
Вот более крупные скриншоты того, как выглядит приложение:


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/89cec3022a462039_856.png)

Для каждого этапа приготовления лимонада на экране есть свое изображение и текстовый ярлык, а также разное поведение приложения в ответ на нажатие. Например, когда пользователь нажимает на лимонное дерево, приложение показывает лимон.

Ваша задача - создать макет пользовательского интерфейса приложения и реализовать логику, позволяющую пользователю пройти все этапы приготовления лимонада.

# 3. Начните работу
Создайте проект
В Android Studio создайте новый проект с шаблоном Empty Activity со следующими данными:

Имя: Lemonade
Название пакета: com.example.lemonade
Минимальный SDK: 24
Когда приложение успешно создано и проект собран, переходите к следующему разделу.

Добавление изображений
Вам предоставляются четыре векторных файла для рисования, которые вы будете использовать в приложении Lemonade.

Получите эти файлы:

Скачайте zip-файл с изображениями для приложения.
Дважды щелкните по zip-файлу. Этот шаг распакует изображения в папку.
Добавьте изображения в папку drawable вашего приложения. Если вы не помните, как это сделать, обратитесь к кодебалету «Создание интерактивного приложения Dice Roller».

Папка вашего проекта должна выглядеть как на следующем снимке экрана, где активы lemon_drink.xml, lemon_restart.xml, lemon_squeeze.xml и lemon_tree.xml теперь находятся в каталоге res > drawable:


![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/ccc5a4aa8a7e9fbd_856.png)

Double click a vector drawable file to see the image preview.
Select the Design pane (not the Code or Split views) to see a full-width view of the image.


After the image files are included in your app, you can refer to them in your code. For example, if the vector drawable file is called lemon_tree.xml, then in your Kotlin code, you can refer to the drawable using its resource ID in the format of R.drawable.lemon_tree.

Add string resources
Add the following strings to your project in the res > values > strings.xml file:

Tap the lemon tree to select a lemon
Keep tapping the lemon to squeeze it
Tap the lemonade to drink it
Tap the empty glass to start again
The following strings are also needed in your project. They're not displayed on the screen in the user interface, but these are used for the content description of the images in your app to describe what the images are. Add these additional strings in your app's strings.xml file:

Lemon tree
Lemon
Glass of lemonade
Empty glass
If you don't remember how to declare string resources in your app, see the Create an interactive Dice Roller app codelab or refer to String. Give each string resource an appropriate identifier name that describes the value it contains. For example, for the string "Lemon", you can declare it in the strings.xml file with the identifier name lemon_content_description, and then refer to it in your code using the resource ID: R.string.lemon_content_description.

Steps of making lemonade
Now you have the string resources and image assets that are needed to implement the app. Here's a summary of each step of the app and what is shown on the screen:

Step 1:

Text: Tap the lemon tree to select a lemon
Image: Lemon tree (lemon_tree.xml)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/b2b0ae4400c0d06d_856.png)

Step 2:

Text: Keep tapping the lemon to squeeze it
Image: Lemon (lemon_squeeze.xml)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/7c6281156d027a8_856.png)

Step 3:

Text: Tap the lemonade to drink it
Image: Full glass of lemonade (lemon_drink.xml)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/38340dfe3df0f721_856.png)

Step 4:

Text: Tap the empty glass to start again
Image: Empty glass (lemon_restart.xml)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/e9442e201777352b_856.png)

Add visual polish
To make your version of the app look like these final screenshots, there are a couple more visual adjustments to make in the app:

Increase the font size of the text so that it's larger than the default font size (such as 18sp).
Add additional space in between the text label and the image below it, so they're not too close to each other (such as 16dp).
Give the button an accent color and slightly rounded corners to let the users know that they can tap the image.
If you want to challenge yourself, build the rest of the app based on the description of how it should work. If you want more guidance, proceed to the next section.

> Warning: Do not proceed with reading the rest of the instructions unless you want hints revealed. It's recommended for you to try the problem yourself and only consult the rest of the instructions if you get stuck.

# 4. Plan out how to build the app
When building an app, it's a good idea to get a minimal working version of the app done first. Then gradually add more functionality until you complete all desired functionality. Identify a small piece of end-to-end functionality that you can build first.

In the Lemonade app, notice that the key part of the app is transitioning from one step to another with a different image and text label shown each time. Initially, you can ignore the special behavior of the squeeze state because you can add this functionality later after you build the foundation of the app.

Below is a proposal of the high-level overview of the steps that you can take to build the app:

Build the UI layout for the first step of making lemonade, which prompts the user to select a lemon from the tree. You can skip the border around the image for now because that's a visual detail that you can add later.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/b2b0ae4400c0d06d_856.png)

Implement the behavior in the app so that when the user taps the lemon tree, the app shows a lemon image and its corresponding text label. This covers the first two steps of making lemonade.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/adbf0d217e1ac77d_856.png)

Add code so that the app displays the rest of the steps to make lemonade, when the image is tapped each time. At this point, a single tap on the lemon can transition to displaying the glass of lemonade.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/33a36bcbe200af53_856.png)

Add custom behavior for the lemon squeeze step, so that the user needs to "squeeze", or tap, the lemon a specific number of times that's randomly generated from 2 to 4.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/a23102cb6c068174_856.png)

Finalize the app with any other necessary visual polish details. For example, change the font size and add a border around the image to make the app look more polished. Verify that the app follows good coding practices, such as adhering to the Kotlin coding style guidelines and adding comments to your code.

If you can use these high-level steps to guide you in the implementation of the Lemonade app, go ahead and build the app on your own. If you find that you need additional guidance on each of these five steps, proceed to the next section.


# 5. Implement the app
Build the UI layout
First modify the app so that it displays the image of the lemon tree and its corresponding text label, which says Tap the lemon tree to select a lemon, in the center of the screen. There should also be 16dp of space in between the text and the image below it.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/b2b0ae4400c0d06d_856.png)

If it helps, you can use the following starter code in the MainActivity.kt file:

```kt
package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContent {
           LemonadeTheme {
               LemonApp()
           }
       }
   }
}

@Composable
fun LemonApp() {
   // A surface container using the 'background' color from the theme
   Surface(
       modifier = Modifier.fillMaxSize(),
       color = MaterialTheme.colorScheme.background
   ) {
       Text(text = "Hello there!")
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
   LemonadeTheme {
       LemonApp()
   }
}
```

This code is similar to the code that's autogenerated by Android Studio. However, instead of a Greeting() composable, there's a LemonApp() composable defined and it doesn't expect a parameter. The DefaultPreview() composable is also updated to use the LemonApp() composable so you can preview your code easily.

After you enter this code in Android Studio, modify the LemonApp() composable, which should contain the contents of the app. Here are some questions to guide your thought process:

What composables will you use?
Is there a standard Compose layout component that can help you arrange the composables into the desired positions?

> Important: To make your app accessible for more users, remember to set content descriptions on the images to describe what the image contains.

Go and implement this step so that you have the lemon tree and text label displayed in your app, when your app launches. Preview your composable in Android Studio to see how the UI looks as you modify your code. Run the app to ensure that it looks like the screenshot that you saw earlier in this section.

Return to these instructions when you're done, if you want more guidance on how to add behavior when the image is tapped on.

Add click behavior
Next you will add code so that when the user taps the image of the lemon tree, the image of the lemon appears along with the text label Keep tapping the lemon to squeeze it. In other words, when you tap the lemon tree, it causes the text and image to change.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/adbf0d217e1ac77d_856.png)

Earlier in this pathway, you learned how to make a button clickable. In the case of the Lemonade app, there's no Button composable. However, you can make any composable, not just buttons, clickable when you specify the clickable modifier on it. For an example, see the clickable documentation page.

What should happen when the image is clicked? The code to implement this behavior is non-trivial, so take a step back to revisit a familiar app.

Look at the Dice Roller app
Revisit the code from the Dice Roller app to observe how the app displays different dice images based on the value of the dice roll:

MainActivity.kt in Dice Roller app

```kt
...

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
   var result by remember { mutableStateOf(1) }
   val imageResource = when(result) {
       1 -> R.drawable.dice_1
       2 -> R.drawable.dice_2
       3 -> R.drawable.dice_3
       4 -> R.drawable.dice_4
       5 -> R.drawable.dice_5
       else -> R.drawable.dice_6
   }
   Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
       Image(painter = painterResource(id = imageResource), contentDescription = result.toString())
       Button(onClick = { result = (1..6).random() }) {
          Text(stringResource(id = R.string.roll))
       }
   }
}

...
```

Answer these questions about the Dice Roller app code:

Which variable's value determines the appropriate dice image to display?
What action from the user triggers that variable to change?
The DiceWithButtonAndImage() composable function stores the most recent dice roll, in the result variable, which was defined with the remember composable and mutableStateOf() function in this line of code:

```kt
var result by remember { mutableStateOf(1) }
```

When the result variable gets updated to a new value, Compose triggers recomposition of the DiceWithButtonAndImage() composable, which means that the composable will execute again. The result value is remembered across recompositions, so when the DiceWithButtonAndImage() composable runs again, the most recent result value is used. Using a when statement on the value of the result variable, the composable determines the new drawable resource ID to show and the Image composable displays it.

Apply what you learned to the Lemonade app
Now answer similar questions about the Lemonade app:

Is there a variable that you can use to determine what text and image should be shown on the screen? Define that variable in your code.
Can you use conditionals in Kotlin to have the app perform different behavior based on the value of that variable? If so, write that conditional statement in your code.
What action from the user triggers that variable to change? Find the appropriate place in your code where that happens. Add code there to update the variable.

> Note: You may want to represent each step of making lemonade with a number. For example, step 1 of the app has the image of the lemon tree and clicking the image goes to step 2 of the app. This can help you organize which text string goes with which image.
https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/270ecd406fc30120_856.png

This section can be quite challenging to implement and requires changes in multiple places of your code to work correctly. Don't be discouraged if the app doesn't work as you expect right away. Remember that there are multiple correct ways to implement this behavior.

When you're done, run the app and verify that it works. When you launch the app, it should show the image of the lemon tree and its corresponding text label. A single tap of the image of the lemon tree should update the text label and show the image of the lemon. A tap on the lemon image shouldn't do anything for now.

Add remaining steps
Now your app can display two of the steps to make lemonade! At this point, your LemonApp() composable may look something like the following code snippet. It's okay if your code looks slightly different as long as the behavior in the app is the same.

```kt
...
@Composable
fun LemonApp() {
   // Current step the app is displaying (remember allows the state to be retained
   // across recompositions).
   var currentStep by remember { mutableStateOf(1) }

   // A surface container using the 'background' color from the theme
   Surface(
       modifier = Modifier.fillMaxSize(),
       color = MaterialTheme.colorScheme.background
   ) {
       when (currentStep) {
           1 -> {
               Column (
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxSize()
               ){
                   Text(text = stringResource(R.string.lemon_select))
                   Spacer(modifier = Modifier.height(32.dp))
                   Image(
                       painter = painterResource(R.drawable.lemon_tree),
                       contentDescription = stringResource(R.string.lemon_tree_content_description),
                       modifier = Modifier
                           .wrapContentSize()
                           .clickable {
                               currentStep = 2
                           }
                   )
               }
           }
           2 -> {
               Column (
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center,
                   modifier = Modifier.fillMaxSize()
               ){
                   Text(text = stringResource(R.string.lemon_squeeze))
                   Spacer(modifier = Modifier.height(32
                       .dp))
                   Image(
                       painter = painterResource(R.drawable.lemon_squeeze),
                       contentDescription = stringResource(R.string.lemon_content_description),
                       modifier = Modifier.wrapContentSize()
                   )
               }
           }
       }
   }
}
...
```

Next you'll add the rest of the steps to make lemonade. A single tap of the image should move the user to the next step of making lemonade, where the text and image both update. You will need to change your code to make it more flexible to handle all steps of the app, not just the first two steps.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/2c0f70529e0cf69d_856.png)

To have different behavior each time that the image is clicked, you need to customize the clickable behavior. More specifically, the lambda that's executed when the image is clicked needs to know which step we're moving to.

You may start to notice that there's repeated code in your app for each step of making lemonade. For the when statement in the previous code snippet, the code for case 1 is very similar to case 2 with small differences. If it's helpful, create a new composable function, called LemonTextAndImage() for example, that displays text above an image in the UI. By creating a new composable function that takes some input parameters, you have a reusable function that's useful in multiple scenarios as long as you change the inputs that you pass in. It's your job to figure out what the input parameters should be. After you create this composable function, update your existing code to call this new function in relevant places.

Another advantage to having a separate composable like LemonTextAndImage() is that your code becomes more organized and robust. When you call LemonTextAndImage(), you can be sure that both the text and image will get updated to the new values. Otherwise, it's easy to accidentally miss one case where an updated text label is displayed with the wrong image.

Here's one additional hint: You can even pass in a lambda function to a composable. Be sure to use function type notation to specify what type of function should be passed in. In the following example, a WelcomeScreen() composable is defined and accepts two input parameters: a name string and an onStartClicked() function of type () -> Unit. That means that the function takes no inputs (the empty parentheses before the arrow) and has no return value ( the Unit following the arrow). Any function that matches that function type () -> Unit can be used to set the onClick handler of this Button. When the button is clicked, the onStartClicked() function is called.

```kt
@Composable
fun WelcomeScreen(name: String, onStartClicked: () -> Unit) {
    Column {
        Text(text = "Welcome $name!")
        Button(
            onClick = onStartClicked
        ) {
            Text("Start")
        }
    }
}
```

Passing in a lambda to a composable is a useful pattern because then the WelcomeScreen() composable can be reused in different scenarios. The user's name and the button's onClick behavior can be different each time because they're passed in as arguments.

With this additional knowledge, go back to your code to add the remaining steps of making lemonade to your app.

Return to these instructions if you want additional guidance on how to add the custom logic around squeezing the lemon a random number of times.

## Add squeeze logic
Great job! Now you have the basis of the app. Tapping the image should move you from one step to the next. It's time to add the behavior of needing to squeeze the lemon multiple times to make lemonade. The number of times that the user needs to squeeze, or tap, the lemon should be a random number between 2 to 4 (inclusive). This random number is different each time that the user picks a new lemon from the tree.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/52b9b7321e689600_856.png)

Here are some questions to guide your thought process:

How do you generate random numbers in Kotlin?
At what point in your code should you generate the random number?
How do you ensure that the user tapped the lemon the required number of times before moving to the next step?
Do you need any variables stored with the remember composable so that the data doesn't get reset every time the screen is redrawn?
When you're done implementing this change, run the app. Verify that it takes multiple taps of the image of the lemon to move to the next step, and that the number of taps required each time is a random number between 2 and 4. If a single tap of the lemon image displays the lemonade glass, go back to your code to figure out what's missing and try again.

Return to these instructions if you want additional guidance on how to finalize the app.

Finalize the app
You're almost done! Add some last details to polish up the app.

As a reminder, here are the final screenshots of how the app looks:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/b2b0ae4400c0d06d_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/7c6281156d027a8_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/38340dfe3df0f721_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-button-click-practice-problem/img/e9442e201777352b_856.png)

Vertically and horizontally center the text and images within the screen.
Set the font size of the text to 18sp.
Add 16dp of space between the text and image.
Add a thin border of 2dp around the images with slightly rounded corners of 4dp. The border has an RGB color value of 105 for red, 205 for green, and 216 for blue. For examples of how to add a border, you can Google search it. Or you can refer to the documentation on Border.
When you've completed these changes, run your app and then compare it with the final screenshots to ensure that they match.

As part of good coding practices, go back and add comments to your code, so that anyone who reads your code can understand your thought process more easily. Remove any import statements at the top of your file that aren't used in your code. Ensure that your code follows the Kotlin style guide. All these efforts will help make your code more readable by other people and easier to maintain!

Well done! You did an amazing job implementing the Lemonade app! That was a challenging app with many parts to figure out. Now treat yourself to a refreshing glass of lemonade. Cheers!