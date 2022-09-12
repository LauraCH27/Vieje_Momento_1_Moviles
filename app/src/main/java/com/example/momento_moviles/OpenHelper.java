package com.example.momento_moviles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {
    public OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table TblViaje(codigo_Vieje intiger primary key," +
                "ciudad_destino text not null,cantidad_personas text not null,valor intiger not null," +
                "activo text not null default'si' )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(("DROP TABLE TblViaje"));{
            onCreate(db);
        }

    }
}
