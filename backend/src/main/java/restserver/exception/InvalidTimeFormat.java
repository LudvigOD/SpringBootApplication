package restserver.exception;

public class InvalidTimeFormat extends Exception {

  public InvalidTimeFormat() {
    super();
  }

  public InvalidTimeFormat(String errorMessage) {
    super(errorMessage);
  }

  public InvalidTimeFormat(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }

}
