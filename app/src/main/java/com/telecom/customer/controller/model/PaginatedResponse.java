package com.telecom.customer.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> content;
    private int size;
    private int page;
    private int totalPages;
}
