package cn.ivan.futuredemo;

import cn.ivan.future.core.FutureDispatcher;
import cn.ivan.future.core.FutureRequest;
import cn.ivan.future.core.FutureRequestBuilder;
import cn.ivan.future.core.HttpFutureResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class FutureDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureDemoApplication.class, args);
    }

    @Autowired
    private FutureDispatcher futureDispatcher;

    @GetMapping("test")
    public String test(String functionId, HttpServletRequest request){
        FutureRequest futureRequest = FutureRequestBuilder.build(functionId, request);
        HttpFutureResponse response = new HttpFutureResponse();
        futureDispatcher.dispatch(futureRequest,response);
        return response.getResponseString();
    }
}
