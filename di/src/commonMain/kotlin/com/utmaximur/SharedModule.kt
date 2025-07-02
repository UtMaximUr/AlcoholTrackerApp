package com.utmaximur

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.utmaximur.actions.ActionImageComponentModule
import com.utmaximur.alcohol_calculator.AlcoholCalculatorMainComponentModule
import com.utmaximur.app.base.BaseModule
import com.utmaximur.calculator.CalculatorMainComponentModule
import com.utmaximur.calendar.CalendarComponentModule
import com.utmaximur.calendar.CalendarNavigationComponentModule
import com.utmaximur.confirmDialog.ConfirmDialogMainComponentModule
import com.utmaximur.connection.NetworkConnectionModule
import com.utmaximur.core.logging.LoggerModule
import com.utmaximur.createDrink.CreateDrinkMainComponentModule
import com.utmaximur.createDrink.CreateDrinkNavigationComponentModule
import com.utmaximur.createTrack.CreateTrackMainComponentModule
import com.utmaximur.createTrack.CreateTrackNavigationComponentModule
import com.utmaximur.crm_tracker.FirebaseTrackerAnalyticsModule
import com.utmaximur.currency.CurrencyComponentModule
import com.utmaximur.data.actions.ImageActionsDataModule
import com.utmaximur.data.base_remote.RemoteModule
import com.utmaximur.data.calculator.CalculatorDataModule
import com.utmaximur.data.calendar.CalendarDataModule
import com.utmaximur.data.confirmDialog.ConfirmDialogDataModule
import com.utmaximur.data.createDrink.CreateDrinkDataModule
import com.utmaximur.data.createTrack.CreateTrackDataModule
import com.utmaximur.data.datePicker.DatePickerDataModule
import com.utmaximur.data.detailTrack.DetailTrackDataModule
import com.utmaximur.data.drinks.DrinkManagerModule
import com.utmaximur.data.geocoder.GeocoderDataModule
import com.utmaximur.data.kandinsky.KandinskyDataModule
import com.utmaximur.data.map.MapDataModule
import com.utmaximur.data.settings.SettingsDataModule
import com.utmaximur.data.sortingDrinks.SortingDrinksDataModule
import com.utmaximur.data.splash_screen.SplashScreenDataModule
import com.utmaximur.data.statistic.StatisticDataModule
import com.utmaximur.data.tracks.TrackManagerModule
import com.utmaximur.data.tracksModal.TracksModalDataModule
import com.utmaximur.databaseRoom.RoomDataBaseModule
import com.utmaximur.datePicker.DatePickerMainComponentModule
import com.utmaximur.day.StatisticDayComponentModule
import com.utmaximur.detailTrack.DetailTrackMainComponentModule
import com.utmaximur.detailTrack.DetailTrackNavigationComponentModule
import com.utmaximur.domain.root.RootDomainModule
import com.utmaximur.drink.StatisticDrinkComponentModule
import com.utmaximur.geocoder.GeocoderMainComponentModule
import com.utmaximur.kandinsky.KandinskyScreenComponentModule
import com.utmaximur.map.MapComponentModule
import com.utmaximur.map.MapNavigationComponentModule
import com.utmaximur.mappers.implementation.ProjectImplementationModule
import com.utmaximur.message.MessageDataModule
import com.utmaximur.money.StatisticMoneyComponentModule
import com.utmaximur.root.RootComponentModule
import com.utmaximur.settings.SettingsMainComponentModule
import com.utmaximur.settings.SettingsNavigationComponentModule
import com.utmaximur.settingsManager.SettingsManagerDataModule
import com.utmaximur.sortingDrinks.SortingDrinksMainComponentModule
import com.utmaximur.splash.SplashScreenComponentModule
import com.utmaximur.statistic.StatisticMainComponentModule
import com.utmaximur.tracker.TrackerAnalyticsModule
import com.utmaximur.tracksModal.TracksModalMainComponentModule
import com.utmaximur.widget.AppWidgetModule
import com.utmaximur.yandex_map.YandexMapModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(
    includes = [
        /*
       core modules
        */
        BaseModule::class,
        LoggerModule::class,
        SettingsManagerDataModule::class,
        RoomDataBaseModule::class,
        RemoteModule::class,
        TrackerAnalyticsModule::class,
        FirebaseTrackerAnalyticsModule::class,
        ProjectImplementationModule::class,
        NetworkConnectionModule::class,
        /*
        feature modules
         */
        RootComponentModule::class,
        RootDomainModule::class,
        MessageDataModule::class,
        DrinkManagerModule::class,
        TrackManagerModule::class,
        CalendarNavigationComponentModule::class,
        CalendarComponentModule::class,
        CalendarDataModule::class,
        MapNavigationComponentModule::class,
        MapComponentModule::class,
        MapDataModule::class,
        YandexMapModule::class,
        CreateTrackNavigationComponentModule::class,
        CreateTrackMainComponentModule::class,
        CreateTrackDataModule::class,
        CalculatorDataModule::class,
        CalculatorMainComponentModule::class,
        DatePickerDataModule::class,
        DatePickerMainComponentModule::class,
        SettingsDataModule::class,
        SettingsNavigationComponentModule::class,
        SettingsMainComponentModule::class,
        CurrencyComponentModule::class,
        StatisticMainComponentModule::class,
        StatisticDayComponentModule::class,
        StatisticDataModule::class,
        StatisticDrinkComponentModule::class,
        StatisticMoneyComponentModule::class,
        DetailTrackDataModule::class,
        DetailTrackNavigationComponentModule::class,
        DetailTrackMainComponentModule::class,
        ConfirmDialogDataModule::class,
        ConfirmDialogMainComponentModule::class,
        SplashScreenComponentModule::class,
        SplashScreenDataModule::class,
        ActionImageComponentModule::class,
        ImageActionsDataModule::class,
        CreateDrinkDataModule::class,
        CreateDrinkNavigationComponentModule::class,
        CreateDrinkMainComponentModule::class,
        GeocoderDataModule::class,
        GeocoderMainComponentModule::class,
        TracksModalDataModule::class,
        TracksModalMainComponentModule::class,
        KandinskyScreenComponentModule::class,
        KandinskyDataModule::class,
        SortingDrinksDataModule::class,
        SortingDrinksMainComponentModule::class,
        AppWidgetModule::class,
        AlcoholCalculatorMainComponentModule::class
    ]
)

@ComponentScan
class SharedModule {
    @Single
    fun provideStoreFactory(): StoreFactory = LoggingStoreFactory(DefaultStoreFactory())
}
