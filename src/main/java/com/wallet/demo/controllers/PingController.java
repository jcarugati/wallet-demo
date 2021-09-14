package com.wallet.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PingController expose ping endpoints.
 */
@RestController
public class PingController {

    /**
     * Endpoint to ping the app.
     *
     * @return Returns pong
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
