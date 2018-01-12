package com.teksine.queryapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.model.EndUser;
import com.teksine.queryapplication.other.Notification;
import com.teksine.queryapplication.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String selectedGoogleId;
    String questionId;
    private DatabaseReference mDatabaseReferance = FirebaseDatabase.getInstance().getReference().getRoot();
    DatabaseReference queryRoot;
    private  TextView questionText;
    private TextView answer;
    private Button save;
    private EndUser endUser;
    ProgressDialog progressDialog;
    private final int PROGRESS_TIMEOUT = 3000;


    private OnFragmentInteractionListener mListener;

    public AnswerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerFragment newInstance(String param1, String param2) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedGoogleId=SharedPreferencesManager.getSharedPreferanceManager().getGoogleId(getContext());
        questionId=SharedPreferencesManager.getSharedPreferanceManager().getQueryId(getContext());
        queryRoot = mDatabaseReferance.child("1").child("query").child(selectedGoogleId);
         progressDialog=createProgressDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_answer, container, false);

        questionText= (TextView) rootView.findViewById(R.id.questionText);
        save=(Button) rootView.findViewById(R.id.saveButton);
        answer=(TextView) rootView.findViewById(R.id.answerText);
        queryRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
             updateQuestion(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Log.d("obje",""+endUser);
                endUser.setAnswerStatus((long) 1);
                endUser.setAnswer(answer.getText().toString());
                queryRoot.child(questionId).setValue(endUser);
                progressDialog.dismiss();
                Fragment fragment = new ExpertHomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.setCustomAnimations( R.anim.push_left_in,R.anim.push_left_out);
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
    private ProgressDialog createProgressDialog() {
        final ProgressDialog pd = new ProgressDialog(getContext());

        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set the progress dialog title and message
        pd.setTitle("Updating Answer");
        pd.setMessage("Communicating with server");

        // Set the progress dialog background color
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                pd.dismiss();
            }
        }, PROGRESS_TIMEOUT);

        pd.setIndeterminate(false);


        // Finally, show the progress dialog
        return pd;
    }
    private void updateQuestion(DataSnapshot dataSnapshot) {
//        Toast.makeText(getContext(), "" + "updATE", Toast.LENGTH_SHORT).show();
            Iterator iterator=dataSnapshot.getChildren().iterator();
        if(dataSnapshot.getKey().equals(questionId)) {
            endUser=new EndUser();
//            Toast.makeText(getContext(), "" + ((DataSnapshot) iterator.next()).getValue().toString(), Toast.LENGTH_SHORT).show();
            endUser.setAnswer(((DataSnapshot) iterator.next()).getValue().toString());
            endUser.setAnswerStatus((Long) ((DataSnapshot) iterator.next()).getValue());
            endUser.setPhotoUrl(((DataSnapshot) iterator.next()).getValue().toString());
            endUser.setQuery(((DataSnapshot) iterator.next()).getValue().toString());
            endUser.setTopic(((DataSnapshot) iterator.next()).getValue().toString());
            questionText.setText(endUser.getQuery());
            answer.setText(endUser.getAnswer());

        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
