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

public class RecAlQuran extends RecyclerView.Adapter<RecAlQuran.ViewHolder> implements Filterable {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private List<DataAlQuran> messageList;
    private List<DataAlQuran> messageListFull;

    private OnItemClickListener mListener;

    public RecAlQuran(Context mContext, ArrayList<DataAlQuran> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
        messageListFull = new ArrayList<>(messageList);
    }

    @NonNull
    @Override
    public RecAlQuran.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_alquran, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecAlQuran.ViewHolder holder, int position) {
        holder.listNoSurah.setText(messageList.get(position).getListnomor());
        holder.listNamaSurah.setText(messageList.get(position).getListnamaSurah());
        holder.listArtiSurah.setText(messageList.get(position).getListartiSurah());
        holder.listArabSurah.setText(messageList.get(position).getListarabSurah());
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
            List<DataAlQuran> filteredList = new ArrayList<>();
            if (constraint == null ||  constraint.length() == 0){
                filteredList.addAll(messageListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataAlQuran item : messageListFull){
                    if (item.getListnamaSurah().toLowerCase().contains(filterPattern)){
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
        TextView listNoSurah, listNamaSurah, listArabSurah, listArtiSurah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // List Surah
            listNoSurah = itemView.findViewById(R.id.idTxt);
            listNamaSurah = itemView.findViewById(R.id.titleTxt);
            listArtiSurah = itemView.findViewById(R.id.latinTxt);
            listArabSurah = itemView.findViewById(R.id.arabTxt);
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
