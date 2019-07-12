package com.example.diaryapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.diaryapp.databaseTemplate.entrriesMainTable;

public class MainActivityRecyclerviewAdapter extends RecyclerView.Adapter<MainActivityRecyclerviewAdapter.ViewHolder> {

    private  Context mContext;
    private  LayoutInflater mlayoutInflater ;
    private  Cursor mCursor;
    private  int subjectPos=0;
    private  int titlePos=0;
    private  int datePos = 0;
    private int idPos=0;




    public MainActivityRecyclerviewAdapter(@NonNull Context context, Cursor cursor) {
        mContext= context;
        mCursor= cursor;
        mlayoutInflater=LayoutInflater.from(mContext);

        getCursorPosition();

    }

    void getCursorPosition(){
        if(mCursor == null)
            return;
        subjectPos = mCursor.getColumnIndex(entrriesMainTable.SUBJECT_COLUMS);
        titlePos = mCursor.getColumnIndex(entrriesMainTable.TITLE_COLUMN);
        idPos= mCursor.getColumnIndex(entrriesMainTable._ID);
        datePos= mCursor.getColumnIndex(entrriesMainTable.DATE_COLOMN);


    }
    void cursorChange(Cursor cursor){
        if(mCursor!=cursor && mCursor!=null)
            mCursor.close();
        mCursor=cursor;
        getCursorPosition();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= mlayoutInflater.inflate(R.layout.individual_items, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        mCursor.moveToPosition(i);
        int pos = mCursor.getInt(idPos);
        viewHolder.textSubject.setText(mCursor.getString(subjectPos));
          viewHolder.textTitle.setText(mCursor.getString(titlePos));
          viewHolder.textDate.setText(mCursor.getString(datePos));




          viewHolder.id = pos;

    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0:mCursor.getCount() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textSubject;
            TextView textTitle;
            TextView textDate;
            int id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubject= itemView.findViewById(R.id.textView6);
            textTitle= itemView.findViewById(R.id.textView7);
            textDate=itemView.findViewById(R.id.textView8);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, activity_note.class);
                    intent.putExtra(activity_note.ENTRY, id);

                    mContext.startActivity(intent);

                }
            });
        }
    }
}
