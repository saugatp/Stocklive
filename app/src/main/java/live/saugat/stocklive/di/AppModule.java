package live.saugat.stocklive.di;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import live.saugat.stocklive.network.StockApiService;
import live.saugat.stocklive.viewmodel.StockViewModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class AppModule {
    private final Application application;
    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(){
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient httpClient){
        return new Retrofit.Builder()
                .baseUrl("https://saugat45.pythonanywhere.com/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    @Singleton
    StockApiService apiService(Retrofit retrofit){
        return retrofit.create(StockApiService.class);
    }
    @Provides
    @Singleton
    StockViewModel provideStockViewModel(StockApiService apiService){
        return new StockViewModel(apiService);
    }
}
