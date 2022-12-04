package edu.hdu.personalfinancialmanagementsystems.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.hdu.personalfinancialmanagementsystems.IncomeChart;
import edu.hdu.personalfinancialmanagementsystems.IncomeInsert;
import edu.hdu.personalfinancialmanagementsystems.IncomeShow;
import edu.hdu.personalfinancialmanagementsystems.IncomeUpdate;
import edu.hdu.personalfinancialmanagementsystems.OutcomeChart;
import edu.hdu.personalfinancialmanagementsystems.OutcomeInsert;
import edu.hdu.personalfinancialmanagementsystems.OutcomeShow;
import edu.hdu.personalfinancialmanagementsystems.OutcomeUpdate;
import edu.hdu.personalfinancialmanagementsystems.R;
import edu.hdu.personalfinancialmanagementsystems.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    TextView income_textview=root.findViewById(R.id.incom_textview);
    TextView outcome_textview=root.findViewById(R.id.outcome_textview);
    Button income_insert,income_update,income_view,income_chart,outcome_insert,outcome_update,outcome_view,outcome_chart;
    income_insert=root.findViewById(R.id.income_insert);
    income_update=root.findViewById(R.id.income_update);
    income_view=root.findViewById(R.id.income_view);
    income_chart=root.findViewById(R.id.income_chart);
    outcome_insert=root.findViewById(R.id.outcome_insert);
    outcome_update=root.findViewById(R.id.outcome_update);
    outcome_view=root.findViewById(R.id.outcome_view);
    outcome_chart=root.findViewById(R.id.outcome_chart);
    income_textview.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    outcome_textview.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    income_insert.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    income_update.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    income_view.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    income_chart.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    outcome_insert.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    outcome_update.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    outcome_view.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    outcome_chart.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    ImageView income_insertimg=root.findViewById(R.id.income_insertimg);


    income_insert.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), IncomeInsert.class);
            startActivity(intent);
        }
    });


    income_insertimg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), IncomeInsert.class);
            startActivity(intent);
        }
    });


    income_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), IncomeShow.class);
            startActivity(intent);
        }
    });

    income_update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), IncomeUpdate.class);
            startActivity(intent);
        }
    });

    income_chart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), IncomeChart.class);
            startActivity(intent);
        }
    });

    outcome_insert.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), OutcomeInsert.class);
            startActivity(intent);
        }
    });
    outcome_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), OutcomeShow.class);
                startActivity(intent);
            }
    });
    outcome_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), OutcomeUpdate.class);
                startActivity(intent);
            }
    });
    outcome_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), OutcomeChart.class);
                startActivity(intent);
            }
    });




    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}