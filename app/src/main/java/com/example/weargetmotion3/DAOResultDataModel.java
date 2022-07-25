package com.example.weargetmotion3;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOResultDataModel {
    private DatabaseReference databaseReference;

    public DAOResultDataModel() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(ResultDataModel.class.getSimpleName());
    }

    public Task<Void> add(ResultDataModel rdm){
        return databaseReference.push().setValue(rdm);
    }
}
