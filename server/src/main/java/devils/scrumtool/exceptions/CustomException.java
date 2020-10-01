package devils.scrumtool.exceptions;

public class CustomException extends RuntimeException {

    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}
