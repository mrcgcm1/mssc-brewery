package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/{customId}"})
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customId") UUID customId){
        return new ResponseEntity<>(customerService.getCustomerById(customId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CustomerDto> handlePost(@RequestBody CustomerDto beerDto){
        CustomerDto savedDto = customerService.saveNewCustomer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer" + savedDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.OK);

    }

    @PutMapping({"/{customId}"})
    public ResponseEntity<BeerDto> handleUpdate(@PathVariable("customId") UUID beerId,@RequestBody CustomerDto beerDto){
        customerService.updateCustomer(beerId, beerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    @DeleteMapping({"/{customId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable("customId") UUID beerId){
        customerService.deleteById(beerId);

    }
}
