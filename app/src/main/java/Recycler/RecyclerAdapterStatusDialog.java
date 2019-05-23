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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Models.penjualan;

public class RecyclerAdapterStatusDialog extends RecyclerView.Adapter<RecyclerAdapterStatusDialog.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<penjualan> penjualanList;
    private List<penjualan> penjualanListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterStatusDialog(Context context, List<penjualan> penjualanList) {
        this.context = context;
        this.penjualanList = penjualanList;
    }

    @NonNull
    @Override
    public RecyclerAdapterStatusDialog.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_status, viewGroup, false);
        final RecyclerAdapterStatusDialog.MyViewHolder holder = new RecyclerAdapterStatusDialog.MyViewHolder(v);

        penjualanListFull = new ArrayList<>(penjualanList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterStatusDialog.MyViewHolder myViewHolder, int i) {
        penjualan penjualan = penjualanList.get(i);
        myViewHolder.nama_cabang.setText(penjualan.getNama_cabang());
        myViewHolder.plat_kendaraan.setText(penjualan.getNo_plat_kendaraan());

        if(penjualan.getStatus_transaksi().equals("belum") && penjualan.getStatus_pembayaran().equals("belum")) {
            myViewHolder.status.setText("Transaksi belum selesai");
        }
        else if(penjualan.getStatus_transaksi().equals("sudah") && penjualan.getStatus_pembayaran().equals("belum")) {
            myViewHolder.status.setText("Transaksi belum dibayar");
        }

        myViewHolder.total_belanja.setText(penjualan.getGrand_total().toString());
        myViewHolder.created_at.setText(penjualan.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return penjualanList.size();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<penjualan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                for (penjualan item : penjualanListFull) {
                    if (item.getStatus_pembayaran().equals("belum")) {
                        filteredList.add(item);
                    }
//                    else if(item.getSales_supplier().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
                }
//                filteredList.addAll(penjualanListFull);
            }
            else {
                Log.d("performFiltering","in");
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (penjualan item : penjualanListFull) {
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
            penjualanList.clear();
            penjualanList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////Click listener
    public RecyclerAdapterStatusDialog(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nama_cabang;
        private TextView plat_kendaraan;
        private TextView status;
        private TextView total_belanja;
        private TextView created_at;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_cabang = itemView.findViewById(R.id.penjualan_recycler_cabang);
            plat_kendaraan = itemView.findViewById(R.id.penjualan_recycler_plat);
            status = itemView.findViewById(R.id.penjualan_recycler_status);
            total_belanja = itemView.findViewById(R.id.penjualan_recycler_total_harga);
            created_at = itemView.findViewById(R.id.penjualan_recycler_created);

        }
    }
}