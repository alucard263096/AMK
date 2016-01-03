package com.helpfooter.steve.amkdoctor;

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

import com.helpfooter.steve.amkdoctor.Common.UrlImageLoader;
import com.helpfooter.steve.amkdoctor.CustomControlView.CircleImageView;
import com.helpfooter.steve.amkdoctor.DAO.MemberDao;
import com.helpfooter.steve.amkdoctor.Interfaces.IMyFragment;
import com.helpfooter.steve.amkdoctor.Utils.StaticVar;
import com.helpfooter.steve.amkdoctor.Utils.ToolsUtil;
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
    private static Bundle margs;
    ImageView imgMyPhoto;
    private Activity mActivity;

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
        MemberMainFragment fragment = new MemberMainFragment(margs);
        margs.putString(ARG_PARAM1, param1);
        margs.putString(ARG_PARAM2, param2);
        fragment.setArguments(margs);
        return fragment;
    }
    public MemberMainFragment(Bundle args){
        this.margs=args;
    };
    public MemberMainFragment(Activity activ) {
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

    public void LoadMember(){
        try {
            if (StaticVar.Doctor != null) {
                ((TextView) memberview.findViewById(R.id.txtMyName)).setText("你好," + StaticVar.Doctor.getName() );
                if (StaticVar.Doctor.getPhoto().length() > 0) {
                    ImageView img = ((ImageView) memberview.findViewById(R.id.imgMyPhoto));
                    UrlImageLoader loader = new UrlImageLoader(img, StaticVar.ImageFolderURL + "doctor/" + StaticVar.Doctor.getPhoto());
                    loader.start();
                }
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
    String errorstr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_member_main, container, false);
        memberview=view;
       initUI(view);

            return view;



    }
    private void initUI(View view) {
        //图文咨询
        ((ImageView) view.findViewById(R.id.imgNormalChat)).setOnClickListener(this);
        //视频聊天
        ((ImageView) view.findViewById(R.id.imgVideoChat)).setOnClickListener(this);
        //消息中心
        ((ImageView) view.findViewById(R.id.imgMessage)).setOnClickListener(this);
        //个人资料
        ((ImageView) view.findViewById(R.id.imgInfo)).setOnClickListener(this);
        //隐私与政策
        ((ImageView) view.findViewById(R.id.imgSecrit)).setOnClickListener(this);
        //关于我们
        ((ImageView) view.findViewById(R.id.imgUs)).setOnClickListener(this);
       //((ImageView) view.findViewById(R.id.go)).setOnClickListener(this);

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
                    if(StaticVar.Doctor!=null) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image*//**//*");
                        this.getActivity().startActivityForResult(intent, 1);
                    }
                } catch (ActivityNotFoundException e) {

                }
                return;
            case R.id.imgInfo:
                 /*intent = new Intent(this.getActivity(), MemberInfoActivity.class);
                startActivity(intent);*/
                return;
            case R.id.imgNormalChat:
                MainActivity parActivity=(MainActivity)mActivity;
                parActivity.onClick(StaticVar.lstBottomBar.get(1).GetEnteryLayout());
                return;
            case R.id.imgVideoChat:
                MainActivity parsActivity=(MainActivity)mActivity;
                parsActivity.onClick(StaticVar.lstBottomBar.get(0).GetEnteryLayout());
                return;
            case R.id.imgMessage:
               intent = new Intent(this.getActivity(), NoticeListActivity.class);
                startActivity(intent);
                return;
            case R.id.imgSecrit:
                intent = new Intent(this.getActivity(), GeneralTextActivity.class);
                intent.putExtra("code","privacypolicy");
                intent.putExtra("title","隐私与政策");
                try {
                    startActivity(intent);
                }
                catch (Exception ex)
                {


                }
                return;
            case R.id.imgUs:

                intent = new Intent(this.getActivity(), GeneralTextActivity.class);
               /* intent = new Intent(this.getActivity(), VideoChatActivitytest.class);*/

                intent.putExtra("code","aboutus");
                intent.putExtra("title","关于我们");
                startActivity(intent);
                return;

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
