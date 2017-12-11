package com.teksine.queryapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.model.EndUser;
import com.teksine.queryapplication.model.User;
import com.teksine.queryapplication.utils.GeneralFunctions;
import com.teksine.queryapplication.utils.SharedPreferencesManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link queryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link queryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class queryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference mDatabase;
    //private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
    SharedPreferencesManager msharedManger=SharedPreferencesManager.getSharedPreferanceManager();

    private OnFragmentInteractionListener mListener;

    public queryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment queryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static queryFragment newInstance(String param1, String param2) {
        queryFragment fragment = new queryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_query, container, false);
        final EditText queryText= (EditText) view.findViewById(R.id.query_text);
        Button submitButton=(Button)view.findViewById(R.id.submit_query);
        submitButton.setOnClickListener(new View.OnClickListener() {
            User user = msharedManger.getUserInformation(getContext(),"user");


            @Override
            public void onClick(View view) {
                EndUser endUser=new EndUser();
                endUser.setQuery(queryText.getText().toString());
                endUser.setEmail(user.getEmail());
                endUser.setGoogleId(user.getGoogleId());
                endUser.setAnswerStatus(0);
                endUser.setFirstName(user.getFirstName());
                msharedManger.setEndUserInformation(getContext(),endUser);
                Fragment fragment = new ExpertListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        return view;
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
