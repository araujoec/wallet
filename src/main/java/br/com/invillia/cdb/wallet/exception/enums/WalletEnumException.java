package br.com.invillia.cdb.wallet.exception.enums;

public enum WalletEnumException {

    NOT_ENOUGH_BALANCE("wallet-exception-001", "Cliente não possui saldo suficiente para comprar CDB."),
    NO_CDB_PAPER_TO_SELL("wallet-exception-002", "Cliente não possui papéis CDB para fazer venda.");

    public final String label;
    public final String message;

    private WalletEnumException(String label, String message) {
        this.label = label;
        this.message = message;
    }
}
