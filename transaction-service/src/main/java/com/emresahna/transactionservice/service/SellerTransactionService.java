package com.emresahna.transactionservice.service;

import com.emresahna.transactionservice.client.WalletService;
import com.emresahna.transactionservice.dto.BalanceRequest;
import com.emresahna.transactionservice.dto.SellerTransactionRequest;
import com.emresahna.transactionservice.dto.TransactionRequest;
import com.emresahna.transactionservice.entity.SellerTransaction;
import com.emresahna.transactionservice.repository.SellerTransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public record SellerTransactionService(SellerTransactionRepository sellerTransactionRepository, WalletService walletService) {

    public SellerTransaction createSellerTransaction(SellerTransactionRequest sellerTransactionRequest) {
        return sellerTransactionRepository.save(SellerTransaction.builder()
                .buyerId(sellerTransactionRequest.getBuyer_id())
                .sellerId(sellerTransactionRequest.getSeller_id())
                .purchaseItemId(sellerTransactionRequest.getPurchased_item_id())
                .amount(sellerTransactionRequest.getAmount())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build());
    }

    public Boolean createSellerTransactionWithWallet(TransactionRequest transactionRequest) {
        walletService.addBalance(BalanceRequest.builder()
                .id(transactionRequest.getSeller_id())
                .amount(transactionRequest.getAmount())
                .build());

        createSellerTransaction(SellerTransactionRequest.builder()
                .buyer_id(transactionRequest.getBuyer_id())
                .seller_id(transactionRequest.getSeller_id())
                .purchased_item_id(transactionRequest.getPurchased_item_id())
                .amount(transactionRequest.getAmount())
                .build());
        return true;
    }

    public List<SellerTransaction> getTransactions(String sellerId) {
        return sellerTransactionRepository.findBySellerId(sellerId);
    }
}