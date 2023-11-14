package com.example.recipe.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Moment {

    public static String at() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }
}
