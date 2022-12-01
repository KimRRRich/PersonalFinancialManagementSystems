package edu.hdu.personalfinancialmanagementsystems.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
    income_textview.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));
    outcome_textview.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fonts_1.ttf"));

    return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}