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

public class RecDoaHarian extends RecyclerView.Adapter<RecDoaHarian.ViewHolder> implements Filterable {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private List<DataDoaHarian> messageList;
    private List<DataDoaHarian> messageListFull;

    private OnItemClickListener mListener;

    public RecDoaHarian(Context mContext, ArrayList<DataDoaHarian> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
        messageListFull = new ArrayList<>(messageList);
    }

    @NonNull
    @Override
    public RecDoaHarian.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_doaharian, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecDoaHarian.ViewHolder holder, int position) {
        // Text View
        holder.nomor.setText(messageList.get(position).getNomor());
        holder.title.setText(messageList.get(position).getTitle());
        holder.latin.setText(messageList.get(position).getLatin());
        holder.arab.setText(messageList.get(position).getArabic());
        holder.translation.setText(messageList.get(position).getTranslation());

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
            List<DataDoaHarian> filteredList = new ArrayList<>();
            if (constraint == null ||  constraint.length() == 0){
                filteredList.addAll(messageListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataDoaHarian item : messageListFull){
                    if (item.getTitle().toLowerCase().contains(filterPattern)){
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
        TextView nomor, title, latin, translation, arab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomor = itemView.findViewById(R.id.idTxt);
            title = itemView.findViewById(R.id.titleTxt);
            latin = itemView.findViewById(R.id.latinTxt);
            arab = itemView.findViewById(R.id.arabTxt);
            translation = itemView.findViewById(R.id.translationTxt);
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
