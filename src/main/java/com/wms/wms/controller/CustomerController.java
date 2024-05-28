//package com.wms.wms.controller;
//
//
//import com.wms.wms.entity.Customer;
//import com.wms.wms.exception.ResourceNotFoundException;
//import com.wms.wms.dto.response.ResponseSuccess;
//import com.wms.wms.service.ICustomerService;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Min;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class CustomerController {
//    private ICustomerService customerService;
//
//    public CustomerController(ICustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @GetMapping("/customers")
//    public ResponseSuccess findAll() {
//        List<Customer> customerList = customerService.findAll();
//        return new ResponseSuccess(HttpStatus.OK, "Get customers successfully", customerList);
//    }
//
//    @PostMapping("/customers")
//    public ResponseSuccess addCustomer(@RequestBody @Valid Customer customer) {
//        customer.setId(0);
//        Customer dbCustomer = customerService.save(customer);
//
//        return new ResponseSuccess(HttpStatus.CREATED, "Customer added successfully", dbCustomer);
//    }
//
//    @PutMapping("/customers")
//    public ResponseSuccess updateCustomer(@RequestBody @Valid Customer theCustomer) {
//        Customer dbCustomer = customerService.save(theCustomer);
//        return new ResponseSuccess(HttpStatus.ACCEPTED, "Customer updated successfully", dbCustomer);
//    }
//
//    // Get warehouse by id
//    @GetMapping("/customers/{customerId}")
//    public ResponseSuccess findById(
//            @Min(value = 1, message = "Id must be greater than 0") @PathVariable int customerId)
//            throws ResourceNotFoundException {
//
//        Customer customer = customerService.findById(customerId);
//        return new ResponseSuccess(HttpStatus.OK, "Get the customer id " + customerId + " successfully", customer);
//    }
//
//    @DeleteMapping("/customers/{customerId}")
//    public ResponseSuccess deleteById(
//            @Min(value = 1, message = "Id must be greater than 0") @PathVariable int customerId)
//            throws ResourceNotFoundException {
//
//        customerService.deleteById(customerId);
//        return new ResponseSuccess(HttpStatus.NO_CONTENT, "Successfully deleted customer id: " + customerId);
//    }
//}
