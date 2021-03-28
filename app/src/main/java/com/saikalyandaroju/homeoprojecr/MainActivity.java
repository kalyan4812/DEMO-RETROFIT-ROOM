package com.saikalyandaroju.homeoprojecr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saikalyandaroju.homeoprojecr.ADAPTER.CrewMemberAdapter;
import com.saikalyandaroju.homeoprojecr.NetworkService.ServiceGenerator;
import com.saikalyandaroju.homeoprojecr.POJOS.CrewResponse;
import com.saikalyandaroju.homeoprojecr.db.CrewMember;
import com.saikalyandaroju.homeoprojecr.db.MyDao;
import com.saikalyandaroju.homeoprojecr.db.MyDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //ui
    private RecyclerView recyclerView;

    // vars
    private CompositeDisposable disposables = new CompositeDisposable();
    private CrewMemberAdapter adapter;
    private List<CrewResponse> responses = new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayout;
    FloatingActionButton floatingActionButton;
    MyDao myDao;
    List<CrewMember> crewMembers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.refresh);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        myDao = MyDatabase.getDatabase(getApplicationContext()).myDao();
        recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();
        loadMembers();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_fab_icon));
                loadMembers();
                shimmerFrameLayout.startShimmer();
                shimmerFrameLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void loadMembers() {
        if (MyApplication.isInternetAvailable()) {
            loadFromServer();
        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
            loadFromLocalDatabase();
        }
    }

    private void loadFromLocalDatabase() {
        Log.i("check", "load from db");
        crewMembers.clear();
        myDao.getCrewMembers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CrewMember>>() {
                    @Override
                    public void accept(List<CrewMember> crewMembers) throws Exception {
                        responses.clear();
                        for (CrewMember crewMember : crewMembers) {
                            CrewResponse crewResponse = new CrewResponse(crewMember.getName(), crewMember.getAgency(), crewMember.getImageurl()
                                    , crewMember.getWikiurl(), crewMember.getStatus());
                            Log.i("check",crewMember.getImageurl());
                            responses.add(crewResponse);
                        }
                        //adapter.notifyDataSetChanged();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(),"LOADED FROM LOCAL DB",Toast.LENGTH_SHORT).show();

    }

    private void loadFromServer() {
        Log.i("check", "load from server");
        ServiceGenerator.getRequestApi().getUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<CrewResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(List<CrewResponse> myresponses) {
                Log.i("response", myresponses.size() + "");
                responses.clear();
                responses.addAll(myresponses);
                adapter.notifyDataSetChanged();
                performDbOperations(myresponses);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"LOADED FROM SERVER",Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void performDbOperations(final List<CrewResponse> myresponses) {
        crewMembers.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (CrewResponse crewResponse : myresponses) {
                    CrewMember crewMember = new CrewMember(crewResponse.getId(),crewResponse.getName(), crewResponse.getAgency(), crewResponse.getStatus(), crewResponse.getImage()
                            , crewResponse.getWikipedia());
                    crewMembers.add(crewMember);
                }
                clearOldData(crewMembers);
            }
        }).start();
    }

    private void clearOldData(final List<CrewMember> crewMembers) {

        myDao.deleteAllCrewMembers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                refreshData(crewMembers);
                Log.i("check",integer+"rows deleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("check",e.getMessage());
            }
        });
    }

    private void refreshData(List<CrewMember> crewMembers) {
        myDao.insertUser(crewMembers).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i("check", "insertion done");

            }

            @Override
            public void onError(Throwable e) {
                Log.i("check", e.getMessage());
            }
        });

    }

    private void initRecyclerView() {
        adapter = new CrewMemberAdapter(MainActivity.this, responses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cleardb:
               clearDb();
                Toast.makeText(this, "Cache Cleared", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearDb() {
        myDao.deleteAllCrewMembers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {

                Log.i("check",integer+"rows deleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("check",e.getMessage());
            }
        });
    }
}
