package com.example.recylerviewlearn.demo1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recylerviewlearn.R;
import com.example.recylerviewlearn.demo1.date.User;

import java.util.List;

public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH> {
    private List<User> mDatas;
    public NormalAdapter(List<User> data){
        this.mDatas = data;
    }

    public void setDatas(List<User> mDatas) {
        this.mDatas = mDatas;
    }

    public List<User> getDatas(){
        return this.mDatas;
    }

    public void addData(User user){
        mDatas.add(user);
    }

    public void deleteLastData(){

        if(mDatas.size()>0){
            mDatas.remove(mDatas.size()-1);
        }
    }

    public void deleteData(int pos){
        if(mDatas.size()>0){
            mDatas.remove(pos);
        }
    }

    public int getDataSize(){
        if(mDatas.size()>0){
            return mDatas.size();
        }else return 0;
    }



    enum ITEM_TYPE{
        RECEIVE,
        SEND
    }

    @NonNull
    @Override
    public int getItemViewType(int position){
        if(mDatas.get(position).getMType().equals("Dragon")){
            return ITEM_TYPE.RECEIVE.ordinal();
        }else{
            return ITEM_TYPE.SEND.ordinal();
        }
    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("viewType:", String.valueOf(viewType));
        if(viewType == ITEM_TYPE.RECEIVE.ordinal()){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive_item,parent,false);
            return  new VH(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item,parent,false);
            return  new VH(v);
        }

    }


    @Override
    public void onBindViewHolder(VH holder, final int position) {
        User user = mDatas.get(position);
        holder.content.setText(user.getMContent());
//        holder.title.setText(model.title);
        Log.d("position", "onBindViewHolder: position="+position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //点击事件
//                Log.d("TAG", "onClick: 123");
//            }
//        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG","POS"+position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDatas!=null){
            return mDatas.size();
        }
        else return 0;
    }


    public static class VH extends RecyclerView.ViewHolder{
//        public final TextView title;
        public final ImageView img;
        public final TextView content;

        public VH(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.Header);
//            title = (TextView) v.findViewById(R.id.title);
            content = (TextView) v.findViewById(R.id.Content);
        }
    }


}
