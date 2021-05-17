package cn.ivan.future.core;

/**
 * @author yanqi69
 * @date 2021/5/17
 */
public abstract class BeanNameHandlerAdapter implements HandlerAdapter{


    @Override
    public boolean supports(Object handler) {
        return handler instanceof String;
    }

    @Override
    public void handle(FutureRequest request, FutureResponse response, Object handler) {
        // 从工厂中获取 bean
        Object bean = getBean((String) handler);
    }


    /**
     *  获取bean 的 方法，
     * @param beanName 名称
     * @return Object
     */
    protected abstract Object getBean(String beanName);
}
