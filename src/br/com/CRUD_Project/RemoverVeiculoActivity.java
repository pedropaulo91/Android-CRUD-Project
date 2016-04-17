package br.com.CRUD_Project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RemoverVeiculoActivity extends Activity implements OnItemClickListener {
	
	private MyDbHelper dbHelper;
	private SQLiteDatabase db;
	private AlertDialog dialogConfirmacao;
	// Id da linha do item que foi clicado
	private long posicaoLinha = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_remover_veiculo_layout);
		// Vetor com os nomes das colunas da tabela veiculo
		String[] fromFieldNames = {MyDbHelper.V_MARCA,MyDbHelper.V_MODELO,MyDbHelper.V_COR,MyDbHelper.V_PLACA};
		// Vetor de inteiros com os ids dos textViews 
		int[] toViewsIds = {R.id.lblInputMarca,R.id.lblInputModelo,R.id.lblInputCor,R.id.lblInputPlaca};
		// Criando adapter
		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, getAllRows(), fromFieldNames, toViewsIds,0);
		// Referência do listView
		ListView listView = (ListView) findViewById(R.id.listViewRemoverVeiculo);
		// Setando o adapter
		listView.setAdapter(simpleCursorAdapter);
		// Setando o OnItemClickListener
		listView.setOnItemClickListener(this);
		
		dialogConfirmacao = criarDialogoConfirmacao();
	}

	// Método para obter todas as linhas da tabela veiculo
	// Retorna um objeto do tipo cursor
	private Cursor getAllRows(){
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

	// Método chamado quando um item do ListView é clicado
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		posicaoLinha = id;
		// Exibe o dialogo
		dialogConfirmacao.show();
	}

	// AlertDialog
	private AlertDialog criarDialogoConfirmacao(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirmacaoExclusaoVeiculo);
		builder.setPositiveButton(getString(R.string.alertDialogOpcaoSim),dialogo);
		builder.setNegativeButton(getString(R.string.alertDialogOpcaoNao),dialogo);
		return builder.create();
	}

	//Classe interna que implementa a interface DialogInterface.OnClickListener
	private class Dialogo implements OnClickListener{
		// Método chamado quano uma opção do AlertDialog é clicada
		@Override
		public void onClick(DialogInterface dialog, int which) {
			String MSG = "Veículo Removido";
			
			switch(which){

			case AlertDialog.BUTTON_POSITIVE:

				db = dbHelper.getWritableDatabase();
				String where = "_id = ?";
				String[] idVeiculo = {Long.toString(posicaoLinha)};
				db.delete(MyDbHelper.TABLE_NAME, where, idVeiculo);
				Toast.makeText(getBaseContext(),MSG, Toast.LENGTH_SHORT).show();
				finish();
				startActivity(new Intent(getBaseContext(), RemoverVeiculoActivity.class));
				break;
				
			case AlertDialog.BUTTON_NEGATIVE:
				
				// Destrói o dialogo removendo-o da tela.
				dialogConfirmacao.dismiss();
				break;
			}
		}
	}

	// Instância da classe Dialogo
	Dialogo dialogo = new Dialogo();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}


}
