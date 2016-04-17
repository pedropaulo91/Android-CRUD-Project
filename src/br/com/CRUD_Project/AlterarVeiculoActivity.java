package br.com.CRUD_Project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AlterarVeiculoActivity extends Activity implements OnItemClickListener {
	
	private MyDbHelper dbHelper;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_alterar_veiculo_layout);
		
		// Vetor de Strings com os nomes dos campos
		String[] fromFieldNames = {MyDbHelper.V_MARCA,MyDbHelper.V_MODELO,MyDbHelper.V_COR,MyDbHelper.V_PLACA};
		// Vetor de inteiros com os ids dos textViews 
		int[] toViewsIds = {R.id.lblInputMarca,R.id.lblInputModelo,R.id.lblInputCor,R.id.lblInputPlaca};
		// Criando adapter
		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, getAllRows(), fromFieldNames, toViewsIds,0);
		// Referência do listView
		ListView listView = (ListView) findViewById(R.id.listViewAlterarVeiculo);
		// Setando o adapter
		listView.setAdapter(simpleCursorAdapter);
		// Setando o OnItemClickListener
		listView.setOnItemClickListener(this);
		

	}

	// Método para obter todas as linhas da tabela veiculo
	// Retorna um objeto do tipo cursor
	private Cursor getAllRows(){
		// Objeto Cursor
		Cursor cursor = null;
		// Vetor de Strings com os nomes das colunas
		// É NECESSÁRIO colocar o campo _id
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		// Referência do textView que exibe a placa 
		TextView textViewPlaca = (TextView) view.findViewById(R.id.lblInputPlaca);
		String placa = textViewPlaca.getText().toString();
		
		Intent intencao = new Intent(this, AlterarCamposVeiculoActivity.class); 
		
		intencao.putExtra("Placa", placa);
		
		finish();
		
		startActivity(intencao);
		
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
	
}
