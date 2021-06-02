# Trucking-Company
 
Веб-риложение доступно по ссылке: https://my-app-for-pr.herokuapp.com/
 
## О проекте

Данный проект разрабатывается в учебных целях.
Он предоставляет функционал веб-приложения компании грузоперевозок.
В качестве системы сборки используется maven.
Проект написан на Java. На данный момент используются такие технологии, как Spring Web, Spring Security, Spring Data.
В качестве СУБД выбрана PostgreSQL.
## Зависимости
- hibernate-validator для проверки вводимых данных
- spring-boot-starter-data-jpa для для работы Spring Data ORM
- spring-boot-starter-web для работы Spring Web
- spring-boot-starter-thymeleaf для редактирования HTML страниц
- spring-boot-starter-security для работы Spring Security
- postgresql для работы драйвера PostgreSQL
- spring-boot-starter-mail для организации рассылки сообщений по эл.почте
## Сборка и запуск
- сборка: mvn install
- запуск: java -jar demo-0.0.1-SNAPSHOT.jar
