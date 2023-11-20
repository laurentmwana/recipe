package com.example.recipe.helper;


import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class Hasher {

    public static String generate(String s) {
        return BCrypt.withDefaults().hashToString(12, s.toCharArray());
    }

    public static boolean check(String plainPassword, String hashPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashPassword).verified;
    }
}


