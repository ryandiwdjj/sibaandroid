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

import java.util.ArrayList;
import java.util.List;

import Models.sparepart;


public class RecyclerAdapterSparepart extends RecyclerView.Adapter<RecyclerAdapterSparepart.MyViewHolder> implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<sparepart> sparepartList;
    private List<sparepart> sparepartListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterSparepart(Context context, List<sparepart> sparepartList) {
        this.context = context;
        this.sparepartList = sparepartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_sparepart, viewGroup, false);
        final MyViewHolder holder = new MyViewHolder(v);

        sparepartListFull = new ArrayList<>(sparepartList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSparepart.MyViewHolder myViewHolder, int i) {
        sparepart spare = sparepartList.get(i);
        myViewHolder.nama_sparepart.setText(spare.getNama_sparepart());
        myViewHolder.harga_jual.setText("Rp. " + spare.getHarga_jual_sparepart().toString());
        //set image
    }

    @Override
    public int getItemCount() {
        return sparepartList.size();
    }

    public Filter getSearchFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<sparepart> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(sparepartListFull);
            }
            else {
                Log.d("performFiltering","in");
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (sparepart item : sparepartListFull) {
                    if (item.getNama_sparepart().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sparepartList.clear();
            sparepartList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////Click listener
    public RecyclerAdapterSparepart(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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
        private TextView harga_jual;
        private ImageView gambar_sparepart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_sparepart = itemView.findViewById(R.id.sparepart_recycler_h1);
            harga_jual = itemView.findViewById(R.id.sparepart_recycler_h2);
            gambar_sparepart = itemView.findViewById(R.id.sparepart_recycler_img);
        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}


