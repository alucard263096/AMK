package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.helpfooter.steve.amklovebaby.CustomControlView.IndexBannerSilderView;
import com.helpfooter.steve.amklovebaby.CustomControlView.MyTextView;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyFragment;
import com.helpfooter.steve.amklovebaby.Loader.BannerLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements IMyFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LinearLayout mDoctor,mQuickAskQuestion,mVideoAsk,mNews1,mNews2,mNews3;

    private IndexBannerSilderView mImageSilder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void initUI(View view) {
        mDoctor=(LinearLayout)view.findViewById(R.id.doctor);
        mDoctor.setOnClickListener((MainActivity)this.getActivity());


        ((LinearLayout)view.findViewById(R.id.lvTaiwanDoctor)).setOnClickListener((MainActivity) this.getActivity());
        ((LinearLayout)view.findViewById(R.id.lvCharDoctor)).setOnClickListener((MainActivity) this.getActivity());
        ((LinearLayout)view.findViewById(R.id.lvVideoDoctor)).setOnClickListener((MainActivity) this.getActivity());

        mNews1=(LinearLayout)view.findViewById(R.id.askForHealth);
        mNews1.setOnClickListener(((MainActivity)this.getActivity()));

        mNews2=(LinearLayout)view.findViewById(R.id.earlierStudy);
        mNews2.setOnClickListener(((MainActivity)this.getActivity()));

        mNews3=(LinearLayout)view.findViewById(R.id.pregnancy);
        mNews3.setOnClickListener(((MainActivity)this.getActivity()));

        MyTextView textView = (MyTextView)view.findViewById(R.id.findDoctor);
        TextPaint tp = textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.findDoctorDesc);
        tp = textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.quickAsk);
        tp = textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.quickAskDesc);
        tp = textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.quickShiping);
        tp=textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.quickTuwen);
        tp=textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.healthAsk);
        tp=textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.newsList1);
        tp=textView.getPaint();
        tp.setFakeBoldText(true);

        textView = (MyTextView)view.findViewById(R.id.newsList2);
        tp=textView.getPaint();
        tp.setFakeBoldText(true);
    }

    public void initBanner(View view)  {

        mImageSilder = (IndexBannerSilderView)view.findViewById(R.id.imageSlider);
        mImageSilder.LoadBanner();
        mImageSilder.StartCircle();
        BannerLoader bannerLoader=new BannerLoader(this.getActivity());
        bannerLoader.setCallBack(mImageSilder);
        bannerLoader.setCallCode(StaticVar.IndexBannerApi);
        bannerLoader.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        initBanner(view);
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

    @Override
    public String getTitle() {
        return "爱我宝贝";
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


}
