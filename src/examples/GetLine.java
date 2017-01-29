package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Line;
import pinnacle.api.enums.BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.PERIOD;
import pinnacle.api.enums.TEAM_TYPE;

public class GetLine {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.sportId(29);
		parameter.leagueId(1980);
		parameter.eventId(682273368);
		parameter.periodNumber(PERIOD.SOCCER_MATCH);
		parameter.betType(BET_TYPE.SPREAD);
		parameter.team(TEAM_TYPE.TEAM_1);
		//parameter.side(SIDE_TYPE.OVER);
		parameter.handicap("-1.75");
		parameter.oddsFormat(ODDS_FORMAT.AMERICAN);
		
		// response as plain json text
		String jsonText = api.getLineAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Line line = api.getLineAsObject(parameter);
		System.out.println(line);
	}
}
