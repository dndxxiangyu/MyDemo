// INewBookListener.aidl
package com.example.mydemo.aidl;

// Declare any non-default types here with import statements

import com.example.mydemo.aidl.Book;

interface INewBookListener {
    void onNewBookArrived(in Book newBook);
}
