package live.saugat.stocklive.ui;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import live.saugat.stocklive.R;
import live.saugat.stocklive.StockApplication;
import live.saugat.stocklive.viewmodel.StockViewModel;
import live.saugat.stocklive.model.Stock;

import javax.inject.Inject;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements FilterBottomSheet.FilterListener{
    @Inject
    StockViewModel viewModel;
    private CompositeDisposable disposables = new CompositeDisposable();
    private StocksListAdapter stocksListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((StockApplication) getApplication()).getAppComponent().inject(this);
        RecyclerView stocksListView = findViewById(R.id.stockListView);
        stocksListView.setLayoutManager(new LinearLayoutManager(this));
        stocksListAdapter = new StocksListAdapter();
        stocksListView.setAdapter(stocksListAdapter);

        disposables.add(viewModel.getStocks()
                .subscribe(
                        this::updateStocksDisplay,
                        throwable -> Log.e("MainActivity", "Error updating stocks", throwable)
                ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            showFilterDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStocksDisplay(List<Stock> stocks) {
        stocksListAdapter.setStocks(stocks);
    }
    private void showFilterDialog() {
        FilterBottomSheet bottomSheet = new FilterBottomSheet();
        bottomSheet.setListener(this);
        bottomSheet.show(getSupportFragmentManager(), "FilterBottomSheet");
    }

    @Override
    public void onFilterApplied(String filter) {
        viewModel.addStock(filter);
    }

    @Override
    protected void onDestroy() {
        viewModel.dispose();
        super.onDestroy();
    }
}