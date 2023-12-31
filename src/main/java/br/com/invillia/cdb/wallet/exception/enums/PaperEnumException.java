package br.com.invillia.cdb.wallet.exception.enums;

public enum PaperEnumException {

    PAPER_NOT_FOUND("paper-exception-001", "Papel de CDB não encontrado no banco."),
    NONE_PAPER_IN_DATABASE("paper-exception-002", "Nenhum papel de CDB encontrado no banco.");

    public final String label;
    public final String message;

    private PaperEnumException(String label, String message) {
        this.label = label;
        this.message = message;
    }
}
