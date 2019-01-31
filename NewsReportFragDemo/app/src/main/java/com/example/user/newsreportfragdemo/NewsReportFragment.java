package com.example.user.newsreportfragdemo;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsReportFragment extends Fragment {

    String headline , content , author;
    TextView forHeadlines , forContent , forAuthor;
    ImageView forImage;

    public NewsReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headline = getArguments().getString("headline");
        content = getArguments().getString("detail-content");
        author = getArguments().getString("author");

        Log.v("Fragment",author);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_report, container, false);
        forHeadlines = rootView.findViewById(R.id.headlineText);
        forAuthor = rootView.findViewById(R.id.authorText);
        forContent = rootView.findViewById(R.id.contentText);
        forImage = rootView.findViewById(R.id.imageView);

        forImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Your Acttion", Toast.LENGTH_SHORT).show();
            }
        });

        forHeadlines.setText(headline);
        forContent.setText(content);
        forAuthor.setText(author);
        return rootView;
    }

}
