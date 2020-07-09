package devils.scrumtool.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Map error json
    Map<String, Object> errorResponse = new HashMap<>();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> returnMapError(Exception ex) {
        // Return a map with error message
        errorResponse.put("serverErrorMessage", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> returnMapError(CustomException ex) {
        // Return a map with error message
        errorResponse.put("serverErrorMessage", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
