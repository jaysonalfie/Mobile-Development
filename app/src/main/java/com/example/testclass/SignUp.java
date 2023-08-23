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
    TextView textView, enterFirstname,enterLastName;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    ActivitySignUpBinding binding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        button = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);
        editTextEmail = findViewById(R.id.Email);
        editTextPassword = findViewById(R.id.Password);
        enterFirstname = findViewById(R.id.enterFirstName);
        enterLastName = findViewById(R.id.enterLastName);
        textView = findViewById(R.id.loginNow);
        buttonRegister =  findViewById(R.id.registerButton);

        //adding an onClickListener to button
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                addUsers();
//
//
//            }
//
//
//        });






//          binding.registerButton.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View view) {
//                  firstName= binding.enterFirstName.getText().toString();
//                  lastName = binding.enterLastName.getText().toString();
//                  email = binding.Email.getText().toString();
//                  password =binding.Password.getText().toString();
//
//               if(!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()&& !password.isEmpty() ){
//
//
//                   Users users = new Users(firstName,lastName,email,password);
//                   db= FirebaseDatabase.getInstance();
//                   reference = db.getReference("Users");
//                   reference.child(password).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                       @Override
//                       public void onComplete(@NonNull Task<Void> task) {
//
//                           binding.enterFirstName.setText("");
//                           binding.enterLastName.setText("");
//                           binding.Email.setText("");
//                           binding.Password.setText("");
//                           Toast.makeText(SignUp.this, "Successfully Updated",Toast.LENGTH_SHORT ).show();
//
//
//                       }
//                   });
//               }
//
//              }
//          });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

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
                String email, password ,firstName, lastName;
                email = editTextEmail.getText().toString();
                password= editTextPassword.getText().toString();
                firstName = enterFirstname.getText().toString().trim();
                lastName = enterLastName.getText().toString().trim();


                if(TextUtils.isEmpty(email)){


                    Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                    Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    addUsers(firstName,lastName,email,password);

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
//                                    addUsers();

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
            }
        });





    };

    private void addUsers(String firstName, String lastName, String email, String password){



        if(!TextUtils.isEmpty(firstName)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users");
            //String id = databaseUsers.push().getKey();

            Users users = new Users(firstName,lastName,email,password);

            myRef.child(firstName).setValue(users);

            enterFirstname.setText("");

            Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
        }
        else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
