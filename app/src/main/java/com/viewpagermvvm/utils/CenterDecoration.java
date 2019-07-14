package com.viewpagermvvm.utils;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class CenterDecoration extends RecyclerView.ItemDecoration {
    private float firstViewWidth = -1;
    private float lastViewWidth = -1;
    private int spacing;

    public CenterDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int adapterPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition;
        int adapterPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        CenterZoomLayoutManager lm = (CenterZoomLayoutManager) parent.getLayoutManager();
        if (adapterPosition == 0) {
            // Invalidate decorations when this view width has changed
            if (view.getWidth() != firstViewWidth) {
//                view.addon
//                view.doOnPreDraw { parent.invalidateItemDecorations() }
            }
            firstViewWidth = view.getWidth();
            outRect.left = parent.getWidth() / 2 - view.getWidth() / 2;

            // If we have more items, use the spacing provided
            if (lm.getItemCount() > 1) {
                outRect.right = spacing / 2;
            } else {
                // Otherwise, make sure this to fill the whole width with the decoration
                outRect.right = outRect.left;
            }
        } else if (adapterPosition == lm.getItemCount() - 1) {

            // Invalidate decorations when this view width has changed
            if (view.getWidth() != lastViewWidth) {
//                view.On
//                view.doOnPreDraw {
//                    parent.invalidateItemDecorations()
//                }
            }
            lastViewWidth = view.getWidth();
            outRect.right = parent.getWidth() / 2 - view.getWidth() / 2;
            outRect.left = spacing / 2;
        } else {
            outRect.left = spacing / 2;
            outRect.right = spacing / 2;
        }
    }
}
