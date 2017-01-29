package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.*;
import pinnacle.api.enums.*;

public class GetTeaserLines {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter leg1 = Parameter.newInstance();
		leg1.legId();
		leg1.eventId(687863979);
		leg1.betType(BET_TYPE.SPREAD);
		leg1.team(TEAM_TYPE.TEAM_1);
		leg1.periodNumber(PERIOD.SOCCER_MATCH);
		leg1.handicap("5.5");
		
		Parameter leg2 = Parameter.newInstance();
		leg2.legId();
		leg2.eventId(687863980);
		leg2.betType(BET_TYPE.SPREAD);
		leg2.team(TEAM_TYPE.TEAM_2);
		leg2.periodNumber(0);
		leg2.handicap("20.0");
		
		Parameter parameter = Parameter.newInstance();
		parameter.teaserId(50);
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		parameter.legs(leg1, leg2);
		
		// response as plain json text
		String jsonText = api.getTeaserLinesAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		TeaserLines teaserLines = api.getTeaserLinesAsObject(parameter);
		System.out.println(teaserLines);
	}
}
