package com.rh.util;

import org.mindrot.jbcrypt.BCrypt;

public class GenererHash {
    public static void main(String[] args) {
        String motDePasse = "password123";
        String hash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        System.out.println("Hash généré: " + hash);
        System.out.println("Longueur: " + hash.length());
    }
}