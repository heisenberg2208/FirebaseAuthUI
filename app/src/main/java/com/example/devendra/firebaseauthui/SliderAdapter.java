package com.example.devendra.firebaseauthui;

/**
 * Created by Devendra on 3/21/2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Nikhil on 3/20/2018.
 */
public class SliderAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;
    Button click;
    public static int pos=0;

    public SliderAdapter(Context context)

    {
        this.context = context;
    }

    public String[] btnclass={"InformationActivity.class", "QueryActivity.class","SellActivity.class"};

    public String[] slide_heading=
            {"माहिती", "तज्ञांचा सल्ला", "विक्री करा" };

    public int[] slide_images={
            R.drawable.info,
            R.drawable.query,
            R.drawable.sell};
    public String[] slide_desc=
            {
                    "आपण आपल्यासाठी फायदेशीर असेल अशा सर्व वनस्पतींबद्दल माहिती मिळेल",
                    "आपण आमच्या तज्ज्ञ शास्त्रज्ञांकडे विचारू शकता, जे आपल्याला रोपण केव्हा आणि केव्हा रोपण करावे याबद्दल मार्गदर्शन करतील",
                    "आपण येथे आपली पिके विकू शकता आणि त्यासाठी योग्य मूल्य मिळवू शकता आणि आपल्या खरेदीदारांशी संपर्क साधा"

            };

    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view==(RelativeLayout) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position)
    {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        TextView tvHeading=(TextView)view.findViewById(R.id.tvHeading);
        TextView tvDesc=(TextView)view.findViewById(R.id.tvDesc);

        imageView.setImageResource(slide_images[position]);
        tvHeading.setText(slide_heading[position]);
        tvDesc.setText(slide_desc[position]);
        pos=position;

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((RelativeLayout)object);

    }
}