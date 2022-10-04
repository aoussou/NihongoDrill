package com.talisol.kankenkakitori.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.talisol.kankenkakitori.KanjiDatabase
import com.talisol.kankenkakitori.data.KankenQuestionDataSource
import com.talisol.kankenkakitori.data.KankenQuestionDataSourceImpl
import com.talisol.kankenkakitori.data.ProgressDataSource
import com.talisol.kankenkakitori.data.ProgressDataSourceImpl
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
            name = "kanji.db"
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