// IBookManager.aidl
package com.example.mydemo.aidl;

// Declare any non-default types here with import statements

import com.example.mydemo.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}