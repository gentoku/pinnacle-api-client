package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;
import pinnacle.api.enums.TEASER_LINES_ERROR_CODE;
import pinnacle.api.enums.TEASER_LINES_LEG_ERROR_CODE;
import pinnacle.api.enums.TEASER_LINES_STATUS;

public class TeaserLines extends AbstractDataObject {

	private TEASER_LINES_STATUS status;

	public TEASER_LINES_STATUS status() {
		return this.status;
	}

	private Optional<TEASER_LINES_ERROR_CODE> errorCode;

	public Optional<TEASER_LINES_ERROR_CODE> errorCode() {
		return this.errorCode;
	}

	private Optional<BigDecimal> price;

	public Optional<BigDecimal> price() {
		return this.price;
	}

	private Optional<BigDecimal> minRiskStake;

	public Optional<BigDecimal> minRiskStake() {
		return this.minRiskStake;
	}

	private Optional<BigDecimal> maxRiskStake;

	public Optional<BigDecimal> maxRiskStake() {
		return this.maxRiskStake;
	}

	private Optional<BigDecimal> minWinStake;

	public Optional<BigDecimal> minWinStake() {
		return this.minWinStake;
	}

	private Optional<BigDecimal> maxWinStake;

	public Optional<BigDecimal> maxWinStake() {
		return this.maxWinStake;
	}

	private List<Leg> legs = new ArrayList<>();

	public List<Leg> legs() {
		return this.legs;
	}

	public Stream<Leg> legsAsStream() {
		return this.legs.stream();
	}

	private TeaserLines(Json json) {
		this.status = json.getAsString("status").map(TEASER_LINES_STATUS::fromAPI).orElse(null);
		this.errorCode = json.getAsString("errorCode").map(TEASER_LINES_ERROR_CODE::fromAPI);
		this.price = json.getAsBigDecimal("price");
		this.minRiskStake = json.getAsBigDecimal("minRiskStake");
		this.maxRiskStake = json.getAsBigDecimal("maxRiskStake");
		this.minWinStake = json.getAsBigDecimal("minWinStake");
		this.maxWinStake = json.getAsBigDecimal("maxWinStake");
		this.legs = json.getAsJsonStream("legs").map(Leg::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private TeaserLines() {
		this.isEmpty = true;
	}

	public static TeaserLines parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new TeaserLines() : new TeaserLines(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return this.status == null;
	}

	@Override
	public String toString() {
		return "TeaserLines [status=" + status + ", errorCode=" + errorCode + ", price=" + price + ", minRiskStake="
				+ minRiskStake + ", maxRiskStake=" + maxRiskStake + ", minWinStake=" + minWinStake + ", maxWinStake="
				+ maxWinStake + ", legs=" + legs + "]";
	}

	public static class Leg extends AbstractDataObject {

		private TEASER_LINES_STATUS status;

		public TEASER_LINES_STATUS status() {
			return this.status;
		}

		private Optional<TEASER_LINES_LEG_ERROR_CODE> errorCode;

		public Optional<TEASER_LINES_LEG_ERROR_CODE> errorCode() {
			return this.errorCode;
		}

		private String legId;

		public String legId() {
			return this.legId;
		}

		private Optional<Long> lineId;

		public Optional<Long> lineId() {
			return this.lineId;
		}

		private Leg(Json json) {
			this.status = json.getAsString("status").map(TEASER_LINES_STATUS::fromAPI).orElse(null);
			this.errorCode = json.getAsString("errorCode").map(TEASER_LINES_LEG_ERROR_CODE::fromAPI);
			this.legId = json.getAsString("legId").orElse(null);
			this.lineId = json.getAsLong("lineId");
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.status == null || this.legId == null;
		}

		@Override
		public String toString() {
			return "Leg [status=" + status + ", errorCode=" + errorCode + ", legId=" + legId + ", lineId=" + lineId
					+ "]";
		}
	}
}
