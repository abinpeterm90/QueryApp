package com.teksine.queryapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.adapters.CustomBaseAdapter;
import com.teksine.queryapplication.adapters.QueryListAdapter;
import com.teksine.queryapplication.other.Notification;
import com.teksine.queryapplication.other.RowItem;
import com.teksine.queryapplication.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpertHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpertHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpertHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Notification> rowItems;
    ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabaseReferance = FirebaseDatabase.getInstance().getReference().getRoot();
    DatabaseReference notificationRoot = mDatabaseReferance.child("1").child("notification");

    private OnFragmentInteractionListener mListener;
    QueryListAdapter adapter;

    public ExpertHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpertHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpertHomeFragment newInstance(String param1, String param2) {
        ExpertHomeFragment fragment = new ExpertHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rowItems = new ArrayList<Notification>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_expert_home, container, false);
        adapter = new QueryListAdapter(getContext(), rowItems);
        ListView listView = (ListView)rootView.findViewById(R.id.queryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getContext(),"clicked at "+position,Toast.LENGTH_SHORT).show();
                SharedPreferencesManager.getSharedPreferanceManager().setGoogleId(getContext(),rowItems.get(position).getGoogleID());
                SharedPreferencesManager.getSharedPreferanceManager().setQueryId(getContext(),String.valueOf(position+1));
                Fragment fragment = new AnswerFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });






        progressDialog=createProgressDialog();
        progressDialog.show();
        notificationRoot.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
              updateUI(dataSnapshot);
              adapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getContext(),"child change",Toast.LENGTH_SHORT).show();
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




             return rootView;
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

        // Finally, show the progress dialog
        return pd;
    }

    private void updateUI(DataSnapshot dataSnapshot) {

        Iterator i=dataSnapshot.getChildren().iterator();
        Notification notification=new Notification();
        while(i.hasNext()){
            notification.setAnswerStatus((Long) ((DataSnapshot)i.next()).getValue());
            notification.setEmail(((DataSnapshot)i.next()).getValue().toString());
            notification.setFirstName(((DataSnapshot)i.next()).getValue().toString());
            notification.setGoogleID(((DataSnapshot)i.next()).getValue().toString());
            notification.setLastName(((DataSnapshot)i.next()).getValue().toString());
            notification.setPhotoUrl(((DataSnapshot)i.next()).getValue().toString());
            notification.setTopic((((DataSnapshot)i.next()).getValue().toString()));
            rowItems.add(notification);

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
