package guru.springframework.msscbrewery.web.controller.v2;

import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Validated
@RequestMapping(CustomerControllerV2.PATH)
@RestController
public class CustomerControllerV2 {

    public static final String PATH = "/api/v2/customer";

    private final CustomerService customerService;

    public CustomerControllerV2(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"/{customId}"})
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customId") UUID customId){
        return new ResponseEntity<>(customerService.getCustomerById(customId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CustomerDto> handlePost(@Valid @RequestBody CustomerDto customerDto){
        CustomerDto savedDto = customerService.saveNewCustomer(customerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", PATH + savedDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.OK);

    }

    @PutMapping({"/{customId}"})
    public ResponseEntity<BeerDto> handleUpdate(@PathVariable("customId") UUID beerId,@Valid @RequestBody CustomerDto customerDto){
        customerService.updateCustomer(beerId, customerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    @DeleteMapping({"/{customId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable("customId") UUID beerId){
        customerService.deleteById(beerId);

    }
}
