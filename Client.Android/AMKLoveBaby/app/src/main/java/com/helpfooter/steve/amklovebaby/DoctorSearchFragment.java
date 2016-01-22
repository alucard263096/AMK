package com.helpfooter.steve.amklovebaby;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helpfooter.steve.amklovebaby.DAO.DoctorDao;
import com.helpfooter.steve.amklovebaby.DataObjs.DoctorSearchLableObj;
import com.helpfooter.steve.amklovebaby.Interfaces.IMyFragment;
import com.helpfooter.steve.amklovebaby.Utils.StaticVar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorSearchFragment extends Fragment implements IMyFragment, View.OnClickListener, TextView.OnEditorActionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
     * @return A new instance of fragment DoctorSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorSearchFragment newInstance(String param1, String param2) {
        DoctorSearchFragment fragment = new DoctorSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DoctorSearchFragment() {
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

    EditText txtSearch;
    ImageView btnView;
    TextView txtChar;
    TextView txtVideo;
    private ViewGroup container = null;
    ArrayList<TextView> lstTxt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doctor_search, container, false);
        txtSearch=(EditText)view.findViewById(R.id.txtSearch);
        txtSearch.setOnEditorActionListener(this);
        btnView=(ImageView)view.findViewById(R.id.btnSearch);
        btnView.setOnClickListener(this);

        txtChar=(TextView)view.findViewById(R.id.txtChar);
        txtVideo=(TextView)view.findViewById(R.id.txtVideo);

        DoctorDao dao=new DoctorDao(this.getActivity());
        DoctorSearchLableObj charlable=dao.getDoctorCharLables();
        setLableForTxt(txtChar, charlable);

        DoctorSearchLableObj videolable=dao.getDoctorVideoLables();
        setLableForTxt(txtVideo, videolable);

        container = (LinearLayout)view.findViewById(R.id.labelcontainer);
        DoctorSearchLableObj taiwanlable=dao.getDoctorInTaiwanLables();
        ArrayList<DoctorSearchLableObj> lstLable=dao.getDoctorOfficeLables();

        lstTxt=new ArrayList<TextView>();
        lstTxt.add(createTxtForLable(taiwanlable));
        for(DoctorSearchLableObj obj:lstLable){
            if(!obj.getName().equals("")) {
                lstTxt.add(createTxtForLable(obj));
            }
        }

        isFristTime=true;
        //setOfficeLables();
        //container.addView(createTxtForLable(taiwanlable));
        LinearLayout layout =null;

        int count=0;
        int magic=3;
        for(TextView txt:lstTxt){
            if(count%magic==0){
                layout = new LinearLayout(this.getActivity());
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.setOrientation(LinearLayout.HORIZONTAL);
                container.addView(layout);
            }
            layout.addView(txt);
            count++;
        }

        return view;
    }

    private void setLableForTxt(TextView txt,DoctorSearchLableObj searchLableObj){
        txt.setText(searchLableObj.getName()+"("+String.valueOf( searchLableObj.getCount())+")");
        txt.setTag(searchLableObj);
        txt.setOnClickListener(this);
    }
    private TextView createTxtForLable(DoctorSearchLableObj obj){
        TextView txt=new TextView(this.getActivity());
        txt.setTextSize(11);
        txt.setPadding(30, 10, 30, 20);
        txt.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        layoutParams.setMargins(20, 10, 10, 20);

        txt.setBackground(getResources().getDrawable(R.drawable.text_view_border3));
        txt.setLayoutParams(layoutParams);
        setLableForTxt(txt, obj);

        return txt;
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
        return "找医生";
    }

    public void callSearch(){

        String keyword=txtSearch.getText().toString();
        String search=" ( name like '%"+keyword+"%'"+
                " or office like '%"+keyword+"%'"+
                " or title like '%"+keyword+"%'"+
                " or introduce like '%"+keyword+"%'"+
                " or credentials like '%"+keyword+"%'"+
                " or expert like '%"+keyword+"%' )";
        Intent intent2 = new Intent(this.getActivity(), DoctorListActivity.class);
        intent2.putExtra("name", "关键字:"+txtSearch.getText().toString());
        intent2.putExtra("search", search);
        this.getActivity().startActivity(intent2);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSearch){
            callSearch();
        }
        else if(v.getTag() instanceof DoctorSearchLableObj){
            DoctorSearchLableObj lableObj=(DoctorSearchLableObj)v.getTag();
            Intent intent = new Intent(this.getActivity(), DoctorListActivity.class);
            intent.putExtra("name", lableObj.getName());
            intent.putExtra("search", lableObj.getSearch());
            this.getActivity().startActivity(intent);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

            callSearch();
            return true;
        }
        return false;
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


    private void resetTextViewMarginsRight(ViewGroup viewGroup) {
        final TextView tempTextView = (TextView) viewGroup.getChildAt(viewGroup.getChildCount() - 1);
        tempTextView
                .setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void addItemView(TextView tvItem,LayoutInflater inflater, ViewGroup viewGroup, LinearLayout.LayoutParams params, String text) {

        tvItem.setText(text);
    }
    boolean isFristTime=false;
    public void  setOfficeLables(ArrayList<TextView> lstLables ){

        try {
            if ( isFristTime) {
                isFristTime = false;
                final int containerWidth = StaticVar.width - 20;

                final Paint paint = new Paint();
                final LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout layout = new LinearLayout(this.getActivity());
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.setOrientation(LinearLayout.HORIZONTAL);
                container.addView(layout);

                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 0);

                /** 一行剩下的空间 **/
                int remainWidth = containerWidth;


                for (TextView txt : lstLables) {
                    final String text = txt.getText().toString();
                    final float itemWidth = paint.measureText(text) + 10;
                    if (remainWidth > itemWidth) {
                    } else {
                        resetTextViewMarginsRight(layout);
                        layout = new LinearLayout(this.getActivity());
                        layout.setLayoutParams(params);
                        layout.setOrientation(LinearLayout.HORIZONTAL);

                        /** 将前面那一个textview加入新的一行 */
                        layout.addView(txt, tvParams);
                        container.addView(layout);
                        remainWidth = containerWidth;
                    }
                    remainWidth = (int) (remainWidth - itemWidth + 0.5f) - 10;
                }
                resetTextViewMarginsRight(layout);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
