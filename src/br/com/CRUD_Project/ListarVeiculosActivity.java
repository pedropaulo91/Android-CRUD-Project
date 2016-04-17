package br.com.CRUD_Project;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListarVeiculosActivity extends Activity {
	private MyDbHelper dbHelper;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_veiculos_layout);
		
		
		// Vetor de Strings com os nomes dos campos
		String[] fromFieldNames = {MyDbHelper.V_MARCA,MyDbHelper.V_MODELO,MyDbHelper.V_COR,MyDbHelper.V_PLACA};
		// Vetor de inteiros com os ids dos textViews 
		int[] toViewsIds = {R.id.lblInputMarca,R.id.lblInputModelo,R.id.lblInputCor,R.id.lblInputPlaca};
		// Referência do listView  
		ListView listView = (ListView) findViewById(R.id.listViewListarVeiculos);
		// Criando adapter
		
		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, getAllRows(), fromFieldNames, toViewsIds,0);
		// Setando o adapter
		listView.setAdapter(simpleCursorAdapter);
		
	}
	
	
	// Método para obter todas as linhas da tabela veiculo
	// Retorna um objeto do tipo cursor
	private Cursor getAllRows(){
		// Objeto Cursor
		Cursor cursor = null;
		//startManagingCursor(cursor);
		
		// Vetor de Strings com os nomes das colunas
		// É NECESSÁRIO passor o campo _id
		String[] nomeColunas = {MyDbHelper.V_ID,MyDbHelper.V_MARCA,MyDbHelper.V_MODELO,MyDbHelper.V_COR,MyDbHelper.V_PLACA};
		
		dbHelper = new MyDbHelper(this);
		
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.query(MyDbHelper.TABLE_NAME,nomeColunas, null, null, null,null,null);	
		} catch (Exception e) {
			Log.e("Erro select: ", e.getMessage());
		}
		
		return cursor;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	
}
