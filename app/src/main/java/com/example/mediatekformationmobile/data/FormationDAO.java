package com.example.mediatekformationmobile.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mediatekformationmobile.model.Formation;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'accès aux données locales (SQLite) pour la gestion des favoris. Elle permet de stocker,
 * supprimer et récupérer les identifiants des formations marquées comme favorites par l'utilisateur.
 * Cette classe encapsule toute la logique d'accès à la base de données.
 */
public class FormationDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "formations.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORIS = "favoris";
    private static final String COL_ID_FORMATION = "idFormation";

    public FormationDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Appelé lors de la création de la base de données. Initialise les tables nécessaires à l'application.
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FAVORIS + " (" +
                COL_ID_FORMATION + " INTEGER PRIMARY KEY)";
        db.execSQL(createTable);
    }

    /**
     * Appelé lors d'une mise à jour de la version de la base. Supprime et recrée les tables afin
     * d'assurer la cohérence des données.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        onCreate(db);
    }

    /**
     * Ajoute une formation aux favoris.
     * @param idFormation identifiant de la formation
     */
    public void insertFavoris(int idFormation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID_FORMATION, idFormation);
        db.insert(TABLE_FAVORIS, null, values);
        db.close();
    }

    /**
     * Supprime une formation des favoris.
     * @param idFormation identifiant de la formation
     */
    public void deleteFavoris(int idFormation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORIS, COL_ID_FORMATION + "=?",
                new String[]{String.valueOf(idFormation)});
        db.close();
    }

    /**
     * Vérifie si une formation est enregistrée en favori.
     * @param idFormation identifiant de la formation
     * @return true si la formation est en favori, sinon false
     */
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

    /**
     * Récupère la liste des identifiants des formations favorites.
     * @return liste des IDs favoris
     */
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

    /**
     * Nettoie les favoris qui ne sont plus présents dans la liste des formations.Permet d'éviter
     * les données obsolètes en base locale.
     * @param formations liste actuelle des formations disponibles
     */
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
