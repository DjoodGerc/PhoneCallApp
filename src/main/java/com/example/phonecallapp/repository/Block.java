package com.example.phonecallapp.repository;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Block {
    private Long number;
    private String reason;
}
