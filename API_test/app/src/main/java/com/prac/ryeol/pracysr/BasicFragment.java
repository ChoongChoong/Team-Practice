package com.prac.ryeol.pracysr;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BasicFragment extends Fragment {
    private final static String PARAMS_POSTER   = "params_poster",
            PARAMS_TITLE    = "params_title",
            PARAMS_AGE      = "params_age",
            PARAMS_RATE     = "params_rate",
            PARAMS_GRADE    = "params_grade";
    private String  poster  = "",
            title   = "",
            age     = "",
            rate    = "",
            grade   = "";

    private int currentPage = 0;

    public static Fragment newInstance(String poster, String title, String age, String rate, String grade) {
        Bundle args = new Bundle();
        args.putString(PARAMS_POSTER, poster);
        args.putString(PARAMS_TITLE, title);
        args.putString(PARAMS_AGE, age);
        args.putString(PARAMS_RATE, rate);
        args.putString(PARAMS_GRADE, grade);

        Fragment fragment = new BasicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        poster  = bundle.getString(PARAMS_POSTER, "");
        title   = bundle.getString(PARAMS_TITLE, "");
        age     = bundle.getString(PARAMS_AGE, "");
        rate    = bundle.getString(PARAMS_RATE, "");
        grade   = bundle.getString(PARAMS_GRADE, "");

        Log.i(title, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(title, "onCreateView()");
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(title, "onViewCreate()");
        int posterResId = getResources().getIdentifier(poster, "drawable", "com.prac.ryeol.pracysr");
        int ageResId = getResources().getIdentifier(age, "drawable", "com.prac.ryeol.pracysr");

        ImageView iv_poster = (ImageView) view.findViewById(R.id.poster_view);
        ImageView iv_age = (ImageView) view.findViewById(R.id.age_view);
        TextView tv_title = (TextView) view.findViewById(R.id.mv_title);
        TextView tv_rate = (TextView) view.findViewById(R.id.mv_rate);
        TextView tv_grade = (TextView) view.findViewById(R.id.mv_grade);


        iv_poster.setImageResource(posterResId);
        iv_age.setImageResource(ageResId);
        tv_title.setText(title);
        tv_rate.setText("예매율: " + rate);
        tv_grade.setText("평　점: " + grade);

        /** Click Event **/
        iv_poster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPage = ((MainActivity)getActivity()).getCurrentPosition();

                Log.i(title, "Current Item: " + currentPage);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(title, "setUserVisibleHint(" + (isVisibleToUser ? "true" : "false") + ")");
        super.setUserVisibleHint(isVisibleToUser);
    }
}