package examples;

import java.time.LocalDate;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Bets;
import pinnacle.api.enums.BETLIST_TYPE;

public class GetBets {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.betlist(BETLIST_TYPE.SETTLED);
		//parameter.betids();
		parameter.fromDate(LocalDate.of(2017, 1, 20));
		parameter.toDate(LocalDate.of(2017, 1, 31));
		
		// response as plain json text
		String jsonText = api.getBetsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Bets bets = api.getBetsAsObject(parameter);
		System.out.println(bets);
	}
}
