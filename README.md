# Проект автоматизации тестирования для сайта [demoqa.com](https://demoqa.com/)  

---

<img src="https://demoqa.com/images/Toolsqa.jpg" style="width:100%;" alt="Логотип">

---

## Содержание
* <a href="#о-проекте">О проекте</a>
* <a href="#технологии-и-инструменты">Технологии и инструменты</a>
* <a href="#тест-кейсы">Тест-кейсы</a>
* <a href="#запуски-тестов-из-терминала">Запуски тестов из терминала</a>
* <a href="#интеграции">Интеграции</a>
* * <a href="#Сборка-в-Jenkins">Сборка в Jenkins</a>
* * <a href="#интеграция-с-Allure-Report">Интеграция с Allure Report</a>
* * <a href="#интеграция-с-Allure-TestOps">Интеграция с Allure TestOps</a>
* * <a href="#интеграция-с-Jira">Интеграция с Jira</a>
* <a href="#уведомление-о-результатах-тестов-в-telegram">Уведомление о результатах тестов в Telegram</a>
* <a href="#видео-прохождения-тестов-в-selenoid">Видео прохождения тестов в Selenoid</a>

---
## <a id="о-проекте"></a>О проекте

Данный проект представляет собой автоматизацию тестирования для сайта [demoqa.com](https://demoqa.com/). В тестах проверяются различные сценарии заполнения [формы регистрации](https://demoqa.com/automation-practice-form), включая полное и частичное заполнение полей, а также отображение подсказок для обязательных полей.

Проект реализован с использованием **[PageObject](https://martinfowler.com/bliki/PageObject.html)** для структурирования кода и облегчения его поддержки, а также включает **генерацию тестовых данных** для повышения гибкости и точности проверок.

Основные технологии и инструменты, применяемые в проекте, включают **[Java](https://www.java.com/)**, **[Gradle](https://gradle.org/)**, **[Selenide](https://selenide.org/)** для автоматизации взаимодействия с веб-страницей, а также **[Selenoid](https://aerokube.com/selenoid/)** для запуска тестов в разных браузерах и **[Allure Report](https://github.com/allure-framework/allure2)** для отчетов о прохождении тестов. **[JUnit 5](https://junit.org/junit5/)** используется для организации и выполнения тестов, **[Jenkins](https://www.jenkins.io/)** применяется для CI/CD, а **Telegram бот** уведомляет о результатах тестов.


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

---

## <a id="тест-кейсы"></a>Тест-кейсы

В проекте реализованы тесты для проверки различных аспектов функциональности сайта [demoqa.com](https://demoqa.com/). Тесты делятся на два типа: **UI** (пользовательский интерфейс) и **API** (интерфейс программирования приложений).
Тесты помогают удостовериться в корректной работе функционала сайта, выявить возможные ошибки и улучшить качество приложения.
Для каждой группы тестов используются отдельные методы и фреймворки, чтобы обеспечить точность и стабильность выполнения.


<details>
  <summary><strong style="color:#2a9d8f;">🌐 UI Тесты</strong></summary>

**UI тесты** проверяют функциональность сайта с точки зрения пользователя. Основное внимание уделяется заполнению форм, аутентификации и отображению информации на сайте.

#### 1. **Login**
- **Проверка успешной аутентификации** зарегистрированного пользователя.
- **Проверка неудачной аутентификации** с неверными данными (случайные имя пользователя и пароль).
- **Проверка подсветки обязательных полей**, если они не заполнены.

#### 2. **Profile**
- **Удаление книги** из профиля пользователя через UI.
- **Проверка отображения профиля пользователя** и корректности данных, включая имя пользователя, email и другие атрибуты.

#### 3. **Student Registration Form**
- **Проверка заполнения всех полей формы регистрации**.
- **Проверка заполнения только обязательных полей формы регистрации**.
- **Проверка подсветки обязательных полей**, если они не заполнены.

</details>

<details>
  <summary><strong style="color:#e76f51;">💻 API Тесты</strong></summary>

**API тесты** обеспечивают проверку функциональности серверной части сайта. Это включает в себя проверку успешной авторизации, взаимодействие с данными пользователей через API и выполнение операций, таких как регистрация, удаление пользователей и обновление данных.

#### 1. **Account**
- **Проверка успешной авторизации** через API.
- **Проверка регистрации нового пользователя** через API.
- **Проверка получения данных профиля пользователя** через API.
- **Проверка удаления всех книг** из профиля через API.
- **Проверка удаления пользователя** через API.

</details> 

<details> <summary><strong style="color:#f4a261;">📝 Ручные Тесты</strong></summary>

1. Проверка пагинации на странице книг
   
2. Проверка поиска книги по названию на странице с книгами
   
</details>

---

## <a id="запуски-тестов-из-терминала"></a>Запуски тестов из терминала

### Тестовые задачи

| Задача | Описание                                                        |
|:-------|:----------------------------------------------------------------|
| `test` | Запускает **все тесты** </br> _(является значением по умолчанию)_ |
| `api`  | Запускает все **api-тесты**                                      |
| `ui`   | Запускает все **ui-тесты**                                       |
                                     |


### Параметры для запуска тестов

| Параметр               | Описание                                                                                   | Пример значения            |
|:------------------------|:------------------------------------------------------------------------------------------|:---------------------------|
| `${TestType}`           | Тип тестов для выполнения (`test`, `api`, `ui`).                                          | `test`                     |
| `-Denv`                | Среда выполнения тестов (`local`, `remote`).                                              | `remote`                   |
| `-Drwhost`             | Адрес удаленного сервера, на котором выполняются тесты.                                   | `https://selenoid.com`     |
| `-Dlogin`              | Логин для авторизации на удаленном сервере.                                               | `user`                     |
| `-Dbrowser`            | Браузер, используемый для тестов.                                                         | `chrome`, `firefox`        |
| `-DbrowserVersion`     | Версия браузера.                                                                          | `125.0`, `131.0`           |
| `-DbrowserSize`        | Размер окна браузера.                                                                     | `1920x1080`                |
| `-DvideoHost`          | Хост для доступа к видео прохождения тестов.                                              | `https://video.selenoid`   |
| `-DprofileUserName`    | Имя пользователя для авторизации в тестируемом приложении.                                | `evgensh`                  |
| `-DprofileUserPassword`| Пароль пользователя для авторизации в тестируемом приложении.                             | `Evgensh12*`               |


### Команды для запуска 

#### Локальный запуск:

```bash
gradle clean test
```

#### Удалённый запуск:

```bash
gradle clean test -Denv="remote" -Drwhost="selenoid.autotests.cloud" -Dlogin="user1:1234" -Dbrowser="Chrome" -DbrowserVersion="125.0" -DbrowserSize="1920x1080" -DvideoHost="https://selenoid.autotests.cloud/video/" -DprofileUserName="evgenshy" -DprofileUserPassword="Evgenshy12_!"
```

---

## <a id="интеграции"></a>Интеграции

### <img src="assets/logo/Jenkins.svg" height="25" width="25"/></a> <a id="Сборка-в-Jenkins"></a>Сборка в [Jenkins](https://jenkins.autotests.cloud/job/EvgenShiy-DemoQA/build?delay=0sec)

Проект интегрирован с Jenkins для автоматизированного запуска тестов. Сборка в Jenkins включает:

- **Параметризованные сборки:**
  Возможность выбора параметров сборки
- **Запуск тестов на удалённом сервере:** Возможность запуска тестов в распределённой инфраструктуре с использованием Selenoid.
- **Автоматическая генерация отчетов:** После завершения сборки формируется отчет в Allure Report, доступный напрямую из Jenkins.

Пример параметризованной команды для Jenkins:


---

### <img src="assets/logo/Allure_Report.svg" height="25" width="25"/></a> <a id="интеграция-с-Allure-Report"></a>Интеграция с [Allure Report](https://jenkins.autotests.cloud/job/EvgenShiy-DemoQA/9/allure/)

Allure Report — это инструмент для создания подробных и наглядных отчетов о результатах выполнения тестов. В нем отображаются все тестовые шаги, статусы тестов, а также дополнительные детали, такие как время выполнения и возможные ошибки. Allure помогает быстро анализировать результаты тестирования и предоставляет информацию в удобном визуальном формате.

Пример отчета для тестового прогона:

<img src="assets/images/last_Allure_report.png" style="width:100%;" alt="AllureReport">

Отчет в Allure включает шаги выполнения тестов, их статус и возможные ошибки, помогая легче отслеживать качество тестируемого приложения.
Шаги выполнения на примере тест-кейса "Проверка заполнения всех полей формы регистрации":

<img src="assets/images/Allure_Steps.png" style="width:100%;" alt="AllureReports">   

---

### <img src="assets/logo/AllureTestOps.svg" height="25" width="25"/></a> <a id="интеграция-с-Allure-TestOps"></a>Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/4585/dashboards)

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

---

### <img src="assets/logo/Jira.svg" height="25" width="25"/></a> <a id="интеграция-с-Jira"></a>Интеграция с [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1390)


--- 


## <img src="assets/logo/Telegram.svg" height="25" width="25"/></a> <a id="уведомление-о-результатах-тестов-в-telegram"></a>Уведомление о результатах тестов в Telegram

После завершения тестов бот Telegram автоматически отправляет уведомления с результатами тестового прогона, включая информацию о количестве пройденных и неудавшихся тестов.

<p align="center">
<img src="assets/images/TelegramBot.png" alt="Telegram_bot">
</p>


---

## <img src="assets/logo/Selenoid.svg" height="25" width="25"/></a> <a id="видео-прохождения-тестов-в-selenoid"></a>Видео прохождения тестов в Selenoid

Видео прохождения Tест-кейса "Проверка заполнения всех полей формы регистрации":

<p align="center">
<img title="Selenoid Video" src="assets/videos/FullFormVideo.gif" width="600" height="338"  alt="video">   
</p>
