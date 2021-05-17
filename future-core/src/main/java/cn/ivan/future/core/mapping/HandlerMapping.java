package cn.ivan.future.core.mapping;

import cn.ivan.future.core.FutureRequest;
import cn.ivan.future.core.HandlerExecuteChain;

/**
 *
 *  通过 handlerMapping 获取处理的handler 执行链
 * @author yanqi69
 * @date 2021/5/14
 */
public interface HandlerMapping {

    /**
     *  通过请求获取 HandlerExecuteChain
     * @param futureRequest request
     * @return {@link HandlerExecuteChain}
     */
    HandlerExecuteChain getHandler(FutureRequest futureRequest);

}
