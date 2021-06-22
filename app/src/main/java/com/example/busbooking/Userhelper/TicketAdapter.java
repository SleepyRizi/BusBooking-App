package com.example.busbooking.Userhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busbooking.BusModelclass;
import com.example.busbooking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketviewHolder> {
    private ArrayList<BusModelclass> busList;
    private Context context;
    private itemSelected myticket;
    public TicketAdapter(Context context, ArrayList<BusModelclass> list){

        this.busList=list;
        this.myticket= (itemSelected) context;

    }

    public interface itemSelected{
        public void onClickball(int index);
    }

    @NonNull
    @Override
    public TicketviewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_ticketrv,parent,false);
        return new TicketviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TicketAdapter.TicketviewHolder holder, int position) {
        holder.tvlocationticket.setText(busList.get(position).getFromlocation());
        holder.tvmovtime.setText(busList.get(position).getStarttiming());
        holder.tvdestinticket.setText(busList.get(position).getArrivelocation());
        holder.tvarrivetime.setText(busList.get(position).getArrivetiming());
        holder.tvticketprice.setText(busList.get(position).getPrice());
        holder.tvshowTime.setText(busList.get(position).getStarttiming());
        holder.tv_bustype.setText(busList.get(position).getBusCondition());

        //setting tag
        holder.itemView.setTag(busList.get(position));
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    public class TicketviewHolder extends RecyclerView.ViewHolder{
            TextView tvlocationticket;
            TextView tvmovtime;
            TextView tvdestinticket;
            TextView tvarrivetime;
            TextView tvticketprice;
            TextView tvshowTime;
            TextView tv_bustype;
            Button btn_buynow;

            public TicketviewHolder(@NonNull View itemView) {
                super(itemView);
                tvlocationticket=itemView.findViewById(R.id.tvlocationticket);
                tvmovtime=itemView.findViewById(R.id.tvmovtime);
                tvdestinticket=itemView.findViewById(R.id.tvdestinticket);
                tvarrivetime=itemView.findViewById(R.id.tvarrivetime);
                tvticketprice=itemView.findViewById(R.id.tvticketprice);
                tvshowTime=itemView.findViewById(R.id.tvshowTime);
                tv_bustype=itemView.findViewById(R.id.tv_bustype);
                btn_buynow=itemView.findViewById(R.id.btn_buynow);
                //getting tag

                btn_buynow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myticket.onClickball(busList.indexOf((BusModelclass) itemView.getTag()));
                    }
                });
            }
        }
}
