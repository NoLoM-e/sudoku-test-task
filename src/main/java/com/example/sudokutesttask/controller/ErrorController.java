package com.example.sudokutesttask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ErrorController {

    @GetMapping("/error/{message}")
    public ResponseEntity<String> handleErrorWithMessage(@PathVariable String message) {
        return ResponseEntity.badRequest().body(message);
    }

    @GetMapping("/error/404")
    public ResponseEntity<String> handle404() {
        return ResponseEntity.notFound().build();
    }
}
