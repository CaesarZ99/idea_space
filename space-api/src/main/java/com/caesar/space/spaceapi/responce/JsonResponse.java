package com.caesar.space.spaceapi.responce;
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
    private T data;

    public JsonResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static class Builder {
        public static JsonResponse<?> buildSuccess(){
            return new JsonResponse<>(SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE);
        }
        public static JsonResponse<?> buildSuccess(Object data){
            return new JsonResponse<>(SUCCESS_CODE, data);
        }

        public static JsonResponse<?> buildFailure(){
            return new JsonResponse<>(FAILURE_CODE, DEFAULT_FAILURE_MESSAGE);
        }
        public static JsonResponse<?> buildFailure(Object data){
            return new JsonResponse<>(FAILURE_CODE, data);
        }
        public static JsonResponse<?> buildNoData(Object data){
            return new JsonResponse<>(NO_DATA_CODE, data);
        }
        public static JsonResponse<?> buildNoData(){
            return new JsonResponse<>(NO_DATA_CODE, DEFAULT_NO_DATA_MESSAGE);
        }

        public static JsonResponse<?> build(Integer code, Object data) {
            return new JsonResponse<>(code,data);
        }
    }

}
