package com.example.mediatekformationmobile.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mediatekformationmobile.model.Formation;

import java.util.ArrayList;
import java.util.List;

public class FormationDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "formations.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORIS = "favoris";
    private static final String COL_ID_FORMATION = "idFormation";

    public FormationDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORIS + " (" +
                COL_ID_FORMATION + " INTEGER PRIMARY KEY)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        onCreate(db);
    }

    public void insertFavoris(int idFormation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID_FORMATION, idFormation);
        db.insert(TABLE_FAVORIS, null, values);
        db.close();
    }

    public void deleteFavoris(int idFormation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORIS, COL_ID_FORMATION + "=?",
                new String[]{String.valueOf(idFormation)});
        db.close();
    }

    public boolean isFavoris(int idFormation) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_FAVORIS + " WHERE " + COL_ID_FORMATION + "=?",
                new String[]{String.valueOf(idFormation)}
        );
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public List<Integer> getIdFavoris() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Integer> ids = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORIS, null);
        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_FORMATION)));
        }
        cursor.close();
        db.close();
        return ids;
    }

    public void nettoyerFavorisObsoletes(List<Formation> formations) {
        List<Integer> idsFavoris = getIdFavoris();

        for (int idFavori : idsFavoris) {
            boolean existe = false;

            for (Formation formation : formations) {
                if (formation.getId() == idFavori) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                deleteFavoris(idFavori);
            }
        }
    }
}
