package edu.hdu.personalfinancialmanagementsystems.ui.dashboard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.hdu.personalfinancialmanagementsystems.NoteAdd;
import edu.hdu.personalfinancialmanagementsystems.NoteShow;
import edu.hdu.personalfinancialmanagementsystems.NoteUpdate;
import edu.hdu.personalfinancialmanagementsystems.R;
import edu.hdu.personalfinancialmanagementsystems.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    Button note_insert,note_update,note_view;
    note_insert=root.findViewById(R.id.note_insert);
    note_update=root.findViewById(R.id.note_update);
    note_view=root.findViewById(R.id.note_view);
    note_insert.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/fonts_1.ttf"));
    note_update.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/fonts_1.ttf"));
    note_view.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/fonts_1.ttf"));

    note_insert.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), NoteAdd.class);
            startActivity(intent);
        }
    });
    note_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), NoteUpdate.class);
                startActivity(intent);
            }
    });
    note_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), NoteShow.class);
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