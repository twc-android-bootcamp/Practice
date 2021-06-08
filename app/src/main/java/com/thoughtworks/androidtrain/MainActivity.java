package com.thoughtworks.androidtrain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.androidtrain.uiflow.ConstraintActivity;
import com.thoughtworks.androidtrain.uiflow.LoginActivity;
import com.thoughtworks.androidtrain.uiflow.recyclerview.RecyclerViewActivity;
import com.thoughtworks.androidtrain.uiflow.fragment.MyFragmentActivity;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void initUI() {
        Button btn1 = findViewById(R.id.constraint_layout);
        btn1.setOnClickListener(v -> startActivity(new Intent(this, ConstraintActivity.class)));

        Button btn2 = findViewById(R.id.login);
        btn2.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

        Button pickupContact = findViewById(R.id.pick_contact);
        pickupContact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
            }
        });

        Button btnFragment = findViewById(R.id.fragment);
        btnFragment.setOnClickListener(v -> startActivity(new Intent(this, MyFragmentActivity.class)));

        Button btnRecyclerView = findViewById(R.id.recycler_view);
        btnRecyclerView.setOnClickListener(v -> startActivity(new Intent(this, RecyclerViewActivity.class)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            assert data != null;
            Uri contactUri = data.getData();
            String[] projection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                String number = cursor.getString(numberIndex);
                String name = cursor.getString(nameIndex);

                Toast.makeText(this, name + " " + number, Toast.LENGTH_SHORT).show();
            }
        }
    }
}