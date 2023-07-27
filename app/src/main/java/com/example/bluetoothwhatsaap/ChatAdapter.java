package com.example.bluetoothwhatsaap;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{


    View views;
    int argu,lastPosition = -1;
    ArrayList<MessageModel> messageModels;
    Context context;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            views = view;
            argu = viewType;
            changeAnimation(views,messageModels.size()-1,argu);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            views =view;
            argu = viewType;
            changeAnimation(views,messageModels.size()-1,argu);
            return new RecieverViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);

        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMessage.setText(messageModel.getMessage());
            ((SenderViewHolder)holder).senderTime.setText(messageModel.getTime());
        }else{
            ((RecieverViewHolder)holder).recieverMessage.setText(messageModel.getMessage());
            ((RecieverViewHolder)holder).recieverTime.setText(messageModel.getTime());
        }

        changeAnimation(views,position,argu);

    }



    public void changeAnimation(View view, int position, int myargs) {
        Animation animation;

            animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(500);


        if(position > lastPosition) {
//            Toast.makeText(context,""+lastPosition,Toast.LENGTH_SHORT).show();
            view.startAnimation(animation);
            lastPosition=position;
        }
    }







    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getuId()==SENDER_VIEW_TYPE){

            return SENDER_VIEW_TYPE;
        }else{
            return RECEIVER_VIEW_TYPE;
        }
//
//        return super.getItemViewType(position);
//        return SENDER_VIEW_TYPE+position;
    }



    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recieverMessage, recieverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMessage = itemView.findViewById(R.id.receiverText);
            recieverTime = itemView.findViewById(R.id.receiverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMessage, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
//            ObjectAnimator animator1 = ObjectAnimator.ofFloat(itemView,"translationX",200f);
//            animator1.setDuration(1000);
//            ObjectAnimator animator2 = ObjectAnimator.ofFloat(itemView,"translationX",-200f);
//            animator2.setDuration(1000);
            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
