package com.talisol.kankenkakitori.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.talisol.kankenkakitori.KanjiDatabase
import com.talisol.kankenkakitori.data.KanjiQuestionDataSource
import com.talisol.kankenkakitori.data.KanjiQuestionDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.text.Typography.dagger

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
    fun provideKanjiDataSource(driver: SqlDriver): KanjiQuestionDataSource {
        return KanjiQuestionDataSourceImpl(KanjiDatabase(driver))
    }



}