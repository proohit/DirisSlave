package audioplayer.spotify;

public class InvalidUriException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InvalidUriException() {
        super("Invalid Uri provided");
    }
}
