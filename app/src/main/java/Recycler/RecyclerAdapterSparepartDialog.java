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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siba.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Models.sparepart;

import static API.ApiClient.getImageUrl;

public class RecyclerAdapterSparepartDialog extends RecyclerView.Adapter<RecyclerAdapterSparepartDialog.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<sparepart> sparepartList;
    private List<sparepart> sparepartListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterSparepartDialog(Context context, List<sparepart> sparepartList) {
        this.context = context;
        this.sparepartList = sparepartList;
    }

    @NonNull
    @Override
    public RecyclerAdapterSparepartDialog.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_sparepart, viewGroup, false);
        final RecyclerAdapterSparepartDialog.MyViewHolder holder = new RecyclerAdapterSparepartDialog.MyViewHolder(v);

        sparepartListFull = new ArrayList<>(sparepartList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSparepartDialog.MyViewHolder myViewHolder, int i) {
        sparepart spare = sparepartList.get(i);
        myViewHolder.nama_sparepart.setText(spare.getNama_sparepart());
        myViewHolder.harga_beli.setText("Rp. " + spare.getHarga_beli_sparepart().toString()); //get harga beli sparepart
        myViewHolder.kode_spare.setText(spare.getKode_sparepart());

        Picasso.get().load(getImageUrl() + spare.getGambar_sparepart()).resize(200,200)
                .centerCrop().placeholder(R.drawable.ic_atmaauto).into(myViewHolder.gambar_sparepart);
        //set image
    }

    @Override
    public int getItemCount() {
        return sparepartList.size();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////Click listener
    public RecyclerAdapterSparepartDialog(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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
        private TextView nama_sparepart;
        private TextView harga_beli;
        private TextView kode_spare;
        private ImageView gambar_sparepart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_sparepart = itemView.findViewById(R.id.sparepart_recycler_h1);
            harga_beli = itemView.findViewById(R.id.sparepart_recycler_h2);
            gambar_sparepart = itemView.findViewById(R.id.sparepart_recycler_img);
            kode_spare = itemView.findViewById(R.id.sparepart_recycler_h3);
        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}