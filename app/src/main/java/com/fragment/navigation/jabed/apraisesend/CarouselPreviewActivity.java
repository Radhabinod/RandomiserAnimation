package com.fragment.navigation.jabed.apraisesend;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fragment.navigation.jabed.customlayout.CarouselLayoutManager;
import com.fragment.navigation.jabed.customlayout.CarouselZoomPostLayoutListener;
import com.fragment.navigation.jabed.customlayout.CenterScrollListener;


import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarouselPreviewActivity extends AppCompatActivity {

    public static int INVALID_POSITION = -1;
    Button btnRandomiser;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carousel_preview);
        Toolbar tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);
        btnRandomiser = (Button) findViewById(R.id.btnRandomiser);


        CarouselLayoutManager manager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        final HorizontalAdaptar adapter = new HorizontalAdaptar(this, manager);
        CustomRecyclerView rh = (CustomRecyclerView) findViewById(R.id.list_horizontal);
        initRecyclerView(rh, manager, adapter);

        rh.setClickable(false);
        rh.setFocusable(false);

    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final HorizontalAdaptar adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());

        btnRandomiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Random mRandom = new Random();
                final int min = 1;
                final int max = 30;
                final int random = mRandom.nextInt((max - min) + 1) + min;
                Log.e("random", "" + random);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(random);
                        // recyclerView.fling(-10, 0);
                        // handler.postDelayed(this, speedScroll);
                    }
                });
            }
        });

    }


    private static final class HorizontalAdaptar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @SuppressWarnings("UnsecureRandomNumberGeneration")
        private final Random mRandom = new Random();
        private final int[] mColors;
        private final int[] mPosition;
        CarouselLayoutManager manager;
        private Context context;
        private final int[] image = {
                R.drawable.f1,
                R.drawable.f2,
                R.drawable.f3,
                R.drawable.f4,
                R.drawable.f5,
                R.drawable.f6,
                R.drawable.f7,
                R.drawable.f1,
                R.drawable.f2,
                R.drawable.f3,
                R.drawable.f4,
                R.drawable.f5,
                R.drawable.f6,
                R.drawable.f7,
                R.drawable.f5,
                R.drawable.f6,
                R.drawable.f7,
        };
        private final String[] title = {
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",
                "Chin Sze Yen",
                "Prince",
                "John",
                "Jin Yean",
                "Chin Sze Yen",

        };

        LayoutInflater inflater;
        ArrayList<Integer> alImage = new ArrayList<>();
        ArrayList<String> alName = new ArrayList<>();

        HorizontalAdaptar(Context context, CarouselLayoutManager manager) {
            this.context = context;
            this.manager = manager;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mColors = new int[10];
            mPosition = new int[10];
            for (int i = 0; i < 100; i++) {
                alImage.add(image[i % image.length]);
                alName.add("Name" + i);
            }

            for (int i = 0; 10 > i; ++i) {
                //noinspection MagicNumber
                mColors[i] = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
                mPosition[i] = i;

            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.item_view, null);
            final RecyclerView.ViewHolder holder = new RowNewsViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            ((RowNewsViewHolder) holder).cItem1.setText(alName.get(position));
            ((RowNewsViewHolder) holder).pp.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), alImage.get(position), null));
            manager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
                @Override
                public void onCenterItemChanged(int adapterPosition) {
                    if (position == manager.getCenterItemPosition()) {
                        ((RowNewsViewHolder) holder).pp.setAlpha(1f);
                    } else {
                        ((RowNewsViewHolder) holder).pp.setAlpha(0.2f);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return alName.size();
        }
    }

    public static class RowNewsViewHolder extends RecyclerView.ViewHolder {
        TextView cItem1;
        CircleImageView pp;


        public RowNewsViewHolder(View itemView) {
            super(itemView);

            cItem1 = (TextView) itemView.findViewById(R.id.c_item_1);
            pp = (CircleImageView) itemView.findViewById(R.id.profilePicture);

        }
    }


}