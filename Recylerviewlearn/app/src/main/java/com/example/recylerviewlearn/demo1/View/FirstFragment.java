package com.example.recylerviewlearn.demo1.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.recylerviewlearn.R;
import com.example.recylerviewlearn.demo1.Activity1;
import com.example.recylerviewlearn.demo1.NormalAdapter;
import com.example.recylerviewlearn.demo1.SimpleItemTouchCallback;
import com.example.recylerviewlearn.demo1.date.User;
import com.example.recylerviewlearn.demo1.date.UserDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private ArrayList<User> mData;


    private NormalAdapter mAdapter;
    private DividerItemDecoration mDecoration;
    private RecyclerView.LayoutManager mLayoutManager;

//    private static Activity1.MyHandler myHandler;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mTRecyclerView;
    private ArrayList<User> mTData;
    private NormalAdapter mTAdapter;
    private DividerItemDecoration mTDecoration;
    private RecyclerView.LayoutManager mGridLayoutManager;

    private InterceptScrollView mScrollView;


    private Button send_button;
    private EditText send_editText;

    private Activity1 activity1;

    public MyHandler myHandler;



    public final static int MSG_GET_ALL = 1;
    public final static int MSG_SEND = 2;
    public final static int MSG_RECEIVE = 3;
    public final static int MSG_DELETE_LAST = 4;
    public final static int MSG_DELETE = 5;
    public final static int MSG_RELOAD = 6;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myHandler= new MyHandler((Activity1) this.getActivity());
        activity1 = (Activity1)getActivity();
        activity1.setHandler(myHandler);

        initTalk();
    }
    public void initTalk(){

        initMyRecyclerView();

        initTopRecyclerView();

        initScrollView();

        initMySend();

        initMySwipeRefreshLayout();
    }

    private void initScrollView() {
//        mScrollView = (InterceptScrollView) findViewById(R.id.ScroID);
        mScrollView = (InterceptScrollView) getView().findViewById(R.id.ScroID);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mSwipeRefreshLayout.setEnabled(mScrollView.getScrollY()==0&&mRecyclerView.getScrollY()==0);
            }
        });
    }


    private void initMySwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefreshLayout.setProgressViewEndTarget(true,200);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        myHandler.sendEmptyMessage(MSG_RELOAD);
                    }
                }).start();
            }
        });

    }

    private void initMySend() {

        send_button = (Button) getView().findViewById(R.id.send_button);
        send_editText = (EditText) getView().findViewById(R.id.send_text);

//        发送信息按钮
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        User user = new User("Hero",String.valueOf(send_editText.getText()));
//                        mData.add(user);
                        UserDatabase.getDatabase(getContext()).userDao().insertUsers(user);

                        Message msg = new Message();
                        msg.obj = user;
                        msg.what = MSG_SEND;

                        myHandler.sendMessage(msg);
                    }
                }).start();

            }
        });
    }

    private void initMyRecyclerView() {
//        myHandler = (MyHandler) Activity1.getHandler();

        mRecyclerView =getView().findViewById(R.id.rv);
        mDecoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mDecoration);
        initData();
        mAdapter = new NormalAdapter(getmData());
        mRecyclerView.setAdapter(mAdapter);

    }


    @SuppressLint("WrongConstant")
    private void initTopRecyclerView() {
        mTData = new ArrayList<User>();
        for(int i=0;i<18;i++){
            mTData.add(new User("Hero",String.valueOf(i)));
        }

        mTRecyclerView = getView().findViewById(R.id.Trv);
        mTDecoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.HORIZONTAL);
        mGridLayoutManager = new GridLayoutManager(this.getContext(),2, LinearLayout.VERTICAL,false);
        mTRecyclerView.setLayoutManager(mGridLayoutManager);
        mTRecyclerView.addItemDecoration(mTDecoration);
        mTAdapter = new NormalAdapter(mTData);
        mTRecyclerView.setAdapter(mTAdapter);
    }

    public void initData(){

        MyInitThread myInitThread = new MyInitThread();
        myInitThread.start();
    }

    //    子线程让获取数据库信息
    class MyInitThread extends Thread{

        @Override
        public void run(){
            Message msg = new Message();
            msg.obj = UserDatabase.getDatabase(getContext()).userDao().loadAllUsers();
            msg.what = MSG_GET_ALL;
            myHandler.sendMessage(msg);
            Log.d("TAG", "run: 我被跑啦");
        }
    }

    //    静态内部类myHandler
    public static class MyHandler extends Handler{
        WeakReference<Activity1> mActivityReference;
        public MyHandler(Activity1 activity){
            mActivityReference = new WeakReference<>(activity);
            Log.d("TAG", "MyHandler: 初始化handler");
        }

        //        handle接收子线程通知处理UI线程
        @Override
        public void handleMessage(Message msg) {
            Log.d("TAG", "handleMessage: 被调用啦"+String.valueOf(msg.what));
//            super.handleMessage(msg);
            final Activity1 activity = mActivityReference.get();
            if (activity==null)
                Log.d("TAG", "handleMessage: 空activity");
            if(activity!=null){
                switch (msg.what){
                    case MSG_GET_ALL:
                        List<User> datas = (List<User>) msg.obj;
                        activity.getFirstFragment().mAdapter.setDatas(datas);
                        activity.getFirstFragment().mAdapter.notifyDataSetChanged();
//                        if (activity.getFirstFragment().mAdapter.getDatas()!=null){
//                            Log.d("TAG", "handleMessage: "+activity.getFirstFragment().mAdapter.getDatas().get(0).mContent);
//                        }

                        ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchCallback(activity.getFirstFragment().mAdapter, (ArrayList<User>) datas,activity));
                        helper.attachToRecyclerView(activity.getFirstFragment().mRecyclerView);
//                        activity.getFirstFragment().mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MSG_SEND:
//                        下拉软键盘
                        activity.getFirstFragment().getsend_editText().setText(null);
                        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        im.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        User send= (User) msg.obj;
                        activity.getFirstFragment().mAdapter.addData(send);
                        activity.getFirstFragment().mAdapter.notifyItemInserted(activity.getFirstFragment().mAdapter.getItemCount());
                        break;
                    case  MSG_RECEIVE:
                        User receive = (User) msg.obj;
                        activity.getFirstFragment().mAdapter.addData(receive);
                        activity.getFirstFragment().mAdapter.notifyItemInserted(activity.getFirstFragment().mAdapter.getItemCount());
//                        activity.mAdapter.notifyItemRangeChanged(activity.mAdapter.getDataSize(),);
                        break;
                    case MSG_DELETE_LAST:
                        activity.getFirstFragment().mAdapter.deleteLastData();
                        activity.getFirstFragment().mAdapter.notifyItemRemoved(activity.getFirstFragment().mAdapter.getItemCount());
                        activity.getFirstFragment().mAdapter.notifyItemRangeChanged(activity.getFirstFragment().mAdapter.getItemCount(),activity.getFirstFragment().mAdapter.getItemCount());
                        break;
                    case MSG_DELETE:
                        int delete_pos = (int) msg.obj;
                        Log.d("TAG", "handleMessage: "+delete_pos);
                        activity.getFirstFragment().mAdapter.deleteData(delete_pos);
                        activity.getFirstFragment().mAdapter.notifyItemRemoved(delete_pos);
                        activity.getFirstFragment().mAdapter.notifyItemRangeChanged(delete_pos,activity.getFirstFragment().mAdapter.getItemCount());
                        break;
                    case MSG_RELOAD:{
                        activity.getFirstFragment().mSwipeRefreshLayout.setRefreshing(false);
                        activity.getFirstFragment().mAdapter.notifyDataSetChanged();
                        break;
                    }
                    default:
                        Log.d("TAG", "handleMessage: ？？？");
                        break;
                }
            }
        }

    }


    //    菜单栏点击选项按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_add:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = new User("Dragon","我只剩下99%的电量，先不说了，回聊！");
                        UserDatabase.getDatabase(getContext()).userDao().insertUsers(user);

                        Message msg = new Message();
                        msg.obj = user;
                        msg.what = MSG_RECEIVE;
                        myHandler.sendMessage(msg);
                    }
                }).start();
                break;
            case R.id.item_delete:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserDatabase.getDatabase(getContext()).userDao().delectLastUser();

                        Message msg = new Message();
                        msg.what = MSG_DELETE_LAST;

                        myHandler.sendMessage(msg);
                    }
                }).start();

                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void addDrogen(){
        User user = new User("Dragon","我只剩下99%的电量，先不说了，回聊！");
        UserDatabase.getDatabase(getContext()).userDao().insertUsers(user);

        Message msg = new Message();
        msg.obj = user;
        msg.what = MSG_RECEIVE;
        myHandler.sendMessage(msg);
    }

    public void deleteLast(){
        UserDatabase.getDatabase(getContext()).userDao().delectLastUser();

        Message msg = new Message();
        msg.what = MSG_DELETE_LAST;

        myHandler.sendMessage(msg);
    }



    public ArrayList<User> getmData() {
        return mData;
    }

    public void setmData(ArrayList<User> mData) {
        this.mData = mData;
    }
    public EditText getsend_editText() {
        return send_editText;
    }

    public void setsend_editText(EditText edit) {
        this.send_editText = edit;
    }

    public Handler getHandler(){
        return myHandler;
    }

    public NormalAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(NormalAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }
}
