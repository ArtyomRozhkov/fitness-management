# Fitness management

Сервис, предоставляющий функционал записи на тренировки через telegram бота.

## Сборка проекта

cd /root/of/project 

mvn clean package

## Запуск проекта

### Создать telegram бота
Создайте telegram бота, используя [инструкцию с официального сайта](https://core.telegram.org/bots#6-botfather)

### Запустить собранное приложение для обработки запросов с telegram бота
`java -Dtelegram.bot-name=<имя бота> -Dtelegram.bot-token=<токен бота> -jar target/fitness-management*.jar`, 

где использовать _<имя бота>_ и _<токен бота>_, полученные на шаге создания бота

### Использование сервиса

#### В telegram-е найти бота по имени: @<имя бота>
#### Набрать в чате /start