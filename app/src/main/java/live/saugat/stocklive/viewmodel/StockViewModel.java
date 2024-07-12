package live.saugat.stocklive.viewmodel;

import android.util.Log;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import live.saugat.stocklive.model.Stock;
import live.saugat.stocklive.network.StockApiService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StockViewModel {
    private static final int UPDATE_INTERVAL = 10000;
    private final StockApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final BehaviorSubject<List<Stock>> stocksSubject = BehaviorSubject.create();
    private static final List<String> DEFAULT_STOCKS = Arrays.asList("AAPL", "GOOGL", "MSFT", "TSLA", "NVDA", "META", "TSCO.L");
    private final BehaviorSubject<List<String>> filterSubject = BehaviorSubject.createDefault(DEFAULT_STOCKS);

    public void addStock(String stockName) {
        List<String> currentSymbols = new ArrayList<>(filterSubject.getValue());
        if (!currentSymbols.contains(stockName)) {
            currentSymbols.add(stockName.toUpperCase());
            filterSubject.onNext(currentSymbols);
        }
    }

    @Inject
    public StockViewModel(StockApiService stockApiService){
        this.apiService = stockApiService;
        startFetchingStocks();
    }

    private void startFetchingStocks(){
        disposables.add(
                Observable.interval(0, UPDATE_INTERVAL, TimeUnit.MILLISECONDS)
                        .withLatestFrom(filterSubject, (tick, filter) -> filter)
                        .flatMapSingle(this::fetchStocks)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                stocksSubject::onNext,
                                throwable -> Log.e("StockViewModel", "Error fetching stocks", throwable)
                        )
        );
    }
    private Single<List<Stock>> fetchStocks(List<String> symbols) {
        return apiService.getStocks(String.join(",", symbols));
    }
    public Observable<List<Stock>> getStocks(){
        return stocksSubject;
    }
    public void dispose(){
        disposables.clear();
    }
}
