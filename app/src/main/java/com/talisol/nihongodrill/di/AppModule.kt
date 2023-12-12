package com.talisol.nihongodrill.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.talisol.nihongodrill.NihongoDatabase
import com.talisol.nihongodrill.data.ManagerDataSource
import com.talisol.nihongodrill.data.ManagerDataSourceImpl
import com.talisol.nihongodrill.data.ProgressDataSource
import com.talisol.nihongodrill.data.ProgressDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app:Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = NihongoDatabase.Schema,
            context = app,
            name = "nihongo.db"
        )
    }

    @Provides
    @Singleton
    fun provideKanjiDataSource(driver: SqlDriver): ManagerDataSource {
        return ManagerDataSourceImpl(NihongoDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideProgressDataSource(driver: SqlDriver): ProgressDataSource {
        return ProgressDataSourceImpl(NihongoDatabase(driver))
    }



}