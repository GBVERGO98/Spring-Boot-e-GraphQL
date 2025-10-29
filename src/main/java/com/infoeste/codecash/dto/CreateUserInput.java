package com.infoeste.codecash.dto;

public record CreateUserInput (
        String nome,
        String email,
        String document,
        String password
) {


}
