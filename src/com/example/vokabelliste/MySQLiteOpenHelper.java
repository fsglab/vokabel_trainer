package com.example.vokabelliste;

import java.util.LinkedList;
import java.util.List;

import android.R.interpolator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ListeDB";

	private static final String TABLE_LISTE = "liste";
	private static final String KEY_ID = "id";
	private static final String KEY_DEUTSCH = "deutsch";
	private static final String KEY_ENGLISCH = "englisch";
	private static final String[] SPALTEN = { KEY_ID, KEY_DEUTSCH, KEY_ENGLISCH };

	public MySQLiteOpenHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_TABLE_LISTE = "CREATE TABLE " + TABLE_LISTE + " ( "
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DEUTSCH
				+ " TEXT, " + KEY_ENGLISCH + " TEXT )";

		db.execSQL(CREATE_TABLE_LISTE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTE);

		this.onCreate(db);
	}

	// ------------------------------------------------------------------------------------

	public void addVokabel(Vokabel vokabel) {

		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DEUTSCH, vokabel.getDeutsch());
		values.put(KEY_ENGLISCH, vokabel.getEnglisch());

		db.insert(TABLE_LISTE, null, values);

		db.close();

	}

	public Vokabel getVokabel(int id) {

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_LISTE, SPALTEN, "id = ?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}

		Vokabel vokabel = new Vokabel();
		vokabel.setId(Integer.parseInt(cursor.getString(0)));
		vokabel.setDeutsch(cursor.getString(1));
		vokabel.setEnglisch(cursor.getString(2));

		return vokabel;
	}

	public List<Vokabel> getAllVokabeln() {

		List<Vokabel> vokabeln = new LinkedList<Vokabel>();

		String query = "SELECT * FROM " + TABLE_LISTE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Vokabel vokabel = null;
		if (cursor.moveToFirst()) {
			do {
				vokabel = new Vokabel();
				vokabel.setId(Integer.parseInt(cursor.getString(0)));
				vokabel.setDeutsch(cursor.getString(1));
				vokabel.setEnglisch(cursor.getString(2));

				vokabeln.add(vokabel);
			} while (cursor.moveToNext());

		}

		return vokabeln;

	}

	public int updateVokabel(Vokabel vokabel, int id) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DEUTSCH, vokabel.getDeutsch());
		values.put(KEY_ENGLISCH, vokabel.getEnglisch());

		int i = db.update(TABLE_LISTE, values, KEY_ID + " =?",
				new String[] { String.valueOf(id) });

		db.close();
		return i;
	}

	public void deleteVokabel(Vokabel vokabel) {

		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_LISTE, KEY_ID + " =?",
				new String[] { String.valueOf(vokabel.getId()) });

		db.close();

	}

}
