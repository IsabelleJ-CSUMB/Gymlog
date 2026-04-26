package com.example.hw04_gymlog_v300;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.hw04_gymlog_v300.database.GymLogRepository;
import com.example.hw04_gymlog_v300.database.entities.User;
import com.example.hw04_gymlog_v300.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private GymLogRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Put in your username silly");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
//                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
//                    sharedPreferencesEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, user.getId());
//                    sharedPreferencesEditor.apply();
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                toastMaker((String.format("No %s found", username)));
                binding.passwordLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}