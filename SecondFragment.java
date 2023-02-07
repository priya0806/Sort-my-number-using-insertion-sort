package com.example.sortmynumber;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sortmynumber.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exitApp(view);

            }

            public void exitApp(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Do you want to exit the app?");
                builder.setPositiveButton("Yes, Exit now!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        getActivity().finish();
                    }

                });
                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearText(view);

            }

            public void clearText(View view) {
                EditText editText = (EditText) getView().findViewById(R.id.inputEditText);
                TextView textView1 = getView().findViewById(R.id.textView1);

                editText.setText("");
                textView1.setText("");

            }

        });


        binding.sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginSorting(view);
            }

            public void beginSorting(View view) {
                EditText editText = (EditText) getView().findViewById(R.id.inputEditText);
                TextView textView1 = getView().findViewById(R.id.textView1);


                try {
                    String myString = editText.getText().toString();
                    Integer[] numberArr = inputValidation(myString);
                    if (numberArr == null || numberArr.length == 0)
                        return;
                    String finalString = numberSorting(numberArr);

                    textView1.setText(finalString);
                    hideKeyboard(view);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            public void hideKeyboard(View view) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            public String numberSorting(Integer[] numberArr) {
                int step = 1;
                String finalString = "Input Array: " +
                        Arrays.toString(numberArr).replaceAll("\\[|\\]|,|\\s", " ") + "\n\n"
                        + "Insertion Sort (Intermediate Steps)"
                        + "\n\t\t\t\t\t\t\t\t\t\tStep" + "\t" + step + " : "
                        + Arrays.toString(numberArr).replaceAll("\\[|\\]|,|\\s", " ");
                int size = numberArr.length;
                for (int i = 1; i < size; i++) {
                    int keyNum = numberArr[i];
                    int j;
                    for (j = i - 1; j >= 0 && numberArr[j] > keyNum; j--) {
                        numberArr[j + 1] = numberArr[j];

                    }
                    numberArr[j + 1] = keyNum;
                    step = i + 1;
                    finalString = finalString + "\n\t\t\t\t\t\t\t\t\t\tStep" + "\t" + step + " : "
                            + Arrays.toString(numberArr).replaceAll("\\[|\\]|,|\\s", " ");
                }
                return finalString;
            }

            public Integer[] inputValidation(String inputString) {
                String[] myStringList = {};
                // Integer[] numberList= new Integer[];
                if (inputString.equals("")) {
                    this.messageBox("Enter Numbers for Sorting");
                    return null;
                } else if (!inputString.matches("[0-9 ]+")) {
                    this.messageBox("Enter space separated numbers only");
                    return null;
                }

                myStringList = inputString.split(" ");

                ArrayList<Integer> numberList = new ArrayList<Integer>();

                for (int i = 0; i < myStringList.length; i++) {
                    if (!myStringList[i].equals("")) {

                        Integer num = Integer.parseInt(myStringList[i]);
                        //check range
                        if (num >= 0 && num <= 9) {
                            numberList.add(num);
                        } else {
                            this.messageBox("Enter space separated numbers between 0 to 9");
                            return null;
                        }
                    }
                }
                if (numberList.size() < 2) {
                    this.messageBox("Enter minimum 2 numbers");
                    return null;
                } else if (numberList.size() > 8) {
                    this.messageBox("Enter upto  8 numbers only ");
                    return null;
                }

                Integer[] numberArr = new Integer[numberList.size()];
                numberList.toArray(numberArr);

                return numberArr;
            }


            public void messageBox(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alert");
                builder.setMessage(message);
                builder.setPositiveButton("OK", null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}