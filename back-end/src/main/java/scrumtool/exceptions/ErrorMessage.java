package scrumtool.exceptions;

import org.restlet.data.Status;


public class ErrorMessage {

    private String message;
    Status status;

    public ErrorMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
