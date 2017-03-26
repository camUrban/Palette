package com.example.cameronurban.palette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import java.util.LinkedList;
import android.widget.GridLayout.LayoutParams;
import android.support.v7.widget.LinearLayoutCompat;


import java.util.Arrays;


public class PaletteGallery extends AppCompatActivity {
    public static int count = 0;
    public static int x = 10;
    //public static int[] xPos = new int[5];
    public static int y = 180;
    private boolean stopper = true;
    protected static int spot = 0;
    //public static Button[] buttonArr = new Button[5];
    public static LinkedList<Button> buttonList = new LinkedList<Button>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palette_gallery);
        int b = getIntent().getIntExtra("bob", 0);
        /**final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_add_btn);
         final Button button = (Button) findViewById(R.id.click);
         button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams(
        LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        Button button1 = new Button(getApplicationContext());
        button1.setLayoutParams(lparams);
        button1.setText("added");
        linearLayout.addView(button1);
        }
        });*/

        System.out.println(b);
        if(b<=0xFFFFFFFF){


            //System.out.println(buttonList.size());
            //for(int i = 0; i<buttonList.size(); i++) {
            //RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main);
            //Button addButton = new Button(this);
            //if(stopper) {
            //buttonList.add(addButton);
            //stopper = false;
            //}
            //System.out.println(buttonList.size());
            RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main);
            //mainLayout.removeView(mainLayout);

            Button addButton = new Button(this);
            addButton.setId(spot+1);
            buttonList.add(addButton);
            System.out.println(buttonList.size());
            buttonList.get(spot).setText("   ");
            buttonList.get(spot).setBackgroundColor(b);
            LinearLayout layout = new LinearLayout(getApplicationContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(100 + x, 100 + y, 100, 100); // left, top, right, bottom
            buttonList.get(spot).setLayoutParams(layoutParams);
            //RelativeLayout layout = (RelativeLayout) findViewById(R.id.main);
            x += 300;

            if(x==910){
                x = 10;
                y += 200;
            }

                for(int i =0 ; i < buttonList.size(); i++) {
                    if(buttonList.get(i).getParent()!=null)
                        ((ViewGroup)buttonList.get(i).getParent()).removeView(buttonList.get(i)); // <- fix
                    mainLayout.addView(buttonList.get(i));
                    //mainLayout.addView(layout);
                }



            spot = spot + 1;
            //}
            //final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_add_btn);
            //LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            //Button button1 = new Button(getApplicationContext());
            //button1.setLayoutParams(lparams);
            //button1.setText("added");
            //linearLayout.addView(button1);

            /**for(int i = 0; i<count; i++) {
             button.callOnClick();
             }*/

           /** ImageButton button10 = (ImageButton) findViewById(R.id.menuButton1);
            button10.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(PaletteGallery.this,HomePage.class);
                    startActivity(i);
                }
            });*/
        }
        ImageButton button = (ImageButton) findViewById(R.id.menuButton1);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(PaletteGallery.this,HomePage.class);
                startActivity(i);
            }
        });
    }
}