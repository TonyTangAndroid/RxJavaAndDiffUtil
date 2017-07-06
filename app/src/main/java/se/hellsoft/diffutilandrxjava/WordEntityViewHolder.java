package se.hellsoft.diffutilandrxjava;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ztang on 7/6/17.
 */
class WordEntityViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public WordEntityViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
    }

    public void bind(WordEntity thing) {
        itemView.setBackgroundColor(thing.getColor());
        textView.setText(thing.getText());
    }
}
