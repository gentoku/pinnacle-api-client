package pinnacle.api;

import java.time.ZonedDateTime;

import pinnacle.api.enums.BET_TYPE;
import pinnacle.api.enums.LEG_BET_TYPE;
import pinnacle.api.enums.TEAM_TYPE;

public class Validators {

	static void toGetLeagues(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.validateKeys();
	}

	static void toGetFixtures(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("since");
		validator.addOptionalKey("isLive");
		validator.validateKeys();
	}

	static void toGetSettledFixtures(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("since");
		validator.validateKeys();
	}

	static void toGetSpecialFixtures(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("category");
		validator.addOptionalKey("eventId");
		validator.addOptionalKey("specialId	");
		validator.addOptionalKey("since");
		validator.validateKeys();
	}

	static void toGetSettledSpecialFixtures(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("since");
		validator.validateKeys();
	}

	static void toGetTeaserGroups(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("oddsFormat");
		validator.validateKeys();
	}

	static void toGetOdds(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("since");
		validator.addOptionalKey("islive");
		validator.addOptionalKey("oddsFormat");
		validator.validateKeys();
	}

	static void toGetParlayOdds(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("since");
		validator.addOptionalKey("islive");
		validator.addOptionalKey("oddsFormat");
		validator.validateKeys();
	}

	static void toGetTeaserOdds(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("teaserId");
		validator.validateKeys();
	}

	static void toGetSpecialOdds(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addOptionalKey("leagueIds");
		validator.addOptionalKey("since");
		validator.addOptionalKey("specialId");
		validator.addOptionalKey("oddsFormat");
		validator.validateKeys();
	}

	static void toGetLine(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.addRequiredKey("leagueId");
		validator.addRequiredKey("eventId");
		validator.addRequiredKey("periodNumber");
		validator.addRequiredKey("betType");
		BET_TYPE type = BET_TYPE.fromAPI((String) validator.getValue("betType"));
		if (type == BET_TYPE.SPREAD || type == BET_TYPE.MONEYLINE || type == BET_TYPE.TEAM_TOTAL_POINTS) {
			validator.addRequiredKey("team");
		}
		if (type == BET_TYPE.TOTAL_POINTS || type == BET_TYPE.TEAM_TOTAL_POINTS) {
			validator.addRequiredKey("side");
		}
		if (type == BET_TYPE.SPREAD || type == BET_TYPE.TOTAL_POINTS || type == BET_TYPE.TEAM_TOTAL_POINTS) {
			validator.addRequiredKey("handicap");
		}
		validator.addRequiredKey("oddsFormat");
		validator.validateKeys();
	}

	static void toGetParlayLines(Parameter parameter) throws PinnacleException {
		for (Parameter leg : parameter.legs()) {
			validateLegToGetParlayLines(leg);
		}
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("oddsFormat");
		validator.validateKeys();
	}

	private static void validateLegToGetParlayLines(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("uniqueLegId");
		validator.addRequiredKey("eventId");
		validator.addRequiredKey("periodNumber");
		validator.addRequiredKey("legBetType");
		LEG_BET_TYPE type = LEG_BET_TYPE.fromAPI((String) validator.getValue("legBetType"));
		if (type == LEG_BET_TYPE.SPREAD || type == LEG_BET_TYPE.MONEYLINE) {
			validator.addRequiredKey("team");
		}
		if (type == LEG_BET_TYPE.TOTAL_POINTS) {
			validator.addRequiredKey("side");
		}
		if (type == LEG_BET_TYPE.SPREAD || type == LEG_BET_TYPE.TOTAL_POINTS) {
			validator.addRequiredKey("handicap");
		}
		validator.validateKeys();
	}

	static void toGetTeaserLines(Parameter parameter) throws PinnacleException {
		for (Parameter leg : parameter.legs()) {
			validateLegToGetTeaserLines(leg);
		}
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("teaserId");
		validator.addRequiredKey("oddsFormat");
		validator.validateKeys();
	}

	private static void validateLegToGetTeaserLines(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("legId");
		validator.addRequiredKey("eventId");
		validator.addRequiredKey("betType");
		BET_TYPE betType = BET_TYPE.fromAPI((String) validator.getValue("betType"));
		if (betType == BET_TYPE.SPREAD) {
			validator.addRequiredKey("team");
			TEAM_TYPE teamType = TEAM_TYPE.fromAPI((String) validator.getValue("team"));
			if (teamType == TEAM_TYPE.DRAW) {
				throw PinnacleException
						.parameterInvalid("Currenlty, only TEAM_TYPE.TEAM1 and TEAM_TYPE.TEAM2 are supported.");
			}
		} else if (betType == BET_TYPE.TOTAL_POINTS) {
			validator.addRequiredKey("side");
		} else {
			throw PinnacleException
					.parameterInvalid("Currently, only BET_TYPE.SPREAD and BET_TYPE.TOTAL_POINTS are supported.");
		}
		validator.addRequiredKey("periodNumber");
		validator.addRequiredKey("handicap");
		validator.validateKeys();
	}

	static void toGetSpecialLines(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("specialId");
		validator.addRequiredKey("contestantId");
		validator.addRequiredKey("oddsFormat");
		validator.validateKeys();
	}

