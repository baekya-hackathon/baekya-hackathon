package baekya.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaekyaApiResponse<T> {
    private T data;
    private String message;
    private String code;

    public static <G> BaekyaApiResponse<G> createResponse(G data, ResponseMessage responseMessage) {
        return new BaekyaApiResponse<>(data, responseMessage.getMessage(), responseMessage.toString());
    }
}