package org.example;

import java.sql.SQLException;

public class MyApp{
        public static void main(String[] args) {
                DbManager db;
                try {
                        db = new DbManager();
                        try{
                                for (Expire e: db.getExpire()) {
                                        System.out.println(e);
                                };
                        } catch (SQLException e){
                                System.err.println("Errore durante l'esecuzione della query");
                        }
                } catch (SQLException e) {
                        System.err.println("Connessione fallita.");
                } catch (ClassNotFoundException e) {
                        System.err.println("Classe non trovata");
                }

        }
}