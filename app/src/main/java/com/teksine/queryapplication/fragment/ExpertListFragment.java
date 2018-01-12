package com.teksine.queryapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
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
import com.google.firebase.database.ValueEventListener;
import com.teksine.queryapplication.R;
import com.teksine.queryapplication.adapters.CustomBaseAdapter;
import com.teksine.queryapplication.model.EndUser;
import com.teksine.queryapplication.model.User;
import com.teksine.queryapplication.other.RowItem;
import com.teksine.queryapplication.utils.GeneralFunctions;
import com.teksine.queryapplication.utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpertListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpertListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpertListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    List<RowItem> rowItems;
    DatabaseReference mDatabase;
    private EndUser endUser;
    private User user;
    long notificationCount;
    String expertId;
    SharedPreferencesManager msharedManger=SharedPreferencesManager.getSharedPreferanceManager();

    public static final int[] ids =  {1,2,3,4};
    public static final String[] titles = new String[] { "Sebastian P J",
            "Justin John", "Krishna Kumar", "Anoop Kumar" };

    public static final String[] descriptions = new String[] {
            "",
            "", "",
            "" };

    public static final String[] topic = new String[] {
            "DEPUTY COLLECTOR",
            "TAHASILDAR", "VILLAGE OFFICER",
            "FINANCE OFFICER" };

    public static final Integer[] images = { R.drawable.user,
            R.drawable.user, R.drawable.user, R.drawable.user };

    ProgressDialog nDialog;




    private OnFragmentInteractionListener mListener;

    public ExpertListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpertListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpertListFragment newInstance(String param1, String param2) {
        ExpertListFragment fragment = new ExpertListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(ids[i],images[i], titles[i], descriptions[i],topic[i]);
            rowItems.add(item);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        endUser=msharedManger.getEndUserInformation(getContext(),"endUser");
        user=msharedManger.getUserInformation(getContext(),"user");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_expert_list, container, false);
        listView = (ListView)rootView.findViewById(R.id.list);
        CustomBaseAdapter adapter = new CustomBaseAdapter(getContext(), rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 //expertId= String.valueOf(rowItems.get(position).getId());
                expertId="1";
                user.setAnswerStatus(0);
                mDatabase.child(expertId).child("query").child(user.getGoogleId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            // run some code
                            notificationCount= snapshot.getChildrenCount();
                            notificationCount++;
                            //Toast.makeText(getContext(),"count "+notificationCount,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            notificationCount=1;
                            //Toast.makeText(getContext(),"no count "+notificationCount,Toast.LENGTH_SHORT).show();
                        }
                        mDatabase.child(expertId).child("notification").child(String.valueOf(notificationCount)).setValue(user);
                        mDatabase.child(expertId).child("query").child(user.getGoogleId()).child(String.valueOf(notificationCount)).setValue(endUser);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new successFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return rootView;
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
