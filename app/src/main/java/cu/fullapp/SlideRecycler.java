package cu.fullapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by yudel on 28/06/2017.
 */
public class SlideRecycler extends HorizontalScrollView  {
    RecyclerView mRecycler = null;
    public SlideRecycler(Context context) {
        super(context);
        setHorizontalScrollBarEnabled(true);
           setFillViewport(false);

    }

    public void setRecycler(RecyclerView rec)
    {
        mRecycler = rec;
        mRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }




}
