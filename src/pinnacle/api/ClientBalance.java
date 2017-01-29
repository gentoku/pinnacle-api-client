package pinnacle.api;

import java.math.BigDecimal;

import pinnacle.api.PinnacleException.NoNecessaryKeyException;

public class ClientBalance {

	/**
	 * Amount available for betting.
	 */
	private BigDecimal availableBalance;
	public BigDecimal availableBalance () { return this.availableBalance; } 

	/**
	 * Sum of not yet settled bet amounts.
	 */
	private BigDecimal outstandingTransactions;
	public BigDecimal outstandingTransactions () { return this.outstandingTransactions; }

	/**
	 * Client’s credit.
	 */
	private BigDecimal givenCredit;
	public BigDecimal givenCredit () { return this.givenCredit; } 

	/**
	 * Client’s currency code.
	 */
	private String currency;
	public String currency () { return this.currency; } 

	private ClientBalance (Json json) {
		this.availableBalance = json.getAsBigDecimal("availableBalance")
				.orElseThrow(() -> NoNecessaryKeyException.of("ClientBalance.availableBalance"));
		this.outstandingTransactions = json.getAsBigDecimal("outstandingTransactions")
				.orElseThrow(() -> NoNecessaryKeyException.of("ClientBalance.outstandingTransactions"));
		this.givenCredit = json.getAsBigDecimal("givenCredit")
				.orElseThrow(() -> NoNecessaryKeyException.of("ClientBalance.givenCredit"));
		this.currency = json.getAsString("currency")
				.orElseThrow(() -> NoNecessaryKeyException.of("ClientBalance.currency"));
	} 
	
	static ClientBalance parse (String text) throws PinnacleException {
		if (text.equals("")) throw new PinnacleException("Empty response for Client Balance."); 
		return new ClientBalance(Json.of(text));
	}

	@Override
	public String toString() {
		return "ClientBalance [availableBalance=" + availableBalance
				+ ", outstandingTransactions=" + outstandingTransactions
				+ ", givenCredit=" + givenCredit + ", currency=" + currency
				+ "]";
	}
	
}
