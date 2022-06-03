## Картинка, соответствующая изменению курса заданной валюты
REST cервис обращается к сервису курсов валют, и отображает gif:<br>
- если текущий курс на сегодня по отношению к USD стал выше вчерашнего, то сервис выдает рандомную картинку
  отсюда: https://giphy.com/search/rich.
  <br>
- если ниже или равен, то отсюда: https://giphy.com/search/broke.
  <br>
  Ссылки на внешние сервисы:<br>
- REST API курсов валют - https://docs.openexchangerates.org/
  <br>
- REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
  <br>

Сервис на Spring Boot 2.6.9 + Java.<br>
Запросы приходят на HTTP endpoint `/picture`.<br>
Код валюты, по отношению к которой сравнивается курс USD, задается в
параметре запроса `symbol`
.<br>
Для взаимодействия с внешними сервисами используется Open Feign.<br>
Все параметры сервиса(валюта по отношению к которой смотрится курс, адреса и идентификаторы внешних сервисов и т.д.)
вынесены в `application.properties`.
Секреты акканутов внешних сервисов отсутствуют где либо в коде и задаются в `application.properties`<br>
Символ валюты, с которой сравнивается передаваемая в параметре `symbol` валюта, задается `application.properties`
в свойстве `app.config.open-exchange.base`, но в запросе к сервису курсов валют этот параметр не передается, так как требует 
платного аккаунта openexchange.<br>
Написаны тесты. Для мока внешних сервисов использован @MockBean.<br>
Для сборки используется Gradle.<br>
Скрипты для сборки docker образа и запуска сервиса в Docker контейнере находятся в директории /docker.

<b>Для запуска приложения:</b><br>
- скачать docker образ jdk не ниже 14, например `openjdk:16-jdk-alpine`.<br>
- указать идентификаторы для доступа к внешним сервисам в application.properties:<br>
- для giphy в `app.config.giphy.appkey`<br>
- для openexchange в `app.config.open-exchange.appid`<br>
- собрать jar приложения c помощью gradle<br>
- находясь в корневой папке проекта выполнить:<br>
`docker build -f docker/Dockerfile -t currency-info .`<br>
- после построения образа приложения выполнить:<br>
`docker run -it --rm -p 8080:8080 --name currency-info-service currency-info`
- для запуска:<br>
`http://localhost:8080/picture?symbol=EUR`
