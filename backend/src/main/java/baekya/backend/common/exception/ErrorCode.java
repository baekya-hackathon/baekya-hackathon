package baekya.backend.common.exception;

import baekya.backend.common.response.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseMessage {

    /**
     * Category Error
     */
    INVALID_CATEGORY_TYPE("지원하지 않는 카테고리 입니다", HttpStatus.BAD_REQUEST);

    /**
     * Article Error
     */


    private final String message;
    private final HttpStatus status;
}