	static void toPlaceBet(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("uniqueRequestId");
		validator.addRequiredKey("acceptBetterLine");
		validator.addOptionalKey("customerReference");
		validator.addRequiredKey("oddsFormat");
		validator.addRequiredKey("stake");
		validator.addRequiredKey("winRiskStake");
		validator.addRequiredKey("sportId");
		validator.addRequiredKey("eventId");
		validator.addRequiredKey("periodNumber");
		validator.addRequiredKey("betType");
		BET_TYPE type = BET_TYPE.fromAPI((String) validator.getValue("betType"));
		if (type == BET_TYPE.SPREAD || type == BET_TYPE.MONEYLINE || type == BET_TYPE.TEAM_TOTAL_POINTS) {
			validator.addRequiredKey("team");
		}
		if (type == BET_TYPE.TOTAL_POINTS || type == BET_TYPE.TEAM_TOTAL_POINTS) {
			validator.addRequiredKey("side");
		}
		validator.addRequiredKey("lineId");
		validator.addOptionalKey("altLineId");
		validator.addOptionalKey("pitcher1MustStart");
		validator.addOptionalKey("pitcher2MustStart");
		validator.validateKeys();
	}

	static void toPlaceParlayBet(Parameter parameter) throws PinnacleException {
		for (Parameter leg : parameter.legs()) { 
			validateLegToPlaceParlayBet(leg);
		}
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("uniqueRequestId");
		validator.addRequiredKey("acceptBetterLine");
		validator.addRequiredKey("oddsFormat");
		validator.addRequiredKey("riskAmount");
		validator.addRequiredKey("roundRobinOptions");
		validator.validateKeys();
	}

	private static void validateLegToPlaceParlayBet(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("uniqueLegId");
		validator.addRequiredKey("legBetType");
		LEG_BET_TYPE type = LEG_BET_TYPE.fromAPI((String) validator.getValue("legBetType"));
		if (type == LEG_BET_TYPE.SPREAD || type == LEG_BET_TYPE.MONEYLINE) {
			validator.addRequiredKey("team");
		}
		if (type == LEG_BET_TYPE.TOTAL_POINTS) {
			validator.addRequiredKey("side");
		}
		validator.addRequiredKey("lineId");
		validator.addOptionalKey("altLineId");
		validator.addRequiredKey("eventId");
		validator.addRequiredKey("sportId");
		validator.addRequiredKey("periodNumber");
		validator.addOptionalKey("pitcher1MustStart");
		validator.addOptionalKey("pitcher2MustStart");
		validator.validateKeys();
	}

	static void toPlaceTeaserBet(Parameter parameter) throws PinnacleException {
		for (Parameter leg : parameter.legs()) {
			validateLegToPlaceTeaserBet(leg);
		}
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("uniqueRequestId");
		validator.addRequiredKey("teaserId");
		validator.addRequiredKey("oddsFormat");
		validator.addRequiredKey("winRiskStake");
		validator.addRequiredKey("stake");
		validator.validateKeys();
	}

	private static void validateLegToPlaceTeaserBet(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("legId");
		validator.addRequiredKey("betType");
		BET_TYPE betType = BET_TYPE.fromAPI((String) validator.getValue("betType"));
		if (betType == BET_TYPE.SPREAD) {
			validator.addRequiredKey("team");
			TEAM_TYPE teamType = (TEAM_TYPE) validator.getValue("team");
			if (teamType == TEAM_TYPE.DRAW) {
				throw PinnacleException
						.parameterInvalid("Currenlty, only TEAM_TYPE.Team1 and TEAM_TYPE.Team2 are supported.");
			}
		}
		if (betType == BET_TYPE.TOTAL_POINTS) {
			validator.addRequiredKey("side");
		}
		validator.addRequiredKey("lineId");
		validator.addRequiredKey("eventId");
		validator.addRequiredKey("handicap");
		validator.validateKeys();
	}

	static void toPlaceSpecialBet(Parameter parameter) throws PinnacleException {
		for (Parameter bet : parameter.bets()) { // if no subParameter, then
													// throws PinncaleException
			validateBetToPlaceSpecialBet(bet);
		}
	}

	private static void validateBetToPlaceSpecialBet(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("uniqueRequestId");
		validator.addRequiredKey("acceptBetterLine");
		validator.addRequiredKey("oddsFormat");
		validator.addRequiredKey("stake");
		validator.addRequiredKey("winRiskStake");
		validator.addRequiredKey("lineId");
		validator.addRequiredKey("specialId");
		validator.addRequiredKey("contestantId");
		validator.validateKeys();
	}

	static void toGetBets(Parameter parameter) throws PinnacleException {
		boolean usesBetIds = parameter.parameters().containsKey("betids");
		boolean usesBetList = parameter.parameters().containsKey("betlist");
		if (usesBetIds && !usesBetList) {
			validateBetIds(parameter);
		} else if (!usesBetIds && usesBetList) {
			validateBetList(parameter);
		} else {
			throw PinnacleException.parameterInvalid(
					"Parameter must have one of 'betlist' and 'betids' (and cannot have both simultaneously).");
		}
	}

	private static void validateBetList(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("betlist");
		validator.addRequiredKey("fromDate");
		validator.addRequiredKey("toDate");
		validator.validateKeys();
		ZonedDateTime from = ZonedDateTime.parse((String) validator.getValue("fromDate"));
		ZonedDateTime to = ZonedDateTime.parse((String) validator.getValue("toDate"));
		if (to.isAfter(from.plusDays(30)) || !to.isAfter(from)) {
			throw PinnacleException.parameterInvalid(
					"fromDate must be earlier than toDate and the difference between them must be less than 30 days.");
		}
	}

	private static void validateBetIds(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("betids");
		validator.validateKeys();
	}

	static void toGetTranslations(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("cultureCodes");
		validator.addRequiredKey("baseTexts");
		validator.validateKeys();
	}

	static void toGetPeriods(Parameter parameter) throws PinnacleException {
		Validator validator = new Validator(parameter);
		validator.addRequiredKey("sportId");
		validator.validateKeys();
	}

}
