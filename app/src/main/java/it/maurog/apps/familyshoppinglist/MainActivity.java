package it.maurog.apps.familyshoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {
    public final static String KEY_EXTRA_PRODUCT_ID = "KEY_EXTRA_PRODUCT_ID";

    private ListView listView;
    ProductDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.addNew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditProduct.class);
                intent.putExtra(KEY_EXTRA_PRODUCT_ID, 0);
                startActivity(intent);
            }
        });

        dbHelper = new ProductDbHelper(this);

        final Cursor cursor = dbHelper.getAllPersons();
        String [] columns = new String[] {
                ProductDbHelper.PERSON_COLUMN_ID,
                ProductDbHelper.PERSON_COLUMN_NAME
        };
        int [] widgets = new int[] {
                R.id.productID,
                R.id.productName
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.product_info,
                cursor, columns, widgets, 0);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) MainActivity.this.listView.getItemAtPosition(position);
                int productID = itemCursor.getInt(itemCursor.getColumnIndex(ProductDbHelper.PERSON_COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), CreateOrEditProduct.class);
                intent.putExtra(KEY_EXTRA_PRODUCT_ID, productID);
                startActivity(intent);
            }
        });

    }

}
