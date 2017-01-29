package pinnacle.example;

import java.io.IOException;
import java.time.LocalDate;

import pinnacle.api.Enums.BETLIST_TYPE;
import pinnacle.api.Enums.BET_TYPE;
import pinnacle.api.Enums.LANGUAGE;
import pinnacle.api.Enums.ODDS_FORMAT;
import pinnacle.api.Enums.PERIOD;
import pinnacle.api.Enums.ROUND_ROBIN_OPTIONS;
import pinnacle.api.Enums.TEAM_TYPE;
import pinnacle.api.Enums.WIN_RISK_TYPE;
import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;

public class Example {

	public static void main(String[] args) throws IOException {

		Parameter parameter;
		Parameter leg1; 
		Parameter leg2;

		// Set your username and password
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = new PinnacleAPI(username, password);
		
		// Get Sports
		System.out.println(api.getSportsAsXml());
		api.getSportsAsList().forEach(System.out::println);
		
		// Get Leagues
		parameter = Parameter.newInstance();
		parameter.sportId(29);
		System.out.println(api.getLeaguesAsXml(parameter));
		api.getLeaguesAsList(parameter).forEach(System.out::println);
		
		// Get Feed
		parameter = Parameter.newInstance();
		parameter.sportId(29);
		// !! Deprecated for the obsolete operation.
		//System.out.println(api.getFeedAsXml(parameter));
		
		// Get Fixture
		parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueIds(2157, 2159);
		System.out.println(api.getFixturesAsJson(parameter));
		api.getFixturesAsList(parameter).forEach(System.out::println);
		
		// Get Odds
		parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueIds(2157);
		parameter.isLive(true);
		System.out.println(api.getOddsAsJson(parameter));
		System.out.println(api.getOdds(parameter));
		
		// Get Currencies
		System.out.println(api.getCurrenciesAsXml());
		api.getCurrenciesAsList().forEach(System.out::println);
		
		// Get Client Balance
		System.out.println(api.getClientBalanceAsXml());
		System.out.println(api.getClientBalanceAsJson());
		System.out.println(api.getClientBalance());
		
		// Place Bet
		parameter = Parameter.newInstance();
		parameter.uniqueRequestId();
		parameter.acceptBetterLine(true);
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		parameter.stake("1.50");
		parameter.winRiskStake(WIN_RISK_TYPE.RISK);
		parameter.sportId(29);
		parameter.eventId(475764951);
		parameter.periodNumber(0);
		parameter.lineId(208081327);
		parameter.betType(BET_TYPE.MONEYLINE);
		parameter.team(TEAM_TYPE.Team2);
		// !! you will bet real money on your account by this operation.
		//System.out.println(api.placeBetAsJson(parameter));
		
		// Place Parlay Bet
		leg1 = Parameter.newInstance();
		System.out.println(leg1.uniqueLegId());
		leg1.legBetType(BET_TYPE.MONEYLINE);
		leg1.sportId(29);
		leg1.eventId(477254235);
		leg1.lineId(209053849);
		leg1.team(TEAM_TYPE.Team1);
		leg1.periodNumber(0);
		leg2 = Parameter.newInstance();
		System.out.println(leg2.uniqueLegId());
		leg2.legBetType(BET_TYPE.MONEYLINE);
		leg2.sportId(29);
		leg2.eventId(477254045);
		leg2.lineId(209053846);
		leg2.team(TEAM_TYPE.Draw);
		leg2.periodNumber(0);
		parameter = Parameter.newInstance();
		parameter.uniqueRequestId();
		parameter.acceptBetterLine(true);
		parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
		parameter.riskAmount("1.50");
		parameter.roundRobinOptions(ROUND_ROBIN_OPTIONS.Parlay);
		parameter.legs(leg1, leg2);
		// !! you will bet real money on your account by this operation.
		//System.out.println(api.placeParlayBet(parameter));
		
		// Get Line
		parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueId(2157);
		parameter.eventId(475764951);
		parameter.periodNumber(PERIOD.SOCCER_MATCH);
		parameter.betType(BET_TYPE.MONEYLINE);
		parameter.team(TEAM_TYPE.Team1);
		parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
		System.out.println(api.getLineAsXml(parameter));
		
		// Get Parlay Line
		parameter = Parameter.newInstance();
		parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
		leg1 = Parameter.newInstance();
		System.out.println(leg1.uniqueLegId());
		leg1.eventId(477254235);
		leg1.legBetType(BET_TYPE.MONEYLINE);
		leg1.team(TEAM_TYPE.Team1);
		leg1.periodNumber(0);
		leg2 = Parameter.newInstance();
		System.out.println(leg2.uniqueLegId());
		leg2.eventId(477254045);
		leg2.legBetType(BET_TYPE.MONEYLINE);
		leg2.team(TEAM_TYPE.Draw);
		leg2.periodNumber(0);
		parameter.legs(leg1, leg2);
		System.out.println(api.getParlayLineAsJson(parameter));
		
		// Get Bets
		parameter = Parameter.newInstance();
		parameter.betlist(BETLIST_TYPE.running);
		parameter.fromDate(LocalDate.of(2015, 6, 10));
		parameter.toDate(LocalDate.of(2015, 6, 30));
		System.out.println(api.getBetsAsJson(parameter));
		api.getBetsAsList(parameter).forEach(System.out::println);
		
		// Get Inrunning
		System.out.println(api.getInRunningAsXml());
		System.out.println(api.getInRunningAsJson());
		System.out.println(api.getInRunning());
		api.getInRunningEventsAsList().forEach(System.out::println);
		
		// Get Translations
		parameter = Parameter.newInstance();
		parameter.cultureCodes(LANGUAGE.JAPANESE, LANGUAGE.CHINESE_SI);
		parameter.baseTexts("football", "odds", "parlay");
		System.out.println(api.getTranslationsAsJson(parameter));
		api.getTranslationsAsList(parameter).forEach(System.out::println);
	}
	
}
