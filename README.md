# Pinnacle Sports API Java Client

## About

This is the Java client library for [Pinnacle Sports API](http://www.pinnaclesports.com/en/api/manual). Not only to get a response as XML or JSON, but to map them into an object or list of objects. 

## Usage
#### To set parameters

Create a new Parameter instance and set applicable parameters for the operation. You must set all of required parameters while don't have to set optional parameters. 
```java
Parameter parameter = Parameter.newInstance();
parameter.sportId(29);
parameter.leagueIds(2157, 2159);
parameter.stake("25.50"); // decimal
parameter.fromDate(LocalDate.of(2015, 6, 30)); // datetime
``` 

Pinnacle Sports provides [ENUMs](http://www.pinnaclesports.com/en/api/manual#Enums) for some parameters. Every official ENUMs and three unofficial ENUMs are defined in this package. 
```java
parameter.betType(BET_TYPE.MONEYLINE);
parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
parameter.periodNumber(PERIOD.SOCCER_MATCH);
``` 

Operations related with Parlay bet require nested parameters for each 'Leg', which represents a single bet  in Parlay bet respectively. Create a new Parameter instance for each Leg.  
```java
Parameter leg1 = Parameter.newInstance();
leg1.uniqueLegId();
leg1.eventId(475764951);
Parameter leg2 = Parameter.newInstance();
leg2.uniqueLegId();
leg2.eventId(475764951);
parameter.legs(leg1, leg2);
```

These following operations require no parameter at all and then no need to create a Parameter instance.
- Get Sports
- Get Currencies
- Get Client Balance
- Get InRunning

#### To get a response as XML or JSON

Create a PinnacleAPI instance with your username and password. 
```java
PinnacleAPI api = new PinnacleAPI("username", "password");
```

You can also create an instance with encoded credentials. 
```java
PinnacleAPI api = new PinnacleAPI("encodedCredentials");
```

Then get a response as XML or JSON.
```java
String json = api.getLineAsJson(parameter);
String xml = api.getLineAsXml(parameter);
```

Data format of a response depends on its operation. Some support only XML, some do only JSON. Pinnacle Sports stated that their long term aim is to support just the JSON format as a more compact format than XML.

#### To map a response to an object or list of objects

All operations except 'Get Feed' have functions to map their responses to an object respectively defined **or** list of objects.

Get a response as an object.
```java
Odds odds = api.getOdds(parameter);
PlacedBet placedBet= api.placeBet(parameter);
```

Get a response as a list of objects.
```java
List<League> leagues = api.getLeaguesAsList(parameter);
List<Fixture> fixtures = api.getFixturesAsList(parameter);
```

#### 'Get Feed' operation

Pinnacle Sports announced 'Get Feed' operation will be obsolete, so this package doesn't implement an object for the operation and deprecates functions and ENUMs related with the operation. You can still get XML response.

## Requirements
- Java 1.8 or later
- Pinnacle Sports Account

## Third Party Libraries and Dependencies
- [google-gson](https://code.google.com/p/google-gson/)

## License
Under the MIT license. 
