package baekya.backend.domain.category.entity;

import baekya.backend.common.exception.BaekyaException;
import baekya.backend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

import static baekya.backend.common.exception.ErrorCode.*;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    SOCIAL("사회"),
    POLITICS("정치"),
    ECONOMY("경제"),
    LIFE("생활");

    private final String name;

    public static CategoryType from(String value) {
        return Arrays.stream(values())
                .filter(it -> Objects.equals(it.name, value))
                .findFirst()
                .orElseThrow(() -> new BaekyaException(INVALID_CATEGORY_TYPE.getStatus(), INVALID_CATEGORY_TYPE.getMessage(), 400));
    }
}
