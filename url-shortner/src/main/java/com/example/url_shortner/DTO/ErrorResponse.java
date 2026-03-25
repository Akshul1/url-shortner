package com.example.url_shortner.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse
{
    private String errorCode;
    private String message;
}
