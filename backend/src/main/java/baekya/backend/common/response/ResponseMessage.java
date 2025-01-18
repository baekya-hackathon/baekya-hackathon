package baekya.backend.common.response;

import org.springframework.http.HttpStatus;

public interface ResponseMessage {

    String getMessage();
    HttpStatus getStatus();
}
