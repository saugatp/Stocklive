package live.saugat.stocklive.di;

import dagger.Component;
import live.saugat.stocklive.ui.HomeActivity;
import live.saugat.stocklive.viewmodel.StockViewModel;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(HomeActivity activity);
    StockViewModel stockViewModel();
}
