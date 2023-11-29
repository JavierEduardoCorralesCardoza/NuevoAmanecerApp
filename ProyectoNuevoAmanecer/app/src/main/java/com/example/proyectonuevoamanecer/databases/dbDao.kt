package com.example.proyectonuevoamanecer.databases

import android.content.Context
import androidx.room.*
import androidx.room.TypeConverter
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromString(value: String): JSONObject {
        return JSONObject(value)
    }

    @TypeConverter
    fun fromJSONObject(value: JSONObject): String {
        return value.toString()
    }
}
@Entity
data class UsuarioActivo(
    @PrimaryKey val id: Int, // Siempre será 1
    val id_usuario: String,
    var id_grupo: String? = null
)
@Entity
data class Usuario(
    @PrimaryKey val id: String,
    val nombre: String,
    val administrador: Boolean?
)

@Entity
data class Grupo(
    @PrimaryKey val id: String,
    val nombre: String,
    val gamemaster: String
)

@Entity
data class Juego(
    @PrimaryKey val id: Int,
    val nombre: String
)

@Entity(primaryKeys = ["id_grupo", "id_usuario"])
data class Miembro(
    val id_grupo: String,
    val id_usuario: String,
    val configuracion: Boolean
)

@Entity(primaryKeys = ["id_juego", "id_grupo"])
data class Configuracion(
    val id_juego: Int,
    val id_grupo: String,
    val configuracion: String
)

@Entity(primaryKeys = ["id_jugador", "id_grupo", "id_juego"])
data class Historial(
    val id_jugador: String,
    val id_grupo: String,
    val id_juego: Int,
    val nivel: Int
)

@Dao
interface DbDao {
    @Query("SELECT * FROM UsuarioActivo WHERE id = 1")
    suspend fun getUsuarioActivo(): UsuarioActivo?
    @Query("SELECT * FROM Usuario")
    suspend fun getUsuarios(): List<Usuario>?
    @RawQuery
    suspend fun getUsuario(ids: SimpleSQLiteQuery): Usuario?
    @Query("SELECT * FROM Grupo")
    suspend fun getGrupos(): List<Grupo>?
    @RawQuery
    suspend fun getGrupo(ids: SimpleSQLiteQuery): Grupo?
    @Query("SELECT * FROM Grupo")
    suspend fun getJuegos(): List<Juego>?
    @RawQuery
    suspend fun getJuego(ids: SimpleSQLiteQuery): Juego?
    @Query("SELECT * FROM Miembro")
    suspend fun getMiembros(): List<Miembro>?
    @RawQuery
    suspend fun getMiembro(ids: SimpleSQLiteQuery): Miembro?
    @Query("SELECT * FROM Configuracion")
    suspend fun getConfiguraciones(): List<Configuracion>?
    @RawQuery
    suspend fun getConfiguracion(ids: SimpleSQLiteQuery): Configuracion?
    @Query("SELECT * FROM Historial")
    suspend fun getHistoriales(): List<Historial>?
    @RawQuery
    suspend fun getHistorial(ids: SimpleSQLiteQuery): Historial?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUsuarioActivo(usuarioActivo: UsuarioActivo)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrupo(grupo: Grupo)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJuego(juego: Juego)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMiembro(miembro: Miembro)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfiguracion(configuracion: Configuracion)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistorial(historial: Historial)
    @Update
    suspend fun updateUsuario(usuario: Usuario)
    @Update
    suspend fun updateGrupo(grupo: Grupo)
    @Update
    suspend fun updateJuego(juego: Juego)
    @Update
    suspend fun updateMiembro(miembro: Miembro)
    @Update
    suspend fun updateConfiguracion(configuracion: Configuracion)
    @Update
    suspend fun updateHistorial(historial: Historial)
    @Delete
    suspend fun deleteUsuarioActivo(usuarioActivo: UsuarioActivo)
    @Delete
    suspend fun deleteUsuario(usuario: Usuario)
    @Delete
    suspend fun deleteGrupo(grupo: Grupo)
    @Delete
    suspend fun deleteJuego(juego: Juego)
    @Delete
    suspend fun deleteMiembro(miembro: Miembro)
    @Delete
    suspend fun deleteConfiguracion(configuracion: Configuracion)
    @Delete
    suspend fun deleteHistorial(historial: Historial)
}

@Database(entities = [UsuarioActivo::class, Usuario::class, Grupo::class, Juego::class, Miembro::class, Configuracion::class, Historial::class], version = 1)
@TypeConverters(Converters::class)
abstract class DbDatabase : RoomDatabase() {
    abstract fun dbDao(): DbDao
    companion object {
        private var instance: DbDatabase? = null
        fun getInstance(context: Context): DbDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context,DbDatabase::class.java,"DbDatabase.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as DbDatabase
        }
    }
}

