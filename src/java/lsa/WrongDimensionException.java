package lsa;

/**
 * Created by Ruslan on 05.05.2017.
 */
public class WrongDimensionException extends Exception {
    private static final String MESSAGE = "dimension must be positive , integer and less than final matrix dimension";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
