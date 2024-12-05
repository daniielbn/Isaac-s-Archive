package Clases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AdminSQLiteOpenHelper(
    context: Context, name: String, factory: CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        // Tabla de usuarios
        db.execSQL("create table Usuarios(id Integer primary key autoincrement, usuario text, contrasena text not null, personaje_favorito text default null)")

        // Tabla de enemigos
        db.execSQL("create table Enemigos(id Integer primary key autoincrement, nombre text, tipo text not null, descripcion text)")
        db.execSQL(
            "INSERT INTO Enemigos (nombre, tipo, descripcion) VALUES\n" +
                    "('Dark Maggot', 'Común', 'Enemigos que persiguen al jugador, dejando un rastro dañino.'),\n" +
                    "('Psychic Gaper', 'Común', 'Dispara lágrimas rastreadoras hacia el jugador.'),\n" +
                    "('Charged Baby', 'Común', 'Pequeñas criaturas que corren hacia ti rápidamente.'),\n" +
                    "('Mega Fatty', 'Mini-jefe', 'Enorme enemigo que salta y lanza vómitos de lágrimas.'),\n" +
                    "('Fistula', 'Mini-jefe', 'Se divide en partes más pequeñas al ser derrotado.'),\n" +
                    "('Gish', 'Mini-jefe', 'Se mueve lentamente y deja un rastro pegajoso.'),\n" +
                    "('Rag Man', 'Mini-jefe', 'Invoca cabezas flotantes y dispara lágrimas rastreadoras.'),\n" +
                    "('Duke of Flies', 'Mini-jefe', 'Invoca moscas continuamente para protegerse.'),\n" +
                    "('The Husk', 'Mini-jefe', 'Versión más agresiva del Duke of Flies.'),\n" +
                    "('Black Maw', 'Especial', 'Enemigos flotantes que lanzan lágrimas en círculos.'),\n" +
                    "('Blistered Host', 'Protegido', 'Host que lanza ráfagas rápidas de lágrimas.'),\n" +
                    "('Singe', 'Mini-jefe', 'Enemigo que salta y deja fuego tras de sí.'),\n" +
                    "('Great Gideon', 'Jefe', 'Un jefe que invoca oleadas de enemigos menores.'),\n" +
                    "('Little Horn', 'Mini-jefe', 'Lanza bombas y proyectiles desde portales.'),\n" +
                    "('Big Horn', 'Mini-jefe', 'Versión más grande y peligrosa de Little Horn.'),\n" +
                    "('Delirium', 'Jefe', 'Cambia de forma continuamente, imitando a otros jefes.'),\n" +
                    "('The Stain', 'Jefe', 'Se esconde bajo tierra y ataca con ráfagas de lágrimas.'),\n" +
                    "('Dingle', 'Mini-jefe', 'Enemigo en forma de excremento que carga rápidamente.'),\n" +
                    "('Big Poop', 'Común', 'Excremento gigante que deja residuos dañinos.'),\n" +
                    "('Turdling', 'Común', 'Pequeñas criaturas de excremento que atacan en grupo.'),\n" +
                    "('Blue Baby', 'Jefe', 'Dispara lágrimas en patrones complejos.'),\n" +
                    "('Blastocyst', 'Mini-jefe', 'Se divide en formas más pequeñas al ser derrotado.'),\n" +
                    "('Burning Gaper', 'Común', 'Versiones incendiarias de los Gapers que explotan al morir.'),\n" +
                    "('Spider Sack', 'Común', 'Explota en arañas al ser derrotado.'),\n" +
                    "('Evil Twin', 'Especial', 'Imita los movimientos del jugador con precisión.'),\n" +
                    "('Lust', 'Mini-jefe', 'Uno de los Siete Pecados Capitales, persigue al jugador rápidamente.'),\n" +
                    "('Greed', 'Mini-jefe', 'Uno de los Pecados Capitales, ataca en habitaciones de tiendas.'),\n" +
                    "('Wrath', 'Mini-jefe', 'Uno de los Pecados Capitales, lanza bombas hacia el jugador.'),\n" +
                    "('Pestilence', 'Jinete', 'Invoca moscas y deja rastros venenosos.'),\n" +
                    "('Famine', 'Jinete', 'Carga hacia el jugador y genera proyectiles.'),\n" +
                    "('War', 'Jinete', 'Persigue al jugador y lanza bombas.'),\n" +
                    "('Death', 'Jinete', 'Genera rayos de sangre y sicarios voladores.'),\n" +
                    "('Conquest', 'Jinete', 'Ataca con rayos láser y proyectiles en línea recta.'),\n" +
                    "('Skull Turret', 'Trampa', 'Enemigos estacionarios que disparan lágrimas en direcciones fijas.'),\n" +
                    "('Bloody Knight', 'Protegido', 'Versión mejorada del Knight, más rápida y agresiva.'),\n" +
                    "('Cursed Maw', 'Especial', 'Lanza lágrimas teledirigidas al jugador.'),\n" +
                    "('Brain', 'Explosivo', 'Enemigos estacionarios que explotan en ráfagas de sangre.'),\n" +
                    "('Mama Gurdy', 'Mini-jefe', 'Invoca pinchos y trampas mientras ataca desde el aire.'),\n" +
                    "('Isaac', 'Jefe', 'Dispara lágrimas y se teletransporta alrededor de la habitación.'),\n" +
                    "('Ultra Pride', 'Mini-jefe', 'Versión más grande y peligrosa del Pecado Orgullo.'),\n" +
                    "('Mask of Infamy', 'Jefe', 'Máscara que persigue al jugador y es invulnerable por el frente.'),\n" +
                    "('Frail', 'Mini-jefe', 'Empieza como una versión débil de Pin, pero luego se vuelve más agresivo.'),\n" +
                    "('Carrion Queen', 'Jefe', 'Crea excrementos rojos dañinos y tiene un cuerpo largo impenetrable.'),\n" +
                    "('Monstro', 'Jefe', 'Dispara lágrimas en patrones y salta por la habitación.'),\n" +
                    "('Monstro II', 'Jefe', 'Versión más grande de Monstro, dispara un láser de sangre.'),\n" +
                    "('Gurdy', 'Jefe', 'Inmóvil, invoca enemigos y dispara ráfagas de lágrimas.'),\n" +
                    "('Bumbino', 'Mini-jefe', 'Persigue al jugador para robar monedas.'),\n" +
                    "('Gemini', 'Jefe', 'Un enemigo que consta de dos cuerpos unidos.'),\n" +
                    "('The Bloat', 'Jefe', 'Una versión más agresiva de Peep, dispara rayos láser horizontales.'),\n" +
                    "('Peep', 'Jefe', 'Suelta ojos que flotan por la habitación y dispara lágrimas.'),\n" +
                    "('Larry Jr.', 'Jefe', 'Un largo gusano que se divide en partes más pequeñas.'),\n" +
                    "('The Haunt', 'Jefe', 'Fantasmal enemigo que dispara rayos láser y convoca mini-fantasmas.'),\n" +
                    "('Polycephalus', 'Mini-jefe', 'Enemigo que se esconde bajo tierra y ataca sorpresivamente.'),\n" +
                    "('Widow', 'Jefe', 'Una araña gigante que invoca arañas más pequeñas.'),\n" +
                    "('The Wretched', 'Jefe', 'Versión más rápida y peligrosa de Widow.'),\n" +
                    "('Teratoma', 'Mini-jefe', 'Similar a Fistula, pero más peligroso.'),\n" +
                    "('Pin', 'Mini-jefe', 'Rápido enemigo que se oculta bajo tierra y salta para atacar.'),\n" +
                    "('Daddy Long Legs', 'Jefe', 'Un pie gigante que aplasta al jugador desde arriba.'),\n" +
                    "('Triachnid', 'Jefe', 'Versión más agresiva de Daddy Long Legs.'),\n" +
                    "('Satan', 'Jefe', 'El jefe final en Sheol, ataca con diversos patrones.'),\n" +
                    "('Fallen', 'Mini-jefe', 'Versión más pequeña de Satan, ataca con láseres y proyectiles.'),\n" +
                    "('Dopleganger', 'Común', 'Imita los movimientos y ataques del jugador.'),\n" +
                    "('Holy Leech', 'Especial', 'Enemigos que curan a otros enemigos al tocarlos.'),\n" +
                    "('Leaper', 'Común', 'Saltan hacia el jugador desde largas distancias.'),\n" +
                    "('Knight', 'Protegido', 'Enemigos blindados que son invulnerables por el frente.'),\n" +
                    "('Psy Tumor', 'Común', 'Dispara lágrimas rastreadoras que persiguen al jugador.'),\n" +
                    "('Dip', 'Común', 'Pequeñas criaturas de excremento que se lanzan hacia el jugador.'),\n" +
                    "('Mom''s Hand', 'Trampa', 'Una mano que cae desde arriba para agarrar al jugador.'),\n" +
                    "('Krampus', 'Mini-jefe', 'Ataca con un rayo láser y proyectiles.'),\n" +
                    "('Steven', 'Mini-jefe', 'Una versión más pequeña y rápida de Gemini.');\n"
        );


        // Tabla de objetos
        db.execSQL("create table Objetos(id Integer primary key autoincrement, nombre text, rareza text, descripcion text)")
        db.execSQL(
            "INSERT INTO Objetos (nombre, rareza, descripcion) VALUES\n" +
                    "('Sad Onion', 'Común', 'Incrementa la velocidad de disparo.'),\n" +
                    "('The Inner Eye', 'Raro', 'Permite disparar en forma triple.'),\n" +
                    "('Polyphemus', 'Legendario', 'Incrementa enormemente el daño pero reduce la cadencia.'),\n" +
                    "('Magic Mushroom', 'Legendario', 'Incrementa todas las estadísticas y otorga un corazón completo.'),\n" +
                    "('Cricket''s Head', 'Raro', 'Incremento significativo del daño.'),\n" +
                    "('The Halo', 'Raro', 'Incrementa todas las estadísticas levemente.'),\n" +
                    "('Holy Mantle', 'Legendario', 'Permite absorber un golpe por habitación.'),\n" +
                    "('Pyro', 'Común', 'Otorga 99 bombas.'),\n" +
                    "('The Pact', 'Raro', 'Incrementa daño y velocidad de disparo, y otorga 2 corazones de alma.'),\n" +
                    "('The Mark', 'Raro', 'Incrementa daño y velocidad, otorga 1 corazón de alma.'),\n" +
                    "('Brimstone', 'Legendario', 'Sustituye disparos por un poderoso rayo de sangre cargado.'),\n" +
                    "('Mom''s Knife', 'Legendario', 'Reemplaza lágrimas con un cuchillo que puede lanzarse.'),\n" +
                    "('Technology', 'Raro', 'Cambia disparos a láser continuo.'),\n" +
                    "('Technology 2', 'Raro', 'Añade un láser continuo sin reemplazar los disparos.'),\n" +
                    "('Mutant Spider', 'Raro', 'Permite disparar en forma cuádruple, reduce cadencia.'),\n" +
                    "('Chocolate Milk', 'Común', 'Permite cargar disparos para mayor daño.'),\n" +
                    "('Sacred Heart', 'Legendario', 'Gran aumento de daño, lágrimas rastreadoras, reducción de velocidad de disparo.'),\n" +
                    "('Dead Cat', 'Legendario', 'Reduce corazones a 1 pero otorga 9 vidas.'),\n" +
                    "('Guppy''s Head', 'Común', 'Genera moscas aliadas al usarse.'),\n" +
                    "('Guppy''s Tail', 'Común', 'Incrementa la generación de cofres pero reduce llaves.'),\n" +
                    "('D20', 'Raro', 'Reroll de objetos consumibles en la habitación.'),\n" +
                    "('D6', 'Legendario', 'Permite cambiar ítems pedestal.'),\n" +
                    "('Tammy''s Head', 'Común', 'Dispara lágrimas en todas las direcciones al activarse.'),\n" +
                    "('Book of Revelations', 'Raro', 'Genera corazones de alma y aumenta probabilidad de jinetes.'),\n" +
                    "('The Nail', 'Raro', 'Incrementa daño y otorga un corazón de alma.'),\n" +
                    "('Spoon Bender', 'Raro', 'Otorga lágrimas rastreadoras.'),\n" +
                    "('Loki''s Horns', 'Común', 'Dispara en 4 direcciones aleatoriamente.'),\n" +
                    "('Brother Bobby', 'Común', 'Familiar que dispara lágrimas normales.'),\n" +
                    "('Sister Maggy', 'Común', 'Familiar que dispara lágrimas de daño alto.'),\n" +
                    "('Little Brimstone', 'Raro', 'Familiar que dispara un rayo de Brimstone.'),\n" +
                    "('Monstro''s Lung', 'Raro', 'Dispara ráfagas de lágrimas cargadas.'),\n" +
                    "('Dead Dove', 'Legendario', 'Otorga vuelo y lágrimas espectrales.'),\n" +
                    "('Spirit of the Night', 'Raro', 'Otorga vuelo y lágrimas espectrales.'),\n" +
                    "('Headless Baby', 'Común', 'Familiar que deja un rastro de sangre.'),\n" +
                    "('Holy Water', 'Común', 'Crea un charco de agua sagrada al ser golpeado.'),\n" +
                    "('Book of Shadows', 'Raro', 'Otorga invulnerabilidad temporal.'),\n" +
                    "('Anarchist Cookbook', 'Común', 'Genera bombas aleatorias en la habitación.'),\n" +
                    "('PHD', 'Raro', 'Mejora las píldoras y otorga corazones al azar.'),\n" +
                    "('Blood Bag', 'Raro', 'Incrementa vida máxima y velocidad.'),\n" +
                    "('IV Bag', 'Común', 'Permite cambiar vida por monedas.'),\n" +
                    "('Goat Head', 'Raro', 'Garantiza acceso a salas del Diablo o Ángel.'),\n" +
                    "('Magic 8 Ball', 'Común', 'Incrementa la velocidad de disparo y revela el futuro.'),\n" +
                    "('Mom''s Wig', 'Común', 'Genera arañas aliadas al disparar.'),\n" +
                    "('Rosary', 'Raro', 'Incrementa corazones de alma y la probabilidad de ítems sagrados.'),\n" +
                    "('Skeleton Key', 'Legendario', 'Otorga 99 llaves.'),\n" +
                    "('Dr. Fetus', 'Legendario', 'Sustituye disparos por bombas lanzadas.'),\n" +
                    "('Epic Fetus', 'Legendario', 'Controla misiles teledirigidos en lugar de disparos.'),\n" +
                    "('Sacrificial Dagger', 'Raro', 'Orbital que causa gran daño al contacto.'),\n" +
                    "('Infamy', 'Común', 'Probabilidad de bloquear proyectiles enemigos.'),\n" +
                    "('Blood of the Martyr', 'Raro', 'Incrementa el daño base, mejora con Brimstone.'),\n" +
                    "('Mutant Spider', 'Raro', 'Permite disparar en forma cuádruple, reduce cadencia.'),\n" +
                    "('Spider Bite', 'Común', 'Otorga la posibilidad de ralentizar enemigos al golpearlos.'),\n" +
                    "('Book of Belial', 'Raro', 'Incrementa significativamente el daño temporalmente.'),\n" +
                    "('Ouija Board', 'Común', 'Otorga lágrimas espectrales.'),\n" +
                    "('Dead Bird', 'Común', 'Invoca un pájaro que ataca enemigos cuando recibes daño.'),\n" +
                    "('Small Rock', 'Raro', 'Incrementa daño, reduce velocidad.'),\n" +
                    "('The Virus', 'Común', 'Inflige veneno al contacto con enemigos.'),\n" +
                    "('Robo-Baby', 'Común', 'Familiar que dispara láser continuo.'),\n" +
                    "('Key Piece 1', 'Raro', 'Necesario para desbloquear la puerta al Ángel Mega Satan.'),\n" +
                    "('Key Piece 2', 'Raro', 'Segunda parte para abrir la puerta a Mega Satan.'),\n" +
                    "('Trinity Shield', 'Raro', 'Genera un escudo que bloquea disparos enemigos.'),\n" +
                    "('Mom''s Contact', 'Raro', 'Congela enemigos al golpearlos con lágrimas.'),\n" +
                    "('Mom''s Perfume', 'Común', 'Incrementa cadencia y causa miedo a enemigos.'),\n" +
                    "('Sacred Heart', 'Legendario', 'Gran mejora de daño con lágrimas rastreadoras.'),\n" +
                    "('Holy Light', 'Raro', 'Lágrimas tienen probabilidad de generar rayos sagrados al impactar.'),\n" +
                    "('Mysterious Liquid', 'Común', 'Las lágrimas generan un charco de veneno al impactar.'),\n" +
                    "('Torn Photo', 'Común', 'Incrementa la velocidad de disparo.'),\n" +
                    "('Latch Key', 'Común', 'Otorga corazones de alma y llaves adicionales.'),\n" +
                    "('Cancer', 'Raro', 'Incrementa enormemente la velocidad de disparo.'),\n" +
                    "('Libra', 'Raro', 'Equilibra todas las estadísticas.'),\n" +
                    "('Scorpio', 'Común', 'Lágrimas tienen efecto venenoso.'),\n" +
                    "('Leo', 'Común', 'Permite destruir rocas al caminar sobre ellas.'),\n" +
                    "('Gemini', 'Común', 'Familiar cuerpo a cuerpo que sigue al jugador.'),\n" +
                    "('Piscis', 'Común', 'Incrementa retroceso de lágrimas y velocidad de disparo.'),\n" +
                    "('Capricorn', 'Raro', 'Incrementa todas las estadísticas.'),\n" +
                    "('Aquarius', 'Común', 'Genera un rastro de lágrimas al moverse.'),\n" +
                    "('Aries', 'Común', 'Incrementa velocidad y daña enemigos al contacto.'),\n" +
                    "('Taurus', 'Común', 'Velocidad aumenta mientras se carga un efecto de invulnerabilidad temporal.'),\n" +
                    "('Sagittarius', 'Raro', 'Lágrimas espectrales y perforantes.'),\n" +
                    "('The Polaroid', 'Raro', 'Otorga escudo temporal al recibir daño con poca vida.'),\n" +
                    "('The Negative', 'Raro', 'Inflige daño masivo a todos los enemigos al recibir daño.'),\n" +
                    "('Holy Grail', 'Legendario', 'Otorga vuelo y un corazón completo.'),\n" +
                    "('Blood Rights', 'Común', 'Consume vida para dañar a todos los enemigos en la habitación.'),\n" +
                    "('Celtic Cross', 'Raro', 'Probabilidad de escudo temporal al recibir daño.'),\n" +
                    "('Ghost Baby', 'Común', 'Familiar que dispara lágrimas espectrales.'),\n" +
                    "('Rotten Baby', 'Raro', 'Genera moscas aliadas al disparar.'),\n" +
                    "('The Parasite', 'Raro', 'Lágrimas se dividen al impactar.'),\n" +
                    "('Cricket''s Body', 'Raro', 'Incrementa cadencia, lágrimas explotan en impactos secundarios.'),\n" +
                    "('Broken Watch', 'Raro', 'Ralentiza o acelera el tiempo al azar.'),\n" +
                    "('The Compass', 'Común', 'Revela la ubicación de habitaciones especiales.'),\n" +
                    "('Treasure Map', 'Común', 'Revela el diseño del mapa sin mostrar habitaciones específicas.'),\n" +
                    "('Thunder Thighs', 'Común', 'Incrementa vida pero reduce velocidad y permite destruir rocas.'),\n" +
                    "('Black Lotus', 'Legendario', 'Otorga corazones de alma, negros y rojos.'),\n" +
                    "('Placenta', 'Común', 'Incrementa vida máxima y regenera corazones lentamente.'),\n" +
                    "('Magic Scab', 'Común', 'Incrementa vida máxima y la suerte.'),\n" +
                    "('Stem Cells', 'Común', 'Incrementa vida máxima y velocidad.'),\n" +
                    "('Binky', 'Común', 'Incrementa la velocidad de disparo y otorga un corazón de alma.'),\n" +
                    "('Mulligan', 'Raro', 'Las lágrimas tienen probabilidad de generar moscas aliadas.');"
        );

        // Tabla de Usuarios_Enemigos
        db.execSQL("create table Usuarios_Enemigos(id Integer primary key autoincrement, id_usuario Integer not null, id_enemigo Integer not null, desbloqueado boolean default 0, foreign key(id_usuario) references Usuarios(id) on delete cascade, foreign key(id_enemigo) references Enemigos(id) on delete cascade)")

        // Tabla de Usuarios_Objetos
        db.execSQL("create table Usuarios_Objetos(id Integer primary key autoincrement, id_usuario Integer not null, id_objeto Integer not null, desbloqueado boolean default 0, foreign key(id_usuario) references Usuarios(id) on delete cascade, foreign key(id_objeto) references Objetos(id) on delete cascade)")

        // Tabla de comentarios
        db.execSQL("create table Comentarios(id Integer primary key autoincrement, id_usuario Integer not null, id_objeto Integer default null, id_enemigo Integer default null , comentario text not null, fecha text not null, foreign key(id_usuario) references Usuarios(id) on delete cascade, foreign key(id_objeto) references Objetos(id) on delete cascade, foreign key(id_enemigo) references Enemigos(id) on delete cascade)")
        db.execSQL(
            "INSERT INTO Comentarios (id_usuario, id_objeto, id_enemigo, comentario, fecha) \n" +
                    "VALUES \n" +
                    "(1, 1, NULL, 'Este objeto es muy útil para las batallas.', '28/11/2024 10:00:00'),\n" +
                    "(1, 2, NULL, 'Me costó mucho encontrar este objeto.', '27/11/2024 11:00:00'),\n" +
                    "(1, 3, NULL, 'No entiendo bien cómo usar este objeto.', '21/11/2024 12:00:00 ');"
        )

        db.execSQL(
            "INSERT INTO Comentarios (id_usuario, id_objeto, id_enemigo, comentario, fecha) \n" +
                    "VALUES \n" +
                    "(1, NULL, 1, 'Este enemigo es muy fuerte, me derrotó varias veces.', '23/11/2024 13:00:00'),\n" +
                    "(1, NULL, 2, 'Fue fácil derrotarlo con el objeto 1.', '12/11/2024 14:00:00'),\n" +
                    "(1, NULL, 3, 'Su ataque especial es muy difícil de esquivar.', '29/11/2024 15:00:00');"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists Usuarios")
        db.execSQL("drop table if exists Usuarios_Enemigos")
        db.execSQL("drop table if exists Usuarios_Objetos")
        db.execSQL("drop table if exists Comentarios")
        db.execSQL("drop table if exists Enemigos")
        db.execSQL("drop table if exists Objetos")
        onCreate(db)
    }

    fun consultarUsuario(usuario: String): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT * FROM Usuarios WHERE usuario = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(usuario))
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun consultarContrasena(usuario: String): String {
        val db = this.writableDatabase
        val SQLQuery = "SELECT contrasena FROM Usuarios WHERE usuario = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(usuario))
        return if (cursor.moveToFirst()) {
            cursor.getString(0)
        } else {
            ""
        }
    }

    fun consultarPersonaje(usuario: String): String {
        val db = this.writableDatabase
        val SQLQuery = "SELECT personaje_favorito FROM Usuarios WHERE usuario = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(usuario))
        return if (cursor.moveToFirst()) {
            cursor.getString(0)
        } else {
            ""
        }
    }

    fun consultarObjeto(objeto: String): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT * FROM Objetos WHERE nombre = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(objeto))
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun consultarEnemigo(enemigo: String): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT * FROM Enemigos WHERE nombre = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(enemigo))
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun consultarNombreUsuario(idUsuario: Int): String {
        val db = this.writableDatabase
        val SQLQuery = "SELECT usuario FROM Usuarios WHERE id = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(idUsuario.toString()))
        return if (cursor.moveToFirst()) {
            cursor.getString(0)
        } else {
            ""
        }
    }

    fun insertarUsuario(context: Context, usuario: String, contrasena: String): Boolean {
        val db = this.writableDatabase
        if (consultarUsuario(usuario) > 0) {
            return false
        }
        val valores = ContentValues().apply {
            put("usuario", usuario)
            put("contrasena", contrasena)
        }
        if (db.insert("Usuarios", null, valores) != -1L) {
            val listaObjetos = obtenerObjetos()
            val nRegistrosObjeto = listaObjetos.size
            for (i in 0 until nRegistrosObjeto) {
                val valoresObjetos = ContentValues().apply {
                    put("id_usuario", consultarUsuario(usuario))
                    put("id_objeto", consultarObjeto(listaObjetos[i].nombre))
                }
                db.insert("Usuarios_Objetos", null, valoresObjetos)
            }

            val listaEnemigos = obtenerEnemigos()
            val nRegistrosEnemigos = listaEnemigos.size
            for(i in 0 until nRegistrosEnemigos){
                val valoresEnemigos = ContentValues().apply {
                    put("id_usuario", consultarUsuario(usuario))
                    put("id_enemigo", consultarEnemigo(listaEnemigos[i].nombre))
                }
                db.insert("Usuarios_Enemigos", null, valoresEnemigos)
            }
            return true
        }
        return false
    }

    fun actualizarDesbloqueadoEnemigo(enemigo: Enemigo, usuario: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("desbloqueado", enemigo.desbloqueado)
        }
        val clausulaWhere = "id_usuario = ? AND id_enemigo = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString(), consultarEnemigo(enemigo.nombre).toString())
        val cantidad = db.update("Usuarios_Enemigos", valores, clausulaWhere, argumentos)
        return cantidad > 0
    }

    fun actualizarDesbloqueadoObjeto(objeto: Objeto, usuario: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("desbloqueado", objeto.desbloqueado)
        }
        val clausulaWhere = "id_usuario = ? AND id_objeto = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString(), consultarObjeto(objeto.nombre).toString())
        val cantidad = db.update("Usuarios_Objetos", valores, clausulaWhere, argumentos)
        return cantidad > 0
    }

    fun actualizarUsuario(usuario: String, nuevoUsuario: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("usuario", nuevoUsuario)
        }
        val clausulaWhere = "usuario = ?"
        val argumentos = arrayOf(usuario)
        val cantidad = db.update("Usuarios", valores, clausulaWhere, argumentos)
        return cantidad > 0
    }

    fun actualizarContrasena(usuario: String, contrasena: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("contrasena", encriptarContrasena(contrasena))
        }
        val clausulaWhere = "usuario = ?"
        val argumentos = arrayOf(usuario)
        val cantidad = db.update("Usuarios", valores, clausulaWhere, argumentos)
        return cantidad > 0
    }

    fun actualizarPersonaje(usuario: String, personaje: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("personaje_favorito", personaje)
        }
        val clausulaWhere = "usuario = ?"
        val argumentos = arrayOf(usuario)
        val cantidad = db.update("Usuarios", valores, clausulaWhere, argumentos)
        return cantidad > 0
    }

    fun eliminarUsuario(usuario: String): Boolean {
        val db = this.writableDatabase
        val clausulaWhere = "usuario = ?"
        val argumentos = arrayOf(usuario)
        return db.delete("Usuarios", clausulaWhere, argumentos) > 0
    }

    fun eliminarDesbloqueosObjetoUsuario(usuario: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("desbloqueado", 0)
        }
        val clausulaWhere = "id_usuario = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString())
        return db.update("Usuarios_Objetos", valores, clausulaWhere, argumentos) > 0
    }

    fun eliminarDesbloqueosEnemigoUsuario(usuario: String): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("desbloqueado", 0)
        }
        val clausulaWhere = "id_usuario = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString())
        return db.update("Usuarios_Enemigos", valores, clausulaWhere, argumentos) > 0
    }

    fun eliminarDesbloqueoEnemigo(enemigo: Enemigo, usuario: String): Boolean {
        val db = this.writableDatabase
        val clausulaWhere = "id_usuario = ? AND id_enemigo = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString(), consultarEnemigo(enemigo.nombre).toString())
        return db.delete("Usuarios_Enemigos", clausulaWhere, argumentos) > 0
    }

    fun eliminarDesbloqueoObjeto(objeto: Objeto, usuario: String): Boolean {
        val db = this.writableDatabase
        val clausulaWhere = "id_usuario = ? AND id_objeto = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString(), consultarObjeto(objeto.nombre).toString())
        return db.delete("Usuarios_Objetos", clausulaWhere, argumentos) > 0
    }

    fun eliminarComentario(comentario: Comentario): Boolean {
        val db = this.writableDatabase
        val clausulaWhere = "id_usuario = ? AND id_objeto = ? AND id_enemigo = ? AND comentario = ?"
        val argumentos = arrayOf(comentario.id_usuario.toString(), comentario.id_objeto.toString(), comentario.id_enemigo.toString(), comentario.comentario)
        val filas = db.delete("Comentarios", clausulaWhere, argumentos)
        return filas > 0
    }

    fun comprobarDesbloqueoEnemigo(enemigo: Enemigo, usuario: String): Boolean {
        val db = this.readableDatabase
        val SQLQuery = "SELECT desbloqueado FROM Usuarios_Enemigos WHERE id_usuario = ? AND id_enemigo = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString(), consultarEnemigo(enemigo.nombre).toString())
        val fila = db.rawQuery(SQLQuery, argumentos)
        if (fila.moveToFirst()) {
            return fila.getInt(0) == 1
        }
        return false
    }

    fun comprobarDesbloqueoObjeto(objeto: Objeto, usuario: String): Boolean {
        val db = this.writableDatabase
        val SQLQuery = "SELECT desbloqueado FROM Usuarios_Objetos WHERE id_usuario = ? AND id_objeto = ?"
        val argumentos = arrayOf(consultarUsuario(usuario).toString(), consultarObjeto(objeto.nombre).toString())
        val fila = db.rawQuery(SQLQuery, argumentos)
        if (fila.moveToFirst()) {
            return fila.getInt(0) == 1
        }
        return false
    }

    fun obtenerComentariosEnemigo(enemigo: Enemigo): ArrayList<Comentario> {
        val db = this.readableDatabase
        val SQLQuery = "SELECT * FROM Comentarios WHERE id_enemigo = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(consultarEnemigo(enemigo.nombre).toString()))
        val comentarios = ArrayList<Comentario>()
        if (cursor.moveToFirst()) {
            do {
                val comentario = Comentario(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )
                comentarios.add(comentario)
            } while (cursor.moveToNext())
        }
        return comentarios
    }

    fun obtenerComentariosObjeto(objeto: Objeto): ArrayList<Comentario> {
        val db = this.writableDatabase
        val SQLQuery = "SELECT * FROM Comentarios WHERE id_objeto = ?"
        val cursor = db.rawQuery(SQLQuery, arrayOf(consultarObjeto(objeto.nombre).toString()))
        val comentarios = ArrayList<Comentario>()
        if (cursor.moveToFirst()) {
            do {
                val comentario = Comentario(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5)
                )
                comentarios.add(comentario)
            } while (cursor.moveToNext())
        }
        return comentarios
    }

    fun obtenerEnemigos(): ArrayList<Enemigo> {
        val db = this.writableDatabase
        val SQLQuery = "SELECT * FROM Enemigos"
        val cursor = db.rawQuery(SQLQuery, null)
        val enemigos = ArrayList<Enemigo>()
        while (cursor.moveToNext()) {
            val enemigo = Enemigo(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
            )
            enemigos.add(enemigo)
        }
        return enemigos
    }

    fun obtenerObjetos(): ArrayList<Objeto> {
        val db = this.writableDatabase
        val SQLQuery = "SELECT * FROM Objetos"
        val cursor = db.rawQuery(SQLQuery, null)
        val objetos = ArrayList<Objeto>()
        while (cursor.moveToNext()) {
            val objeto = Objeto(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
            objetos.add(objeto)
        }
        return objetos
    }

    fun obtenerNumeroObjetos(): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT COUNT(*) FROM Objetos"
        val cursor = db.rawQuery(SQLQuery, null)
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun obtenerNumeroObjetosUsuario(): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT COUNT(*) FROM Usuarios_Objetos WHERE desbloqueado = 1"
        val cursor = db.rawQuery(SQLQuery, null)
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun obtenerNumeroEnemigos(): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT COUNT(*) FROM Enemigos"
        val cursor = db.rawQuery(SQLQuery, null)
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun obtenerNumeroEnemigosUsuario(): Int {
        val db = this.writableDatabase
        val SQLQuery = "SELECT COUNT(*) FROM Usuarios_Enemigos WHERE desbloqueado = 1"
        val cursor = db.rawQuery(SQLQuery, null)
        return if (cursor.moveToFirst()) {
            cursor.getInt(0)
        } else {
            0
        }
    }

    fun anadirComentarioEnemigo(comentario: Comentario): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("id_usuario", comentario.id_usuario)
            put("id_enemigo", comentario.id_enemigo)
            put("comentario", comentario.comentario)
            put("fecha", getFechaActual())
        }
        return db.insert("Comentarios", null, valores) != -1L
    }

    fun anadirComentarioObjeto(comentario: Comentario): Boolean {
        val db = this.writableDatabase
        val valores = ContentValues().apply {
            put("id_usuario", comentario.id_usuario)
            put("id_objeto", comentario.id_objeto)
            put("comentario", comentario.comentario)
            put("fecha", getFechaActual())
        }
        return db.insert("Comentarios", null, valores) != 1L
    }

    private fun getFechaActual(): String {
        val formato =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return LocalDateTime.now().format(formato)
    }

    fun encriptarContrasena(contrasena: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(contrasena.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
