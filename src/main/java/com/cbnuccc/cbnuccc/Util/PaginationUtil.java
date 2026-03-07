package com.cbnuccc.cbnuccc.Util;

import org.springframework.data.domain.Page;

import com.cbnuccc.cbnuccc.Dto.PageDto;

public class PaginationUtil {
    public static <T> PageDto<T> makePaginationMap(Page<T> input) {
        return new PageDto<T>(
                input.getContent(),
                input.getNumberOfElements(),
                input.getNumber(),
                input.getTotalPages(),
                input.getTotalElements());
    }
}
