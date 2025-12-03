package com.example.bankmanagesystem.dto.common;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private long total;     // 总记录数
    private int page;       // 当前页
    private int size;       // 页大小
    private List<T> records; // 数据列表
}
