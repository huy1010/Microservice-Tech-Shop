package com.techshop.clients.productservice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VariantDetailResponse {
    private  Object content;
    private  String errors;
    private Long status;
    private LocalDate timestamp;
}
