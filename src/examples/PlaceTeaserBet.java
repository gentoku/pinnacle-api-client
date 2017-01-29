package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.PlacedTeaserBet;
import pinnacle.api.enums.BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.SIDE_TYPE;
import pinnacle.api.enums.TEAM_TYPE;
import pinnacle.api.enums.WIN_RISK_TYPE;

public class PlaceTeaserBet {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter leg1 = Parameter.newInstance();
		leg1.legId();
		leg1.eventId(480174385);
		leg1.lineId(210663830);
		leg1.betType(BET_TYPE.SPREAD);
		leg1.team(TEAM_TYPE.TEAM_1);
		leg1.handicap("-4.5");

		Parameter leg2 = Parameter.newInstance();
		leg2.legId();
		leg2.eventId(480174384);
		leg2.lineId(480174384);
		leg2.betType(BET_TYPE.TOTAL_POINTS);
		leg2.handicap("2.6");
		leg2.side(SIDE_TYPE.UNDER);
		
		Parameter parameter = Parameter.newInstance();
		parameter.uniqueRequestId();
		parameter.teaserId(50);
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		parameter.winRiskStake(WIN_RISK_TYPE.RISK);
		parameter.stake("25.00");
		parameter.legs(leg1, leg2);
		
		// response as plain json text
		String jsonText = api.placeTeaserBetAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		PlacedTeaserBet placedTeaserBet = api.placeTeaserBetAsObject(parameter);
		System.out.println(placedTeaserBet);
	}
}
