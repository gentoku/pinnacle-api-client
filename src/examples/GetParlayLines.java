package examples;

import java.math.BigDecimal;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.ParlayLines;
import pinnacle.api.enums.LEG_BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.PERIOD;
import pinnacle.api.enums.TEAM_TYPE;

public class GetParlayLines {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
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
		parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
		parameter.legs(leg1, leg2);
		
		// response as plain json text
		String jsonText = api.getParlayLinesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		ParlayLines parlayLines = api.getParlayLinesAsObject(parameter);
		System.out.println(parlayLines);
	}
}
