package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.CustomControlView.DoctorListLoadView;
import com.helpfooter.steve.amklovebaby.CustomControlView.NewsListLoadView;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends Fragment implements IMyFragment,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView btnCategoryH,btnCategoryP,btnCategoryF,btnCategoryE,btnCategoryV;
    private LinearLayout udCategoryH,udCategoryP,udCategoryF,udCategoryE,udCategoryV;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mCategory;
    NewsListLoadView lstLoad;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsListFragment newInstance(String param1, String param2) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsListFragment() {
        // Required empty public constructor
    }

    public void SetCategory(String Category)
    {
        this.mCategory = Category;
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
        View view= inflater.inflate(R.layout.fragment_news_list, container, false);
         lstLoad=new NewsListLoadView(view.getContext(),(LinearLayout)view.findViewById(R.id.news_list));
        lstLoad.LoadNewsListData();
        InitUI(view);
        //DoctorLoader loader=new DoctorLoader(this.getActivity());
        //loader.start();

        return view;
    }

    private void InitUI(View view) {
        btnCategoryH =(TextView)view.findViewById(R.id.btnCategoryH);
        btnCategoryH.setOnClickListener(this);
        btnCategoryP =(TextView)view.findViewById(R.id.btnCategoryP);
        btnCategoryP.setOnClickListener(this);
        btnCategoryE =(TextView)view.findViewById(R.id.btnCategoryE);
        btnCategoryE.setOnClickListener(this);
        btnCategoryV =(TextView)view.findViewById(R.id.btnCategoryV);
        btnCategoryV.setOnClickListener(this);
        btnCategoryF =(TextView)view.findViewById(R.id.btnCategoryF);
        btnCategoryF.setOnClickListener(this);


        udCategoryH =(LinearLayout)view.findViewById(R.id.udCategoryH);
        udCategoryP =(LinearLayout)view.findViewById(R.id.udCategoryP);
        udCategoryV =(LinearLayout)view.findViewById(R.id.udCategoryV);
        udCategoryE =(LinearLayout)view.findViewById(R.id.udCategoryE);
        udCategoryF =(LinearLayout)view.findViewById(R.id.udCategoryF);

        //switch (this.mCategory)
        //{
        //    case "0":
        //        onClick(btnCategoryH);
        //        break;
        //   case "1":
        //       onClick(btnCategoryE);
        //       break;
        //   case "2":
        //       onClick(btnCategoryP);
        //       break;
        //   default:
        //       onClick(btnCategoryH);
        //       break;
        //}
        onClick(btnCategoryH);

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
        return "新闻资讯";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCategoryE:
            case R.id.btnCategoryF:
            case R.id.btnCategoryV:
            case R.id.btnCategoryH:
            case R.id.btnCategoryP:
                clickNewsFiler(v);
        }
    }

    private void clickNewsFiler(View v) {
        String category=(String)v.getTag();
        lstLoad.FilterByCategory(category);
        setFilterButtonWhite(v);
    }

    private void setFilterButtonWhite(View btnCategory) {


        udCategoryH.setVisibility(View.INVISIBLE);
        udCategoryF.setVisibility(View.INVISIBLE);
        udCategoryV.setVisibility(View.INVISIBLE);
        udCategoryE.setVisibility(View.INVISIBLE);
        udCategoryP.setVisibility(View.INVISIBLE);


        switch (btnCategory.getId()){
            case R.id.btnCategoryE:
                udCategoryE.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCategoryF:
                udCategoryF.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCategoryV:
                udCategoryV.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCategoryH:
                udCategoryH.setVisibility(View.VISIBLE);
                break;
            case R.id.btnCategoryP:
                udCategoryP.setVisibility(View.VISIBLE);
                break;
        }
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
