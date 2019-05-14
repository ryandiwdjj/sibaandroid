package Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.siba.R;

import java.util.ArrayList;
import java.util.List;

import Models.cabang;

public class RecyclerAdapterCabang  extends RecyclerView.Adapter<RecyclerAdapterCabang.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<cabang> cabangList;
    private List<cabang> cabangListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterCabang(Context context, List<cabang> cabangList) {
        this.context = context;
        this.cabangList = cabangList;
    }

    @NonNull
    @Override
    public RecyclerAdapterCabang.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_cabang, viewGroup, false);
        final RecyclerAdapterCabang.MyViewHolder holder = new RecyclerAdapterCabang.MyViewHolder(v);

        cabangListFull = new ArrayList<>(cabangList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterCabang.MyViewHolder myViewHolder, int i) {
        cabang cab = cabangList.get(i);
        myViewHolder.nama_cabang.setText(cab.getNama_cabang());
        myViewHolder.alamat_cabang.setText(cab.getAlamat_cabang());
    }

    @Override
    public int getItemCount() {
        return cabangList.size();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////Click listener
    public RecyclerAdapterCabang(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

        this.clicklistener=clicklistener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clicklistener!=null){
                    clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child=recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
        if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(motionEvent)){
            clicklistener.onClick(child,recyclerView.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nama_cabang;
        private TextView alamat_cabang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_cabang = itemView.findViewById(R.id.cabang_recycler_h1);
            alamat_cabang = itemView.findViewById(R.id.cabang_recycler_h2);
        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}