package mobile.database.dbtest02;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AllContactsActivity extends AppCompatActivity {

	final int UPDATE_CODE = 200;
	final String TAG ="rgrg";

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

		/* 매개변수 설정*/
		adapter = new MyCursorAdapter(this, R.layout.listview_layout, null);

		lvContacts.setAdapter(adapter);

//        SQLiteDatabase myDB = helper.getReadableDatabase();
//
//		cursor = myDB.rawQuery("select * from " + ContactDBHelper.TABLE_NAME,null);

//		리스트 뷰 클릭 처리
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            	final long user_id = id;
			//delete from databse
				AlertDialog.Builder builder = new AlertDialog.Builder(AllContactsActivity.this);
					builder.setTitle("삭제하시겠습니까?")
							.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									SQLiteDatabase myDB = helper.getWritableDatabase();
									myDB.execSQL("DELETE FROM " + ContactDBHelper.TABLE_NAME + " WHERE _id=" + user_id + ";");
									Toast.makeText(AllContactsActivity.this, "삭제완료", Toast.LENGTH_SHORT).show();
								}
							})
							.setNegativeButton("취소", null)
							.show();
//				SQLiteDatabase db = helper.getReadableDatabase();
//				cursor = db.rawQuery("select * from " + ContactDBHelper.TABLE_NAME, null);
//				Log.d(TAG, "cursor query");
//				adapter.swapCursor(cursor);
//				Log.d(TAG, "cursor query2");
            }
        });

//		리스트 뷰 롱클릭 처리
		lvContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AllContactsActivity.this, UpdateActivity.class);
				intent.putExtra("_id", String.valueOf(id));
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
					Toast.makeText(AllContactsActivity.this, "데이터 수정 성공", Toast.LENGTH_SHORT).show();
					break;
				case RESULT_CANCELED:
					Toast.makeText(AllContactsActivity.this, "데이터 수정 취소", Toast.LENGTH_SHORT).show();
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




