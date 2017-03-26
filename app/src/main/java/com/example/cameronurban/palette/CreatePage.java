package com.example.cameronurban.palette;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;
import android.widget.Button;
import android.view.MotionEvent;
import android.widget.TextView;
import android.view.View.OnClickListener;

import android.widget.LinearLayout;
import android.graphics.PorterDuff;
import android.widget.RelativeLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutCompat;

import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;


public class CreatePage extends Activity {
    private static int RESULT_LOAD_IMG = 1;
    private int[] arr = new int[3];
    private static int[] colors=new int[20];
    private static int colorsLngt=0;
    private int xPixel;
    private int yPixel;
    private int yolo;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_page);
        
        ImageButton button = (ImageButton) findViewById(R.id.menuButton2);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CreatePage.this,HomePage.class);
                startActivity(i);
            }
        });
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                BitmapFactory.Options options = new BitmapFactory.Options();
                Uri ourImage = data.getData();
                InputStream is = getContentResolver().openInputStream(selectedImage);
                final Bitmap bm = BitmapFactory.decodeStream(is, null, options);

                final Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, 1080, 800, false);
                arr = averagePixelColor(bm);
                final int bob = Color.rgb(arr[0], arr[1], arr[2]);
                final Button avgColor = (Button) findViewById(R.id.average);
                avgColor.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(CreatePage.this, PaletteGallery.class);
                        intent.putExtra("bob", bob);
                        colors[colorsLngt]=bob;
                        writeToFile(CreatePage.this);
                        startActivity(intent);
                    }
                });
                final Button otherColor = (Button) findViewById(R.id.other);
                otherColor.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        PaletteGallery.count++;
                        Intent intent = new Intent(CreatePage.this, PaletteGallery.class);
                        intent.putExtra("bob", yolo);
                        colors[colorsLngt]=yolo;
                        writeToFile(CreatePage.this);
                        startActivity(intent);
                    }
                });
                final TextView textView = (TextView) findViewById(R.id.textView);
                // this is the view on which you will listen for touch events
                final View touchView = findViewById(R.id.touchView);
                touchView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //System.out.println(event.getX());
                        xPixel = (int) event.getX();
                        yPixel = (int) event.getY();
                        System.out.println(yolo);
                        yolo = resizedBitmap.getPixel(xPixel, yPixel - 200);
                        otherColor.setBackgroundColor(yolo);
                        textView.setText("Touch coordinates : " +
                                String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));


                        return true;
                    }
                });


                //System.out.println(resizedBitmap.getPixel(xPixel, yPixel));


                avgColor.setBackgroundColor(bob);


                //RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
                //r.setBackgroundColor(bob);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};


                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
            } else {
                Toast.makeText(this, "You haven't picked an Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
    private static void writeToFile(Context context) { //adds ONE color to .txt file
        String savedata="";
        for(int a=0;a<colors.length;a++) {
            savedata+=colors[a]+" ";
        }
        try {
            File file = new File(context.getFilesDir(), "colors.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("colors.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(savedata);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            //send notification to user: last color save failed
        }
    }
    public static String readFromFile(Context context) { //reads saved .txt
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput("config.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
                Scanner sc=new Scanner(ret);
                for(int a=0;sc.hasNext();a++) {
                    ret+=sc.hasNext()+" ";
                    colors[a]=Integer.parseInt(sc.next());
                    colorsLngt++;
                }
            }
        } catch (IOException e) {
            //send notification to user: corrupted save file
        }
        return ret;
    }


    public int[] averagePixelColor(Bitmap bitmap){
        long redBucket = 0;
        long greenBucket = 0;
        long blueBucket = 0;
        int pixelCount = 0;

        for (int y = 0; y < bitmap.getHeight(); y++){
            for (int x = 0; x < bitmap.getWidth(); x++){
                int c = bitmap.getPixel(x,y);
                pixelCount++;
                redBucket+= Color.red(c);
                greenBucket += Color.green(c);
                blueBucket += Color.blue(c);
            }
        }

        int avgRed = (int)(redBucket/pixelCount);
        int avgGreen = (int)(greenBucket/pixelCount);
        int avgBlue = (int)(blueBucket/pixelCount);
        int arr[] = {avgRed, avgGreen, avgBlue};
        return arr;

        //myPaint.setColor(Color.rgb(int, int, int));

    }
}