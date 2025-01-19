package baekya.backend.domain.article.controller;

import baekya.backend.common.response.BaekyaApiResponse;
import baekya.backend.domain.article.dto.response.ArticleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "뉴스 관련 API")
public interface ArticleApi {
    @Operation(
            summary = "카테고리별 기사 조회",
            description = "특정 카테고리에 해당하는 기사 목록을 조회합니다.",
            parameters = {
                    @Parameter(
                            name = "category",
                            description = "카테고리 주제",
                            required = true,
                            schema = @Schema(type = "string", allowableValues = {"SOCIAL", "POLITICS", "ECONOMY", "LIFE"}),
                            example = "SOCIAL"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "기사 조회에 성공했습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content
                    )
            }
    )
    @GetMapping("/category")
    BaekyaApiResponse<List<ArticleResponse>> getArticleByCategory(
            @RequestParam String category
    );

    @Operation(
            summary = "기사 상세 조회",
            description = "특정 기사 ID에 해당하는 기사의 상세 정보를 조회합니다.",
            parameters = {
                    @Parameter(
                            name = "articleId",
                            description = "기사 ID",
                            required = true,
                            schema = @Schema(type = "integer", example = "1")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "기사 상세 조회에 성공했습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청입니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "해당 ID의 기사를 찾을 수 없습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 내부 오류입니다.",
                            content = @Content
                    )
            }
    )
    BaekyaApiResponse<ArticleResponse> getArticleDetail(
            @PathVariable("articleId") Long articleId
    );
}