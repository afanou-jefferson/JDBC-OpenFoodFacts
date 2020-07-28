package exceptions;

public class TraitementFichierException extends RuntimeException {

    public TraitementFichierException() {
    }

    public TraitementFichierException(String message) {
        super(message);
    }

    public TraitementFichierException(String message, Throwable cause) {
        super(message, cause);
    }

    public TraitementFichierException(Throwable cause) {
        super(cause);
    }
}
