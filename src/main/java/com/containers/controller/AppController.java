package com.containers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController()
@RequestMapping("/stream")
public class AppController {

    private static final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/start")
    public Flux<ServerSentEvent<OrderPerformingResponse>> streamEvents(@RequestParam String id) {
        return Flux.interval(Duration.ofSeconds(3))
                .map(sequence -> ServerSentEvent.<OrderPerformingResponse> builder()
                        .id(String.valueOf(sequence))
                        .event("Order performing event wrapped")
                        .data(new OrderPerformingResponse(id, sequence.intValue()))
                        .build());
    }

    @GetMapping(value = "/start/simple", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderPerformingResponse> streamFlux(@RequestParam String id) {
        return Flux.interval(Duration.ofSeconds(3))
                .map(sequence -> new OrderPerformingResponse(id, sequence.intValue()));
    }

    @GetMapping(value = "/start/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSseMvc(@RequestParam String id) {
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; true; i++) {
                    SseEventBuilder event = SseEmitter.event()
                            .data(new OrderPerformingResponse(id, i))
                            .id(String.valueOf(i))
                            .name("Order performing event SSE");
                    emitter.send(event);
                    Thread.sleep(3000);
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

}
