package br.com.CRUD_Project;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends Activity implements OnClickListener {

	private MyDbHelper dbHelper;
	private SQLiteDatabase db;
	private String marca, modelo, cor, placa;
	private EditText editTextMarca, editTextModelo, editTextCor, editTextPlaca;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_layout);
		// Criando os Widgets
		Button btnCadastroCadastrar, btnCadastroLimparCampos;

		// Obtendo referências das Views
		btnCadastroLimparCampos = (Button) findViewById(R.id.btnCadastroLimparCampos);
		btnCadastroCadastrar = (Button) findViewById(R.id.btnCadastroCadastrar);
		editTextMarca = (EditText) findViewById(R.id.editTextCadastroMarca);
		editTextModelo = (EditText) findViewById(R.id.editTextCadastroModelo);
		editTextCor = (EditText) findViewById(R.id.editTextCadastroCor);
		editTextPlaca = (EditText) findViewById(R.id.editTextCadastroPlaca);

		// Setando o botão cadastrar
		btnCadastroCadastrar.setOnClickListener(this);
		btnCadastroLimparCampos.setOnClickListener(this);

	}


	private void cadastrar(EditText editTextMarca, EditText editTextModelo, EditText editTextCor, EditText editTextPlaca){

		marca = editTextMarca.getText().toString();
		modelo = editTextModelo.getText().toString();
		cor = editTextCor.getText().toString();
		placa = editTextPlaca.getText().toString();

		String msg = "Cadastro realizado!";

		dbHelper = new MyDbHelper(this);
		db = dbHelper.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(MyDbHelper.V_MARCA, marca);
		contentValues.put(MyDbHelper.V_MODELO, modelo);
		contentValues.put(MyDbHelper.V_COR, cor);
		contentValues.put(MyDbHelper.V_PLACA, placa);

		try {
			db.insertOrThrow(MyDbHelper.TABLE_NAME, null, contentValues);
			Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
		} catch (SQLException e) {
			Toast.makeText(getBaseContext(),"Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}finally{
			db.close();
		}
	}

	// Ação do botão cadastrar
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnCadastroCadastrar:
			cadastrar(editTextMarca,editTextModelo,editTextCor,editTextPlaca);
			break;
		case R.id.btnCadastroLimparCampos:
			editTextMarca.setText(null);
			editTextModelo.setText(null);
			editTextCor.setText(null);
			editTextPlaca.setText(null);
			break;
		}
	}
	

}
