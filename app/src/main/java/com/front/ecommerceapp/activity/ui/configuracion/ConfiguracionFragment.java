package com.front.ecommerceapp.activity.ui.configuracion;

import   android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.front.ecommerceapp.R;

public class ConfiguracionFragment extends Fragment {

   // private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_configuracion,container,false);
    }
}