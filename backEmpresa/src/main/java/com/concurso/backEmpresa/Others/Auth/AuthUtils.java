package com.concurso.backEmpresa.Others.Auth;

import java.util.Base64;


public class AuthUtils {


    public static String getID(String token){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        String[] chunksPayload = payload.split("\\,");

        return chunksPayload[0].substring(8, chunksPayload[0].length()-1);
    }

    public static String getEmail(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        String[] chunksPayload = payload.split("\\,");

        return chunksPayload[1].substring(7, chunksPayload[1].length()-1);
    }
}

