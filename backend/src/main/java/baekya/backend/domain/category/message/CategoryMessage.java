package baekya.backend.domain.category.message;

import baekya.backend.common.response.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryMessage implements ResponseMessage {
    ;

    private final String message;
    private final HttpStatus status;
}