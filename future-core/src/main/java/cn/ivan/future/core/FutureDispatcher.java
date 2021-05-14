package cn.ivan.future.core;

import java.util.List;
import java.util.Objects;

/**
 * @author yanqi69
 * @date 2021/5/14
 */
public class FutureDispatcher {


    private List<HandlerAdapter> handlerAdapters;


    private List<HandlerMapping> handlerMappings;




    public void dispatch(FutureRequest request,FutureResponse response){
        // 获取handlerExecuteChain
        HandlerExecuteChain hc = getHandler(request);
        if(hc == null){
            return;
        }
        // 获取 Adapter
        HandlerAdapter ha = getAdapter(hc.getHandler());
        if(ha == null){
            return;
        }
        // 执行前置拦截
        if(!hc.applyPreHandle(request, response)){
            return;
        }
        // handler
        ha.handle(request,response,hc.getHandler());

        // 执行后置拦截
        hc.applyPostHandle(request, response);

    }




    private HandlerExecuteChain getHandler(FutureRequest request){
        return handlerMappings.stream().map(handlerMapping -> handlerMapping.getHandler(request)).filter(Objects::nonNull).findFirst().orElse(null);
    }



    private HandlerAdapter getAdapter(Object handler){
        return handlerAdapters.stream().filter(adapter -> adapter.supports(handler)).findFirst().orElse(null);
    }



}
