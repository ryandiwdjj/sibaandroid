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

import Models.pengadaan;
import Models.penjualan;

public class RecyclerAdapterPengadaan extends RecyclerView.Adapter<RecyclerAdapterPengadaan.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<pengadaan> pengadaanList;
    private List<pengadaan> pengadaanListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterPengadaan(Context context, List<pengadaan> pengadaanList) {
        this.context = context;
        this.pengadaanList = pengadaanList;
    }

    @NonNull
    @Override
    public RecyclerAdapterPengadaan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_pengadaan, viewGroup, false);
        final RecyclerAdapterPengadaan.MyViewHolder holder = new RecyclerAdapterPengadaan.MyViewHolder(v);

        pengadaanListFull = new ArrayList<>(pengadaanList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPengadaan.MyViewHolder myViewHolder, int i) {
        pengadaan pengadaan = pengadaanList.get(i);
        myViewHolder.nama_supplier.setText(pengadaan.getNama_supplier());
        myViewHolder.nama_cabang.setText(pengadaan.getNama_cabang());
        myViewHolder.total_belanja.setText(pengadaan.getTotal_harga_pengadaan().toString());
        myViewHolder.created_at.setText(pengadaan.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return pengadaanList.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<pengadaan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(pengadaanListFull);
            }
            else {
                Log.d("performFiltering","in");
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (pengadaan item : pengadaanListFull) {
//                    if (item.getNama_supplier().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                    else if(item.getSales_supplier().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pengadaanList.clear();
            pengadaanList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////Click listener
    public RecyclerAdapterPengadaan(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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
        private TextView nama_supplier;
        private TextView nama_cabang;
        private TextView total_belanja;
        private TextView created_at;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_supplier = itemView.findViewById(R.id.pengadaan_recycler_supplier);
            nama_cabang = itemView.findViewById(R.id.pengadaan_recycler_cabang);
            total_belanja = itemView.findViewById(R.id.pengadaan_recycler_total_harga);
            created_at = itemView.findViewById(R.id.pengadaan_recycler_created);

        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}