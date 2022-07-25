package com.example.weargetmotion3;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAORawDataModel {
    private DatabaseReference databaseReference;

    public DAORawDataModel(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(RawDataModel.class.getSimpleName());
    }

    public Task<Void> add(RawDataModel rdm){
        return databaseReference.push().setValue(rdm);
    }
}
