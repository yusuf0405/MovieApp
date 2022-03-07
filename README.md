# Программа, полностью написанная на Kotlin с компонентами архитектуры MVVM и чистой архитектурой

Простое приложение, которое написано в чистой архитектуре с использованием языка Kotlin.
Он показывает список фильмов и актеров, подробную информацию о них, возможность поиска, а также добавления в избранное.
Приложение написано в учебных целях.

![image](https://firebasestorage.googleapis.com/v0/b/fruit-market-e5149.appspot.com/o/app_movie_.png?alt=media&token=310daf1a-fff1-48cb-b7ef-a29b5f711a64)
![image](https://firebasestorage.googleapis.com/v0/b/fruit-market-e5149.appspot.com/o/person_search.png?alt=media&token=71a5eea0-0d26-4cc6-8707-94a2ca1c329b)
![image](https://firebasestorage.googleapis.com/v0/b/fruit-market-e5149.appspot.com/o/details_movie.png?alt=media&token=db3221c3-a15b-4927-9f7c-7462c1e19bb3)
![image](https://firebasestorage.googleapis.com/v0/b/fruit-market-e5149.appspot.com/o/favorite_person.png?alt=media&token=bcc4dcde-fb7f-45d5-9fdd-d3b2b8e36a91)

## Libraries

### Android Jetpack
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding) Привязка представлений - это функция, которая позволяет вам легче писать код, взаимодействующий с представлениями.

* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) Интерфейс, который автоматически реагирует на события жизненного цикла.
  
* [Room](https://developer.android.com/jetpack/androidx/releases/room?gclsrc=aw.ds&gclid=Cj0KCQiA09eQBhCxARIsAAYRiyl9xrvsl7MKTcUVF73v6FB8EQyG-U8YVwhZyhA5rzq2UhpBvOUOUuEaAr5jEALw_wcB) Библиотека Room persistence предоставляет уровень абстракции поверх SQLite, обеспечивающий более надежный доступ к базе данных при одновременном использовании всех возможностей SQLite.
  
* [Paging 2](https://developer.android.com/topic/libraries/architecture/paging) Библиотека подкачки помогает загружать и отображать небольшие фрагменты данных одновременно. Загрузка частичных данных по требованию снижает использование пропускной способности сети и системных ресурсов.
  
* [Navigation](https://developer.android.com/guide/navigation?gclsrc=aw.ds&gclid=Cj0KCQiA09eQBhCxARIsAAYRiymyM6hTEs0cGr5ZCXOWtLhVUwDK1O86vf8V_Uq2DWvVYNFZwPFznzAaAllMEALw_wcB) Навигация относится к взаимодействиям, которые позволяют пользователям перемещаться по различным частям контента в вашем приложении, входить в них и выходить из них. Навигационный компонент Android Jetpack помогает вам осуществлять навигацию, от простых нажатий кнопок до более сложных шаблонов, таких как панели приложений и панель навигации. Навигационный компонент также обеспечивает последовательный и предсказуемый пользовательский интерфейс, придерживаясь установленного набора принципов.

* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) Объекты данных, которые уведомляют представления об изменениях базовой базы данных.

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) Данные, связанные с пользовательским интерфейсом, которые не уничтожаются при вращении приложения. Легко планируйте асинхронные задачи для оптимального выполнения.

### Image
* [Glide](https://github.com/bumptech/glide) Библиотека загрузки и кэширования изображений для Android, ориентированная на плавную прокрутку.
  
* [Picasso](https://square.github.io/picasso/) Picasso позволяет вам легко загружать изображения в ваше приложение - часто в одной строке кода.

### HTTP
* [Retrofit2](https://github.com/square/retrofit) Типобезопасный HTTP-клиент для Android и Java.

* [OkHttp](https://github.com/square/okhttp) Клиент HTTP + HTTP / 2 для приложений Android и Java.

### Coroutines
* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) Сопрограммы - это богатая библиотека для сопрограмм, разработанная компанией JetBrains. Он содержит ряд высокоуровневых примитивов с поддержкой сопрограмм, которые рассматриваются в этом руководстве, включая запуск, асинхронность и другие.

### Player
* [Youtube Player](https://github.com/PierfrancescoSoffritti/android-youtube-player) Android-youtube-player - это стабильный и настраиваемый проигрыватель YouTube с открытым исходным кодом для Android. Он обеспечивает простое представление, которое может быть легко интегрировано в каждое Activity / Fragment.

### DI
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Hilt - это библиотека внедрения зависимостей для Android, которая сокращает время выполнения ручного внедрения зависимостей в ваш проект. Выполнение ручного внедрения зависимостей требует, чтобы вы создавали каждый класс и его зависимости вручную, а также использовали контейнеры для повторного использования зависимостей и управления ими.