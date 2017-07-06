package se.hellsoft.diffutilandrxjava;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.computation;

public class MainActivity extends AppCompatActivity {
    private WordAdapter adapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new WordAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listOfThings);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        List<WordEntity> emptyList = new ArrayList<>();
        adapter.setThings(emptyList);
        Pair<List<WordEntity>, DiffUtil.DiffResult> initialPair = Pair.create(emptyList, null);
        disposable = WordEntityRepository
                .latestThings(2, TimeUnit.SECONDS)
                .scan(initialPair, (pair, next) -> {
                    WordDiffCallback callback = new WordDiffCallback(pair.first, next);
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
                    return Pair.create(next, result);
                })
                .skip(1)
                .subscribeOn(computation())
                .observeOn(mainThread())
                .subscribe(listDiffResultPair -> {
                    adapter.setThings(listDiffResultPair.first);
                    listDiffResultPair.second.dispatchUpdatesTo(adapter);
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.dispose();
    }

}
