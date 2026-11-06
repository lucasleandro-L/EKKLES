package com.ekkles.api.dto;

public record LoginResponse(String token, String tipo, long expiraEmSegundos, String perfil) {

}