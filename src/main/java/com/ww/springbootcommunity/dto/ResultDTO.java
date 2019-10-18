package com.ww.springbootcommunity.dto;


import lombok.Data;


@Data
public class ResultDTO {

    private Integer code;
    private String message;


    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
