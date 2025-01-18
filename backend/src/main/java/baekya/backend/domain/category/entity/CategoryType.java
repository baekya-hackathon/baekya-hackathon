package baekya.backend.domain.category.entity;

import baekya.backend.common.exception.BaekyaException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

import static baekya.backend.common.exception.ErrorCode.*;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    SOCIAL("social"),
    POLITICS("politics"),
    ECONOMY("economy"),
    LIFE("life");

    private final String value;

    public static CategoryType from(String value) {
        return Arrays.stream(values())
                .filter(it -> Objects.equals(it.value.toLowerCase(), value.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new BaekyaException(INVALID_CATEGORY_TYPE.getStatus(), INVALID_CATEGORY_TYPE.getMessage(), 400));
    }
}
