package live.saugat.stocklive;

import android.app.Application;
import live.saugat.stocklive.di.AppComponent;
import live.saugat.stocklive.di.AppModule;
import live.saugat.stocklive.di.DaggerAppComponent;

public class StockApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
