# Holiday Information Service

Simple web service that finds holiday for two countries that occur at the same day, next to given date.
Holiday data is taken from external service: https://holidayapi.com/ and API KEY from this service is required.

Keep in mind that Free Accounts are limited to last year's historical data only.

## Requirements and How to run

Application requires JDK 17 to run. \
In order to compile and run application from the sources follow steps below:
- Unix/Linux: \
  ./mvnw clean install \
  java -jar target/holiday-0.0.1-SNAPSHOT.jar --external.key=<API_KEY>

- Windows:  \
  ./mvnw.cmd clean install \
  java -jar target/holiday-0.0.1-SNAPSHOT.jar --external.key=<API_KEY>

## Example
Assuming our account is registered @holidayapi.com and our local service is running with API_KEY, we can perform our first check!

Use Postman/curl or other tool you like to execute following request:

GET http://localhost:8080/api/holidays/common?countryCode1=PL&countryCode2=RU&date=2024-01-01

As a response you should see:
```json
{
  "date": "2024-01-06",
  "name1": "Święto Trzech Króli",
  "name2": "Канун Рождества"
}
```
That's because 2022-01-06 is the first day after specified date that both countries have public holidays.
Let's do the same check for different countries:

GET http://localhost:8080/api/holidays/common?countryCode1=PL&countryCode2=DE&date=2024-01-01

```json
{
  "date": "2024-03-20",
  "name1": "Dzień Ziemi",
  "name2": "Frühlingsbeginn"
}
```

As we can see, first common holiday occurred on March 20th.
