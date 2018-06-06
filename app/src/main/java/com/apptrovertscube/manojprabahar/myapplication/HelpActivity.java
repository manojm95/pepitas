package com.apptrovertscube.manojprabahar.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyo.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends Activity {

    private static final int TYPE_IMAGE_VIEW = 0;
    private static final int TYPE_TEXT_VIEW = 1;
    private List<Item> mItems;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    String source;
    ImageView goback;
    String[] answers;
    String [] questions;
    int rn=0;
    int rtoa=0;
    private boolean aboutflag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_help);

//        if(Constants.type== Constants.Type.FREE)
//        {
//            Realm realm = Realm.getDefaultInstance();
//            RealmResults<RealmNote> realmImages = realm.where(RealmNote.class)
//                    .equalTo("userlogin", LoginActivity.parseemail)
//                    .findAll();
//            rn=realmImages.size();
//            RealmResults<RealmTrashArchive> realmImages1 = realm.where(RealmTrashArchive.class)
//                    .equalTo("userlogin", LoginActivity.parseemail)
//                    .findAll();
//            rtoa = realmImages1.size();
//            realm.close();
//        }
//        else
//        {
//            Realm realm = Realm.getDefaultInstance();
//            RealmResults<RealmNote> realmImages = realm.where(RealmNote.class)
//                    .equalTo("userlogin", LoginActivity.parseemail)
//                    .findAll();
//            rn=realmImages.size();
//            RealmResults<RealmTrashArchive> realmImages1 = realm.where(RealmTrashArchive.class)
//                    .equalTo("userlogin", LoginActivity.parseemail)
//                    .findAll();
//            rtoa = realmImages1.size();
//            realm.close();
//            ParseQuery<ParseObject> taquery = ParseQuery.getQuery("ParseTrashArchive");
//            taquery.setLimit(1000);
//            taquery.findInBackground(new FindCallback<ParseObject>() {
//                @Override
//                public void done(List<ParseObject> objects, ParseException e) {
//                    if(e==null)
//                    {
//                        rtoa=objects.size();
//                        ParseQuery<ParseObject> notequery = ParseQuery.getQuery("ParseNote");
//                        notequery.setLimit(1000);
//                        notequery.findInBackground(new FindCallback<ParseObject>() {
//                            @Override
//                            public void done(List<ParseObject> objects, ParseException e) {
//                                if(e==null)
//                                {
//                                    rn=objects.size();
//                                }
//                            }
//                        });
//
//                    }
//                }
//            });
//        }

        //aboutflag = getIntent().getStringExtra("about").equals("about");

        answers = getResources().getStringArray(R.array.answers);
        questions = getResources().getStringArray(R.array.questions);

        //source = getIntent().getStringExtra("source");
        goback = (ImageView) findViewById(R.id.gobackhelp);

        goback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(source.equals("0")) {
//                    startActivity(new Intent(HelpActivity.this, MainActivity.class));
//                    finish();
//                } else
//                if(source.equals("1")) {
//                    Intent intent = new Intent(HelpActivity.this, NotesActivity.class);
//                    intent.putExtra("viewpagerselect","0");
//                    startActivity(intent);
//                    finish();
//                } else
//                if(source.equals("2")) {
//                    Intent intent = new Intent(HelpActivity.this, NotesActivity.class);
//                    intent.putExtra("viewpagerselect","1");
//                    startActivity(intent);
//                    finish();
//                } else
//                if(source.equals("3")) {
//                    Intent intent = new Intent(HelpActivity.this, NotesActivity.class);
//                    intent.putExtra("viewpagerselect","2");
//                    startActivity(intent);
//                    finish();
//                } else
//                if(source.equals("4")) {
//                    Intent intent = new Intent(HelpActivity.this, NotesActivity.class);
//                    intent.putExtra("viewpagerselect","3");
//                    startActivity(intent);
//                    finish();
//                }
//                else
//                {
                startActivity(new Intent(HelpActivity.this, MainActivity.class));
                finish();
//                }
            }
        });

        mItems = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            mItems.add(new Item());
        }
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof MyViewHolder) {
                MyViewHolder holder = (MyViewHolder) v.getTag();
                boolean result = holder.expandableLayout.toggleExpansion();
                Item item = mItems.get(holder.getAdapterPosition());
                item.isExpand = result ? !item.isExpand : item.isExpand;
            } else if (v.getTag() instanceof TextViewHolder) {
                TextViewHolder holder = (TextViewHolder) v.getTag();
                boolean result = holder.expandableLayout.toggleExpansion();
                Item item = mItems.get(holder.getAdapterPosition());
                item.isExpand = result ? !item.isExpand : item.isExpand;
            }
        }
    };

    private ExpandableLayout.OnExpandListener mOnExpandListener = new ExpandableLayout.OnExpandListener() {

        private boolean isScrollingToBottom = false;

        @Deprecated
        @Override
        public void onToggle(ExpandableLayout view, View child,
                             boolean isExpanded) {
        }

        @Override
        public void onExpandOffset(ExpandableLayout view, View child,
                                   float offset, boolean isExpanding) {
            if (view.getTag() instanceof MyViewHolder) {
                final MyViewHolder holder = (MyViewHolder) view.getTag();
                if (holder.getAdapterPosition() == mItems.size() - 1) {
                    if (!isScrollingToBottom) {
                        isScrollingToBottom = true;
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isScrollingToBottom = false;
                                mRecyclerView.scrollToPosition(holder
                                        .getAdapterPosition());
                            }
                        }, 100);
                    }
                }
            }
        }
    };

    private class MyAdapter extends
            RecyclerView.Adapter<ViewHolder> {
        private List<Item> items;

        public MyAdapter(List<Item> infos) {
            this.items = infos;
        }

        @Override
        public int getItemCount() {
            return 17;
        }

        @Override
        public int getItemViewType(int position) {
//            if(aboutflag==false)
//            {
//                if (position==0 || position==1) return 767;
//            }
            if (position == 0 || position== 6 || position== 12) {
                return TYPE_IMAGE_VIEW;
            } else {
                return TYPE_TEXT_VIEW;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            if (viewType == TYPE_IMAGE_VIEW) {
                LayoutInflater inflater = LayoutInflater.from(parent
                        .getContext());
                View itemView = inflater.inflate(R.layout.item_listview,
                        parent, false);
                MyViewHolder holder = new MyViewHolder(itemView);
                holder.imageView.setOnClickListener(mOnClickListener);
                holder.imageView.setTag(holder);
                holder.expandableLayout.setTag(holder);
                holder.expandableLayout.setOnExpandListener(mOnExpandListener);
                return holder;
            } else if(viewType == TYPE_TEXT_VIEW){
                LayoutInflater inflater = LayoutInflater.from(parent
                        .getContext());
                View itemView = inflater.inflate(
                        R.layout.item_expandable_textview, parent, false);
                TextViewHolder holder = new TextViewHolder(itemView);
                holder.textView.setOnClickListener(mOnClickListener);
                holder.textView.setTag(holder);
                holder.expandableLayout.setTag(holder);
                holder.expandableLayout.setOnExpandListener(mOnExpandListener);
                return holder;
            }else
            {
                LayoutInflater inflater = LayoutInflater.from(parent
                        .getContext());
                View itemView = inflater.inflate(
                        R.layout.item_empty_view, parent, false);
                Empty holder = new Empty(itemView);
                holder.empty.setTag(holder);
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            //Log.d("nm000--->",position+"");
            if (holder instanceof MyViewHolder) {
                // Log.d("nm111--->",position+"");
                MyViewHolder viewHolder = (MyViewHolder) holder;
                if(position==0)
                {
                    viewHolder.imageView.setImageResource(R.drawable.ic_information);
                    viewHolder.tv.setText("Help");
                }
                else if(position==6)
                {
                    viewHolder.imageView.setImageResource(R.drawable.ic_settings_forhelp);
                    viewHolder.tv.setText("Settings(In Options/Menu)");
                }
                else if(position==12) {viewHolder.imageView.setImageResource(R.drawable.ic_settings_help);
                    viewHolder.tv.setText("TroubleShoot");}
                else if(position==13) {viewHolder.imageView.setImageResource(R.drawable.ic_limitation);
                    viewHolder.tv.setText("Constraints");}
//                else if(position==27) {viewHolder.imageView.setImageResource(R.drawable.ic_settings_help);
//                    viewHolder.tv.setText("TroubleShoot");}
//                else if(position==34) {viewHolder.imageView.setImageResource(R.drawable.ic_limitation);
//                    viewHolder.tv.setText("Constraints");}
                Item item = items.get(position);
                viewHolder.expandableLayout.setExpanded(item.isExpand, false);
            } else if(holder instanceof TextViewHolder) {
                //   Log.d("nm222--->",position+"");
                TextViewHolder viewHolder = (TextViewHolder) holder;
                Item item = items.get(position);
                viewHolder.expandableLayout.setExpanded(item.isExpand, false);
                viewHolder.textView
                        .setText(questions[position]);

                viewHolder.expandableTextView
                        .setText(answers[position]);
                if(position==36)
                {
                    String a = "For categories description please check previous question \n\n"+"Notes in Category A: "+ rn+" \n" +"Notes in Category B: "+rtoa;
                    viewHolder.expandableTextView
                            .setText(a);
                }
            }

        }
    }

    static class MyViewHolder extends ViewHolder {
        ExpandableLayout expandableLayout;
        ImageView imageView;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            expandableLayout = (ExpandableLayout) itemView
                    .findViewById(R.id.expandablelayout);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            tv = (TextView)itemView.findViewById(R.id.tvinimage);
        }
    }

    static class TextViewHolder extends ViewHolder {
        ExpandableLayout expandableLayout;
        TextView textView;
        TextView expandableTextView;
        ImageView iv;

        public TextViewHolder(View itemView) {
            super(itemView);
            expandableLayout = (ExpandableLayout) itemView
                    .findViewById(R.id.expandablelayout);
            textView = (TextView) itemView.findViewById(R.id.textview);
            iv = (ImageView) itemView.findViewById(R.id.ivintextview);
            expandableTextView = (TextView) itemView
                    .findViewById(R.id.expandable_textview);
        }
    }

    static class Empty extends ViewHolder {
        LinearLayout empty;

        public Empty(View itemView) {
            super(itemView);
            empty = (LinearLayout) itemView
                    .findViewById(R.id.emptyll);
        }
    }

    static class Item {
        boolean isExpand;
    }
}
