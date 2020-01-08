package com.example.mydemo.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mydemo.aidl.Book;
import com.example.mydemo.aidl.IBookManager;
import com.example.mydemo.aidl.INewBookListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 创建时间：2020-01-08
 * author: wuxiangyu.lc
 * describe:
 */
public class BookManagerService extends Service {
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<INewBookListener> remoteList = new RemoteCallbackList<>();
    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e("TAG", "add book; " + mBookList.size() + "; " + Thread.currentThread().getName());
            mBookList.add(book);
            int listenerLength = remoteList.beginBroadcast();
            for (int i = 0; i < listenerLength; i++) {
                remoteList.getBroadcastItem(i).onNewBookArrived(book);
            }
            remoteList.finishBroadcast();
        }

        @Override
        public void registerListener(INewBookListener listener) throws RemoteException {
            remoteList.register(listener);
        }

        @Override
        public void unregisterListener(INewBookListener listener) throws RemoteException {
            remoteList.unregister(listener);
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
