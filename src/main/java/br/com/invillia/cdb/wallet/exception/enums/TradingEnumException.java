package br.com.invillia.cdb.wallet.exception.enums;

public enum TradingEnumException {

    AMOUNT_EQUAL_OR_LOWER_THAN_ZERO("trading-exception-001", "Quantidade de papéis menor ou igual a zero."),
    CUSTOMER_NOT_FOUND("trading-exception-002", "Cliente não encontrado no banco de dados.");

    public final String label;
    public final String message;

    private TradingEnumException(String label, String message) {
        this.label = label;
        this.message = message;
    }
}
