package com.helpfooter.steve.amkdoctor;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.helpfooter.steve.amkdoctor.CustomControlView.BookListLoadView;
import com.helpfooter.steve.amkdoctor.CustomControlView.MessageListLoadView;
import com.helpfooter.steve.amkdoctor.Interfaces.IMyFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NormalChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NormalChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NormalChatFragment extends Fragment implements IMyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private OnFragmentInteractionListener mListener;
    private Activity mActivity;

    private static Bundle margs;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NormalChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NormalChatFragment newInstance(String param1, String param2) {
        NormalChatFragment fragment = new NormalChatFragment(margs);

        margs.putString(ARG_PARAM1, param1);
        margs.putString(ARG_PARAM2, param2);
        fragment.setArguments(margs);
        return fragment;
    }

    public NormalChatFragment(Bundle args){
        this.margs=args;
    };
    public NormalChatFragment(Activity activ){

        this.mActivity=activ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_normal_chat, container, false);
        final MessageListLoadView lstLoad=new MessageListLoadView(this.mActivity,view.getContext(),(LinearLayout)view.findViewById(R.id.normal_chat_list));
        lstLoad.LoadList();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public String getTitle() {
        return "消息列表";
    }
}
