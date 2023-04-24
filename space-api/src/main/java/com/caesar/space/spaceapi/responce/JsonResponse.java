package com.caesar.space.spaceapi.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>JsonResponce</h3>
 * <p>json对象返回值</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-23 16:37
 **/
@Data
@NoArgsConstructor
public class JsonResponse<T>{

    public static final Integer SUCCESS_CODE = 1;
    public static final Integer NO_DATA_CODE = 99;
    public static final Integer FAILURE_CODE = 0;
    public static final String DEFAULT_SUCCESS_MESSAGE = "success";
    public static final String DEFAULT_FAILURE_MESSAGE = "failure";
    public static final String DEFAULT_NO_DATA_MESSAGE = "no data";

    private Integer code;
    private T message;

    public JsonResponse(Integer code, T message) {
        this.code = code;
        this.message = message;
    }

    public static class Builder {
        public static JsonResponse<?> buildSuccess(){
            return new JsonResponse<>(SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
        }
        public static JsonResponse<?> buildSuccess(Object message){
            return new JsonResponse<>(SUCCESS_CODE, message);
        }

        public static JsonResponse<?> buildFailure(){
            return new JsonResponse<>(FAILURE_CODE, DEFAULT_FAILURE_MESSAGE);
        }
        public static JsonResponse<?> buildFailure(Object message){
            return new JsonResponse<>(FAILURE_CODE, message);
        }
        public static JsonResponse<?> buildNoData(Object message){
            return new JsonResponse<>(NO_DATA_CODE, message);
        }
        public static JsonResponse<?> buildNoData(){
            return new JsonResponse<>(NO_DATA_CODE, DEFAULT_NO_DATA_MESSAGE);
        }
    }

    public JsonResponse<?> build(Integer code, T message) {
        return new JsonResponse<>(code,message);
    }

}
