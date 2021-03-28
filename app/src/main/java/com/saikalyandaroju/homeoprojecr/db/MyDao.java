package com.saikalyandaroju.homeoprojecr.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MyDao {
    @Insert
    Completable insertUser(List<CrewMember> crewMember);

    @Query("SELECT *  FROM CrewMembers")
    Flowable<List<CrewMember>> getCrewMembers();

    @Query("DELETE  FROM CrewMembers")
    Single<Integer>  deleteAllCrewMembers();
}
