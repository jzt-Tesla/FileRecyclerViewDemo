package com.baoda.vending.filedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //todo FILE_PATH :这个是你需要获取文件的地址，自己来定义
    String FILE_PATH = "/storage/emulated/0/Android/logs";
    private ArrayList<File> fileArrayList = new ArrayList<>();
    private ArrayList<String> fileNameArrayList = new ArrayList<>();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processExtraData();
    }

    private void processExtraData() {
        RecyclerView uploadLogsRecycleView = findViewById(R.id.fragment_upload_logs);
        File file = new File(FILE_PATH);
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        //获取文件列表内的所有文件名称的集合
        for (File i : files) {
            fileArrayList.add(i);
            fileNameArrayList.add(i.getName());
        }
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager
                (mContext, LinearLayoutManager.VERTICAL, false);
        uploadLogsRecycleView.setLayoutManager(mLinearLayoutManager);
        UploadLogsAdapter adapter = new UploadLogsAdapter(fileNameArrayList, mContext);
        uploadLogsRecycleView.setAdapter(adapter);
    }

    private class UploadLogsAdapter extends RecyclerView.Adapter {
        ArrayList<String> mList = new ArrayList<>();
        private Context context;

        private UploadLogsAdapter(ArrayList<String> mList, Context context) {
            this.mList = mList;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = null;
            View view = View.inflate(context, R.layout.item_file, null);
            //第二种获取item的View的方法，不需要传入context
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            ((MyViewHolder) holder).fileNameTextView.setText(mList.get(position));
            ((MyViewHolder) holder).detailTextView.setText(String.format(getResources().getString
                    (R.string.text_file_size), getReadableFileSize(fileArrayList.get(position).length())));

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView fileNameTextView;
            private TextView detailTextView;

            MyViewHolder(View itemView) {
                super(itemView);
                fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
                detailTextView = itemView.findViewById(R.id.fileDetailTextView);
            }
        }
    }

    private String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
