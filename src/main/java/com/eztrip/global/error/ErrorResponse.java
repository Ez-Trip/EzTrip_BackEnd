package com.eztrip.global.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter @Builder
public class ErrorResponse {

    private String httpStatus;

    private Integer errorCode;

    private String errorMessage;

    public static ErrorResponse of(HttpStatus httpStatus) {

        return ErrorResponse.builder()
                .httpStatus(httpStatus.toString())
                .errorCode(httpStatus.value())
                .errorMessage(httpStatus.getReasonPhrase())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode) {

        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus().name())
                .errorCode(errorCode.getErrorCode())
                .errorMessage(errorCode.getErrorPhrase())
                .build();
    }

    public static ErrorResponse of(HttpStatus status, BindingResult bindingResult){
        return ErrorResponse.builder()
                .httpStatus(status.toString())
                .errorCode(status.value())
                .errorMessage(createErrorMessage(bindingResult)) // BindingResult 안에 에러 정보들이 들어감
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult) {

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        for (FieldError err : fieldErrors){

            if (!isFirst)
                sb.append(", ");

            else isFirst = false;

            sb.append("[ ");
            sb.append(err.getField());
            sb.append(" ]");
            sb.append(err.getDefaultMessage());
        }

        return sb.toString();
    }
}
