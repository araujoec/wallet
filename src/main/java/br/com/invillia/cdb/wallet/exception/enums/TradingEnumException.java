package br.com.invillia.cdb.wallet.exception.enums;

public enum TradingEnumException {

    AMOUNT_EQUAL_OR_LOWER_THAN_ZERO("tex-001", "Quantidade de paper menor ou igual a zero."),
    CUSTOMER_NOT_FOUND("tex-002", "Customer não encontrado no banco de dados.");

    public final String label;
    public final String message;

    private TradingEnumException(String label, String message) {
        this.label = label;
        this.message = message;
    }
}
