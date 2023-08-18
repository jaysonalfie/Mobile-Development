package com.example.testclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testclass.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


     Button button;
     Button buttonRegister;

     EditText editTextEmail, editTextPassword;

      TextView textView;

      ProgressBar progressBar;

      FirebaseAuth mAuth;


      ActivitySignUpBinding binding;
      String firstName, lastName,email,password;
      FirebaseDatabase db;
      DatabaseReference reference;





    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        textView = findViewById(R.id.loginNow);
        buttonRegister =  findViewById(R.id.registerButton);

          binding.registerButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  firstName= binding.enterFirstName.getText().toString();
                  lastName = binding.enterLastName.getText().toString();
                  email = binding.Email.getText().toString();
                  password =binding.Password.getText().toString();

               if(!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()&& !password.isEmpty() ){


                   Users users = new Users(firstName,lastName,email,password);
                   db= FirebaseDatabase.getInstance();
                   reference = db.getReference("Users");
                   reference.child(password).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {

                           binding.enterFirstName.setText("");
                           binding.enterLastName.setText("");
                           binding.Email.setText("");
                           binding.Password.setText("");
                           Toast.makeText(SignUp.this, "Successfully Updated",Toast.LENGTH_SHORT ).show();


                       }
                   });
               }

              }
          });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

       // button.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
         //       startActivity(intent);
        //        finish();
        //    }
      //  });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText().toString());
                password= String.valueOf(editTextPassword.getText().toString());

                if(TextUtils.isEmpty(email)){


                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(SignUp.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });





        };
    }
