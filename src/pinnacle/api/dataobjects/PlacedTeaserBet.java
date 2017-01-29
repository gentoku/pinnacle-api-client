package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.PLACE_TEASER_BET_ERROR_CODE;
import pinnacle.api.enums.PLACE_TEASER_BET_LEG_ERROR_CODE;
import pinnacle.api.enums.PLACE_TEASER_BET_LEG_STATUS;
import pinnacle.api.enums.PLACE_TEASER_BET_STATUS;

public class PlacedTeaserBet extends AbstractDataObject {

	private PLACE_TEASER_BET_STATUS status;

	public PLACE_TEASER_BET_STATUS status() {
		return this.status;
	}

	private Optional<PLACE_TEASER_BET_ERROR_CODE> errorCode;

	public Optional<PLACE_TEASER_BET_ERROR_CODE> errorCode() {
		return this.errorCode;
	}

	private String uniqueRequestId;

	public String uniqueRequestId() {
		return this.uniqueRequestId;
	}

	private Optional<Long> betId;

	public Optional<Long> betId() {
		return this.betId;
	}

	private Optional<BigDecimal> price;

	public Optional<BigDecimal> price() {
		return this.price;
	}

	private Optional<BigDecimal> riskAmount;

	public Optional<BigDecimal> riskAmount() {
		return this.riskAmount;
	}

	private Optional<BigDecimal> winAmount;

	public Optional<BigDecimal> winAmount() {
		return this.winAmount;
	}

	private List<InvalidLeg> invalidLegs = new ArrayList<>();

	public List<InvalidLeg> invalidLegs() {
		return this.invalidLegs;
	}

	public Stream<InvalidLeg> invalidLegsAsStream() {
		return this.invalidLegs.stream();
	}

	private List<ValidLeg> validLegs = new ArrayList<>();

	public List<ValidLeg> validLegs() {
		return this.validLegs;
	}

	public Stream<ValidLeg> validLegsAsStream() {
		return this.validLegs.stream();
	}

	private PlacedTeaserBet(Json json) {
		this.status = json.getAsString("status").map(PLACE_TEASER_BET_STATUS::fromAPI).orElse(null);
		this.errorCode = json.getAsString("errorCode").map(PLACE_TEASER_BET_ERROR_CODE::fromAPI);
		this.uniqueRequestId = json.getAsString("uniqueRequestId").orElse(null);
		this.betId = json.getAsLong("betId");
		this.price = json.getAsBigDecimal("price");
		this.riskAmount = json.getAsBigDecimal("riskAmount");
		this.winAmount = json.getAsBigDecimal("winAmount");
		this.invalidLegs = json.getAsJsonStream("invalidLegs").map(InvalidLeg::new).collect(Collectors.toList());
		this.validLegs = json.getAsJsonStream("validLegs").map(ValidLeg::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private PlacedTeaserBet() {
		this.isEmpty = true;
	}

	public static PlacedTeaserBet parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new PlacedTeaserBet() : new PlacedTeaserBet(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null || this.uniqueRequestId == null;
	}

	@Override
	public String toString() {
		return "PlacedTeaserBet [status=" + status + ", errorCode=" + errorCode + ", uniqueRequestId=" + uniqueRequestId
				+ ", betId=" + betId + ", price=" + price + ", riskAmount=" + riskAmount + ", winAmount=" + winAmount
				+ ", invalidLegs=" + invalidLegs + ", validLegs=" + validLegs + "]";
	}

	public static class InvalidLeg extends AbstractDataObject {

		private PLACE_TEASER_BET_LEG_STATUS status;

		public PLACE_TEASER_BET_LEG_STATUS status() {
			return this.status;
		}

		private PLACE_TEASER_BET_LEG_ERROR_CODE errorCode;

		public PLACE_TEASER_BET_LEG_ERROR_CODE errorCode() {
			return this.errorCode;
		}

		private String legId;

		public String legId() {
			return this.legId;
		}

		private InvalidLeg(Json json) {
			this.status = json.getAsString("status").map(PLACE_TEASER_BET_LEG_STATUS::fromAPI).orElse(null);
			this.errorCode = json.getAsString("errorCode").map(PLACE_TEASER_BET_LEG_ERROR_CODE::fromAPI).orElse(null);
			this.legId = json.getAsString("legId").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.status == null || this.errorCode == null || this.legId == null;
		}

		@Override
		public String toString() {
			return "InvalidLeg [status=" + status + ", errorCode=" + errorCode + ", legId=" + legId + "]";
		}
	}

	public static class ValidLeg extends AbstractDataObject {

		private PLACE_TEASER_BET_LEG_STATUS status;

		public PLACE_TEASER_BET_LEG_STATUS status() {
			return this.status;
		}

		private String legId;

		public String legId() {
			return this.legId;
		}

		private Long lineId;

		public Long lineId() {
			return this.lineId;
		}

		private BigDecimal points;

		public BigDecimal points() {
			return this.points;
		}

		private ValidLeg(Json json) {
			this.status = json.getAsString("status").map(PLACE_TEASER_BET_LEG_STATUS::fromAPI).orElse(null);
			this.legId = json.getAsString("legId").orElse(null);
			this.lineId = json.getAsLong("lineId").orElse(null);
			this.points = json.getAsBigDecimal("points").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.status == null || this.legId == null || this.lineId == null || this.points == null;
		}

		@Override
		public String toString() {
			return "ValidLeg [status=" + status + ", legId=" + legId + ", lineId=" + lineId + ", points=" + points
					+ "]";
		}
	}
}
