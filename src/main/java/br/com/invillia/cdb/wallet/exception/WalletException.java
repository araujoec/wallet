package br.com.invillia.cdb.wallet.exception;

import br.com.invillia.cdb.wallet.exception.enums.WalletEnumException;

public class WalletException extends RuntimeException {
    public WalletException(WalletEnumException enumException) {
        super(String.format("Code %s: %s", enumException.label, enumException.message));
    }
}
