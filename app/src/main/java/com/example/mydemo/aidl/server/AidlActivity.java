package com.example.mydemo.aidl.server;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.mydemo.R;
import com.example.mydemo.aidl.Book;
import com.example.mydemo.aidl.IBookManager;
import com.example.mydemo.aidl.INewBookListener;

import java.util.List;

/**
 * 创建时间：2020-01-08
 * author: wuxiangyu.lc
 * describe:
 */
public class AidlActivity extends AppCompatActivity {
    IBookManager bookManager;
    private INewBookListener listener = new INewBookListener() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookManager != null) {
                    try {
                        bookManager.registerListener(new INewBookListener.Stub() {
                            @Override
                            public void onNewBookArrived(Book newBook) throws RemoteException {
                                Log.e("AIDL", "add book---->book:" + newBook.toString());
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        findViewById(R.id.btnUnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.btnAddBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 添加书籍
                try {
                    if (bookManager != null) {
                        bookManager.addBook(new Book(3, "book3"));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btnBinder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AidlActivity.this, BookManagerService.class);
                bindService(intent, cnn, BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.btnUnBinder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(cnn);
            }
        });
    }

    private ServiceConnection cnn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                // 添加书籍
                Log.e("AIDL", "---->book:" + Thread.currentThread().getName());
                bookManager.addBook(new Book(3, "book3"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                // 获取书籍
                List<Book> bookList = bookManager.getBookList();
                for (Book book : bookList) {
                    Log.e("AIDL", "---->book:" + book.toString());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
