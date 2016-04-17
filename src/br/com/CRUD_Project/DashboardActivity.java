package br.com.CRUD_Project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DashboardActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);
		
		Button btnDashboardNovoVeiculo, btnDashboardAlterar, btnDashboardListar, 
		btnDashboardRemover, btnDashboardSair;
		
		btnDashboardNovoVeiculo = (Button) findViewById(R.id.btnDashboardNovoVeiculo);
		btnDashboardAlterar = (Button) findViewById(R.id.btnDashboardAlterar);
		btnDashboardListar = (Button) findViewById(R.id.btnDashboardListar);
		btnDashboardRemover = (Button) findViewById(R.id.btnDashboardRemover);
		btnDashboardSair = (Button) findViewById(R.id.btnDashboardSair);
		
		btnDashboardNovoVeiculo.setOnClickListener(this);
		btnDashboardAlterar.setOnClickListener(this);
		btnDashboardListar.setOnClickListener(this);
		btnDashboardRemover.setOnClickListener(this);
		btnDashboardSair.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btnDashboardNovoVeiculo:
			startActivity(new Intent(this, CadastroActivity.class));
			break;
		case R.id.btnDashboardAlterar:
			startActivity(new Intent(this, AlterarVeiculoActivity.class));
			break;
		case R.id.btnDashboardListar:
			startActivity(new Intent(this, ListarVeiculosActivity.class));
			break;
		case R.id.btnDashboardRemover:
			startActivity(new Intent(this, RemoverVeiculoActivity.class));
			break;
		case R.id.btnDashboardSair:
			finish();
			break;
		}
		
	}
	
	
}
