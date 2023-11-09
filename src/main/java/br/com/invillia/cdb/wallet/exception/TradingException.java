package br.com.invillia.cdb.wallet.exception;

import br.com.invillia.cdb.wallet.exception.enums.TradingEnumException;

public class TradingException extends RuntimeException {
    public TradingException(TradingEnumException enumException) {
        super(String.format("Code %s: %s", enumException.label, enumException.message));
    }
}
