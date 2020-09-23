package mobile.database.dbtest02;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    String user_id;

    EditText etName;
    EditText etPhone;
    EditText etCategory;

    ContactDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        user_id = getIntent().getStringExtra("_id");
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etCategory = findViewById(R.id.etCategory);

        helper = new ContactDBHelper(this);
    }

    public void onClick(View v) {
        SQLiteDatabase myDB = helper.getWritableDatabase();
        switch(v.getId()) {
            case R.id.btnUpdateContact:
//                DB 데이터 업데이트 작업 수행
//                int up_id = Integer.parseInt(user_id);
                String upName = etName.getText().toString();
                String upPhone = etPhone.getText().toString();
                String upCat = etCategory.getText().toString();

                ContentValues row = new ContentValues();

                row.put(ContactDBHelper.COL_NAME, upName);
                row.put(ContactDBHelper.COL_PHONE, upPhone);
                row.put(ContactDBHelper.COL_CATEGORY, upCat);

                String whereClause = "id=?";
                String[] whereArgs = new String[]{user_id};

                myDB.update(ContactDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                setResult(RESULT_OK);
                break;
            case R.id.btnUpdateContactClose:
//                DB 데이터 업데이트 작업 취소
                setResult(RESULT_CANCELED);
                break;
        }
        helper.close();
        finish();
    }


}
