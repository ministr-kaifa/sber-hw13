package ru.zubkoff.sber.hw13;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/items")
public class ApiController {
  
  record Item(long id, String name) {
  }

  @CrossOrigin(origins = {"*"})
  @GetMapping(value = "/as-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<Item>> getAllItems() {
    return Flux.just(
        new Item(1, "Apple"),
        new Item(2, "Pencil"),
        new Item(3, "Mug"),
        new Item(4, "Dog"),
        new Item(5, "Peach"))
      .map(item -> ServerSentEvent.builder(item).build())
      .delayElements(Duration.ofSeconds(5))
      .doFirst(() -> System.out.println("stream started"))
      .doOnNext(item -> System.out.println("next item"))
      .doOnComplete(() -> System.out.println("stream succeed"))
      .doOnCancel(() -> System.out.println("stream canceled"));
  }
  
}
