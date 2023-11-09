package br.com.invillia.cdb.wallet.exception;

import br.com.invillia.cdb.wallet.exception.enums.PaperEnumException;

public class PaperException extends RuntimeException {

    public PaperException(PaperEnumException enumException) {
        super(String.format("Code %s: %s", enumException.label, enumException.message));
    }
}
