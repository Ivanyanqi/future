package cn.ivan.future.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * handler 执行链
 *
 * @author yanqi69
 * @date 2021/5/14
 */
public class HandlerExecuteChain {

    /**
     * 具体执行 方法, 可以是任何的 Object
     */
    @Getter
    private Object handler;

    private List<FutureInterceptor> interceptorList;

    private FutureInterceptor[] interceptors;


    public HandlerExecuteChain(Object handler) {
        this(handler, null);
    }

    public HandlerExecuteChain(Object handler, FutureInterceptor... interceptors) {
        this.handler = handler;
        this.interceptors = interceptors;
    }

    public boolean applyPreHandle(FutureRequest request, FutureResponse response) {
        FutureInterceptor[] interceptors = getInterceptors();
        if (interceptors == null || interceptors.length == 0) {
            return true;
        }
        for (int i = 0; i < interceptors.length; i++) {
            if (!interceptors[i].preHandle(request, response, this.handler)) {
                return false;
            }
        }
        return true;
    }


    public void applyPostHandle(FutureRequest request, FutureResponse response) {
        FutureInterceptor[] interceptors = getInterceptors();

        if (interceptors == null || interceptors.length == 0) {
            return;
        }
        for (int i = interceptors.length - 1; i >= 0; i--) {
            interceptors[i].postHandle(request, response, this.handler);
        }
    }

    public void addInterceptor(FutureInterceptor interceptor){
        if(interceptorList == null){
            interceptorList = new ArrayList<>();
        }
        interceptorList.add(interceptor);
    }

    private FutureInterceptor[] getInterceptors(){
        if(this.interceptors == null && this.interceptorList != null){
            this.interceptors = this.interceptorList.toArray(new FutureInterceptor[this.interceptorList.size()]);
        }
        return this.interceptors;
    }

}
