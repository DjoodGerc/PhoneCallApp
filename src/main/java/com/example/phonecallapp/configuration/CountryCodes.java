package com.example.phonecallapp.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryCodes {
    private String name;
    private String code;
    private List<String> operators;
    private List<String> fraudNumbers;

}
