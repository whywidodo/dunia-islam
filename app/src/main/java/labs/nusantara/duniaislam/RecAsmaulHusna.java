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

public class RecAsmaulHusna extends RecyclerView.Adapter<RecAsmaulHusna.ViewHolder> implements Filterable {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private List<DataAsmaulHusna> messageList;
    private List<DataAsmaulHusna> messageListFull;

    private OnItemClickListener mListener;

    public RecAsmaulHusna(Context mContext, ArrayList<DataAsmaulHusna> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
        messageListFull = new ArrayList<>(messageList);
    }

    @NonNull
    @Override
    public RecAsmaulHusna.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_asmaulhusna, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecAsmaulHusna.ViewHolder holder, int position) {
        // Text View
//        holder.nomor.setText(messageList.get(position).getId());
        holder.arab1.setText(messageList.get(position).getArabic());
        holder.latin1.setText(messageList.get(position).getLatin());

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
            List<DataAsmaulHusna> filteredList = new ArrayList<>();
            if (constraint == null ||  constraint.length() == 0){
                filteredList.addAll(messageListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataAsmaulHusna item : messageListFull){
                    if (item.getLatin().toLowerCase().contains(filterPattern)){
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
        TextView nomor, latin, arab, translationid, translationen;
        TextView arab1, latin1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            arab1 = itemView.findViewById(R.id.idTxt1);
            latin1 = itemView.findViewById(R.id.asma1);
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
