package live.saugat.stocklive.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import live.saugat.stocklive.R;
import live.saugat.stocklive.model.Stock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StocksListAdapter extends RecyclerView.Adapter<StocksListAdapter.StockViewHolder> {
    private List<Stock> stocks = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public StocksListAdapter.StockViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stock_display, viewGroup, false);
        return new StockViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull @NotNull StocksListAdapter.StockViewHolder viewHolder, int i) {
        Stock stock = stocks.get(i);
        viewHolder.stockSymbol.setText(stock.getSymbol());
        viewHolder.stockPrice.setText(String.format("$%.2f", stock.getPrice()));
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void setStocks(List<Stock> stocks){
        this.stocks = stocks;
        notifyDataSetChanged();
    }
    public class StockViewHolder extends RecyclerView.ViewHolder {
        private TextView stockSymbol;
        private TextView stockPrice;
        public StockViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            stockSymbol = itemView.findViewById(R.id.stockSymbol);
            stockPrice = itemView.findViewById(R.id.stockPrice);
        }
    }
}
