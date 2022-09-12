package com.example.momento_moviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText jetcodigo, jetciudad, jetcantidad, jetvalor;
    CheckBox jcbactivo;
    OpenHelper admin = new OpenHelper(this, "viaje.db", null, 1);
    String codigo_Vieje, ciudad_destino, cantidad_personas, valor;
    long resp;
    int sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jetcodigo = findViewById(R.id.etcodigo);
        jetciudad = findViewById(R.id.etciudad);
        jetcantidad = findViewById(R.id.etcantidad);
        jetvalor = findViewById(R.id.etvalor);
        jcbactivo = findViewById(R.id.activo);
        sw = 0;
    }
    public void Guardar(View view){
        codigo_Vieje=jetcodigo.getText().toString();
        ciudad_destino=jetciudad.getText().toString();
        cantidad_personas=jetcantidad.getText().toString();
        valor=jetvalor.getText().toString();
        if (codigo_Vieje.isEmpty()||ciudad_destino.isEmpty()||cantidad_personas.isEmpty()||valor.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else {
            SQLiteDatabase sd=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("codigo_Vieje",codigo_Vieje);
            registro.put("ciudad_destino",ciudad_destino);
            registro.put("cantidad_personas",cantidad_personas);
            registro.put("valor", Integer.parseInt(valor));
            if(sw==0) {
                resp = sd.insert("TblViaje", null, registro);
            }
            else {
                resp = sd.update("TblViaje", registro, "codigo='" + codigo_Vieje + "'", null);
            }
            if (resp>0){
                Toast.makeText(this,"Registro guardado",Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else{ Toast.makeText(this,"Error en registro",Toast.LENGTH_SHORT).show();
            sd.close();
            }
        }
    }
    public void Consultar(View view){
        codigo_Vieje=jetcodigo.getText().toString();
        if (codigo_Vieje.isEmpty()){
            Toast.makeText(this,"El codigo no existe",Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
            SQLiteDatabase sd=admin.getReadableDatabase();
            Cursor fila= sd.rawQuery("select * from TblViaje where codigo_Vieje='"+codigo_Vieje+"'",null);
            if(fila.moveToNext()) {
                sw = 1;
                jetciudad.setText(fila.getString(1));
                jetcantidad.setText(fila.getString(2));
                jetvalor.setText(fila.getString(3));
                if (fila.getString(4).equals("si")) {
                    jcbactivo.setChecked(true);
                } else {
                    jcbactivo.setChecked(false);
                }
            }
            else {
                Toast.makeText(this, "Viaje no registrado", Toast.LENGTH_SHORT).show();
                sd.close();
            }
        }
    }
    public void Anular(View view){
        if(sw==0){
            Toast.makeText(this, "Primero debe consultar", Toast.LENGTH_SHORT).show();
            jetcodigo.requestFocus();
        }
        else{
            SQLiteDatabase sd=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("activo","no");
            resp=sd.update("TblViaje",registro,"codigo_Vieje='"+ codigo_Vieje + "'",null);
            if (resp>0){
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else {
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
        }

    }
    public void Cancelar(View view){
        Limpiar_campos();
    }

    public void Regresar(View view){
        Intent intmain= new Intent(this,MainActivity.class);
        startActivity(intmain);
    }
    private void Limpiar_campos(){
        jetcodigo.setText("");
        jetvalor.setText("");
        jetciudad.setText("");
        jetcantidad.setText("");
        jcbactivo.setChecked(false);
        jetcodigo.requestFocus();
        sw=0;
    }
}
