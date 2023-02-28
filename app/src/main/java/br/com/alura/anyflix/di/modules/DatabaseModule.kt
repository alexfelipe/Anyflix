package br.com.alura.anyflix.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.anyflix.database.AnyflixDatabase
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.model.toMovieEntity
import br.com.alura.anyflix.sampleData.sampleMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Volatile
    private var INSTANCE: AnyflixDatabase? = null

    private class AnyflixDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    sampleMovies.forEach { movie ->
                        database.movieDao().save(movie.toMovieEntity())
                    }
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnyflixDatabase {
        return INSTANCE ?: synchronized(this) {
            val scope = CoroutineScope(Dispatchers.IO)
            Room.databaseBuilder(
                context,
                AnyflixDatabase::class.java,
                "anyflix.db"
            ).addCallback(AnyflixDatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }

    @Provides
    fun provideContatoDao(db: AnyflixDatabase): MovieDao {
        return db.movieDao()
    }

}