package br.com.CRUD_Project;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
	static final String DB_NAME = "garagem.bd";
	static final int DB_VERSION = 1;
	static final String TABLE_NAME = "veiculo";
	static final String V_ID = "_id";
	static final String V_MARCA = "marca";
	static final String V_MODELO = "modelo";
	static final String V_COR = "cor";
	static final String V_PLACA = "placa";
	private final String CREATE_TABLE_STMT = "create table " + TABLE_NAME + " ("+ V_ID + 
						 " integer primary key autoincrement, " + V_MARCA + " text, " +
						 V_MODELO + " text, " + V_COR + " text, " + V_PLACA + " text unique)" ;
	
	private final String DROP_TABLE_STMT = "drop table if exists " + TABLE_NAME;
	
	// Criação do banco de dados
	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try {
			db.execSQL(CREATE_TABLE_STMT);
			
		} catch (SQLException e) {
			 Log.e("Erro DbHelper: " ,e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL(DROP_TABLE_STMT);
		
		onCreate(db);
		
	}

}
