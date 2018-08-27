package com.example.abdullah.budgetary.ui.utils;

/**
 * Used for interacting between {@link com.example.abdullah.budgetary.ui.CustomDialogFragment} and child fragment shown to the user
 */
public interface DialogInteractionInterface  {

    /**
     * Notify the child fragment about cancellation of dialog
     */
    void onCancel();

    /**
     * Notify child about confirmation
     * @return true if dialog should close
     */
    boolean onConfirm();
}
