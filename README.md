# Проект автоматизации тестирования для сайта [demoqa.com](https://demoqa.com/)  

---

<img src="https://demoqa.com/images/Toolsqa.jpg" style="width:100%;">

---

## Содержание
* <a href="#о-проекте">О проекте</a>
* <a href="#технологии-и-инструменты">Технологии и инструменты</a>
* <a href="#тест-кейсы">Тест-кейсы</a>
* <a href="#запуск-тестов-в-jenkins">Запуск тестов в Jenkins</a>
* <a href="#отчет-о-выполнении-тестов-в-allure-report">Отчет о выполнении тестов в Allure Report</a>
* <a href="#уведомление-о-результатах-тестов-в-telegram">Уведомление о результатах тестов в Telegram</a>
* <a href="#видео-прохождения-тестов-в-selenoid">Видео прохождения тестов в Selenoid</a>

---
## <a id="о-проекте"></a>О проекте

Данный проект представляет собой автоматизацию тестирования для сайта [demoqa.com](https://demoqa.com/). В тестах проверяются различные сценарии заполнения [формы регистрации](https://demoqa.com/automation-practice-form), включая полное и частичное заполнение полей, а также отображение подсказок для обязательных полей.

Проект реализован с использованием **[PageObject](https://martinfowler.com/bliki/PageObject.html)** для структурирования кода и облегчения его поддержки, а также включает **генерацию тестовых данных** для повышения гибкости и точности проверок.

Основные технологии и инструменты, применяемые в проекте, включают **[Java](https://www.java.com/)**, **[Gradle](https://gradle.org/)**, **[Selenide](https://selenide.org/)** для автоматизации взаимодействия с веб-страницей, а также **[Selenoid](https://aerokube.com/selenoid/)** для запуска тестов в разных браузерах и **[Allure Report](https://github.com/allure-framework/allure2)** для отчетов о прохождении тестов. **[JUnit 5](https://junit.org/junit5/)** используется для организации и выполнения тестов, **[Jenkins](https://www.jenkins.io/)** применяется для CI/CD, а **Telegram бот** уведомляет о результатах тестов.


<img src="assets/images/form.png" style="width:100%;">

---

## <a id="технологии-и-инструменты"></a>Технологии и инструменты
<p align="center">
  <a href="https://www.java.com/"><img src="assets/logo/Java.svg" alt="Java Logo" height="50" width="50"/></a>
  <a href="https://www.jetbrains.com/idea/"><img src="assets/logo/Intelij_IDEA.svg" alt="Intelij_IDEA Logo"height="50" width="50"/></a>
  <a href="https://github.com/"><img src="assets/logo/GitHub.svg" alt="Github" height="50" width="50"/></a>
  <a href="https://junit.org/junit5/"><img src="assets/logo/JUnit5.svg" alt="JUnit 5" height="50" width="50"/></a>
  <a href="https://gradle.org/"><img src="assets/logo/Gradle.svg" alt="Gradle" height="50" width="50"/></a>
  <a href="https://selenide.org/"><img src="assets/logo/Selenide.svg" alt="Selenide" height="50" width="50"/></a>
  <a href="https://aerokube.com/selenoid/"><img src="assets/logo/Selenoid.svg" alt="Selenoid" height="50" width="50"/></a>
  <a href="https://github.com/allure-framework"><img src="assets/logo/Allure_Report.svg" alt="Allure" height="50" width="50"/></a>
  <a href="https://www.jenkins.io/"><img src="assets/logo/Jenkins.svg" alt="Jenkins" height="50" width="50"/></a>
  <a href="https://web.telegram.org/"><img src="assets/logo/Telegram.svg" alt="Telegram" height="50" width="50"/></a>
</p>

---

## <a id="тест-кейсы"></a>Тест-кейсы
1. Проверка корректного заполнения всех полей формы регистрации.
2. Проверка корректного заполнения только обязательных полей формы регистрации.
3. Проверка подсветки обязательных полей, если они не заполнены.  

---

## <img src="assets/logo/Jenkins.svg" height="25" width="25"/></a> <a id="запуск-тестов-в-jenkins"></a>Запуск тестов в [Jenkins](https://jenkins.autotests.cloud/job/shiian_demoQA_tests/build?delay=0sec)

В данном проекте Jenkins управляет запуском тестов, предоставляет возможность интеграции с различными браузерами и версиями через параметризированные сборки, а также генерирует отчеты о результатах тестирования.

<img src="assets/images/Jenrins_pr.png" style="width:100%;">

### Параметаризированная сборка позволяет протестировать различные варианты:  
* `browserSize` - Разрешение экрана(по умолчанию стандартное разрешение 1920x1080)  
* `browserName` - Браузер (по умолчанию Chrome)  
* `browserVersion` - Версия браузера (по умолчанию 125,0)  

Jenkins также автоматически интегрируется с Allure Report для создания отчетов и с Telegram для уведомлений о результатах тестов.

### Команды для запуска из терминала

***Локальный запуск:***
```bash  
gradle clean demoqa_test
```

***Удалённый запуск через Jenkins:***
```bash  
clean
demoqa_test
-DremoteDriverURL=https://user1:1234@${remoteDriverURL}/wd/hub
-Dbrowser=${browserName}
-DbrowserVersion=${browserVersion}
-DbrowserSize=${browserSize}
-DvideoHost=${videoHost}
```

---

## <img src="assets/logo/Allure_Report.svg" height="25" width="25"/></a> <a id="отчет-о-выполнении-тестов-в-allure-report"></a>Отчет о выполнении тестов в [Allure Report](https://jenkins.autotests.cloud/job/shiian_demoQA_tests/2/allure/)

Allure Report — это инструмент для создания подробных и наглядных отчетов о результатах выполнения тестов. В нем отображаются все тестовые шаги, статусы тестов, а также дополнительные детали, такие как время выполнения и возможные ошибки. Allure помогает быстро анализировать результаты тестирования и предоставляет информацию в удобном визуальном формате.

Пример отчета для тестового прогона:

<img src="assets/images/AllureReport_overview.png" style="width:100%;">

Отчет в Allure включает шаги выполнения тестов, их статус и возможные ошибки, помогая легче отслеживать качество тестируемого приложения.
Шаги выполнения на примере Тест-кейса №2 "Проверка заполнения всех полей формы регистрации":

<img src="assets/images/Allure_steps.png" style="width:100%;">   

---

## <img src="assets/logo/Telegram.svg" height="25" width="25"/></a> <a id="уведомление-о-результатах-тестов-в-telegram"></a>Уведомление о результатах тестов в Telegram

После завершения тестов бот Telegram автоматически отправляет уведомления с результатами тестового прогона, включая информацию о количестве пройденных и неудавшихся тестов.

<p align="center">
<img src="assets/images/Telegram_bot.png">
</p>


---

## <img src="assets/logo/Selenoid.svg" height="25" width="25"/></a> <a id="видео-прохождения-тестов-в-selenoid"></a>Видео прохождения тестов в Selenoid

Видео прохождения Tест-кейса №2 "Проверка заполнения всех полей формы регистрации":

<p align="center">
<img title="Selenoid Video" src="assets/videos/FullFormVideo.gif" width="550" height="350"  alt="video">   
</p>
