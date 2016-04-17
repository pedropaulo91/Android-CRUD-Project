package br.com.CRUD_Project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlterarCamposVeiculoActivity extends Activity implements OnClickListener {
	private MyDbHelper dbhelper;
	private SQLiteDatabase db;
	private AlertDialog dialogoConfirmacao;
	private EditText editTextMarca, editTextModelo, editTextCor, editTextPlaca;;
	private Intent intent;
	private String marca, modelo, cor, placa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alterar_veiculo_layout);

		intent = getIntent();

		Button btnAlterar = (Button) findViewById(R.id.btnAlteracaoAlterar);

		editTextMarca = (EditText) findViewById(R.id.editTextAlteracaoMarca);
		editTextModelo = (EditText) findViewById(R.id.editTextAlteracaoModelo);
		editTextCor = (EditText) findViewById(R.id.editTextAlteracaoCor);
		editTextPlaca = (EditText) findViewById(R.id.editTextAlteracaoPlaca);

		btnAlterar.setOnClickListener(this);
		dialogoConfirmacao = criarDialogoConfirmacao();
		dbhelper = new MyDbHelper(this);
		preencherCampos();

	}

	private void preencherCampos(){
		dbhelper = new MyDbHelper(this);
		db = dbhelper.getReadableDatabase();
		Cursor cursor;
		String[] colunas = {MyDbHelper.V_MARCA,MyDbHelper.V_MODELO,MyDbHelper.V_COR,MyDbHelper.V_PLACA};
		String where = "placa = ?";
		String[] argumentoWhere = {intent.getStringExtra("Placa")};

		cursor = db.query(MyDbHelper.TABLE_NAME, colunas, where, argumentoWhere, null, null, null);
		cursor.moveToFirst();

		editTextMarca.setText(cursor.getString(0));
		editTextModelo.setText(cursor.getString(1));
		editTextCor.setText(cursor.getString(2));
		editTextPlaca.setText(cursor.getString(3));
	}

	// Método chamado quando o botão Alterar é clicado
	@Override
	public void onClick(View v) {
		// Exibe o dialogo
		dialogoConfirmacao.show();
	}

	// AlertDialog
	private AlertDialog criarDialogoConfirmacao(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirmacaoAlteracaoVeiculo);
		builder.setPositiveButton(R.string.alertDialogOpcaoSim, dialogo);
		builder.setNegativeButton(R.string.alertDialogOpcaoNao, dialogo);
		return builder.create();
	}

	//Classe interna que implementa a interface DialogInterface.OnClickListener
	private class Dialogo implements DialogInterface.OnClickListener{
		// Método chamado quano uma opção do AlertDialog é clicada
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch(which){
			case Dialog.BUTTON_POSITIVE:
				marca = editTextMarca.getText().toString();
				modelo = editTextModelo.getText().toString();
				cor = editTextCor.getText().toString();
				placa = editTextPlaca.getText().toString();

				ContentValues values = new ContentValues();
				values.put(MyDbHelper.V_MARCA, marca);
				values.put(MyDbHelper.V_MODELO, modelo);
				values.put(MyDbHelper.V_COR, cor);
				values.put(MyDbHelper.V_PLACA, placa);

				int resultado = 0;

				db = dbhelper.getWritableDatabase();
				String where = "placa = ?";
				String[] placa = {intent.getStringExtra("Placa")};

				resultado = db.update(MyDbHelper.TABLE_NAME, values, where, placa);
				
				if(resultado > 0){
					Toast.makeText(getBaseContext(), "Alteração realizada", Toast.LENGTH_SHORT).show();
					finish();
					startActivity(new Intent(getBaseContext(), DashboardActivity.class));
				}else{
					Toast.makeText(getBaseContext(), "Alteração não realizada", Toast.LENGTH_SHORT).show();
					finish();
					startActivity(new Intent(getBaseContext(), DashboardActivity.class));
				}
				break;
			case Dialog.BUTTON_NEGATIVE:
				// Destrói o dialogo removendo-o da tela.
				dialogoConfirmacao.dismiss();
				break;
			}

		}

	}

	// Instância da classe Dialogo
	Dialogo dialogo = new Dialogo();


}
