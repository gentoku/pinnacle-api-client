package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.PlacedBet;
import pinnacle.api.enums.BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.PERIOD;
import pinnacle.api.enums.TEAM_TYPE;
import pinnacle.api.enums.WIN_RISK_TYPE;

public class PlaceBet {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.uniqueRequestId();
		parameter.acceptBetterLine(true);
		//parameter.customerReference();
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		parameter.stake("100.0");
		parameter.winRiskStake(WIN_RISK_TYPE.WIN);
		parameter.sportId(29);
		parameter.eventId(687863980);
		parameter.periodNumber(PERIOD.SOCCER_1ST_HALF);
		parameter.betType(BET_TYPE.MONEYLINE);
		parameter.team(TEAM_TYPE.TEAM_1);
		//parameter.side();
		parameter.lineId(366253618);
		//parameter.altLineId();
		//parameter.pitcher1MustStart();
		//parameter.pitcher2MustStart();
		
		// response as plain json text
		String jsonText = api.placeBetAsJson(parameter); // !! you will bet real money by this operation !!
		System.out.println(jsonText);
		
		// response as data object
		PlacedBet placedBet = api.placeBetAsObject(parameter); // !! you will bet real money by this operation !!
		System.out.println(placedBet);
	}
}
