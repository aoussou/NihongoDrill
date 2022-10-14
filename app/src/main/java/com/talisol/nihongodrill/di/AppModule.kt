package com.talisol.kankenkakitori.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.talisol.nihongodrill.KanjiDatabase
import com.talisol.nihongodrill.data.KankenQuestionDataSource
import com.talisol.nihongodrill.data.KankenQuestionDataSourceImpl
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
    fun provideSqlDriver(app:Application): SqlDriver{
        return AndroidSqliteDriver(
            schema = KanjiDatabase.Schema,
            context = app,
            name = "nihongo.db"
        )
    }

    @Provides
    @Singleton
    fun provideKanjiDataSource(driver: SqlDriver): KankenQuestionDataSource {
        return KankenQuestionDataSourceImpl(KanjiDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideProgressDataSource(driver: SqlDriver): ProgressDataSource {
        return ProgressDataSourceImpl(KanjiDatabase(driver))
    }



}