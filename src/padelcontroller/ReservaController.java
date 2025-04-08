package src.padelcontroller;

import src.padelmodel.*;
import src.padelutils.ArchivoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservaController {
    private List<Reserva> reservas = new ArrayList<>();

    public void crearReserva(Scanner sc) {
        System.out.println("=== CREAR RESERVA ===");

        // Aquí puedes adaptar estos datos para pedir fecha/hora real
        Horario horario = new Horario("18:00", "19:00");

        System.out.print("Tipo de pista (INDOOR/OUTDOOR): ");
        String tipo = sc.nextLine().toUpperCase();
        Tipo_pista_padel pista = Tipo_pista_padel.valueOf(tipo);

        System.out.print("Ubicación: ");
        String ubic = sc.nextLine();
        Ubicacion ubicacion = new Ubicacion(ubic);

        ArrayList<Participante> participantes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.print("Nombre del participante " + (i + 1) + ": ");
            String nombre = sc.nextLine();
            participantes.add(new Participante(nombre));
        }

        Reserva nueva = new Reserva(horario, pista, participantes, ubicacion);
        reservas.add(nueva);
        System.out.println("Reserva creada correctamente.");
    }

    public void mostrarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            int i = 1;
            for (Reserva r : reservas) {
                System.out.println("Reserva #" + i++);
                System.out.println(r);
                System.out.println("---------------");
            }
        }
    }

    public void modificarReserva(Scanner sc) {
        mostrarReservas();
        if (reservas.isEmpty()) return;

        System.out.print("Número de reserva a modificar: ");
        int index = sc.nextInt() - 1;
        sc.nextLine(); // limpiar buffer

        if (index >= 0 && index < reservas.size()) {
            System.out.println("Modificando reserva...");
            crearReserva(sc);
            reservas.set(index, reservas.remove(reservas.size() - 1));
            System.out.println("Reserva modificada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void eliminarReserva(Scanner sc) {
        mostrarReservas();
        if (reservas.isEmpty()) return;

        System.out.print("Número de reserva a eliminar: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index >= 0 && index < reservas.size()) {
            reservas.remove(index);
            System.out.println("Reserva eliminada.");
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public void guardarEnArchivo() {
        ArchivoUtils.guardarReservas(reservas);
    }

    public void cargarDesdeArchivo() {
        List<Reserva> cargadas = ArchivoUtils.cargarReservas();
        if (cargadas != null) {
            reservas = cargadas;
            System.out.println("Reservas cargadas correctamente.");
        } else {
            System.out.println("No se pudo cargar el archivo.");
        }
    }
}
