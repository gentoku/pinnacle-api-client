# Pinnacle Sports API Java Client

## About

This is the Java client library for [Pinnacle Sports API](https://www.pinnacle.com/de/api/manual). You can get response as a plain JSON text or as a mapped Data Object prepared in this package.

## Update
#### January 29th 2017
- Removed every codes related with XML format due to their removal since December 15th 2016.
- Implemented new operations Pinnacle added.
- Changed structure of package.
- Revised some details.

## Usage
#### To set parameters

Create a new Parameter instance and set applicable parameters for each operation. You must set all of required parameters while you don't have to set optional parameters.
```java
Parameter parameter = Parameter.newInstance();
parameter.sportId(29);
parameter.leagueIds(1980, 1977);
parameter.stake("25.50"); // decimal
parameter.fromDate(LocalDate.of(2017, 6, 30)); // datetime
```

Pinnacle Sports provides [ENUMs](https://www.pinnacle.com/de/api/manual#enums) for some parameters. All of official ENUMs (and some unofficial ENUMs) are prepared in this package.
```java
parameter.betType(BET_TYPE.MONEYLINE);
parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
parameter.periodNumber(PERIOD.SOCCER_MATCH); // unofficial
```

Operations for Parlay bet and Teaser bet require nested parameters called 'legs'. A leg represents a single line and Parlay bet and Teaser bet consist of multiple legs respectively. Create a new Parameter instance for each Leg.  
```java
Parameter leg1 = Parameter.newInstance();
leg1.uniqueLegId();
leg1.eventId(682273368);
leg1.legBetType(LEG_BET_TYPE.SPREAD);
leg1.team(TEAM_TYPE.TEAM_1);
leg1.handicap(new BigDecimal("-1.75"));
leg1.periodNumber(PERIOD.SOCCER_MATCH);

Parameter leg2 = Parameter.newInstance();
leg2.uniqueLegId();
leg2.eventId(682273372);
leg2.legBetType(LEG_BET_TYPE.SPREAD);
leg2.team(TEAM_TYPE.TEAM_2);
leg2.handicap("0.0");
leg2.periodNumber(0);

Parameter parameter = Parameter.newInstance();
parameter.legs(leg1, leg2);
```
Operation to place Special Bet requires nested parameter called 'bets'. You can bet multiple special bets simultaneously even if they are offered in different sports.
```java
Parameter bet1 = Parameter.newInstance();
bet1.uniqueRequestId();
bet1.acceptBetterLine(true);
bet1.oddsFormat(ODDS_FORMAT.DECIMAL);
bet1.stake("100.00");
bet1.winRiskStake(WIN_RISK_TYPE.RISK);
bet1.lineId(46850206);
bet1.specialId(680281398);
bet1.contestantId(680281399);

Parameter bet2 = Parameter.newInstance();
bet2.uniqueRequestId();
bet2.acceptBetterLine(true);
bet2.oddsFormat(ODDS_FORMAT.DECIMAL);
bet2.stake("150.00");
bet2.winRiskStake(WIN_RISK_TYPE.RISK);
bet2.lineId(46865918);
bet2.specialId(686671592);
bet2.contestantId(686671598);

Parameter parameter = Parameter.newInstance();
parameter.bets(bet1, bet2);
```


These following operations require no parameter at all, therefore no need to create a Parameter instance.
- Get Sports
- Get Currencies
- Get Client Balance
- Get Inrunning
- Get Cancellation Reasons

#### To get a response

Create a PinnacleAPI instance with your username and password.
```java
PinnacleAPI api = new PinnacleAPI("username", "password");
```

You can also create an instance with encoded text, which is `Base64 value of UTF-8 encoded "username:password"`
```java
String encodedCredentials = PinnacleAPI.encode("username", "password");
PinnacleAPI api = new PinnacleAPI(encodedCredentials);
```

Then you can get a JSON response.
```java
String json = api.getLineAsJson(parameter);
```
Or you can get a response as a mapped Data Object.
```java
Odds odds = api.getOddsAsObject(parameter);
PlacedBet placedBet = api.placeBetAsObject(parameter);
```

## Data Objects
#### Types
Classes in `pinnacle.api.dataobjects` are prepared as Data Objects to be mapped from JSON responses. Each JSON value type will be set into these types respectively as following.

- JSON number: `Integer`, `Long`, `Enum`
- JSON string: `String`, `Enum`
- JSON boolean: `Boolean`
- JSON object: `T extends AbstractDataObject`
- JSON array: `List<T extends AbstractDataObject>`
- JSON null: `Optional.empty()`

When JSON doesn't have a key for a parameter, the value which will be set depends on whether the key is required or optional.

- If required key not exists in JSON, the value will be: `null` or empty `ArrayList`, and `isFlawed = true`  
- if optional key not exists in JSON, the value will be: `Optional.empty()`

**Note that `Optional.empty()` is used for different two purposes.** For optional (not required) parameters, regardless whether or not a JSON response has these optional keys, an Object to be mapped must have fields for them. `Optional<T>` is literally useful for such a case. On the other hand, some parameters Pinnacle defined as 'required' are returned with JSON null, which should be dealt with `Optional<T>`. It is confusing but I couldn't find out any other solutions.

#### Empty JSON
Sometimes API returns an empty JSON like `{}`. Though I can't confirm what case makes this response, it seems not error but looks like they have just no answer. An empty Data Object with `isEmpty = true` status will be returned for such an empty JSON response.

#### Error JSON
When JSON response has two keys of 'code' and 'message', this package regards it as [GenericException type](https://www.pinnacle.com/de/api/manual#Exceptions) and throws `PinnacleException` with `TYPE.ERROR_RETURNED`. GenericException are not prepared as a Data Object to be mapped, but you can check its JSON text by `PinnacleException`. Other type of errors, such as `TEASER_LINES_ERROR_CODE` or `PLACEBET_ERROR_CODE`, are mapped into a Data Object as 'errorCode' and no Exceptions will be thrown for them.

## ENUMs
As mentioned above, all of ENUMs except BOOLEAN and BOOLEAN2 are prepared at `pinnacle.api.enums`. They are defined in UPPER_CASE_EMBEDDED_UNDERSCORE style and added one more element, 'UNDEFINED', which will be applied for any undefined values in response. **Note that they may add/remove/change any value of ENUMs for future update.**

## Corrections
To map into Data Objects, there are some mismatched names, types, and values of parameter between on Official website and in an actual JSON response. They are respectively corrected as following. **Note that conflict or error may be occurred when they correct them in future.**

#### Get Settled Fixtures - Response
- [lineId] Not exists in JSON (Required **Yes** -> Required **No**)

#### Get Special Fixtures - Response
- [status] Not exists in ENUMs (**BETTING**_EVENT_STATUS -> EVENT_STATUS)

#### Get Odds - Response
- [moneyLine] Not case sensitive. (money**L**ine -> money**l**ine)
- [maxMoneyLine] Not case sensitive. (maxMoney**L**ine -> maxMoney**l**ine)

#### Get Special Odds - Response
- [maxRisk] Different name used in JSON. (max**Risk** -> max**Bet**)

#### Get Client Balance - Response
- [currency] Incorrect type. (**Decimal** -> **String**)

#### Get Line - Response
- [effectiveAsOf] Returned without time zone character. ("2017-01-10T16:07:55.1919" -> "2017-01-10T16:07:55.1919**Z**")

#### Get Parlay Line - Response
- [RoundRobinOdds] Not case sensitive. (**R**oundRobinOdds -> **r**oundRobinOdds)
- [Odds] Not case sensitive. (**O**dds -> **o**dds)

#### Get Bets - Response
- [placedAt] Returned without time zone character. ("2017-01-28T08:47:57" -> "2017-01-28T08:47:57**Z**")

## Requirements
- Java 1.8 or later
- Pinnacle Sports Account (must be funded)

## Third Party Libraries and Dependencies
- [google-gson](https://code.google.com/p/google-gson/)

## License
Under the MIT license.
