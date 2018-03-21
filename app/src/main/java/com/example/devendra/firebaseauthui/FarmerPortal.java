package com.example.devendra.firebaseauthui;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FarmerPortal extends AppCompatActivity
{
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] mDots;
    private Button btnClick;
    private SliderAdapter sliderAdapter;
    private static int i;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_portal);
        viewPager=(ViewPager)findViewById(R.id.slideViewPager);
        linearLayout=(LinearLayout)findViewById(R.id.dots);
        btnClick=(Button)findViewById(R.id.click);

        sliderAdapter= new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

      /*   i = SliderApadter.pos;*/
        Log.i("Position", String.valueOf(i));






        addDotsIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator(final int position)
    {
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(position==0)
                {
//                    Intent intent=new Intent(FarmerPortal.this,InformationActivity.class);
  //                  startActivity(intent);
                }
                if(position==1)
                {
    //                Intent intent=new Intent(FarmerPortal.this,QueryActivity.class);
      //              startActivity(intent);
                }
                if(position==2)
                {
                    Intent intent=new Intent(FarmerPortal.this,SellCrop.class);
                    startActivity(intent);
                }

            }
        });



        mDots=new TextView[3];
        linearLayout.removeAllViews();

        for(int i=0;i<mDots.length;i++)
        {
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(45);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));
            linearLayout.addView(mDots[i]);

        }
        if(mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.greylight));
        }
    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    };
}