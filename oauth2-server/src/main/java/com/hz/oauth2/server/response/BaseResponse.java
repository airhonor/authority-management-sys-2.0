package com.hz.oauth2.server.response;

import com.hz.oauth2.server.constant.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: OAuth-2.0
 * @author: zgr
 * @create: 2021-07-01 16:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    private String msg;

    @Builder.Default
    private int code = ResultCode.SUCCESS.getCode();

    private T data;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("success", ResultCode.SUCCESS.getCode(), data);
    }

    public static <T> BaseResponse<T> badCredentials() {
        return new BaseResponse<>("bad credentials", ResultCode.UN_AUTHORIZED.getCode(), null);
    }

    public static <T> BaseResponse<T> buildResponse(ResultCode code, String msg) {
        return new BaseResponse<>(msg, code.getCode(), null);

    }


}
