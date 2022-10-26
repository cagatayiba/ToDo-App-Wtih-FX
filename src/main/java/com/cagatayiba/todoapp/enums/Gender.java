package com.cagatayiba.todoapp.enums;

public enum Gender {
    MALE(1),
    FEMALE(2),
    UNDEFINED();

    int value;
    Gender(){

    }
    Gender(int value){
        this.value = value;
    }
    public static Gender getByValue(int value){
        if(value == 0) return MALE;
        if(value == 1) return FEMALE;
        return UNDEFINED;
    }
}
