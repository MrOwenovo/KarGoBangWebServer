package com.example.entity.repo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.lang.ref.SoftReference;

@Data
@Builder(toBuilder = true)
@FieldDefaults(makeFinal = true)
public class RestBeanBuilder<T> {

    public final static boolean DEFAULT = false;

    public final static boolean USER_DEFINED = true;

    @Builder.Default
    @NonNull ResultCode code = ResultCode.SUCCESS;

    String message;

    @Builder.Default
    boolean messageType = DEFAULT;

    T data ;

    public RestBean<T> ToRestBean() {
        SoftReference<Boolean> SoftSuccess =code.getCode() == 200?new SoftReference<>(true):new SoftReference<>(false);
        if (messageType)
            return (RestBean<T>) RestBean.builder().success(SoftSuccess.get()).code(code.getCode()).message(message).data(data).build();
        return (RestBean<T>) RestBean.builder().success(SoftSuccess.get()).code(code.getCode()).message(code.getMessage()).data(data).build();
    }

}

