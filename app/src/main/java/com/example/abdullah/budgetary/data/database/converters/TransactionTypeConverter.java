package com.example.abdullah.budgetary.data.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.abdullah.budgetary.data.database.TransactionType;

public class TransactionTypeConverter {
    @TypeConverter
    public static TransactionType toTransactionType(int type) {
        return TransactionType.getType(type);
    }

    @TypeConverter
    public static int toInteger(TransactionType type) {
        return type.id;
    }

}
