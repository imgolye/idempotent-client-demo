package org.link.controller;

import lombok.extern.slf4j.Slf4j;
import org.link.core.component.IdempotentComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author gol
 */
@Slf4j
@RestController
@RequestMapping("idempotent")
public class IdempotentTestController {

    private final IdempotentComponent idempotentComponent;

    public IdempotentTestController(IdempotentComponent idempotentComponent) {
        this.idempotentComponent = idempotentComponent;
    }

    /**
     * 幂等测试接口
     */
    @GetMapping("insert/{token}")
    public void insert(final HttpServletResponse response,@PathVariable String token) throws IOException {
        PrintWriter writer = response.getWriter();
        String key = token;
        boolean check = idempotentComponent.idempotentCheck(key, 1L,TimeUnit.SECONDS,true);
        if (!check) {
            response.setStatus(500);
            writer.print("insert fail");
            log.info("insert fail...");
        } else {
            response.setStatus(200);
            writer.print("token=" + token + " insert success");
            log.info("token=" + token + " insert success");
        }
        writer.close();
    }

    /**
     * 非幂等接口,不拦截
     *
     * @param id id
     * @return java.lang.String
     */
    @GetMapping("select/{id}")
    public String select(@PathVariable String id) {
        log.info("select...");
        return "select " + id + " success";
    }

}
