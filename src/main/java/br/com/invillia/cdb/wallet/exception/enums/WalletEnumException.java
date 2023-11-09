package br.com.invillia.cdb.wallet.exception.enums;

public enum WalletEnumException {

    NOT_ENOUGH_BALANCE("wex-001", "Customer não possui saldo suficiente para comprar CDB."),
    NO_CDB_PAPER_TO_SELL("wex-002", "Customer não possui saldo em CDB para fazer venda." );

    public final String label;
    public final String message;

    private WalletEnumException(String label, String message) {
        this.label = label;
        this.message = message;
    }
}
