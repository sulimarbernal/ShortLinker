package com.ShortLinker.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinkReducerController {

    @PostMapping("/createLinkReducer")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("OK");
    }
}