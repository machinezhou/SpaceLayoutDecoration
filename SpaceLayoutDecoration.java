package com.xuanyi.cqecar.ui.view.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by lawson on 16/6/22.
 */
public class SpaceLayoutDecoration extends RecyclerView.ItemDecoration {

  private static final String TAG = "SpaceLayoutDecoration";

  public static final int LINEAR_LAYOUT_VERTICAL = 0;
  public static final int LINEAR_LAYOUT_HORIZONTAL = 1;
  public static final int GRID_LAYOUT = 2;

  @Orientation private int type;

  private int linearSpace;
  private int gridSpace;
  private int headCut = -1;
  private int tailCut = -1;
  private int leftCut = -1;
  private int rightCut = -1;
  private int rowSpace;
  private int columnSpace;
  private int span;

  private SpaceLayoutDecoration(@Orientation int type, int span, int linearSpace, int gridSpace,
      int headCut, int tailCut, int leftCut, int rightCut, int rowSpace, int columnSpace) {
    this.type = type;
    this.linearSpace = linearSpace;
    this.gridSpace = gridSpace;
    this.headCut = headCut;
    this.tailCut = tailCut;
    this.leftCut = leftCut;
    this.rightCut = rightCut;
    this.rowSpace = rowSpace;
    this.columnSpace = columnSpace;
    this.span = span;
  }

