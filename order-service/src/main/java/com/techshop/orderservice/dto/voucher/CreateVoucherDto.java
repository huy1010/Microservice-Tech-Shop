package com.techshop.orderservice.dto.voucher;

import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
public class CreateVoucherDto {
    @NotNull(message = "Voucher name must not be null")
    @Size(max = 100, message = "Voucher name must be less than 100 characters")
    private String voucherName;

    @Size(max = 100, message = "Category description must be less than 100 characters")
    private String voucherDesc;

    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must greater than 0")
    private Integer amount;

    @NotNull(message = "Valid date must not be null")
    @FutureOrPresent(message = "Valid date must at future or present")
    private LocalDateTime validDate;

    @NotNull(message = "Expiration date must not be null")
    @Future(message = "Valid date must at future")
    private LocalDateTime expirationDate;

    @NotNull(message = "Voucher value must not be null")
    @Positive(message = "Voucher value must greater than 0")
    private Integer voucherValue;

    @NotNull(message = "Capped at must not be null")
    @Positive(message = "Capped at must greater than 0")
    private Long cappedAt;
}
