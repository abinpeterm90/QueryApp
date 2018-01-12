package com.teksine.queryapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teksine.queryapplication.R;
import com.teksine.queryapplication.model.EndUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewAnswerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewAnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAnswerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EndUser endUser;

    private OnFragmentInteractionListener mListener;

    public ViewAnswerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAnswerFragment newInstance(String param1, String param2) {
        ViewAnswerFragment fragment = new ViewAnswerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
      endUser = (EndUser) extras.getSerializable("item");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_view_answer, container, false);
        TextView topicText=(TextView)rootView.findViewById(R.id.topicTextViewAnswer);
        TextView answerText=(TextView)rootView.findViewById(R.id.query_text_view_answer);
        if(endUser.getTopic()!=null){
            topicText.setText(endUser.getTopic());
        }
        else{
            topicText.setText("unable to find the topic! try again ");
        }
        if(endUser.getAnswer()!=null){
            answerText.setText(endUser.getAnswer());
        }
        else{
            answerText.setText("Not Answered yet !");
        }


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
