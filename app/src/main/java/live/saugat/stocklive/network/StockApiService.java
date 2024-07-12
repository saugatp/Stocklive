package live.saugat.stocklive.network;
import io.reactivex.rxjava3.core.Single;
import live.saugat.stocklive.model.Stock;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface StockApiService {
    @GET("stock_prices")
    Single<List<Stock>> getStocks(@Query("symbols") String symbols);
}
