package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.PlacedSpecialBet;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.WIN_RISK_TYPE;

public class PlaceSpecialBet {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter bet = Parameter.newInstance();
		bet.uniqueRequestId();
		bet.acceptBetterLine(true);
		bet.oddsFormat(ODDS_FORMAT.AMERICAN);
		bet.stake("200.00");
		bet.winRiskStake(WIN_RISK_TYPE.WIN);
		bet.lineId(33997608);
		bet.specialId(480692762);
		bet.contestantId(480692764);
				
		Parameter parameter = Parameter.newInstance();
		parameter.bets(bet);
		
		// response as plain json text
		String jsonText = api.placeSpecialBetAsJson(parameter); // !! you will bet real money by this operation !!
		System.out.println(jsonText);
		
		// response as data object
		PlacedSpecialBet placedSpecialBet = api.placeSpecialBetAsObject(parameter); // !! you will bet real money by this operation !!
		System.out.println(placedSpecialBet);
	}
}
