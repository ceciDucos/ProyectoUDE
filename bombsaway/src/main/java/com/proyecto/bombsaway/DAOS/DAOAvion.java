package com.proyecto.bombsaway.DAOS;
import com.proyecto.bombsaway.clases.Avion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DAOAvion implements IDAOAvion  {
    private static List<Avion> DB = new ArrayList<>();

    @Override
    public int insertAvion(int id, Avion avion) {
        DB.add(new Avion(avion.getId(), avion.getPosicion().getEjeX(),avion.getPosicion().getEjeY(),
                avion.getPosicion().getAngulo()));
        return 1;
    }

    @Override
    public List<Avion> getListAviones() {
        return this.DB;
    }

//    @Override
//    public int insertAvion(Avion avion) {
//        return 0;
//    }

//    //ENTIDAD, TIPO DE DATO DEL ID
//
//    /*
//        @Query(value = "SELECT * FROM quote q WHERE q.quote = ?",
//           nativeQuery = true)
//    Quote findByQuote(String quote);
//}
//     */
//
//    @Query(value = "SELECT * FROM gonza_se_la_come g WHERE " +
//            "g.nombre_pute = ?",
//            nativeQuery = true)
//    EntidadAvion findByNombrePute(String nickname);



}