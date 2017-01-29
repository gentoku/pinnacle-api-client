package pinnacle.api;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pinnacle.api.Enums.BET_TYPE;

public class Validator {

	private Map<String, Object> parameters;
	
	private Set<String> keys;
	
	/**
	 * Key names which can be set as parameters for a certain request.
	 */
	private Set<String> validKeys;
	
	Validator (Map<String, Object> parameters) {
		this.parameters = parameters;
		this.keys = this.parameters.keySet();
		this.validKeys = new HashSet<>();
	}
	
	/**
	 * Compares keys which have been set and keys which can be set. 
	 */
	void validateKeys () {
		this.keys.forEach(this::validateKey);
	}
	
	/**
	 * Checks if the key is valid to be set.
	 * @param key
	 */
	private void validateKey (String key) {
		if (!this.validKeys.contains(key)) {
			throw new IllegalArgumentException(key + " is invalid parameter.");
		}
	}
	
	/**
	 * Sets a key the paremeters must have. 
	 * @param key
	 */
	void requiredKey (String key) {
		this.validKeys.add(key);
		this.verify(key);
	}
	
	/**
	 * Checks if the parameters has the key with value.
	 * @param key
	 */
	private void verify (String key) {
		if (!this.parameters.containsKey(key) || this.parameters.get(key) == null) {
			throw new IllegalArgumentException
				(key + " must be set in parameters."); 
		}
	}

	/**
	 * Sets a key the parameters may have.
	 * @param keys
	 */
	void optionalKey (String key) {
		this.validKeys.add(key);
	}
	
	/**
	 * Validates bets request.
	 * 1. Request must choose one of 'betlist' or 'betids'.
	 * 2. Request with 'betlist' must have 'fromDate' and 'toDate' 
	 * 3. Difference between fromDate and toDdate can’t be more than 30 days.
	 */
	void validateBets () {
		if (!(this.keys.contains("betlist") ^ this.keys.contains("betids"))) { // XNOR, both true or both false
			throw new IllegalArgumentException
				("Bets Request must choose one of 'betlist' or 'betids'.");
		}
		if (this.keys.contains("betids")) {
			this.validateBetIds(); 
		} else { // automatically contains betlist
			this.validateBetList();
		}
	}
	
	private void validateBetIds () {
		this.requiredKey("betids");
		this.validateKeys();
	}
	
	private void validateBetList () {
		this.requiredKey("betlist");
		this.requiredKey("fromDate");
		this.requiredKey("toDate");
		this.validateKeys();
		LocalDate from = (LocalDate) this.parameters.get("fromDate");
		LocalDate to = (LocalDate) this.parameters.get("toDate");
		if (to.isAfter(from.plusDays(30))) {
			throw new IllegalArgumentException
				("Difference between fromDate and toDdate can’t be more than 30 days.");
		}
	}

	void validatePlaceBet () {
		this.requiredKey("uniqueRequestId");
		this.requiredKey("acceptBetterLine");
		this.requiredKey("oddsFormat");
		this.requiredKey("stake");
		this.requiredKey("winRiskStake");
		this.requiredKey("sportId");
		this.requiredKey("eventId");
		this.requiredKey("periodNumber");
		this.requiredKey("betType");
		this.requiredKey("lineId");
		this.optionalKey("customerReference");
		this.optionalKey("altLineId");
		this.optionalKey("pitcher1MustStart");
		this.optionalKey("pitcher2MustStart");
		BET_TYPE type = (BET_TYPE) this.parameters.get("betType");
		if (type.requiresTeam()) this.requiredKey("team");
		if (type.requiresSide()) this.requiredKey("side");
		this.validateKeys();
	}
	
	void validateLine () {
		this.requiredKey("sportId");
		this.requiredKey("leagueId");
		this.requiredKey("eventId");
		this.requiredKey("periodNumber");
		this.requiredKey("betType");
		this.requiredKey("oddsFormat");
		BET_TYPE type = (BET_TYPE) this.parameters.get("betType");
		if (type.requiresTeam()) this.requiredKey("team");
		if (type.requiresSide()) this.requiredKey("side");
		if (type.requiresHandicap()) this.requiredKey("handicap");
		this.validateKeys();
	}
	
	void validateLegToGet () {
		this.requiredKey("uniqueLegId");
		this.requiredKey("eventId");
		this.requiredKey("legBetType");
		this.requiredKey("periodNumber");
		BET_TYPE type = (BET_TYPE) this.parameters.get("legBetType");
		this.validateLegBetType(type);
		if (type.requiresTeam()) this.requiredKey("team");
		if (type.requiresSide()) this.requiredKey("side");
		if (type.requiresHandicap()) this.requiredKey("handicap");
		this.validateKeys();
	}
	
	void validateLegToPlace () {
		this.requiredKey("uniqueLegId");
		this.requiredKey("legBetType");
		this.requiredKey("sportId");
		this.requiredKey("eventId");
		this.requiredKey("lineId");
		this.requiredKey("periodNumber");
		this.optionalKey("altLineId");
		this.optionalKey("pitcher1MustStart");
		this.optionalKey("pitcher2MustStart");
		BET_TYPE type = (BET_TYPE) this.parameters.get("legBetType");
		this.validateLegBetType(type);
		if (type.requiresTeam()) this.requiredKey("team");
		if (type.requiresSide()) this.requiredKey("side");
		if (type.requiresHandicap()) this.requiredKey("handicap");
		this.validateKeys();
	}
	
	private void validateLegBetType (BET_TYPE type) {
		if (type == BET_TYPE.TEAM_TOTAL_POINTS)	{
			throw new IllegalArgumentException
				("legBetType doesn't accept TEAM_TOTAL_POINTS.");
		}
	}
}
