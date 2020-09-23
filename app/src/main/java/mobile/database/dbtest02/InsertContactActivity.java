package mobile.database.dbtest02;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InsertContactActivity extends AppCompatActivity {

	EditText etName;
	EditText etPhone;
	EditText etCategory;

	ContactDBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_contact);

		etName = findViewById(R.id.editText1);
		etPhone = findViewById(R.id.editText2);
		etCategory = findViewById(R.id.editText3);

		helper = new ContactDBHelper(this);
	}
	
	
	public void onClick(View v) {
		SQLiteDatabase myDB = helper.getWritableDatabase();
		switch(v.getId()) {
			case R.id.btnAddNewContact:
	//			DB 데이터 삽입 작업 수행
				String newName = etName.getText().toString();
				String newPhone = etPhone.getText().toString();
				String newCat = etCategory.getText().toString();

				ContentValues row = new ContentValues();

				row.put(ContactDBHelper.COL_NAME, newName);
				row.put(ContactDBHelper.COL_PHONE, newPhone);
				row.put(ContactDBHelper.COL_CATEGORY, newCat);

				myDB.insert(ContactDBHelper.TABLE_NAME, null, row);
				break;
			case R.id.btnAddNewContactClose:
	//			DB 데이터 삽입 취소 수행
				break;
		}
		helper.close();
		finish();
	}
	
	
	
	
	
}
