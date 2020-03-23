package com.example.spacenbeyond.repository;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class SpaceDatabase_DadosHomeDataBase_Impl extends SpaceDatabase.DadosHomeDataBase {
  private volatile SpaceDao _spaceDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `DadosHome` (`id` INTEGER NOT NULL, `imagem` INTEGER NOT NULL, `nomeFoto` TEXT, `nomeAutor` TEXT, `descricao` TEXT, `favoritos` INTEGER NOT NULL, `compartilhar` INTEGER NOT NULL, `dowlo` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Imagem` (`imagem` INTEGER NOT NULL, PRIMARY KEY(`imagem`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"ebc73df6d790bfc08853c49cc1c37961\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `DadosHome`");
        _db.execSQL("DROP TABLE IF EXISTS `Imagem`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsDadosHome = new HashMap<String, TableInfo.Column>(8);
        _columnsDadosHome.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsDadosHome.put("imagem", new TableInfo.Column("imagem", "INTEGER", true, 0));
        _columnsDadosHome.put("nomeFoto", new TableInfo.Column("nomeFoto", "TEXT", false, 0));
        _columnsDadosHome.put("nomeAutor", new TableInfo.Column("nomeAutor", "TEXT", false, 0));
        _columnsDadosHome.put("descricao", new TableInfo.Column("descricao", "TEXT", false, 0));
        _columnsDadosHome.put("favoritos", new TableInfo.Column("favoritos", "INTEGER", true, 0));
        _columnsDadosHome.put("compartilhar", new TableInfo.Column("compartilhar", "INTEGER", true, 0));
        _columnsDadosHome.put("dowlo", new TableInfo.Column("dowlo", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDadosHome = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDadosHome = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDadosHome = new TableInfo("DadosHome", _columnsDadosHome, _foreignKeysDadosHome, _indicesDadosHome);
        final TableInfo _existingDadosHome = TableInfo.read(_db, "DadosHome");
        if (! _infoDadosHome.equals(_existingDadosHome)) {
          throw new IllegalStateException("Migration didn't properly handle DadosHome(com.example.spacenbeyond.model.DadosHome).\n"
                  + " Expected:\n" + _infoDadosHome + "\n"
                  + " Found:\n" + _existingDadosHome);
        }
        final HashMap<String, TableInfo.Column> _columnsImagem = new HashMap<String, TableInfo.Column>(1);
        _columnsImagem.put("imagem", new TableInfo.Column("imagem", "INTEGER", true, 1));
        final HashSet<TableInfo.ForeignKey> _foreignKeysImagem = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesImagem = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoImagem = new TableInfo("Imagem", _columnsImagem, _foreignKeysImagem, _indicesImagem);
        final TableInfo _existingImagem = TableInfo.read(_db, "Imagem");
        if (! _infoImagem.equals(_existingImagem)) {
          throw new IllegalStateException("Migration didn't properly handle Imagem(com.example.spacenbeyond.model.Imagem).\n"
                  + " Expected:\n" + _infoImagem + "\n"
                  + " Found:\n" + _existingImagem);
        }
      }
    }, "ebc73df6d790bfc08853c49cc1c37961", "7af74708d67a24548a9f1dd0a6c501b1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "DadosHome","Imagem");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `DadosHome`");
      _db.execSQL("DELETE FROM `Imagem`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SpaceDao spaceDao() {
    if (_spaceDao != null) {
      return _spaceDao;
    } else {
      synchronized(this) {
        if(_spaceDao == null) {
          _spaceDao = new SpaceDao_Impl(this);
        }
        return _spaceDao;
      }
    }
  }
}
