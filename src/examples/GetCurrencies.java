package examples;

import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Currencies;

public class GetCurrencies {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// response as plain json text
		String jsonText = api.getCurrenciesAsJson();
		System.out.println(jsonText);
		
		// response as data object
		Currencies currencies = api.getCurrenciesAsObject();
		System.out.println(currencies);
	}
}
