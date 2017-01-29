package examples;

import pinnacle.api.Parameter;
import pinnacle.api.PinnacleAPI;
import pinnacle.api.PinnacleException;
import pinnacle.api.dataobjects.Translations;
import pinnacle.api.enums.CULTURE_CODE;

public class GetTranslations {

	public static void main(String[] args) throws PinnacleException {
		// settings
		String username = "yourUserName";
		String password = "yourPassword";
		PinnacleAPI api = PinnacleAPI.open(username, password);
		
		// parameter
		Parameter parameter = Parameter.newInstance();
		parameter.cultureCodes(CULTURE_CODE.FRENCH, CULTURE_CODE.GERMAN);
		parameter.baseTexts("soccer", "baseball", "tennis");
		
		// response as plain json text
		String jsonText = api.getTranslationsAsJson(parameter);
		System.out.println(jsonText);
		
		// response as data object
		Translations translations = api.getTranslationsAsObject(parameter);
		System.out.println(translations);
	}
}