class Repositorio(private val dbDao: DbDao) {
    suspend fun getUsuarioActivo() = withContext(Dispatchers.IO){
        dbDao.getUsuarioActivo()
    }
    suspend fun getUsuarios() = withContext(Dispatchers.IO){
        dbDao.getUsuarios()
    }
    suspend fun getUsuario(ids: Map<String,String?>?) = withContext(Dispatchers.IO){
        val condiciones = ids?.entries?.joinToString(" AND " ){"${it.key} = '${it.value}'" }
        val query = SimpleSQLiteQuery("SELECT * FROM Usuario WHERE $condiciones")
        dbDao.getUsuario(query)
    }
    suspend fun getGrupos() = withContext(Dispatchers.IO){
        dbDao.getGrupos()
    }
    suspend fun getGrupo(ids: Map<String,String?>?) = withContext(Dispatchers.IO){
        val condiciones = ids?.entries?.joinToString(" AND " ){"${it.key} = '${it.value}'" }
        val query = SimpleSQLiteQuery("SELECT * FROM Grupo WHERE $condiciones")
        dbDao.getGrupo(query)
    }
    suspend fun getJuegos() = withContext(Dispatchers.IO){
        dbDao.getJuegos()
    }
    suspend fun getJuego(ids: Map<String,String?>?) = withContext(Dispatchers.IO){
        val condiciones = ids?.entries?.joinToString(" AND " ){"${it.key} = '${it.value}'" }
        val query = SimpleSQLiteQuery("SELECT * FROM Juego WHERE $condiciones")
        dbDao.getJuego(query)
    }
    suspend fun getMiembros() = withContext(Dispatchers.IO){
        dbDao.getMiembros()
    }
    suspend fun getMiembro(ids: Map<String,String?>?) = withContext(Dispatchers.IO){
        val condiciones = ids?.entries?.joinToString(" AND " ){"${it.key} = '${it.value}'" }
        val query = SimpleSQLiteQuery("SELECT * FROM Miembro WHERE $condiciones")
        dbDao.getMiembro(query)
    }
    suspend fun getConfiguraciones() = withContext(Dispatchers.IO){
        dbDao.getConfiguraciones()
    }
    suspend fun getConfiguracion(ids: Map<String,String?>?) = withContext(Dispatchers.IO){
        val condiciones = ids?.entries?.joinToString(" AND " ){"${it.key} = '${it.value}'" }
        val query = SimpleSQLiteQuery("SELECT * FROM Configuracion WHERE $condiciones")
        dbDao.getConfiguracion(query)
    }
    suspend fun getHistoriales() = withContext(Dispatchers.IO){
        dbDao.getHistoriales()
    }
    suspend fun getHistorial(ids: Map<String,String?>?) = withContext(Dispatchers.IO){
        val condiciones = ids?.entries?.joinToString(" AND " ){"${it.key} = '${it.value}'" }
        val query = SimpleSQLiteQuery("SELECT * FROM Historial WHERE $condiciones")
        dbDao.getHistorial(query)
    }
    suspend fun setUsuarioActivo(usuarioActivo: UsuarioActivo) = withContext(Dispatchers.IO){
        dbDao.setUsuarioActivo(usuarioActivo)
    }
    suspend fun insertUsuario(usuario: Usuario) = withContext(Dispatchers.IO) {
        dbDao.insertUsuario(usuario)
    }
    suspend fun insertGrupo(grupo: Grupo) = withContext(Dispatchers.IO) {
        dbDao.insertGrupo(grupo)
    }
    suspend fun insertJuego(juego: Juego) = withContext(Dispatchers.IO) {
        dbDao.insertJuego(juego)
    }
    suspend fun insertMiembro(miembro: Miembro) = withContext(Dispatchers.IO) {
        dbDao.insertMiembro(miembro)
    }
    suspend fun insertConfiguracion(configuracion: Configuracion) = withContext(Dispatchers.IO) {
        dbDao.insertConfiguracion(configuracion)
    }
    suspend fun insertHistorial(historial: Historial) = withContext(Dispatchers.IO) {
        dbDao.insertHistorial(historial)
    }
    suspend fun updateUsuario(usuario: Usuario) = withContext(Dispatchers.IO) {
        dbDao.updateUsuario(usuario)
    }
    suspend fun updateGrupo(grupo: Grupo) = withContext(Dispatchers.IO) {
        dbDao.updateGrupo(grupo)
    }
    suspend fun updateJuego(juego: Juego) = withContext(Dispatchers.IO) {
        dbDao.updateJuego(juego)
    }
    suspend fun updateMiembro(miembro: Miembro) = withContext(Dispatchers.IO) {
        dbDao.updateMiembro(miembro)
    }
    suspend fun updateConfiguracion(configuracion: Configuracion) = withContext(Dispatchers.IO) {
        dbDao.updateConfiguracion(configuracion)
    }
    suspend fun updateHistorial(historial: Historial) = withContext(Dispatchers.IO) {
        dbDao.updateHistorial(historial)
    }
    suspend fun deleteUsuarioActivo(usuarioActivo: UsuarioActivo) = withContext(Dispatchers.IO){
        dbDao.deleteUsuarioActivo(usuarioActivo)
    }
    suspend fun deleteUsuario(usuario: Usuario) = withContext(Dispatchers.IO) {
        dbDao.deleteUsuario(usuario)
    }
    suspend fun deleteGrupo(grupo: Grupo) = withContext(Dispatchers.IO) {
        dbDao.deleteGrupo(grupo)
    }
    suspend fun deleteJuego(juego: Juego) = withContext(Dispatchers.IO) {
        dbDao.deleteJuego(juego)
    }
    suspend fun deleteMiembro(miembro: Miembro) = withContext(Dispatchers.IO) {
        dbDao.deleteMiembro(miembro)
    }
    suspend fun deleteConfiguracion(configuracion: Configuracion) = withContext(Dispatchers.IO) {
        dbDao.deleteConfiguracion(configuracion)
    }
    suspend fun deleteHistorial(historial: Historial) = withContext(Dispatchers.IO) {
        dbDao.deleteHistorial(historial)
    }
}


