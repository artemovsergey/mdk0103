# Практика: Постройте сетку

1. Прежде чем начать
Поздравляем! Вы создали свое первое приложение с прокручиваемым списком. Теперь вы готовы применить полученные знания на практике.

Это упражнение посвящено созданию компонентов, необходимых для построения прокручиваемого списка. Материал развивает знания, полученные в коделабе «Добавление прокручиваемого списка», и позволяет применить их для создания прокручиваемой сетки.

В некоторых разделах вам может потребоваться использовать составные элементы или модификаторы, с которыми вы, возможно, раньше не сталкивались. В таких случаях смотрите раздел «Ссылки», доступный для каждой задачи, где вы найдете ссылки на документацию, связанную с модификаторами, свойствами или составными элементами, с которыми вы не знакомы. Вы можете прочитать документацию и определить, как использовать эти понятия в приложении. Умение понимать документацию - важный навык, который необходимо развивать для расширения своих знаний.

Код решения доступен в конце, но попробуйте решить упражнения, прежде чем проверять ответы. Рассматривайте решение как один из способов реализации приложения.

Предварительные условия
Пройдите курс «Основы Android в Compose» в рамках кодовой лаборатории «Добавление прокручиваемого списка».
Что вам понадобится
Компьютер с доступом в Интернет и установленной программой Android Studio.
Ресурсы
Для создания кода для этих практических задач вам понадобятся следующие ресурсы

Изображения тем. Эти изображения представляют каждую тему в списке.
ic_grain.xml. Это декоративный значок, который отображается рядом с количеством курсов в теме.
Что вы будете строить
В этих практических задачах вы создадите приложение Courses с нуля. В приложении «Курсы» отображается список тем курсов.

Практические задачи разбиты на разделы, в которых вы будете создавать:

Класс данных темы курса:
Данные темы будут содержать изображение, название и количество связанных курсов в этой теме.

Композит для представления элемента сетки тем курса:
Каждый элемент темы отображает изображение, название, количество связанных курсов и декоративную иконку.

Составной элемент для отображения сетки этих элементов темы курса.
Итоговое приложение будет выглядеть следующим образом:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/97c449bee4a2029d_856.png)

# 2. Getting Started
Create a new project with Empty Activity template and minimum SDK 24.


# 3. Topic Data Class
In this section, you will create a class to store data for each course topic.

Look at the elements from the final application.

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/bf68e7995b2f47bd_856.png)

Каждая тема курса содержит три части уникальной информации. Используя уникальное содержимое каждого предмета в качестве ссылки, создайте класс для хранения этих данных.


# 4. Источник данных
В этом разделе вы создадите набор данных для сетки курсов.

Скопируйте следующие элементы в файл app/src/main/res/values/strings.xml:


```xml
<string name="architecture">Architecture</string>
<string name="crafts">Crafts</string>
<string name="business">Business</string>
<string name="culinary">Culinary</string>
<string name="design">Design</string>
<string name="fashion">Fashion</string>
<string name="film">Film</string>
<string name="gaming">Gaming</string>
<string name="drawing">Drawing</string>
<string name="lifestyle">Lifestyle</string>
<string name="music">Music</string>
<string name="painting">Painting</string>
<string name="photography">Photography</string>
<string name="tech">Tech</string>
```

Создайте пустой файл с именем DataSource.kt. Скопируйте в этот файл следующий код:

```kt
object DataSource {
    val topics = listOf(
        Topic(R.string.architecture, 58, R.drawable.architecture),
        Topic(R.string.crafts, 121, R.drawable.crafts),
        Topic(R.string.business, 78, R.drawable.business),
        Topic(R.string.culinary, 118, R.drawable.culinary),
        Topic(R.string.design, 423, R.drawable.design),
        Topic(R.string.fashion, 92, R.drawable.fashion),
        Topic(R.string.film, 165, R.drawable.film),
        Topic(R.string.gaming, 164, R.drawable.gaming),
        Topic(R.string.drawing, 326, R.drawable.drawing),
        Topic(R.string.lifestyle, 305, R.drawable.lifestyle),
        Topic(R.string.music, 212, R.drawable.music),
        Topic(R.string.painting, 172, R.drawable.painting),
        Topic(R.string.photography, 321, R.drawable.photography),
        Topic(R.string.tech, 118, R.drawable.tech)
    )
}
```

# 5. Элемент тематической сетки
Создайте композит для представления элемента сетки тем.

Итоговый скриншот
После завершения реализации макет элемента темы должен соответствовать скриншоту ниже:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/f7e47f86ab7ea8b3_856.png)

Спецификации пользовательского интерфейса
Используйте следующие спецификации пользовательского интерфейса:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/3bdfc5ea4f3d619d_856.png)

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/b051bb634fa06501_856.png)

Подсказка: какая композиция располагает своих детей вертикально, а какая - горизонтально?

# 6. Сетка тем курсов
Когда элемент сетки тем создан, его можно использовать для создания сетки тем курсов.

В этом упражнении вы используете элемент сетки composable для создания сетки с двумя колонками.

Итоговый скриншот
После завершения реализации ваш дизайн должен соответствовать скриншоту ниже:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/97c449bee4a2029d_856.png)

Спецификация пользовательского интерфейса
Используйте следующие спецификации пользовательского интерфейса:

![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-grid/img/aee57a3a525e91bb_856.png)