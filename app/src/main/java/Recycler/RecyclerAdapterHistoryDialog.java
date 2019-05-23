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

import Models.penjualan;

public class RecyclerAdapterHistoryDialog extends RecyclerView.Adapter<RecyclerAdapterHistoryDialog.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<penjualan> penjualanList;
    private List<penjualan> penjualanListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterHistoryDialog(Context context, List<penjualan> penjualanList) {
        this.context = context;
        this.penjualanList = penjualanList;
    }

    @NonNull
    @Override
    public RecyclerAdapterHistoryDialog.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_history, viewGroup, false);
        final RecyclerAdapterHistoryDialog.MyViewHolder holder = new RecyclerAdapterHistoryDialog.MyViewHolder(v);

        penjualanListFull = new ArrayList<>(penjualanList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterHistoryDialog.MyViewHolder myViewHolder, int i) {
        penjualan penjualan = penjualanList.get(i);
        myViewHolder.nama_cabang.setText(penjualan.getNama_cabang());
        myViewHolder.no_plat.setText(penjualan.getNo_plat_kendaraan());
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
                    if (item.getStatus_transaksi().equals("sudah")
                            && item.getStatus_pembayaran().equals("sudah")) {
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
    public RecyclerAdapterHistoryDialog(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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
        private TextView no_plat;
        private TextView total_belanja;
        private TextView created_at;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_cabang = itemView.findViewById(R.id.penjualan_recycler_cabang);
            no_plat = itemView.findViewById(R.id.penjualan_recycler_plat);
            total_belanja = itemView.findViewById(R.id.penjualan_recycler_total_harga);
            created_at = itemView.findViewById(R.id.penjualan_recycler_created);

        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}