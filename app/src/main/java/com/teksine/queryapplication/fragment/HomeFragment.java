package com.teksine.queryapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.activity.LoginActivity;
import com.teksine.queryapplication.activity.SplashScreenActivity;
import com.teksine.queryapplication.adapters.QueryHistoryAdapter;
import com.teksine.queryapplication.adapters.QueryListAdapter;
import com.teksine.queryapplication.model.EndUser;
import com.teksine.queryapplication.model.User;
import com.teksine.queryapplication.other.Notification;
import com.teksine.queryapplication.utils.GeneralFunctions;
import com.teksine.queryapplication.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int PROGRESS_TIMEOUT = 3000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  DatabaseReference queryRoot;
    private User user;
    private String googleId;
    private TextView textView;
    QueryHistoryAdapter adapter;
    StringBuilder userName;
    List<EndUser> rowItems;
    Shimmer shimmer;
    private DatabaseReference mDatabaseReferance = FirebaseDatabase.getInstance().getReference().getRoot();
    SharedPreferencesManager msharedManger=SharedPreferencesManager.getSharedPreferanceManager();
    ProgressDialog progressDialog;



    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shimmer = new Shimmer();


        rowItems = new ArrayList<EndUser>();
        user=msharedManger.getUserInformation(getContext(),"user");
        googleId=user.getGoogleId();
        userName=new StringBuilder();
        userName.append("Hi !").append(" ").append(user.getFirstName().substring(0,1).toUpperCase()+ user.getFirstName().substring(1));
        queryRoot = mDatabaseReferance.child("1").child("query").child(googleId);
        progressDialog=createProgressDialog();
        if ( getActivity().getSupportFragmentManager().getFragments() != null &&  getActivity().getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getActivity().getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getActivity().getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rowItems.clear();

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        textView=view.findViewById(R.id.userNameText);
        ShimmerTextView myShimmerTextView=view.findViewById(R.id.expertAdviceText);
        textView.setText(userName);
        ListView listView=view.findViewById(R.id.previousList);
        listView.setEmptyView(view.findViewById(R.id.empty));
        adapter = new QueryHistoryAdapter(getContext(), rowItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Bundle data = new Bundle();
                data.putSerializable("item",rowItems.get(position));
                Fragment fragment = new ViewAnswerFragment();
                fragment.setArguments(data);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.setCustomAnimations( R.anim.push_left_in,R.anim.push_left_out);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        progressDialog.show();
        shimmer.setStartDelay(2000);
        shimmer.start(myShimmerTextView);

        TextView expertAdviceButton= (TextView) view.findViewById(R.id.expertAdviceText);
        expertAdviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new queryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        queryRoot.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                updateUI(dataSnapshot);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(getContext(),"child change",Toast.LENGTH_SHORT).show();
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





        return view;
    }

    private ProgressDialog createProgressDialog() {
        final ProgressDialog pd = new ProgressDialog(getContext());

        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set the progress dialog title and message
        pd.setTitle("Updating Queries");
        pd.setMessage("Loading.........");

        // Set the progress dialog background color
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

        pd.setIndeterminate(false);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               pd.dismiss();
            }
        }, PROGRESS_TIMEOUT);

        // Finally, show the progress dialog
        return pd;
    }

    private void updateUI(DataSnapshot dataSnapshot) {
        EndUser endUser=new EndUser();
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            endUser.setAnswer(((DataSnapshot) i.next()).getValue().toString());
            endUser.setAnswerStatus((Long) ((DataSnapshot) i.next()).getValue());
            endUser.setPhotoUrl(((DataSnapshot) i.next()).getValue().toString());
            endUser.setQuery(((DataSnapshot) i.next()).getValue().toString());
            endUser.setTopic(((DataSnapshot) i.next()).getValue().toString());
            rowItems.add(endUser);
            //Log.d("++++++++++", String.valueOf(temp.add(((DataSnapshot)i.next()).getKey().toString())));
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
        rowItems.clear();
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
