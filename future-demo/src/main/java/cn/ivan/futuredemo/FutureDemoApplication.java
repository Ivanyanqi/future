package cn.ivan.futuredemo;


import cn.ivan.future.core.FutureDispatcher;
import cn.ivan.future.core.FutureRequest;
import cn.ivan.future.core.HttpFutureRequest;
import cn.ivan.future.core.HttpFutureResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@SpringBootApplication
public class FutureDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutureDemoApplication.class, args);
    }

    @Autowired
    private FutureDispatcher futureDispatcher;

    @GetMapping("api/v1/{functionId}")
    public String test(@PathVariable("functionId") String functionId, HttpServletRequest request, HttpServletResponse response){
        FutureRequest futureRequest = new HttpFutureRequest(functionId,request);
        HttpFutureResponse futureResponse = new HttpFutureResponse(response);
        futureDispatcher.dispatch(futureRequest,futureResponse);
        return futureResponse.getResponseString();
    }
}
