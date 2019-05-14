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

import Models.detail_pengadaan;
import Models.sparepart;

public class RecyclerAdapterDetailPengadaan extends RecyclerView.Adapter<RecyclerAdapterDetailPengadaan.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<detail_pengadaan> pengadaanList;
    private List<detail_pengadaan> sparepartListFull;
    private ClickListener clicklistener;
    private GestureDetector gestureDetector;

    public RecyclerAdapterDetailPengadaan(Context context, List<detail_pengadaan> pengadaanList) {
        this.context = context;
        this.pengadaanList = pengadaanList;
    }

    @NonNull
    @Override
    public RecyclerAdapterDetailPengadaan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_pengadaan_detail, viewGroup, false);
        final RecyclerAdapterDetailPengadaan.MyViewHolder holder = new RecyclerAdapterDetailPengadaan.MyViewHolder(v);

        sparepartListFull = new ArrayList<>(pengadaanList);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterDetailPengadaan.MyViewHolder myViewHolder, int i) {
        detail_pengadaan pengadaan = pengadaanList.get(i);
        myViewHolder.nama_sparepart.setText(pengadaan.getNama_sparepart());
        myViewHolder.harga_beli.setText("Rp. " + pengadaan.getHarga_beli_sparepart().toString()); //get harga beli sparepart
        myViewHolder.jumlah_beli.setText("1");

        Picasso.get().load(pengadaan.getGambar_sparepart()).resize(200,200)
                .centerCrop().placeholder(R.drawable.ic_atmaauto).into(myViewHolder.gambar_sparepart);
        //set image

    }

    @Override
    public int getItemCount() {
        return pengadaanList.size();
    }

    public Filter getSearchFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<detail_pengadaan> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(sparepartListFull);
            }
            else {
                Log.d("performFiltering","in");
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (detail_pengadaan item : sparepartListFull) {
                    if (item.getNama_sparepart().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    if(item.getKode_sparepart().toLowerCase().contains(filterPattern)) {
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
            pengadaanList.clear();
            pengadaanList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    ////////////////////////////////////////////////////////////////////////////////////////////////////Click listener
    public RecyclerAdapterDetailPengadaan(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

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
        private TextView jumlah_beli;
        private ImageView gambar_sparepart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_sparepart = itemView.findViewById(R.id.sparepart_recycler_nama);
            harga_beli = itemView.findViewById(R.id.sparepart_recycler_harga);
            gambar_sparepart = itemView.findViewById(R.id.sparepart_recycler_img);
            jumlah_beli = itemView.findViewById(R.id.sparepart_recycler_jumlah_beli);
        }
        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Hey, you clicked me", Toast.LENGTH_SHORT).show();
        }
    }
}