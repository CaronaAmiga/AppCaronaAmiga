package br.edu.iff.ccc.caronaamiga.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/")
public class MainRestViewController {

    @GetMapping("/status")
    public ResponseEntity getStatus() {
        return ResponseEntity.ok("API is running");
    }
}