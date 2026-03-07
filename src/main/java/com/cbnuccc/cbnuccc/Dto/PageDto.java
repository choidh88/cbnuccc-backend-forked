package com.cbnuccc.cbnuccc.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

// This DTO class is for data transferring
@Data
@AllArgsConstructor
public class PageDto<T> {
    // data
    List<T> data;

    // size of current slice's elements
    Integer length;

    // current page's number of all pages
    Integer pageAt;

    // total number of all pages
    Integer totalPage;

    // total number of all elements
    Long totalElement;
}
