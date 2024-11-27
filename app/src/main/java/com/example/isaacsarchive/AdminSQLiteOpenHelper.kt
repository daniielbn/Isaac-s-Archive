package com.example.isaacsarchive

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(context: Context, name: String, factory: CursorFactory?,
                            version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        // Tabla de usuarios
        db.execSQL("create table Usuarios(id Integer primary key autoincrement, usuario text, contrasena text not null, personaje_favorito text default null)")

        // Tabla de enemigos
        db.execSQL("create table Enemigos(id Integer primary key autoincrement, nombre text, tipo text not null, descripcion text, desbloqueado boolean default false)")
        db.execSQL("INSERT INTO Enemigos (nombre, tipo, descripcion) VALUES\n" +
                "('Dark Maggot', 'Común', 'Enemigos que persiguen al jugador, dejando un rastro dañino.'),\n" +
                "('Psychic Gaper', 'Común', 'Dispara lágrimas rastreadoras hacia el jugador.'),\n" +
                "('Charged Baby', 'Común', 'Pequeñas criaturas que corren hacia ti rápidamente.'),\n" +
                "('Mega Fatty', 'Mini-jefe', 'Enorme enemigo que salta y lanza vómitos de lágrimas.'),\n" +
                "('Fistula', 'Mini-jefe', 'Se divide en partes más pequeñas al ser derrotado.'),\n" +
                "('Gish', 'Mini-jefe', 'Se mueve lentamente y deja un rastro pegajoso.'),\n" +
                "('Rag Man', 'Mini-jefe', 'Invoca cabezas flotantes y dispara lágrimas rastreadoras.'),\n" +
                "('Duke of Flies', 'Mini-jefe', 'Invoca moscas continuamente para protegerse.'),\n" +
                "('The Husk', 'Mini-jefe', 'Versión más agresiva del Duke of Flies.'),\n" +
                "('Swarm Spider', 'Común', 'Arañas rápidas que saltan hacia el jugador.'),\n" +
                "('Black Maw', 'Especial', 'Enemigos flotantes que lanzan lágrimas en círculos.'),\n" +
                "('Maw of the Void', 'Especial', 'Dispara rayos oscuros que dañan en un área amplia.'),\n" +
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
                "('Hush Fly', 'Común', 'Moscas que giran alrededor del jefe Hush.'),\n" +
                "('Blue Baby', 'Jefe', 'Dispara lágrimas en patrones complejos.'),\n" +
                "('Blastocyst', 'Mini-jefe', 'Se divide en formas más pequeñas al ser derrotado.'),\n" +
                "('Pale Fatty', 'Común', 'Enemigos grandes que disparan lágrimas de sangre.'),\n" +
                "('Rebounder', 'Común', 'Saltan hacia las paredes y rebotan hacia el jugador.'),\n" +
                "('Burning Gaper', 'Común', 'Versiones incendiarias de los Gapers que explotan al morir.'),\n" +
                "('Sticky Sack', 'Trampa', 'Se pega a las paredes y lanza arañas pequeñas.'),\n" +
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
                "('Satan''s Leech', 'Especial', 'Pequeños enemigos que se adhieren al jugador para robar salud.'),\n" +
                "('Red Poop Fly', 'Común', 'Moscas que orbitan alrededor de residuos rojos.'),\n" +
                "('Skull Turret', 'Trampa', 'Enemigos estacionarios que disparan lágrimas en direcciones fijas.'),\n" +
                "('Bloody Knight', 'Protegido', 'Versión mejorada del Knight, más rápida y agresiva.'),\n" +
                "('Cursed Maw', 'Especial', 'Lanza lágrimas oscuras que ralentizan al jugador.'),\n" +
                "('Toxic Leech', 'Común', 'Se explota en veneno al ser derrotado.'),\n" +
                "('Creepy Charger', 'Ágil', 'Cargan hacia el jugador de manera errática.'),\n" +
                "('Evil Eye', 'Especial', 'Dispara lágrimas rastreadoras con un rastro venenoso.'),\n" +
                "('Dank Maggot', 'Común', 'Versión oscura de los Maggots, más rápida.'),\n" +
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
                "('Blastocyst', 'Mini-jefe', 'Se divide en formas más pequeñas al ser derrotado.'),\n" +
                "('Teratoma', 'Mini-jefe', 'Similar a Fistula, pero más peligroso.'),\n" +
                "('Pin', 'Mini-jefe', 'Rápido enemigo que se oculta bajo tierra y salta para atacar.'),\n" +
                "('Daddy Long Legs', 'Jefe', 'Un pie gigante que aplasta al jugador desde arriba.'),\n" +
                "('Triachnid', 'Jefe', 'Versión más agresiva de Daddy Long Legs.'),\n" +
                "('Satan', 'Jefe', 'El jefe final en Sheol, ataca con diversos patrones.'),\n" +
                "('Fallen', 'Mini-jefe', 'Versión más pequeña de Satan, ataca con láseres y proyectiles.'),\n" +
                "('Greedling', 'Común', 'Versión más pequeña y rápida de Greed.'),\n" +
                "('Dopleganger', 'Común', 'Imita los movimientos y ataques del jugador.'),\n" +
                "('Holy Leech', 'Especial', 'Enemigos que curan a otros enemigos al tocarlos.'),\n" +
                "('Globin', 'Común', 'Pequeños enemigos que se regeneran al ser derrotados.'),\n" +
                "('Leaper', 'Común', 'Saltan hacia el jugador desde largas distancias.'),\n" +
                "('Knight', 'Protegido', 'Enemigos blindados que son invulnerables por el frente.'),\n" +
                "('Psy Tumor', 'Común', 'Dispara lágrimas rastreadoras que persiguen al jugador.'),\n" +
                "('Dip', 'Común', 'Pequeñas criaturas de excremento que se lanzan hacia el jugador.'),\n" +
                "('Nerve Ending', 'Común', 'Disparan lágrimas desde posiciones estáticas.'),\n" +
                "('Mom''s Hand', 'Trampa', 'Una mano que cae desde arriba para agarrar al jugador.'),\n" +
                "('Krampus', 'Mini-jefe', 'Ataca con un rayo láser y proyectiles.'),\n" +
                "('Steven', 'Mini-jefe', 'Una versión más pequeña y rápida de Gemini.'),\n" +
                "('Turdy', 'Común', 'Pequeñas criaturas de excremento que dejan residuos dañinos.'),\n" +
                "('Camo Kid', 'Común', 'Enemigos que se vuelven invisibles hasta acercarse al jugador.');\n");




        // Tabla de objetos
        db.execSQL("create table Objetos(id Integer primary key autoincrement, nombre text, rareza text, descripcion text, desbloqueado boolean default false)")
        db.execSQL("INSERT INTO Objetos (nombre, rareza, descripcion) VALUES\n" +
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
                "('Pisces', 'Común', 'Incrementa retroceso de lágrimas y velocidad de disparo.'),\n" +
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
                "('Placenta', 'Común', 'Regenera vida lentamente y aumenta corazones.'),\n" +
                "('Mulligan', 'Raro', 'Las lágrimas tienen probabilidad de generar moscas aliadas.'),\n" +
                "('Fly', 'Común', 'Una mosca básica que vuela hacia el jugador.'),\n" +
                "('Red Fly', 'Común', 'Mosca más rápida que sigue al jugador.'),\n" +
                "('Boom Fly', 'Común', 'Mosca que explota al ser derrotada.'),\n" +
                "('Pooter', 'Común', 'Mosca que dispara lágrimas en ráfagas cortas.'),\n" +
                "('Mulligan', 'Común', 'Genera varias moscas pequeñas al ser derrotado.'),\n" +
                "('Globin', 'Común', 'Corre hacia el jugador y se regenera si no es destruido.'),\n" +
                "('Clotty', 'Común', 'Dispara lágrimas en varias direcciones.'),\n" +
                "('Gusher', 'Común', 'Deja un charco de sangre al morir.'),\n" +
                "('Charger', 'Común', 'Corre rápidamente hacia el jugador.'),\n" +
                "('Fatty', 'Común', 'Enemigo grande que lanza lágrimas de sangre.'),\n" +
                "('Leaper', 'Común', 'Salta rápidamente hacia el jugador.'),\n" +
                "('Knight', 'Raro', 'Solo puede ser dañado por la espalda.'),\n" +
                "('Vis', 'Raro', 'Flota y dispara rayos de sangre.'),\n" +
                "('Host', 'Común', 'Se protege con una concha y dispara cuando se descubre.'),\n" +
                "('Hopper', 'Común', 'Salta erráticamente por la habitación.'),\n" +
                "('Black Charger', 'Común', 'Versión más rápida y peligrosa de Charger.'),\n" +
                "('Dople', 'Raro', 'Imita los movimientos del jugador.'),\n" +
                "('Bomb Fly', 'Común', 'Explota al morir.'),\n" +
                "('Tritagonist', 'Legendario', 'Dispara ráfagas triples de lágrimas espectrales.'),\n" +
                "('Gurgling', 'Raro', 'Deja un rastro de sangre mientras persigue al jugador.'),\n" +
                "('Maggot', 'Común', 'Se mueve en zigzag hacia el jugador.'),\n" +
                "('Walking Host', 'Común', 'Camina lentamente y dispara al descubrirse.'),\n" +
                "('Eternal Fly', 'Raro', 'Mosca inmortal que protege a otros enemigos.'),\n" +
                "('Evil Twin', 'Legendario', 'Imita al jugador pero con ataques más poderosos.'),\n" +
                "('Sack', 'Común', 'Enemigo inmóvil que lanza proyectiles al azar.'),\n" +
                "('Spity', 'Común', 'Escupe proyectiles de veneno al jugador.'),\n" +
                "('Bony', 'Raro', 'Esqueleto que dispara huesos al jugador.'),\n" +
                "('Flesh Maw', 'Común', 'Mordida lenta pero letal.'),\n" +
                "('Spider', 'Común', 'Salta hacia el jugador.'),\n" +
                "('Trite', 'Común', 'Pequeña araña que salta en grandes distancias.'),\n" +
                "('Swarm Spider', 'Común', 'Genera varias arañas pequeñas al morir.'),\n" +
                "('Explosive Diarrhea', 'Legendario', 'Deja rastros explosivos detrás de él.'),\n" +
                "('Tarboy', 'Común', 'Deja un rastro de alquitrán que ralentiza al jugador.'),\n" +
                "('Blubber', 'Común', 'Enemigo grande y lento que explota al morir.'),\n" +
                "('Floating Knight', 'Raro', 'Solo puede ser dañado desde atrás y dispara proyectiles.'),\n" +
                "('Bloody Charger', 'Raro', 'Cargador más agresivo que deja rastros de sangre.'),\n" +
                "('Bomb Leech', 'Raro', 'Corre hacia el jugador y explota al contacto.'),\n" +
                "('Spiteful Spirit', 'Legendario', 'Fantasma que dispara proyectiles espectrales.'),\n" +
                "('Fatty Jr.', 'Común', 'Enemigo pequeño y rápido que deja un charco de sangre.'),\n" +
                "('Creep', 'Común', 'Lentamente se acerca al jugador dejando rastros de veneno.'),\n" +
                "('Burning Gaper', 'Raro', 'Versión de fuego que explota al morir.'),\n" +
                "('Meatball', 'Común', 'Rueda por la habitación a gran velocidad.'),\n" +
                "('Lil'' Chub', 'Raro', 'Dispara rápidamente proyectiles en línea recta.'),\n" +
                "('Silkworm', 'Común', 'Se mueve rápidamente en línea recta hacia el jugador.'),\n" +
                "('Brimstone Keeper', 'Legendario', 'Dispara rayos de Brimstone en varias direcciones.'),\n" +
                "('Maul', 'Común', 'Ataque cuerpo a cuerpo rápido.'),\n" +
                "('Fat Bat', 'Común', 'Vuela lentamente y lanza proyectiles de sangre.'),\n" +
                "('Flaming Hopper', 'Raro', 'Versión en llamas del Hopper.'),\n" +
                "('Dark Maw', 'Común', 'Mordida más lenta pero con más alcance.'),\n" +
                "('Bloodshot Eye', 'Raro', 'Flota alrededor de la habitación disparando lágrimas.'),\n" +
                "('Round Worm', 'Común', 'Se mueve en línea recta y dispara lágrimas en varias direcciones.'),\n" +
                "('Plague Cyst', 'Común', 'Explota en proyectiles de veneno al ser golpeado.'),\n" +
                "('Splasher', 'Común', 'Deja charcos venenosos al moverse.'),\n" +
                "('Grub', 'Común', 'Corre en línea recta hacia el jugador y explota.'),\n" +
                "('The Harbinger', 'Legendario', 'Flota sobre la habitación invocando pequeños enemigos.'),\n" +
                "('Skuzz', 'Común', 'Vuela por la habitación disparando proyectiles al azar.'),\n" +
                "('Cursed Knight', 'Legendario', 'Se protege constantemente, solo vulnerable en momentos breves.'),\n" +
                "('Bloody Pooter', 'Raro', 'Dispara proyectiles de sangre en ráfagas rápidas.'),\n" +
                "('Frost Charger', 'Raro', 'Carga hacia el jugador y deja un rastro congelante.'),\n" +
                "('Conjoined Twin', 'Legendario', 'Ataca en conjunto con otro enemigo, compartiendo salud.'),\n" +
                "('Maggot Knight', 'Raro', 'Se mueve erráticamente y se regenera si no es destruido.'),\n" +
                "('Leech', 'Común', 'Ataca al jugador y regenera salud al morder.'),\n" +
                "('Walking Bomb', 'Común', 'Explota al acercarse al jugador.'),\n" +
                "('Fat Leech', 'Común', 'Versión más grande del Leech que regenera más salud al atacar.'),\n" +
                "('Charged Maw', 'Raro', 'Mordida más rápida con rastros eléctricos.'),\n" +
                "('Psycho', 'Legendario', 'Enemigo volador que lanza poderosos proyectiles psíquicos.'),\n" +
                "('Flesh Crawler', 'Común', 'Se arrastra lentamente por el suelo pero lanza ráfagas de sangre.'),\n" +
                "('Toxic Knight', 'Legendario', 'Deja rastros de veneno y solo puede ser dañado desde atrás.'),\n" +
                "('Splitter', 'Raro', 'Al morir, se divide en dos enemigos más pequeños.'),\n" +
                "('Sanguine Spider', 'Común', 'Araña que lanza proyectiles sangrientos al saltar.'),\n" +
                "('Venomous Maw', 'Común', 'Deja rastros venenosos al morder.'),\n" +
                "('Crawler', 'Común', 'Se mueve rápidamente por el suelo y salta para atacar.'),\n" +
                "('Fire Worm', 'Raro', 'Dispara proyectiles de fuego al jugador.'),\n" +
                "('Necrotic Eye', 'Común', 'Flota y dispara proyectiles de veneno en todas las direcciones.'),\n" +
                "('Shrieker', 'Raro', 'Emite un grito que ralentiza al jugador y lanza proyectiles de sangre.'),\n" +
                "('Flesh Worm', 'Común', 'Se mueve rápido y deja rastros de carne.'),\n" +
                "('Cyclops', 'Raro', 'Enemigo grande que dispara un único pero poderoso proyectil.'),\n" +
                "('Demon Fly', 'Raro', 'Mosca negra que explota en veneno.'),\n" +
                "('Blood Spider', 'Común', 'Araña que deja charcos de sangre al moverse.'),\n" +
                "('Blood Maw', 'Común', 'Mordida rápida que deja rastros de sangre.'),\n" +
                "('Hexer', 'Legendario', 'Lanza proyectiles mágicos que siguen al jugador.'),\n" +
                "('Flamer', 'Común', 'Enemigo volador que dispara ráfagas de fuego.'),\n" +
                "('Undead Fly', 'Común', 'Mosca que regresa a la vida tras morir.'),\n" +
                "('Haunting Spirit', 'Raro', 'Desaparece y reaparece, lanzando proyectiles espectrales.'),\n" +
                "('Gargantuan Leech', 'Legendario', 'Leech gigante que drena grandes cantidades de salud al atacar.'),\n" +
                "('Pest', 'Común', 'Enemigo que deja nubes de veneno al moverse.'),\n" +
                "('Rotten Maw', 'Común', 'Mordida venenosa que deja charcos de veneno.'),\n" +
                "('Feral Beast', 'Raro', 'Corre rápidamente y ataca con ferocidad al jugador.'),\n" +
                "('Toxic Charger', 'Raro', 'Carga dejando rastros de veneno por toda la habitación.');");

        // Tabla de Usuarios_Enemigos
        db.execSQL("create table Usuarios_Enemigos(id Integer primary key autoincrement, id_usuario Integer not null, id_enemigo Integer not null, desbloqueado boolean default 0, foreign key(id_usuario) references Usuarios(id) on delete cascade, foreign key(id_enemigo) references Enemigos(id) on delete cascade)")

        // Tabla de Usuarios_Objetos
        db.execSQL("create table Usuarios_Objetos(id Integer primary key autoincrement, id_usuario Integer not null, id_objeto Integer not null, desbloqueado boolean default 0, foreign key(id_usuario) references Usuarios(id) on delete cascade, foreign key(id_objeto) references Objetos(id) on delete cascade)")

        // Tabla de comentarios
        db.execSQL("create table Comentarios(id Integer primary key autoincrement, id_usuario Integer not null, id_objeto Integer default null, id_enemigo Integer default null , comentario text not null, fecha datetime default current_timestamp, foreign key(id_usuario) references Usuarios(id) on delete cascade, foreign key(id_objeto) references Objetos(id) on delete cascade, foreign key(id_enemigo) references Enemigos(id) on delete cascade)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // No usaremos este método
    }
}