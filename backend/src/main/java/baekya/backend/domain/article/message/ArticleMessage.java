package baekya.backend.domain.article.message;

import baekya.backend.common.response.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ArticleMessage implements ResponseMessage {
    SUCCESS_GET_ARTICLES("기사 조회에 성공했습니다.", HttpStatus.OK);

    private final String message;
    private final HttpStatus status;
}
