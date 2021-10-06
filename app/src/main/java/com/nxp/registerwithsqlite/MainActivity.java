package com.nxp.registerwithsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.nxp.registerwithsqlite.exam.DBHelper;
import com.nxp.registerwithsqlite.exam.ListProduct;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    EditText edName, edQuantity;
    Button btAdd, btView;
    private SimpleCursorAdapter simpleCursorAdapter;
    private ListView lvProducts;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        edName = findViewById(R.id.edName);
        edQuantity = findViewById(R.id.edQuantity);
        btAdd = findViewById(R.id.btAdd);
        btView = findViewById(R.id.btView);
        btAdd.setOnClickListener(this);
        btView.setOnClickListener(this);
        lvProducts = findViewById(R.id.lbProducts);
        cursor = dbHelper.getProduct();
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_product, cursor, new String[]{
                DBHelper.ID, DBHelper.NAME, DBHelper.QUANTITY}, new int[]{R.id.tvId, R.id.tvName, R.id.tvQuantity},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvProducts.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btAdd) {
            onRegister();
        } else if (view.getId() == R.id.btView) {
            onGetAll();
        }
    }

    private void onGetAll() {
        Intent intent = new Intent(this, ListProduct.class);
        startActivity(intent);
    }

    private void onRegister() {
        boolean result = dbHelper.addProduct(edName.getText().toString(), Integer.parseInt(edQuantity.getText().toString()));
        if (result) {
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor c = dbHelper.getProduct();
        simpleCursorAdapter.changeCursor(c);
        simpleCursorAdapter.notifyDataSetChanged();
        dbHelper.close();
    }
}