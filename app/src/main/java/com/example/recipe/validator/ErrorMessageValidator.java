package com.example.recipe.validator;

import java.util.HashMap;

public abstract class ErrorMessageValidator {

    public static String getMessage(String keyMessage) {
        return MESSAGES().get(keyMessage);
    }

    private static HashMap<String, String>MESSAGES() {
        HashMap<String, String> err =  new HashMap<String, String>();

        err.put("required", "ce champs est obligatoire.");
        err.put("regex", "ce champs n'est pas valide.");
        err.put("min", "ce champs doit avoir au moins %s caractère(s).");
        err.put("max", "ce champs ne doit pas contenir plus de %s caractère(s).");
        err.put("between", "ce champs doit avoir entre %s et %s caractère(s).");
        err.put("positive", "ce champs doit avoir un nombre supérieur à zéro.");
        err.put("number", "ce champs doit avoir une valeur supérieur à zéro.");
        err.put("time", "heure invalide.");

        return err;
    }
}