  public void setSpan(int span) {
    if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
      throw new IllegalArgumentException("LinearLayout has no span.");
    }
    this.span = span;
  }

  public void setRowSpace(int rowSpace) {
    if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
      throw new IllegalArgumentException("LinearLayout has no rowSpace.");
    }
    this.rowSpace = rowSpace;
  }

  public void setColumnSpace(int columnSpace) {
    if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
      throw new IllegalArgumentException("LinearLayout has no columnSpace.");
    }
    this.columnSpace = columnSpace;
  }

  public void setLinearSpace(int space) {
    if (type == GRID_LAYOUT) {
      throw new IllegalArgumentException(
          "Should use setGridSpace instead with grid type, if you want to set the same space");
    }
    this.linearSpace = space;
  }

  public void setGridSpace(int space) {
    if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
      throw new IllegalArgumentException(
          "Should use setLinearSpace instead with linear type, if you want to set the same space");
    }
    this.gridSpace = space;
  }

  /**
   * cut the head space of recycler view
   *
   * @param headCut true make head space 0, false won't change
   */
  public void cutHeadSpace(int headCut) {
    this.headCut = headCut;
  }

  /**
   * cut the tail space of recycler view
   *
   * @param tailCut true make tail space 0, false won't change
   */
  public void cutTailSpace(int tailCut) {
    this.tailCut = tailCut;
  }

  /**
   * cut the left space of recycler view
   *
   * @param leftCut true make left space 0, false won't change
   */
  public void cutLeftSpace(int leftCut) {
    if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
      throw new IllegalArgumentException("left and right space setting only allow in grid layout");
    }
    this.leftCut = leftCut;
  }

  /**
   * cut the right space of recycler view
   *
   * @param rightCut true make right space 0, false won't change
   */
  public void cutRightSpace(int rightCut) {
    if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
      throw new IllegalArgumentException("left and right space setting only allow in grid layout");
    }
    this.rightCut = rightCut;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    int position = parent.getChildLayoutPosition(view);
    int lastPosition = parent.getAdapter().getItemCount() - 1;

    switch (type) {
      case LINEAR_LAYOUT_VERTICAL:
        layoutVertical(outRect, position, lastPosition);
        break;
      case LINEAR_LAYOUT_HORIZONTAL:
        layoutHorizontal(outRect, position, lastPosition);
        break;
      case GRID_LAYOUT:
        layoutGrid(outRect, position, lastPosition);
        break;
      default:
        throw new UnsupportedOperationException("unrecognizable type");
    }
  }

  private void layoutVertical(Rect outRect, int position, int lastPosition) {
    outRect.top = 0;
    outRect.bottom = linearSpace;
    if (headCut != -1 && position == 0) {
      outRect.top = 0;
      outRect.bottom = headCut;
    }
    if (tailCut != -1 && position == lastPosition) {
      outRect.top = 0;
      outRect.bottom = tailCut;
    }
  }

  private void layoutHorizontal(Rect outRect, int position, int lastPosition) {
    outRect.left = 0;
    outRect.right = linearSpace;
    if (headCut != -1 && position == 0) {
      outRect.left = 0;
      outRect.right = headCut;
    }
    if (tailCut != -1 && position == lastPosition) {
      outRect.left = 0;
      outRect.right = tailCut;
    }
  }

  private void layoutGrid(Rect outRect, int position, int lastPosition) {
    int r = rowSpace;
    int c = columnSpace;
    if (gridSpace != 0) {
      r = c = gridSpace;
    }
    outRect.top = r / 2;
    outRect.bottom = r / 2;
    outRect.left = c / 2;
    outRect.right = c / 2;

    if (position < span) {  // first row
      if (position == 0) {  //first column
        outRect.left = leftCut == -1 ? c : leftCut;
      }
      if (position == span - 1) { //last column
        outRect.right = rightCut == -1 ? c : rightCut;
      }
      if (headCut != -1) {
        outRect.top = headCut;
      } else {
        outRect.top = r;
      }
    } else {  //non-first row
      if (position % span == 0) { //first column
        outRect.left = leftCut == -1 ? c : leftCut;
      }
      if ((position + 1) % span == 0) { //last column
        outRect.right = rightCut == -1 ? c : rightCut;
      }
    }
    if (position >= span && lastPosition % span > 0 && lastPosition / span > 0 && (lastPosition
        - position <= lastPosition % span)) { //last row
      if (tailCut != -1) {
        outRect.bottom = tailCut;
      } else {
        outRect.bottom = r;
      }
    }
  }

  public static class Builder {

    @Orientation private int type;
    private int span;

    private int linearSpace;
    private int gridSpace;
    private int headCut = -1;
    private int tailCut = -1;
    private int leftCut = -1;
    private int rightCut = -1;
    private int rowSpace;
    private int columnSpace;

    public Builder(@Orientation int type) {
      this.type = type;
    }

    public Builder span(int span) {
      if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
        throw new IllegalArgumentException("LinearLayout has no span.");
      }
      this.span = span;
      return this;
    }

    public Builder linearSpace(int space) {
      if (type == GRID_LAYOUT) {
        throw new IllegalArgumentException(
            "Should use setGridSpace instead, if you want to set the same space in grid type");
      }
      this.linearSpace = space;
      return this;
    }

    public Builder gridSpace(int space) {
      if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
        throw new IllegalArgumentException(
            "Should use setLinearSpace instead , if you want to set the same space in linear type");
      }
      this.gridSpace = space;
      return this;
    }

    /**
     * only for grid layout
     */
    public Builder rowSpace(int rowSpace) {
      if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
        throw new IllegalArgumentException("LinearLayout has no rowSpace.");
      }
      this.rowSpace = rowSpace;
      return this;
    }

    /**
     * only for grid layout
     */
    public Builder columnSpace(int columnSpace) {
      if (type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL) {
        throw new IllegalArgumentException("LinearLayout has no columnSpace.");
      }
      this.columnSpace = columnSpace;
      return this;
    }

    public Builder headCut(int headCut) {
      this.headCut = headCut;
      return this;
    }

    public Builder tailCut(int tailCut) {
      this.tailCut = tailCut;
      return this;
    }

    /**
     * only for grid layout
     */
    public Builder leftCut(int leftCut) {
      this.leftCut = leftCut;
      return this;
    }

    /**
     * only for grid layout
     */
    public Builder rightCut(int rightCut) {
      this.rightCut = rightCut;
      return this;
    }

    public SpaceLayoutDecoration build() {
      if (type == GRID_LAYOUT && gridSpace == 0 && rowSpace == 0 && columnSpace == 0) {
        Log.d(TAG, "Should set gridSpace, or rowSpace and columnSpace");
      }
      if ((type == LINEAR_LAYOUT_VERTICAL || type == LINEAR_LAYOUT_HORIZONTAL)
          && linearSpace == 0) {
        Log.d(TAG, "Should set linearSpace");
      }
      return new SpaceLayoutDecoration(type, span, linearSpace, gridSpace, headCut, tailCut,
          leftCut, rightCut, rowSpace, columnSpace);
    }
  }
}
