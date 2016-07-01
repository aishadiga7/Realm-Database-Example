package retrofit.aishwarya.com.realmexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit.aishwarya.com.realmexample.model.MyBook;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public Realm mRealm;
    public Button mAdd;
    public Button mRemove;
    public Button mDisplay;
    public EditText mTitleEntry;
    public TextView mDisplayText;
    public FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        mRealm = Realm.getInstance(realmConfig);
        mAdd = (Button) findViewById(R.id.add);
        mTitleEntry = (EditText) findViewById(R.id.title);
        mRemove = (Button) findViewById(R.id.remove);
        mDisplay = (Button) findViewById(R.id.display);
        mDisplayText = (TextView) findViewById(R.id.content);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        mRemove.setOnClickListener(this);
        mDisplay.setOnClickListener(this);
    }

    private String getTrimmedTitle() {
        return mTitleEntry.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addToDatabase();
                break;
            case R.id.remove:
                removeFromDataBase();
                break;
            case R.id.display:
                display();
                break;
            case R.id.fab:
                toastMessage();
                break;
        }
    }

    private void toastMessage() {
        Toast.makeText(this, "Fab clicked", Toast.LENGTH_LONG).show();
    }

    private void display() {
        RealmResults<MyBook> realmResults = mRealm.where(MyBook.class).findAll();
        StringBuilder titleList = new StringBuilder();
        for (int index = 0; index < realmResults.size(); index++) {
            titleList.append(realmResults.get(index).getTitle());
            titleList.append("\n");
        }
        Log.d(TAG, titleList.toString());
        displayResult(titleList.toString());
    }

    private void displayResult(String title) {
        mDisplayText.setText(title);
    }

    private void removeFromDataBase() {
        mRealm.beginTransaction();
        RealmResults<MyBook> myBooks = mRealm.where(MyBook.class).equalTo("mTitle", getTrimmedTitle()).findAll();
        if (!myBooks.isEmpty()) {
            for (int i = myBooks.size() - 1; i >= 0; i--) {
                myBooks.get(i).deleteFromRealm();
            }
        }
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Deleted from database:");
        mRealm.commitTransaction();
    }

    private void addToDatabase() {
        mRealm.beginTransaction();
        MyBook myBook = mRealm.createObject(MyBook.class);
        myBook.setTitle(getTrimmedTitle());
        mRealm.commitTransaction();
        Toast.makeText(this, "Added", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Added to database:" + getTrimmedTitle());
    }

}
