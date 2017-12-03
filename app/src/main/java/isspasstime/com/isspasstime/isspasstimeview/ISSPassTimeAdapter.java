package isspasstime.com.isspasstime.isspasstimeview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import isspasstime.com.isspasstime.datacontainer.PassTime;
import isspasstime.com.isspasstime.utils.Utils;

/**
 * Created by Sagar on 12/3/2017.
 */

public class ISSPassTimeAdapter extends RecyclerView.Adapter<ISSPassTimeAdapter.ViewHolder> {

    private List<PassTime> passTimes;
    private Context mContext;
    private PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textv1;
        public TextView textv2;
        PostItemListener mItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            textv1 = (TextView) itemView.findViewById(android.R.id.text1);
            textv2 = (TextView) itemView.findViewById(android.R.id.text2);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PassTime item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(item.getDuration());

            notifyDataSetChanged();
        }
    }

    public ISSPassTimeAdapter(Context context, List<PassTime> posts, PostItemListener itemListener) {
        passTimes = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public ISSPassTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ISSPassTimeAdapter.ViewHolder holder, int position) {
        PassTime passTimes = this.passTimes.get(position);
        TextView textView1 = holder.textv1;
        textView1.setText("Pass " + (position + 1) + " Duration is " + passTimes.getDuration().toString() + "Sec");
        TextView textView2 = holder.textv2;
        textView2.setText("Pass " + (position + 1) + " Time is " + Utils.getCurrentTime(passTimes.getRisetime()));
    }

    @Override
    public int getItemCount() {
        return passTimes.size();
    }

    public void updateAnswers(List<PassTime> passTimes) {
        this.passTimes = passTimes;
        notifyDataSetChanged();
    }

    private PassTime getItem(int adapterPosition) {
        return passTimes.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}