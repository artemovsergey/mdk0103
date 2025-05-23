# Практика: приложение «Воды мне!

### Прежде чем начать

В этом разделе вы узнали о `WorkManager`, которая представляет собой библиотеку Jetpack для отложенной фоновой работы. Эта фоновая работа гарантированно будет выполняться, даже если вы закроете исходное приложение.

Изучая `WorkManager`, вы узнали, как определять работу в классе Worker, как создавать WorkRequest для Worker, а также как выставлять очередь и планировать работу.

В этом практическом задании вы используете изученные концепции и улучшите приложение `Water Me`!

Код решения доступен в конце. Чтобы извлечь максимальную пользу из этого учебного опыта, постарайтесь реализовать и устранить как можно больше неполадок, прежде чем изучать предоставленный код решения. Именно во время практической работы вы узнаете больше всего нового.

Предварительные условия
Изучение курса «Основы Android с Compose» в рамках коделаба «Расширенный менеджер работы и тестирование».
Что вам понадобится
Компьютер с доступом в интернет и Android Studio
Что вы будете создавать
В этом практическом задании вы воспользуетесь изученными концепциями и улучшите приложение «Water Me!».

В настоящее время приложение отображает список растений в виде прокручивающегося списка. Когда вы нажимаете на растение, приложение позволяет установить напоминание о необходимости полить его.

<div style="display:flex">
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-water-me-app/img/fb69d6519999f217_856.png"/>
    </div>
    <div>
        <img src="https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-water-me-app/img/d6b68d1e9f1026c5_856.png"/>
    </div>
</div>

Хотя вы можете выбрать время напоминания, уведомление о напоминании не отображается.

Ваша задача - реализовать фоновую работу для отображения уведомления о напоминании.

После завершения работы над кодом приложение сможет отображать уведомление о напоминании по истечении выбранного времени.



![](https://developer.android.com/static/codelabs/basic-android-kotlin-compose-practice-water-me-app/img/a8f9bceed83af5a9_856.png){style="width:400px"}


# 2. Получите стартовый код
Чтобы начать работу, загрузите стартовый код:

file_downloadDownload zip

Также вы можете клонировать репозиторий GitHub для кода:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-waterme.git
$ cd basic-android-kotlin-compose-training-waterme
$ git checkout starter
```

> Примечание: Код стартера находится в ветке starter клонированного репозитория.

Вы можете просмотреть код приложения Water Me! в этом репозитории GitHub.

Запуск стартового кода
Чтобы ознакомиться со стартовым кодом, выполните следующие шаги:

Откройте проект со стартовым кодом в Android Studio.
Запустите приложение на Android-устройстве или эмуляторе.
Теперь вы готовы приступить к кодированию!

# 3. Расписание уведомлений с помощью WorkManager
Функциональность приложения Water Me! в основном реализована, за исключением возможности запланировать уведомление о напоминании.

Код для создания уведомления находится в файле WaterReminderWorker.kt, который находится в пакете worker. Класс WaterReminderWorker расширяет класс CoroutineWorker, и код для создания уведомления находится в его методе doWork().

Поскольку уведомления могут быть для вас новой темой, этот код уже завершен.

```kt
override suspend fun doWork(): Result {

    val plantName = inputData.getString(nameKey)

    makePlantReminderNotification(
        applicationContext.resources.getString(R.string.time_to_water, plantName),
        applicationContext
    )

    return Result.success()
}
```

Ваша задача - создать OneTimeWorkRequest, вызывающий этот метод с правильными параметрами из WorkManagerWaterRepository.

Для получения дополнительной помощи обратитесь к разделу Background Work with WorkManager.

Создание рабочих запросов
Чтобы запланировать уведомление, необходимо реализовать метод scheduleReminder() в файле WorkManagerWaterRepository.kt.

Создайте переменную data с помощью Data.Builder. Данные должны состоять из одного строкового значения, где WaterReminderWorker.nameKey - ключ, а plantName, переданное в scheduleReminder(), - значение.
Создайте одноразовый запрос на выполнение работ с помощью класса WaterReminderWorker. Используйте длительность и единицу измерения, переданные в функцию scheduleReminder(), и установите входные данные в созданную вами переменную данных.
Вызовите метод workManager'а enqueueUniqueWork(). Передайте название растения, скомбинированное с продолжительностью, используйте REPLACE в качестве ExistingWorkPolicy и объект запроса на выполнение работы.

> Примечание: Передача имени растения, совмещенного с продолжительностью, позволяет установить несколько напоминаний для каждого растения. Вы можете запланировать одно напоминание через 5 секунд для пиона и другое напоминание через 1 день для пиона. Если вы передадите только название растения, то когда вы запланируете второе напоминание для пиона, оно заменит ранее запланированное напоминание для пиона.

Теперь ваше приложение должно работать так, как ожидалось.

### Дополнительное испытание (необязательно)
Для дополнительной практики кодирования измените параметры длительности времени на следующие:

5 секунд
1 минута
2 минуты
3 минуты

После завершения работы протестируйте каждую длительность, чтобы убедиться, что она работает так, как ожидалось.

> Примечание: В предоставленном коде решения не содержится реализация этой дополнительной задачи.


### Получите код решения
Чтобы загрузить код готовой кодовой лаборатории, вы можете воспользоваться следующими командами:

```
$ git clone https://github.com/google-developer-training/basic-android-kotlin-compose-training-waterme.git
$ cd basic-android-kotlin-compose-training-waterme
$ git checkout main
```