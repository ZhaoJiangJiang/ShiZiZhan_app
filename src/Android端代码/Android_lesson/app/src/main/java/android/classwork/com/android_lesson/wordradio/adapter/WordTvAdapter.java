package android.classwork.com.android_lesson.wordradio.adapter;

import android.classwork.com.android_lesson.R;
import android.classwork.com.android_lesson.util.vocb_list.OnItemClickListener;
import android.classwork.com.android_lesson.wordradio.entity.WordTv;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 赵江江 on 2018/12/18.
 * 单词电台的RecyclerView的适配器
 */

public class WordTvAdapter extends RecyclerView.Adapter<WordTvAdapter.ViewHolder>{
    /** WordTv列表 */
    private List<WordTv> wordTvs;
    /** item点击事件回调接口 */
    private OnItemClickListener itemClickListener;

    public WordTvAdapter(List<WordTv> wordTvs) {
        this.wordTvs = wordTvs;
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        itemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_tv_item, parent, false);
        final WordTvAdapter.ViewHolder viewHolder = new WordTvAdapter.ViewHolder(view);
        //添加点击事件
        TextView wordEnglish = (TextView)viewHolder.itemView.findViewById(R.id.word_tv_english);
        wordEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemTvClick(view);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WordTv wordTv = wordTvs.get(position);
        holder.wordEnglish.setText(wordTv.getEnglish());
        holder.wordEnglish.setTag(position);    //放了该项列表的下标
    }

    @Override
    public int getItemCount() {
        return wordTvs.size();
    }

    /**
     * 内部ViewHolder类
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView wordEnglish;

        public ViewHolder(View view){
            super(view);
            wordEnglish = (TextView) view.findViewById(R.id.word_tv_english);
        }
    }
}
