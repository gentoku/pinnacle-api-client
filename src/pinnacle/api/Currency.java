package pinnacle.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pinnacle.api.PinnacleException.NoNecessaryKeyException;
import pinnacle.api.Xml.Element;

public class Currency {

	/**
	 * Currency code
	 */
	private String code;
	public String code () { return this.code; }
	
	/**
	 * Name of the currency.
	 */
	private String name;
	public String name () {	return this.name; }
	
	/**
	 * Exchange rate to USD.
	 */
	private BigDecimal rate;
	public BigDecimal rate () { return this.rate; }
	
	private Currency (Element element) {
		this.code = element.attr("code")
				.orElseThrow(() -> NoNecessaryKeyException.of("Currency.@code"));
		this.name = element.value();
		this.rate = element.attr("rate")
				.map(BigDecimal::new)
				.orElseThrow(() -> NoNecessaryKeyException.of("Currency.@rate"));
	}
	
	static List<Currency> parse (String xml) throws PinnacleException {
		return xml.equals("") 
				? new ArrayList<>() 
				: Xml.of(xml)
					.streamOf("currency")
					.map(Currency::new)
					.collect(Collectors.toList());
	}
	
	@Override
	public String toString () {
		return String.format("%s@%s (%s)", this.code, this.rate.toString(), this.name);
	}
}

