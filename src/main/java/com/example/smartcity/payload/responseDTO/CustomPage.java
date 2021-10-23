package com.example.smartcity.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPage<T> {
    private List<T> content;
    private int numberOfElements;
    private int number;
    private long totalElements;
    private int totalPages;
    private int size;
}
