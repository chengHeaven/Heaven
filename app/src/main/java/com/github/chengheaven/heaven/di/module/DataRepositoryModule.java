package com.github.chengheaven.heaven.di.module;

import android.content.Context;

import com.github.chengheaven.heaven.data.book.BookDataRepository;
import com.github.chengheaven.heaven.data.book.BookDataSource;
import com.github.chengheaven.heaven.data.every.EveryDataRepository;
import com.github.chengheaven.heaven.data.every.EveryDataSource;
import com.github.chengheaven.heaven.data.movie.MovieDataRepository;
import com.github.chengheaven.heaven.data.movie.MovieDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Heaven・Cheng Created on 17/4/19.
 */

@Module
public class DataRepositoryModule {

    @Singleton
    @Provides
    EveryDataSource provideEveryDataSource(Context context) {
        return new EveryDataRepository(context);
    }

    @Singleton
    @Provides
    MovieDataSource provideMovieDataSource(Context context) {
        return new MovieDataRepository(context);
    }

    @Singleton
    @Provides
    BookDataSource provideBookDataSource(Context context) {
        return new BookDataRepository(context);
    }
}
