package com.example.busbooking.Userhelper;

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

public class TicketAdapter extends FirebaseRecyclerAdapter<BusModelclass,TicketAdapter.TicketviewHolder> {

    public TicketAdapter(@NonNull FirebaseRecyclerOptions<BusModelclass> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull TicketAdapter.TicketviewHolder holder, int position, @NonNull BusModelclass model) {
        holder.tvlocationticket.setText(model.getFromlocation());
        holder.tvmovtime.setText(model.getStarttiming());
        holder.tvdestinticket.setText(model.getArrivelocation());
        holder.tvarrivetime.setText(model.getArrivetiming());
        holder.tvticketprice.setText(model.getPrice());
        holder.tvshowTime.setText(model.getStarttiming());
        holder.tv_bustype.setText(model.getBusCondition());


    }

    @NonNull
    @Override
    public TicketviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_ticketrv,parent,false);
        return new TicketviewHolder(view);
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
        }
    }
}
