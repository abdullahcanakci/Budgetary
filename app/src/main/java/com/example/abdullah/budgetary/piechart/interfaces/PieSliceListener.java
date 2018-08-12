package com.example.abdullah.budgetary.piechart.interfaces;

import com.example.abdullah.budgetary.piechart.PieSlice;

/**
 * Interface for listening PieSliceNotifications.
 */
public interface PieSliceListener {
    /**
     *
     * @param slice that is clicked
     * @return if the view should return to the original state
     */
    boolean onClickPieSlice(PieSlice slice);

    void sliceAnimationEnded();
}
