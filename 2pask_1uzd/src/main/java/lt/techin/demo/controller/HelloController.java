package lt.techin.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public ResponseEntity<String> printText() {
    return ResponseEntity.ok("Hello, Spring Web!");
  }

//  @GetMapping("/hello")
//  public String printText() {
//    return "Hello, Spring Web!";
//  }
}
