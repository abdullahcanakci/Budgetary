package com.example.abdullah.budgetary.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.ui.utils.DialogInteractionInterface;

public class CustomDialogFragment extends DialogFragment {
    private TextView titleView;
    private FrameLayout container;
    private Button buttonConfirm;
    private Button buttonCancel;
    private ImageButton buttonClose;
    private boolean isInitialized = false;

    private DialogInteractionInterface listener = null;

    private Integer titleResId;
    private Integer confirmTextResId;
    private Integer cancelTextResId;
    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
        if(this.fragment instanceof DialogInteractionInterface) {
            listener = ((DialogInteractionInterface) fragment);
        }
        if(!isInitialized) {
            updateContainer();
        }
    }

    public void setTitle(Integer resourceId) {
        if(resourceId == null)
            return;
        titleResId = resourceId;
        if(!isInitialized) {
            return;
        }
        titleView.setText(titleResId);
    }

    public void setButtons(Integer confirmResId, Integer cancelResId) {
        this.confirmTextResId = confirmResId;
        this.cancelTextResId = cancelResId;

        if(!isInitialized) {
            return;
        }

        if(confirmTextResId == null) {
            buttonConfirm.setVisibility(View.GONE);
        } else {
            buttonConfirm.setVisibility(View.VISIBLE);
            buttonConfirm.setText(this.confirmTextResId);
        }

        if (cancelTextResId == null) {
            buttonCancel.setVisibility(View.GONE);
        } else {
            buttonConfirm.setVisibility(View.VISIBLE);
            buttonCancel.setText(this.cancelTextResId);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        view.setPadding(16,16,16,16);

        buttonCancel = view.findViewById(R.id.dialog_button_cancel);
        buttonConfirm = view.findViewById(R.id.dialog_button_confirm);
        buttonClose = view.findViewById(R.id.dialog_button_close);
        this.container = view.findViewById(R.id.dialog_fragment_container);
        titleView = view.findViewById(R.id.dialog_title);
        bindButtons();
        isInitialized = true;
        fillViews();
        return view;
    }

    private void bindButtons() {
        buttonCancel.setOnClickListener((view -> {
            if(listener != null)
                listener.onCancel();
            this.dismiss();
        }));
        buttonClose.setOnClickListener((view -> {
           if(listener != null)
               listener.onCancel();
           this.dismiss();
        }));
        buttonConfirm.setOnClickListener(view -> {
            if(listener != null) {
                if(listener.onConfirm()) {
                    this.dismiss();
                } else {
                    return;
                }
            }
            this.dismiss();
        });

    }

    private void fillViews() {
        setTitle(this.titleResId);
        setButtons(this.confirmTextResId, this.cancelTextResId);
        updateContainer();

    }

    private void updateContainer() {
        int id = R.id.dialog_fragment_container;
        if(container != null) {
            try {
                if (getChildFragmentManager().findFragmentById(id) == null) {
                    getChildFragmentManager().beginTransaction().add(id, fragment).commit();
                } else {
                    getChildFragmentManager().beginTransaction().replace(id, fragment).commit();
                }
            } catch (NullPointerException ex) {
                Log.d("CustomDialogFragment", "updateContainer: findFragmentById threw error" + ex);
            }
        }
    }
}
