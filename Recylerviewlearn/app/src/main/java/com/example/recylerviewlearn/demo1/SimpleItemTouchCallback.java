package com.example.recylerviewlearn.demo1;

import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.example.recylerviewlearn.demo1.Activity1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recylerviewlearn.demo1.date.User;
import com.example.recylerviewlearn.demo1.date.UserDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.recylerviewlearn.demo1.View.FirstFragment.MSG_DELETE;

public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {
    private NormalAdapter mAdapter;
    private  ArrayList<User> mData;
    private Activity1 activity1;
    public SimpleItemTouchCallback(NormalAdapter adapter, ArrayList<User> data, Activity1 activity1){
        mAdapter = adapter;
        this.activity1 = activity1;
        Log.d("simpleitem", "SimpleItemTouchCallback: ");
    }




    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//上下拖拽
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END; //左右滑动
//        Log.d("TAG", "onSwiped: "+pos);
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        int from = viewHolder.getAdapterPosition();
//        int to = target.getAdapterPosition();
//        Collections.swap(mData,from,to);
//        mAdapter.notifyItemMoved(from,to);
        Log.d("simpleitem", "onMove: ");
        return true;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, final int direction) {
        final int pos = viewHolder.getAdapterPosition();

        final Message msg = new Message();
        msg.what = MSG_DELETE;
        msg.obj = pos;
        Handler handler = activity1.getFirstFragment().getHandler();
        handler.sendMessage(msg);
        User user = mAdapter.getDatas().get(pos);
        Log.d("simpleitem", "onSwiped: "+pos);
        new Thread(new Runnable(){

            @Override
            public void run() {
                List<User> list = UserDatabase.getDatabase(activity1).userDao().loadAllUsers();
                UserDatabase.getDatabase(activity1).userDao().delectPosUser(list.get(pos).getUid());
            }
        }).start();



    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState){
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            NormalAdapter.VH holder = (NormalAdapter.VH)viewHolder;
            holder.itemView.setBackgroundColor(0x66ccff);//设置拖拽和侧滑时的颜色
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
        super.clearView(recyclerView, viewHolder);
        NormalAdapter.VH holder = (NormalAdapter.VH)viewHolder;
//        holder.itemView.setBackgroundColor(0xffeeeeee); //背景色还原
    }



}
