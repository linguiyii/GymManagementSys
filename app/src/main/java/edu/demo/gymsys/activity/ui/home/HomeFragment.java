package edu.demo.gymsys.activity.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.demo.gymsys.adapter.UserAdapter;
import edu.demo.gymsys.dao.UserDao;
import edu.demo.gymsys.databinding.FragmentHomeBinding;
import edu.demo.gymsys.model.User;
import edu.demo.gymsys.service.UserService;
import edu.demo.gymsys.serviceImpl.UserServiceImpl;
import edu.demo.gymsys.view.SlideLayout;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listView;
    private UserAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.homeList;

        HomeViewModel.HomeViewModelFactory factory = new HomeViewModel.HomeViewModelFactory(requireContext());
        HomeViewModel homeViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);

        homeViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                adapter = new UserAdapter(getContext(), users);
                listView.setAdapter(adapter);
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