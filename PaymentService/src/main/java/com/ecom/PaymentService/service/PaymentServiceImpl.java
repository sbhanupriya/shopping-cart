package com.ecom.PaymentService.service;

import com.ecom.PaymentService.entity.TransactionDetails;
import com.ecom.PaymentService.model.PaymentMode;
import com.ecom.PaymentService.model.PaymentRequest;
import com.ecom.PaymentService.model.PaymentResponse;
import com.ecom.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details {} ", paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCEESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .paymentDate(Instant.now())
                .build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction completed with id {} ", transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the orderId");
        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(Long.valueOf(orderId));
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();
        return paymentResponse;
    }

}
