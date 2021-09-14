package com.wallet.lemon.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    public PingController() {
    }

    @GetMapping(path = "/ping")
    public String ping() {
        return "pong";
    }
}
