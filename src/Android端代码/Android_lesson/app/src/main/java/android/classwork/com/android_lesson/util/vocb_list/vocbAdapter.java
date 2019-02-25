package android.classwork.com.android_lesson.util.vocb_list;

import android.classwork.com.android_lesson.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 赵江江 on 2018/12/2.
 */

public class vocbAdapter extends RecyclerView.Adapter<vocbAdapter.ViewHolder> implements View.OnClickListener{

    private List<vocbInfo> mList;
    private OnItemClickListener itemClickListener;


    public void setItemClickListener(OnItemClickListener onItemClickListener){
        itemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.vocb_item_chinese:
                itemClickListener.onItemTvClick(view);
                break;
            case R.id.vocb_item_detail:
                itemClickListener.onItemBtnClick(view);
                break;
            default:
                break;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocb_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        // 中文部分的点击事件
        TextView tv_Chinese = (TextView) viewHolder.itemView.findViewById(R.id.vocb_item_chinese);
        tv_Chinese.setOnClickListener(this);

        // 跳转按钮的点击事件
        Button Btn_ToDetail = (Button) viewHolder.itemView.findViewById(R.id.vocb_item_detail);
        Btn_ToDetail.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        vocbInfo info = mList.get(position);
        holder.setChinese.setText(info.getChinese());
        holder.setEnglish.setText(info.getEnglish());
        holder.toDetail.setTag(info.getEnglish());  // 传给 单词详情 界面的信息
    }

    

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //内部ViewHolder类
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView setEnglish;
        TextView setChinese;
        Button toDetail;
        public ViewHolder(View view){
            super(view);
            setEnglish = (TextView) view.findViewById(R.id.vocb_item_english);
            setChinese = (TextView) view.findViewById(R.id.vocb_item_chinese);
            toDetail = (Button) view.findViewById(R.id.vocb_item_detail);
        }
    }

    public vocbAdapter(List<vocbInfo> List){
        this.mList = List;
    }
}
