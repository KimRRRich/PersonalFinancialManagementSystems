package edu.hdu.personalfinancialmanagementsystems.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.hdu.personalfinancialmanagementsystems.Login;
import edu.hdu.personalfinancialmanagementsystems.MainPage;
import edu.hdu.personalfinancialmanagementsystems.R;
import edu.hdu.personalfinancialmanagementsystems.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

private FragmentNotificationsBinding binding;
//Button QuitButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);


    binding = FragmentNotificationsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    Button QuitButton=root.findViewById(R.id.QuitButton);
    QuitButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getActivity(), Login.class);
            startActivity(intent);
        }
    });
    //final Button QuitButton=binding.

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}