package cn.ivan.future.core;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
public class RequestFunctionHandlerMapping implements HandlerMapping{

    private Map<String,Object> handlerMap;


    @Override
    public HandlerExecuteChain getHandler(FutureRequest request) {
        String functionId = request.getFunctionId();
        if(!StringUtils.hasText(functionId)){
            return null;
        }
        Object handler = this.handlerMap.get(functionId);
        if(handler instanceof HandlerExecuteChain){
            return (HandlerExecuteChain)handler;
        }
        return new HandlerExecuteChain(handler);
    }
}
