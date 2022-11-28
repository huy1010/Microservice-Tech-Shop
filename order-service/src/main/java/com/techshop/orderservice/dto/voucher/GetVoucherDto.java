package com.techshop.orderservice.dto.voucher;

import com.techshop.orderservice.entity.Voucher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetVoucherDto {
    private final Long voucherId;
    private final String voucherName;
    private final String voucherDesc;
    private final Integer amount;
    private final LocalDateTime validDate;
    private final LocalDateTime expirationDate;
    private final Integer voucherValue;
    private final Long cappedAt;
    private final Boolean isDeleted;

    public GetVoucherDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.voucherName = voucher.getVoucherName();
        this.voucherDesc = voucher.getVoucherDesc();
        this.amount = voucher.getAmount();
        this.validDate = voucher.getValidDate();
        this.expirationDate = voucher.getExpirationDate();
        this.voucherValue = voucher.getVoucherValue();
        this.cappedAt = voucher.getCappedAt();
        this.isDeleted = voucher.getIsDeleted();
    }
}