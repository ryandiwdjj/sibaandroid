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

public class RecyclerAdapterPenjualan extends RecyclerView.Adapter<RecyclerAdapterPenjualan.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<penjualan> penjualanList;
    private List<penjualan> penjualanListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterPenjualan(Context context, List<penjualan> penjualanList) {
        this.context = context;
        this.penjualanList = penjualanList;
    }

    @NonNull
    @Override
    public RecyclerAdapterPenjualan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_penjualan, viewGroup, false);
        final RecyclerAdapterPenjualan.MyViewHolder holder = new RecyclerAdapterPenjualan.MyViewHolder(v);

        penjualanListFull = new ArrayList<>(penjualanList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPenjualan.MyViewHolder myViewHolder, int i) {
//        supplier sup = supplierList.get(i);
//        myViewHolder.nama_supplier.setText(sup.getNama_supplier());
//        myViewHolder.sales_supplier.setText(sup.getSales_supplier());
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
                filteredList.addAll(penjualanListFull);
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
    public RecyclerAdapterPenjualan(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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
//        private TextView nama_supplier;
//        private TextView sales_supplier;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            nama_supplier = itemView.findViewById(R.id.supplier_recycler_h1);
//            sales_supplier = itemView.findViewById(R.id.supplier_recycler_h2);
        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}