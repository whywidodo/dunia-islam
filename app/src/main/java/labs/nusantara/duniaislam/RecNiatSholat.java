package labs.nusantara.duniaislam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecNiatSholat extends RecyclerView.Adapter<RecNiatSholat.ViewHolder> implements Filterable {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private List<DataNiatSholat> messageList;
    private List<DataNiatSholat> messageListFull;

    private OnItemClickListener mListener;

    public RecNiatSholat(Context mContext, ArrayList<DataNiatSholat> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
        messageListFull = new ArrayList<>(messageList);
    }

    @NonNull
    @Override
    public RecNiatSholat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_niatsholat, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecNiatSholat.ViewHolder holder, int position) {
        // Text View
        holder.id.setText(messageList.get(position).getId());
        holder.nomor.setText(messageList.get(position).getNomor());
        holder.nama.setText(messageList.get(position).getNama());
        holder.latin.setText(messageList.get(position).getLatin());
        holder.arab.setText(messageList.get(position).getArabic());
        holder.terjemahan.setText(messageList.get(position).getTerjemahan());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    @Override
    public Filter getFilter() {
        return filterData;
    }

    private Filter filterData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataNiatSholat> filteredList = new ArrayList<>();
            if (constraint == null ||  constraint.length() == 0){
                filteredList.addAll(messageListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataNiatSholat item : messageListFull){
                    if (item.getNama().toLowerCase().contains(filterPattern)){
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
            messageList.clear();
            messageList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id, nomor, nama, latin, terjemahan, arab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idTxt);
            nomor = itemView.findViewById(R.id.nomorTxt);
            nama = itemView.findViewById(R.id.namaTxt);
            latin = itemView.findViewById(R.id.latinTxt);
            arab = itemView.findViewById(R.id.arabTxt);
            terjemahan = itemView.findViewById(R.id.terjemahanTxt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }
}
