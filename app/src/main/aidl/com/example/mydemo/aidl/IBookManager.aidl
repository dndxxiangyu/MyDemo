// IBookManager.aidl
package com.example.mydemo.aidl;

// Declare any non-default types here with import statements

import com.example.mydemo.aidl.Book;
import com.example.mydemo.aidl.INewBookListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

    void registerListener(INewBookListener listener);

    void unregisterListener(INewBookListener listener);
}