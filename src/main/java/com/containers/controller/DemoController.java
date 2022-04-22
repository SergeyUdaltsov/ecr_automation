package com.containers.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class DemoController {

    @GetMapping()
    public String initial() {
        return helloController();
    }

    @GetMapping("/demo/test")
    public String helloController() {
        String page = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Hello world!!! new</h1>\n" +
                "  <script>\n" +
                "  </script>\n" +
                "</body>\n" +
                "</html>";
        return page;
    }
}
