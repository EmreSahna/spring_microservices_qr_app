package com.emresahna.transactionservice.controller;

import com.emresahna.transactionservice.dto.CustomerTransactionRequest;
import com.emresahna.transactionservice.dto.SellerIdResponse;
import com.emresahna.transactionservice.entity.CustomerTransaction;
import com.emresahna.transactionservice.service.CustomerTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/customer-transaction")
public class CustomerTransactionController {
    private final CustomerTransactionService customerTransactionService;

    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        this.customerTransactionService = customerTransactionService;
    }

    @PostMapping("/read-qr")
    public ResponseEntity<SellerIdResponse> readQr(@RequestParam("qrCode") MultipartFile qrPhoto) {
        return ResponseEntity.ok(customerTransactionService.getDataFormQRCode(qrPhoto));
    }

    @PostMapping("/pay")
    public ResponseEntity<String> doPayment(@RequestBody CustomerTransactionRequest customerTransactionRequest) {
        return ResponseEntity.ok(customerTransactionService.createCustomerTransactionWithWallet(customerTransactionRequest));
    }

    @GetMapping("/get-transaction/{customer_id}")
    public ResponseEntity<List<CustomerTransaction>> getTransactions(@PathVariable String customer_id) {
        return ResponseEntity.ok(customerTransactionService.getTransactions(customer_id));
    }
}