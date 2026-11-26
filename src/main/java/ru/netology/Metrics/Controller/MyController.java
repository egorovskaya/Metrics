package ru.netology.Metrics.Controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class MyController {
    private final Counter myRequestCounter;
    public MyController(MeterRegistry meterRegistry) {
        this.myRequestCounter = meterRegistry.counter("custom_requests_total",
                "endpoint", "/my-endpoint");
    }
    @GetMapping("/my-endpoint")
    public String myEndpoint() {
        myRequestCounter.increment(); // увеличиваем счетчик
        return "Custom metric recorded!";
    }

    @GetMapping("/ok")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/bad-request")
    public ResponseEntity<String> notFound() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/server-error")
    public ResponseEntity<String> serverError() {
        return ResponseEntity.internalServerError().body("Internal server error");
    }
}