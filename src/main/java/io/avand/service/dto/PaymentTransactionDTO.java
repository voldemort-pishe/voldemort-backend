package io.avand.service.dto;

import io.avand.domain.enumeration.PaymentTransactionStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PaymentTransactionDTO implements Serializable {

    @NotNull
    private Long id;

    private Long userId;

    private String referenceId;

    private String trackingCode;

    private Integer amount;

    @NotNull
    private Long invoiceId;

    private PaymentTransactionStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public PaymentTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentTransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentTransactionDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", referenceId=" + referenceId +
            ", amount=" + amount +
            ", invoiceId=" + invoiceId +
            ", status=" + status +
            '}';
    }
}
