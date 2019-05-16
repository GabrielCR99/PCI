package com.studio.pci.utils;

import android.support.annotation.NonNull;

public final class NameCutterHelper {

    private static final String regex = " ";

    public NameCutterHelper() {
    }

    public static String cutName(@NonNull String name){
        String[] splittedName = name.split(regex);
        return splittedName[0];
    }

    public static String cutName(@NonNull String name,@NonNull int start,@NonNull int end){
        if(end<=start) return "";
        String[] splittedName = name.split(regex);
        StringBuilder resultName = null;
        for( int i = start ; i < end ; i++) resultName.append(splittedName[i]);
        return resultName.toString();
    }

    public static String cutName(String name, int index){
        String[] splittedName = name.split(regex);
        return splittedName[index];
    }
}