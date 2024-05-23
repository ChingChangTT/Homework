package src.exception;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
 class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}