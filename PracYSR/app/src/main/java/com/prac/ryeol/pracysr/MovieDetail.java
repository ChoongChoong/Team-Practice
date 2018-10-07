package com.prac.ryeol.pracysr;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class MovieDetail extends DialogFragment {
    private static View view;

    private final static String PARAM_POSITION = "";

    private int width = 0,
                height = 0,
                position = 0;

    private TextView tvTitle;
    private ImageView ivClose;
    private Button btnTicketing;

    private String[] arrTitle;
    private String[] arrTrailer;

    private String API_KEY = "AIzaSyA_uHWSIxWhc0e5tNI3ukti49L1uWm0RNM";
    private String urlTrailer = "";

    private YouTubePlayerSupportFragment youTubePlayerFragment;

    public static MovieDetail newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(PARAM_POSITION, position);

        MovieDetail fragment = new MovieDetail();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        position = bundle.getInt(PARAM_POSITION);

        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null)
                parent.removeView(view);
        }

        return inflater.inflate(R.layout.movie_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        arrTitle = getResources().getStringArray(R.array.movie_title_arr);
        arrTrailer = getResources().getStringArray(R.array.movie_trailer_arr);

        tvTitle = (TextView) view.findViewById(R.id.mv_title);
        ivClose = (ImageView) view.findViewById(R.id.iv_close);
        btnTicketing = (Button) view.findViewById(R.id.btn_ticketing);

        tvTitle.setText(arrTitle[position]);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        /* YouTubePlayer */
        urlTrailer = arrTrailer[position];

        youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.movieTrailer);

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(urlTrailer);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        btnTicketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        DisplayMetrics dm = getActivity().getApplicationContext().getResources().getDisplayMetrics();
        int deviceW = dm.widthPixels;
        int deviceH = dm.heightPixels;

        width = deviceW / 5 * 4;    // 디바이스 화면 너비
        height = deviceH / 5 * 4;   // 디바이스 화면 높이

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wm = window.getAttributes();

        wm.dimAmount = 0.6f;
        wm.width = width;
        wm.height = height;

        window.setAttributes(wm);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // DialogFragment 외부 터치시 종료 방지
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(youTubePlayerFragment).commit();
    }
}