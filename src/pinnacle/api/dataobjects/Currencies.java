package pinnacle.api.dataobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pinnacle.api.Json;
import pinnacle.api.PinnacleException;

public class Currencies extends AbstractDataObject {

	private List<Currency> currencies = new ArrayList<>();

	public List<Currency> currencies() {
		return this.currencies;
	}

	public Stream<Currency> currenciesAsStream() {
		return this.currencies.stream();
	}

	private Currencies(Json json) {
		this.currencies = json.getAsJsonStream("currencies").map(Currency::new).collect(Collectors.toList());
		this.checkRequiredKeys();
	}

	private Currencies() {
		this.isEmpty = true;
	}

	public static Currencies parse(String jsonText) throws PinnacleException {
		return jsonText.equals("{}") ? new Currencies() : new Currencies(Json.of(jsonText));
	}

	@Override
	boolean hasRequiredKeyWithNull() {
		return false;
	}

	@Override
	public String toString() {
		return "Currencies [currencies=" + currencies + "]";
	}

	public static class Currency extends AbstractDataObject {

		private String code;

		public String code() {
			return this.code;
		}

		private String name;

		public String name() {
			return this.name;
		}

		private BigDecimal rate;

		public BigDecimal rate() {
			return this.rate;
		}

		private Currency(Json json) {
			this.code = json.getAsString("code").orElse(null);
			this.name = json.getAsString("name").orElse(null);
			this.rate = json.getAsBigDecimal("rate").orElse(null);
			this.checkRequiredKeys();
		}

		@Override
		boolean hasRequiredKeyWithNull() {
			return this.code == null || this.name == null || this.rate == null;
		}

		@Override
		public String toString() {
			return "Currency [code=" + code + ", name=" + name + ", rate=" + rate + "]";
		}
	}
}
