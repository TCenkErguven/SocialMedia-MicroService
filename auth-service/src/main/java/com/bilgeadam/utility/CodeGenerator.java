package com.bilgeadam.utility;

import lombok.Data;

import java.util.Arrays;
import java.util.UUID;

//Kayıt olan kullanıcıya bir aktivasyon kodu dönecektir.
//Bu kod ile kullanıcı giriş yapabilir. Bu kod olamadan giriş hatası alacaktır.
//CodeGenerator sınıfı da yalnızca bu kodun üretilmesinden sorumludur.
@Data
public class CodeGenerator {

    public static String generateCode(){
        String code = UUID.randomUUID().toString(); //örnek UUID --> 321321-sdase-123213-e213-32112sdada
        String[] data = code.split("-");
        String newCode="";
        for(String str: data){
            newCode += str.charAt(0);
        }
        return newCode;
    }
}
