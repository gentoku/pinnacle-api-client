package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.PlacedParlayBet;
import pinnacle.api.enums.LEG_BET_TYPE;
import pinnacle.api.enums.ODDS_FORMAT;
import pinnacle.api.enums.ROUND_ROBIN_OPTIONS;
import pinnacle.api.enums.SIDE_TYPE;
import pinnacle.api.enums.TEAM_TYPE;

public class PlaceParlayBet {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter leg1 = Parameter.newInstance();
		leg1.uniqueLegId();
		leg1.lineId(138029470);
		leg1.sportId(29);
		leg1.eventId(383910968);
		leg1.legBetType(LEG_BET_TYPE.SPREAD);
		leg1.team(TEAM_TYPE.TEAM_1);
		leg1.periodNumber(0);

		Parameter leg2 = Parameter.newInstance();
		leg2.uniqueLegId();
		leg2.lineId(138029469);
		leg2.sportId(29);
		leg2.eventId(383910969);
		leg2.legBetType(LEG_BET_TYPE.MONEYLINE);
		leg2.team(TEAM_TYPE.TEAM_1);
		leg2.periodNumber(0);
		//leg2.pitcher1MustStart();
		//leg2.pitcher2MustStart();
		
		Parameter leg3 = Parameter.newInstance();
		leg3.uniqueLegId();
		leg3.lineId(138029471);
		//leg3.altLineId();
		leg3.sportId(29);
		leg3.eventId(383910970);
		//leg3.team();
		leg3.legBetType(LEG_BET_TYPE.TOTAL_POINTS);
		leg3.side(SIDE_TYPE.OVER);
		leg3.periodNumber(0);
		//leg3.pitcher1MustStart();
		//leg3.pitcher2MustStart();
		
		Parameter parameter = Parameter.newInstance();
		parameter.uniqueRequestId();
		parameter.acceptBetterLine(true);
		parameter.oddsFormat(ODDS_FORMAT.DECIMAL);
		parameter.riskAmount("10.00");
		parameter.roundRobinOptions(ROUND_ROBIN_OPTIONS.BY_2_LEGS);
		parameter.legs(leg1, leg2, leg3);
		
		// response as plain json text
		String jsonText = api.placeParlayBetAsJson(parameter); // !! you will bet real money by this operation !!
		System.out.println(jsonText);
		
		// response as data object
		PlacedParlayBet placedParlayBet = api.placeParlayBetAsObject(parameter); // !! you will bet real money by this operation !!
		System.out.println(placedParlayBet);
	}
}
