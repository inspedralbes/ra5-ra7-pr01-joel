package src.padelutils;

import src.padelmodel.Reserva;

import java.io.*;
import java.util.List;

public class ArchivoUtils {
    private static final String ARCHIVO = "reservas.dat";

    public static void guardarReservas(List<Reserva> reservas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(reservas);
            System.out.println("Reservas guardadas en archivo.");
        } catch (IOException e) {
            System.out.println("Error al guardar reservas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Reserva> cargarReservas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (List<Reserva>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar reservas: " + e.getMessage());
            return null;
        }
    }
}
