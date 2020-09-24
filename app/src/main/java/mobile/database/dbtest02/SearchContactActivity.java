package mobile.database.dbtest02;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchContactActivity extends AppCompatActivity {

	EditText etSearchName;
	TextView resultView;

	ContactDBHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);

		etSearchName = findViewById(R.id.etSearchName);
		resultView = findViewById(R.id.tvSearchResult);
		helper = new ContactDBHelper(this);
	}
	
	
	public void onClick(View v) {
		SQLiteDatabase myDB = helper.getReadableDatabase();
		switch(v.getId()) {
			case R.id.btnSearchContactSave:
				//			DB 검색 작업 수행
				String result = "";
				String searchName = etSearchName.getText().toString();
				String selection = "name=?";
				String[] selectArgs = new String[]{searchName};

				Cursor cursor =
						myDB.query(ContactDBHelper.TABLE_NAME, null, selection, selectArgs
						,null, null, null, null);

				while(cursor.moveToNext()){
					int id = cursor.getInt(0);
					String phone = cursor.getString(2);
					result += "전화번호 " + (id+1) +" : " + phone + "\n";
				}
				resultView.setText(result);
				break;
			case R.id.btnClose :
				finish();
				break;
		}
	}
	
	
	
}
