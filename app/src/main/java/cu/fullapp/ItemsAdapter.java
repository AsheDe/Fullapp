package cu.fullapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemHolder> {
    private List<Item> items, selectedItems;
    private ItemHolder.OnItemClickListener itemClickListener;
    private boolean multipleFileSelectionEnabled;

    ItemsAdapter(ItemHolder.OnItemClickListener itemClickListener, boolean multipleFileSelectionEnabled) {
        this.itemClickListener = itemClickListener;
        items = new ArrayList<>();
        this.multipleFileSelectionEnabled = multipleFileSelectionEnabled;
        if (multipleFileSelectionEnabled) {
            selectedItems = new ArrayList<>();
        }
    }

    void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemHolder(inflater.inflate(R.layout.list_item, parent, false), itemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Item item = items.get(position);
        if (item.isFile() && multipleFileSelectionEnabled) {
            holder.bind(items.get(position), selectedItems);
        } else {
            holder.bind(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
