package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.Common.MemberMgr;
import com.helpfooter.steve.amklovebaby.Common.UrlImageLoader;
import com.helpfooter.steve.amklovebaby.CustomControlView.CircleImageView;
import com.helpfooter.steve.amklovebaby.DAO.MemberDao;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyFragment;
import com.helpfooter.steve.amklovebaby.Loader.MemberUpdateLoader;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;
import com.helpfooter.steve.amklovebaby.Utils.ToolsUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MemberMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MemberMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberMainFragment extends Fragment  implements IMyFragment,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imgMyPhoto;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberMainFragment newInstance(String param1, String param2) {
        MemberMainFragment fragment = new MemberMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MemberMainFragment() {
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

    public void LoadMember(){
        try {
            if (StaticVar.Member != null) {
                ((TextView) memberview.findViewById(R.id.txtMyName)).setText("你好," + StaticVar.Member.getName());
                if (StaticVar.Member.getPhoto().length() > 0) {
                    ImageView img = ((ImageView) memberview.findViewById(R.id.imgMyPhoto));
                    UrlImageLoader loader = new UrlImageLoader(img, StaticVar.ImageFolderURL + "member/" + StaticVar.Member.getPhoto());
                    loader.start();
                }
            } else {
                ((TextView) memberview.findViewById(R.id.txtMyName)).setText("请登录");
                ImageView img = ((ImageView) memberview.findViewById(R.id.imgMyPhoto));
                img.setImageResource(R.drawable.logo1);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(StaticVar.Member==null){
//           MainActivity main= (MainActivity)this.getActivity();
//            main.SetToHome();
//
//        }else {
//            ((TextView)this.getActivity().findViewById(R.id.txtMyName)).setText("你好," + StaticVar.Member.getName());
//        }
//    }

    View memberview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_member_main, container, false);
        memberview=view;
        initUI(view);
        return  view;
    }
    private void initUI(View view) {
        ((LinearLayout) view.findViewById(R.id.btnMyInfo)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.btnCharlist)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.btnMyOrder)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.btnMyDoctor)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.btnPP)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.btnAboutus)).setOnClickListener(this);
        ((LinearLayout) view.findViewById(R.id.btnHelpDocument)).setOnClickListener(this);

        imgMyPhoto= ((ImageView) view.findViewById(R.id.imgMyPhoto));
        imgMyPhoto.setOnClickListener(this);

        ((TextView) view.findViewById(R.id.txtMyName)).setOnClickListener(this);
        LoadMember();
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.imgMyPhoto:
                try
                {
                    if(StaticVar.Member!=null) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        this.getActivity().startActivityForResult(intent, 1);
                    }
                } catch (ActivityNotFoundException e) {

                }
                return;
            case R.id.btnMyInfo:
                 intent = new Intent(this.getActivity(), MemberInfoActivity.class);
                startActivity(intent);
                return;
            case R.id.btnCharlist:
                intent = new Intent(this.getActivity(), CharListActivity.class);
                startActivity(intent);
                return;
            case R.id.btnMyOrder:
                 intent = new Intent(this.getActivity(), OrderListActivity.class);
                startActivity(intent);
                return;
            case R.id.btnMyDoctor:
                intent = new Intent(this.getActivity(), FollowDoctorActivity.class);
                startActivity(intent);
                return;
            case R.id.btnPP:
                intent = new Intent(this.getActivity(), GeneralTextActivity.class);
                intent.putExtra("code","privacypolicy");
                intent.putExtra("title","隐私与政策");
                startActivity(intent);
                return;
            case R.id.btnAboutus:

                intent = new Intent(this.getActivity(), GeneralTextActivity.class);
                intent.putExtra("code","aboutus");
                intent.putExtra("title","关于我们");
                startActivity(intent);
                return;
            case R.id.btnHelpDocument:

                intent = new Intent(this.getActivity(), GeneralTextActivity.class);
                intent.putExtra("code","helpus");
                intent.putExtra("title","帮助");
                startActivity(intent);
                return;
            case R.id.txtMyName:
                if(StaticVar.Member==null) {
                    if(MemberMgr.CheckIsLogin(this.getActivity())){

                    }
                    return;
                }
        }
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
        return "我的资料";
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
