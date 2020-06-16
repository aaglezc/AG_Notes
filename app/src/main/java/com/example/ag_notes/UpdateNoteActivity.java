package com.example.ag_notes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ag_notes.Model.Nota;
import com.example.ag_notes.Model.SQLiteHelper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {


    EditText edtTitle;
    EditText  edtDesc;
    ImageView ivPhoto;

    ImageView ivRecord;
    ImageView ivStop;
    ImageView ivPlay;


    private String catName = "Default";
    private String date;

    private MediaRecorder miGrabacion;
    //   private MediaPlayer   m;
    private String outputFile = null;
    private String fileName;

    String recId;

    final int REQUEST_CODE_RECORD = 1000;

    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper mySqliteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        mySqliteHelper = new  SQLiteHelper(this,"RECORDDB.sqlite",null,1);

        edtTitle = (EditText) findViewById(R.id.edtTitle2);
        edtDesc  = (EditText) findViewById(R.id.edtDesc2);
        ivPhoto  = (ImageView)findViewById(R.id.imageView2);


        ivRecord  = (ImageView)findViewById(R.id.imageViewRecord2);
        ivStop = (ImageView)findViewById(R.id.imageViewStop2);
        ivPlay  = (ImageView)findViewById(R.id.imageViewPlay2);


        //edtTitle.setText(value);

        Bundle b = getIntent().getExtras();

        //value = b.getString("idRec");
        if( b!=null) {
            String title = "NO TITLE";
            if(b.containsKey("idRec")) {
                recId= b.getString("idRec");
                edtTitle.setText(recId);
            }

            if(b.containsKey("catName")) {
                catName= b.getString("catName");
                //edtTitle.setText(recId);

            }
        }
        else
        {
            edtTitle.setText("No DATA");
        }


        /////// get data

        Cursor cursor = mySqliteHelper.getData("SELECT * FROM Notes WHERE id = "+recId);

        while(cursor.moveToNext())
        {
            int id       = cursor.getInt(0);
            String title = cursor.getString(1);
            edtTitle.setText(title);
            String desc  = cursor.getString(2);
            edtDesc.setText(desc);
            String date  = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            ivPhoto.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));

            String category = cursor.getString(5);
            catName = category;

            String audio   = cursor.getString(6);
            outputFile = audio;

        }



        //fileName = "rec1.3gp";
        //cat = "Default";
        //date = new Date();
        //SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        //final String dateF=df.format(date);

        //Select image
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Permission and runtime permisssion devices 6.0 and above
                ActivityCompat.requestPermissions(
                        UpdateNoteActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );


            }
        });

        ivRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat .requestPermissions(UpdateNoteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
                }

                outputFile = outputFile = getFilesDir().getAbsolutePath()+"/miaudio.3gp";


                if (miGrabacion == null)
                {
                    miGrabacion = new MediaRecorder();
                }

                miGrabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                miGrabacion.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                miGrabacion.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                miGrabacion.setOutputFile(outputFile);

                try {

                    miGrabacion.prepare();
                    miGrabacion.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Recording in progres..", Toast.LENGTH_LONG).show();
                ivRecord.setEnabled(false);
                ivRecord.setImageResource(R.drawable.ic_action_record);

                ivPlay.setEnabled(false);
                ivPlay.setImageResource(R.drawable.ic_action_done);


            }
        });

        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (miGrabacion != null)
                {
                    miGrabacion.stop();
                    miGrabacion.release();
                    miGrabacion = null;
                }
                ivRecord.setEnabled(true);
                ivRecord.setImageResource(R.drawable.ic_action_mic_off);

                ivPlay.setEnabled(true);
                ivPlay.setImageResource(R.drawable.ic_action_play);


            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaPlayer m = new MediaPlayer();
                try {
                    m.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                m.start();
                Toast.makeText(getApplicationContext(), "reproducción de audio", Toast.LENGTH_LONG).show();
            }
        });


    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY)
        {
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(this,"Don't have permission to access.", Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();
                //set image chossen
                ivPhoto.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error  = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_note:

                try {
                    mySqliteHelper.updateData(
                            edtTitle.getText().toString().trim(),
                            edtDesc.getText().toString().trim(),
                            date,
                            imageViewToByte(ivPhoto),
                            catName,
                            outputFile,Integer.valueOf(recId)
                    );
                    Toast.makeText(UpdateNoteActivity.this,"Note Updated Successfully",Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent(UpdateNoteActivity.this,ListNotesActivity.class);
                intent.putExtra("catName",catName);
                startActivity(intent);
                break;


        }
        return true;
    }

}
