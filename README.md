# Проект автоматизации тестирования для [demoqa.com](https://demoqa.com/)  

---

<img src="https://demoqa.com/images/Toolsqa.jpg" style="width:100%;" alt="Логотип">

---

## Содержание
* <a href="#о-проекте">О проекте</a>
* <a href="#технологии-и-инструменты">Технологии и инструменты</a>
* <a href="#тест-кейсы">Тест-кейсы</a>
* <a href="#запуски-тестов-из-терминала">Запуски тестов из терминала</a>
* <a href="#интеграции">Интеграции</a>
  * <a href="#сборка-в-Jenkins">Сборка в Jenkins</a>
  * <a href="#интеграция-с-Allure-Report">Интеграция с Allure Report</a>
  * <a href="#интеграция-с-Allure-TestOps">Интеграция с Allure TestOps</a>
  * <a href="#интеграция-с-Jira">Интеграция с Jira</a>
* <a href="#уведомление-о-результатах-тестов-в-telegram">Уведомление о результатах тестов в Telegram</a>
* <a href="#видео-прохождения-тестов-в-selenoid">Видео прохождения тестов в Selenoid</a>

---
## <a id="о-проекте"></a>О проекте

Данный проект представляет собой автоматизацию тестирования приложения [demoqa.com](https://demoqa.com/) с использованием Java, Selenide, Rest Assured и других инструментов. В проекте реализовано как UI-, так и API-тестирование, включая сценарии авторизации, управления корзиной и проверки отображения пользовательских данных.

<img src="assets/images/main.png" style="width:100%;" alt="Главная страница">

---
## <a id="технологии-и-инструменты"></a>Технологии и инструменты
<p align="center">
  <a href="https://www.java.com/"><img src="assets/logo/Java.svg" alt="Java Logo" height="50" width="50"/></a>
  <a href="https://www.jetbrains.com/idea/"><img src="assets/logo/IntelliJ_IDEA.svg" alt="Intellij_IDEA Logo" height="50" width="50"/></a>
  <a href="https://github.com/"><img src="assets/logo/GitHub.svg" alt="Github" height="50" width="50"/></a>
  <a href="https://junit.org/junit5/"><img src="assets/logo/JUnit5.svg" alt="JUnit 5" height="50" width="50"/></a>
  <a href="https://rest-assured.io/"><img src="assets/logo/RestAssured.png" alt="RestAssured" height="50" width="50"/></a>
  <a href="https://gradle.org/"><img src="assets/logo/Gradle.svg" alt="Gradle" height="50" width="50"/></a>
  <a href="https://selenide.org/"><img src="assets/logo/Selenide.svg" alt="Selenide" height="50" width="50"/></a>
  <a href="https://aerokube.com/selenoid/"><img src="assets/logo/Selenoid.svg" alt="Selenoid" height="50" width="50"/></a>
  <a href="https://qameta.io/"><img src="assets/logo/AllureTestOps.svg" alt="AllureTest" height="50" width="50"/></a>
  <a href="https://github.com/allure-framework"><img src="assets/logo/Allure_Report.svg" alt="Allure" height="50" width="50"/></a>
  <a href="https://www.jenkins.io/"><img src="assets/logo/Jenkins.svg" alt="Jenkins" height="50" width="50"/></a>
  <a href="https://www.atlassian.com/software/jira"><img src="assets/logo/Jira.svg" alt="Jira" height="50" width="50"/></a>
  <a href="https://web.telegram.org/"><img src="assets/logo/Telegram.svg" alt="Telegram" height="50" width="50"/></a>
</p>


Проект реализован с использованием следующих библиотек и инструментов:
* **[Java](https://www.java.com/)** — основной язык программирования.
* **[Selenide](https://selenide.org/)** — инструмент для автоматизации UI-тестов.
* **[Selenoid](https://aerokube.com/selenoid/)** — платформа для запуска тестов в разных браузерах.
* **[Rest Assured](https://rest-assured.io/)** — библиотека для тестирования API.
* **[JUnit 5](https://junit.org/junit5/)** — фреймворк для модульного тестирования.
* **[Gradle](https://gradle.org/)** — система сборки проекта.
* **[Allure Report](https://github.com/allure-framework/allure2)** — инструмент для генерации детализированных отчетов.
* **[Jenkins](https://www.jenkins.io/)** — сервер для организации CI/CD процессов.
* **[Allure TestOps](https://qameta.io/)** — платформа для управления тестами.
* **[Jira](https://www.atlassian.com/ru/software/jira)** — система управления задачами.
* **Telegram бот** для уведомления о результатах тестирования.

---
## <a id="тест-кейсы"></a>Тест-кейсы

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

В проекте реализованы автотесты (UI и API) и ручные тесты. Они проверяют функциональность приложения, выявляют возможные ошибки и помогают улучшить качество продукта.
Для каждой группы тестов используются отдельные методы и фреймворки, чтобы обеспечить точность и стабильность выполнения.


<details>
  <summary><strong style="color:#2a9d8f;">🌐 UI Тесты</strong></summary>

**UI тесты** проверяют функциональность сайта с точки зрения пользователя. Основное внимание уделяется заполнению форм, аутентификации и отображению информации на сайте.

#### 1. **Авторизация (Login)**
1. Проверка успешного входа зарегистрированного пользователя.
2. Проверка ошибки при вводе некорректных учетных данных.
3. Проверка валидации обязательных полей.

#### 2. **Книжный магазин (BookStore)**
1. **Удаление книги из профиля пользователя через UI**.

#### 3. **Форма регистрации студента (Student Registration Form)**
1. Проверка заполнения всех полей формы.
2. Проверка заполнения только обязательных полей формы.
3. Проверка валидации незаполненных обязательных полей.

</details>

<details>
  <summary><strong style="color:#e76f51;">💻 API Тесты</strong></summary>

**API тесты** обеспечивают проверку функциональности серверной части сайта. Это включает в себя проверку успешной авторизации, взаимодействие с данными пользователей через API и выполнение операций, таких как регистрация, удаление пользователей и обновление данных.

#### 1. **Учетная запись пользователя (Account)**
1. Регистрация нового пользователя.
2. Удаление пользователя.
3. Проверка ошибок при вводе некорректных данных.

#### 2. **Книжный магазин (BookStore)**
1. **Удаление всех книг пользователя**.

</details> 

<details> <summary><strong style="color:#f4a261;">📝 Ручные Тесты</strong></summary>

1. Проверка пагинации на странице с книгами.
2. Проверка поиска книги по названию.
   
</details>

---

## <a id="запуски-тестов-из-терминала"></a>Запуски тестов из терминала

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

Тесты можно запускать из терминала с помощью Gradle. Доступны различные параметры для настройки окружения, браузера и учетных данных.  
В проекте предусмотрены задачи для удобного запуска тестов разных уровней.

### Тестовые задачи
<details>
  <summary>Развернуть список тестовых задач</summary>

- **`test`** – выполняет **все тесты** (по умолчанию).
- **`api`** – запускает только **API-тесты**, проверяющие серверную логику.
- **`ui`** – выполняет только **UI-тесты**, проверяющие пользовательский интерфейс.

Дополнительно можно передавать параметры для настройки окружения, браузера и учетных данных.

</details>

### Параметры для запуска тестов

<details>
  <summary>Развернуть список параметров</summary>

| Параметр                | Описание                                                      | Пример значения          |
|:------------------------|:--------------------------------------------------------------|:-------------------------|
| `${TestType}`           | Тип тестов для выполнения (`test`, `api`, `ui`).              | `test`                   |
| `-Denv`                 | Среда выполнения тестов (`local`, `remote`).                  | `remote`                 |
| `-Drwhost`              | Адрес удаленного сервера, на котором выполняются тесты.       | `https://selenoid.com`   |
| `-Dlogin`               | Логин для авторизации на удаленном сервере.                   | `user`                   |
| `-Dbrowser`             | Браузер, используемый для тестов.                             | `chrome`, `firefox`      |
| `-DbrowserVersion`      | Версия браузера.                                              | `125.0`, `131.0`         |
| `-DbrowserSize`         | Размер окна браузера.                                         | `1920x1080`              |
| `-DvideoHost`           | Хост для доступа к видео прохождения тестов.                  | `https://video.selenoid` |
| `-DprofileUserName`     | Имя пользователя для авторизации в тестируемом приложении.    | `evgensh`                |
| `-DprofileUserPassword` | Пароль пользователя для авторизации в тестируемом приложении. | `Evgensh12*`             |

</details>

### Команды для запуска

<details>
  <summary>Развернуть список команд</summary>

#### Локальный запуск:

```bash
gradle clean test
```

#### Удалённый запуск:

```bash
gradle clean test -Denv="remote" -Drwhost="selenoid.autotests.cloud" -Dlogin="user1:1234" -Dbrowser="Chrome" -DbrowserVersion="125.0" -DbrowserSize="1920x1080" -DvideoHost="https://selenoid.autotests.cloud/video/" -DprofileUserName="evgenshy" -DprofileUserPassword="Evgenshy12_!"
```
</details>

---

## <a id="интеграции"></a>Интеграции

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

### <img src="assets/logo/Jenkins.svg" height="25" width="25"/> <a id="сборка-в-Jenkins"></a>Сборка в [Jenkins](https://jenkins.autotests.cloud/job/EvgenShiy-DemoQA/build?delay=0sec)

Проект интегрирован с Jenkins, что позволяет:
* Запускать тесты в распределённой инфраструктуре.
* Генерировать отчеты в Allure Report.
* Запускать параметризованные сборки.

Пример параметризованной сборки в Jenkins:

<img src="assets/images/Jenkins_param.png" style="width:100%;" alt="Сборка в Jenkins">

---

### <img src="assets/logo/Allure_Report.svg" height="25" width="25"/></a> <a id="интеграция-с-Allure-Report"></a>Интеграция с [Allure Report](https://jenkins.autotests.cloud/job/EvgenShiy-DemoQA/allure/)

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

Allure Report предоставляет подробные отчёты о тестовых прогонах, включая статусы тестов, шаги выполнения и ошибки.

Пример отчета для тестового прогона:

<img src="assets/images/last_Allure_report.png" style="width:100%;" alt="AllureReport">

Шаги выполнения теста:

<img src="assets/images/Allure_Steps.png" style="width:100%;" alt="AllureReports">   

---

### <img src="assets/logo/AllureTestOps.svg" height="25" width="25"/></a> <a id="интеграция-с-Allure-TestOps"></a>Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/4585/dashboards)

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

Allure TestOps — это мощная платформа для управления процессами тестирования и анализа результатов. В рамках проекта Allure TestOps используется для:

- Хранения и управления тест-кейсами: Все тест-кейсы проекта (и авто- и ручные) создаются и поддерживаются в Allure TestOps, что упрощает их организацию и обновление.
- Мониторинга результатов тестирования: Интеграция позволяет в реальном времени отслеживать прогресс тестирования, статусы тестов и ключевые метрики.
- Анализа ошибок: Все ошибки и сбои автоматически загружаются в систему, что помогает быстро определить корневые причины и устранить их.
- Планирования тестовых прогонов: Allure TestOps обеспечивает удобный интерфейс для создания и планирования тестовых наборов, что позволяет гибко управлять тестированием в зависимости от задач.

Интеграция с Allure TestOps помогает команде эффективно управлять процессами тестирования, автоматизировать рутинные задачи и улучшать качество продукта.

Пример панели мониторинга в Allure TestOps:

<p align="center"> <img src="assets/images/Allure_Dash_Board.png" alt="AllureTestOps Dashboard" style="width:100%;"> </p>

Авто-тесты проекта: 

<p align="center"> <img src="assets/images/AllureTestOpsAuto.png" alt="AllureTestOps авто-тесты" style="width:100%;"> </p>

Ручные тесты проекта:

<p align="center"> <img src="assets/images/AllureTestOpsManualTests.png" alt="AllureTestOps ручные тесты" style="width:100%;"> </p>

Дефекты:

<p align="center"> <img src="assets/images/AllureBug.png" alt="AllureTestOps bug" style="width:100%;"> </p>

---

### <img src="assets/logo/Jira.svg" height="25" width="25"/></a> <a id="интеграция-с-Jira"></a>Интеграция с [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1390)

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

Для повышения прозрачности и управляемости процессами тестирования проект интегрирован с Jira, что позволяет связывать технические результаты с бизнес-задачами, улучшая коммуникацию между командами. 
Кроме того, Jira позволяет эффективно управлять багами, выявленными тестами, отслеживать их жизненный цикл и фиксировать прогресс исправлений. 
Интеграция тестов с Jira улучшает качество продукта и прозрачность процессов тестирования.

<p align="center">
<img src="assets/images/Jira_main_bug.png" alt="Jira main task">
</p>

--- 


## <img src="assets/logo/Telegram.svg" height="25" width="25"/></a> <a id="уведомление-о-результатах-тестов-в-telegram"></a>Уведомление о результатах тестов в Telegram

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

Для оперативного уведомления о результатах тестов используется Telegram-бот, который отправляет сообщения о статусе тестов.
<p align="center">
<img src="assets/images/TelegramBot.png" alt="Telegram_bot">
</p>

---

## <img src="assets/logo/Selenoid.svg" height="25" width="25"/></a> <a id="видео-прохождения-тестов-в-selenoid"></a>Видео прохождения тестов в Selenoid

<p style="text-align: right;"><a href="#содержание">Перейти к содержанию</a></p>

Тесты, выполняемые в Selenoid, записываются на видео. Это помогает анализировать их выполнение и разбирать возможные ошибки.

<p align="center">
<img title="Selenoid Video" src="assets/videos/FullFormVideo.gif" width="600" height="338"  alt="video">   
</p>
