package mobile.database.dbtest02;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AllContactsActivity extends AppCompatActivity {

	final int UPDATE_CODE = 200;

	ListView lvContacts = null;
	ContactDBHelper helper;
	Cursor cursor;
	MyCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_contacts);
		lvContacts = (ListView)findViewById(R.id.lvContacts);

		helper = new ContactDBHelper(this);

//		  SimpleCursorAdapter 객체 생성
		/* 매개변수 설정*/
		adapter = new MyCursorAdapter(this, R.layout.listview_layout, null);

		lvContacts.setAdapter(adapter);

//		리스트 뷰 클릭 처리
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			//delete from databse
				SQLiteDatabase myDB = helper.getWritableDatabase();

				long user_id = id;

				myDB.execSQL("DELETE FROM " + ContactDBHelper.TABLE_NAME + " WHERE id=" + user_id
				+ ");");
//				cursor
            }
        });

//		리스트 뷰 롱클릭 처리
		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				final int db_id;
				Intent intent = new Intent(AllContactsActivity.this, UpdateActivity.class);
				intent.putExtra("_id", id);
				startActivityForResult(intent, UPDATE_CODE);
				return true;
			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == UPDATE_CODE) {
			switch (resultCode) {
				case RESULT_OK:
					String update;
					Toast.makeText(AllContactsActivity.this, "데이터 추가 성공", Toast.LENGTH_SHORT).show();
					break;
				case RESULT_CANCELED:
					Toast.makeText(AllContactsActivity.this, "데이터 추가 취소", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
        helper.close();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//        cursor 사용 종료
		if (cursor != null) cursor.close();
	}

}




