package com.example.mydemo.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mydemo.aidl.Book;
import com.example.mydemo.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2020-01-08
 * author: wuxiangyu.lc
 * describe:
 */
public class BookManagerService extends Service {
    private ArrayList<Book> mBookList = new ArrayList<>();
    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e("TAG", "add book; " + mBookList.size()+ "; " + Thread.currentThread().getName());
            mBookList.add(book);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "---->远程服务启动");
        mBookList.add(new Book(1, "book1"));
        mBookList.add(new Book(2, "book2"));
    }
}
