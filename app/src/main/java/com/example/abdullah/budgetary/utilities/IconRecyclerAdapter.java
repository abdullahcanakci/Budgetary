package com.example.abdullah.budgetary.utilities;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abdullah.budgetary.R;
import com.example.abdullah.budgetary.data.Icon;
import com.example.abdullah.budgetary.databinding.LayoutIconHolderBinding;
import com.example.abdullah.budgetary.ui.newcategory.NewCategoryViewModel;

import java.util.List;

public class IconRecyclerAdapter extends RecyclerView.Adapter<IconViewHolder> {
    private final NewCategoryViewModel model;
    private List<Icon> icons;
    private ImageView selectedIcon;

    public IconRecyclerAdapter(NewCategoryViewModel model) {
        this.model = model;
        this.setHasStableIds(true);

    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutIconHolderBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.layout_icon_holder,
                viewGroup,
                false);

        binding.imageView.setOnClickListener((view) -> {
            model.setIcon(binding.getIcon());
            if(selectedIcon != null)
                selectedIcon.getDrawable().setTint(0xFF7AADC7);
            selectedIcon = ((ImageView) binding.getRoot());
            selectedIcon.getDrawable().setTint(selectedIcon.getContext().getResources().getColor(R.color.colorAccent, null));
        });

        return new IconViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder iconViewHolder, int i) {
        iconViewHolder.bind(icons.get(i));
    }

    @Override
    public int getItemCount() {
        return icons == null ? 0 : icons.size();
    }

    public void setList(List<Icon> icons) {
        this.icons = icons;
        notifyDataSetChanged();
    }
}
