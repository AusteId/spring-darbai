package lt.techin.rental.controller;

import lt.techin.rental.service.RentalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RentalController {

  private RentalService rentalService;


}
