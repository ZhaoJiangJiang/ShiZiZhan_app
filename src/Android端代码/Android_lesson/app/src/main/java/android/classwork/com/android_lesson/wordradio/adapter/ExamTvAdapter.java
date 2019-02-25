package android.classwork.com.android_lesson.wordradio.adapter;

import android.classwork.com.android_lesson.R;
import android.classwork.com.android_lesson.util.vocb_list.OnItemClickListener;
import android.classwork.com.android_lesson.wordradio.entity.ExamTv;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 赵江江 on 2018/12/18.
 *
 * 考试音频的RecyclerView的适配器
 *
 */

public class ExamTvAdapter extends RecyclerView.Adapter<ExamTvAdapter.ViewHolder> {
    /** ExamTv列表 */
    private List<ExamTv> examTvs;
    /** item点击事件的回调接口 */
    private OnItemClickListener itemClickListener;

    public ExamTvAdapter(List<ExamTv> examTvs) {
        this.examTvs = examTvs;
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        itemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_tv_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //添加点击事件
        TextView examTitle = (TextView)viewHolder.itemView.findViewById(R.id.exam_tv_title);
        examTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemTvClick(view);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExamTv examTv = examTvs.get(position);
        holder.examTvTitle.setText(examTv.getTitle());
        holder.examTvTitle.setTag(examTv.getFileName());    //放了文件名这个数据
    }

    @Override
    public int getItemCount() {
        return examTvs.size();
    }

    /**
     * 内部ViewHolder类
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView examTvTitle;

        public ViewHolder(View view){
            super(view);
            examTvTitle = (TextView) view.findViewById(R.id.exam_tv_title);
        }
    }
}
