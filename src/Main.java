package src;

import src.padelmodel.*;
import src.padelcontroller.*;
import src.padelutils.*;

import java.util.Scanner;
import java.util.ArrayList;


public class Main {


    public static void mostrarIntroduccion (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Buenos/as dias/tarde/noche, bienvenido a la aplicación de reservas para" +
                "jugar a padel. Recordar que el precio de la pista es de 10€ y depende del lugar o del tipo de pista\nel precio puede variar. Lo primero que tenemos que hacer es seleccionar la fecha y la hora a " +
                "la que queremos jugar.");
        System.out.println();
        System.out.println("Escribe la fecha en numeros (XX/XX/XXXX) y la hora (XX:XX):");


    }
    public static Horario obtenerHorario (String fecha, String hora){
        String horari = fecha + " " + hora;
        Horario ho = null;
        try {
            ho = new Horario(horari);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ho;
    }

    public static Ubicacion obtenerUbicacion(Scanner scanner) {
        System.out.println("Selecciona el lugar:");
        System.out.println("1. Club Natció Montjuic (+2€)");
        System.out.println("2. Go Go Padel (+2€)");
        System.out.println("3. Padel Top");
        System.out.println("4. Forus Picornell");
        System.out.println("5. LET's PADEL");
        System.out.println("6. Padel Delfos");

        int opcion;
        do {
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpia buffer
        } while (opcion < 1 || opcion > 6);

        String lugar = "";
        switch (opcion) {
            case 1:
                lugar = "Club Natció Montjuic";
                break;
            case 2:
                lugar = "Go Go Padel";
                break;
            case 3:
                lugar = "Padel Top";
                break;
            case 4:
                lugar = "Forus Picornell";
                break;
            case 5:
                lugar = "LET's PADEL";
                break;
            case 6:
                lugar = "Padel Delfos";
                break;
        }
        return new Ubicacion(lugar);
    }


    public static Tipo_pista_padel obtenerTipoPista(Scanner scanner) {
        System.out.println("Selecciona el tipo de pista:");
        System.out.println("1. INDOOR");
        System.out.println("2. OUTDOOR");

        int opcion;
        do {
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
        } while (opcion != 1 && opcion != 2);

        return (opcion == 1) ? Tipo_pista_padel.INDOOR : Tipo_pista_padel.OUTDOOR;
    }

    public static ArrayList<Participante> obtenerParticipantes(Scanner scanner) {
        ArrayList<Participante> lista = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.print("Nombre del participante " + i + ": ");
            String nombre = scanner.nextLine();
            lista.add(new Participante(nombre));
        }
        return lista;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservaController controller = new ReservaController();

        int opcion;
        do {
            System.out.println("\n=== MENÚ RESERVAS DE PÁDEL ===");
            System.out.println("1. Crear nueva reserva");
            System.out.println("2. Mostrar reservas");
            System.out.println("3. Modificar reserva");
            System.out.println("4. Eliminar reserva");
            System.out.println("5. Guardar en archivo");
            System.out.println("6. Cargar desde archivo");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    Horario horario = obtenerHorario(scanner);
                    Ubicacion ubicacion = obtenerUbicacion(scanner);
                    Tipo_pista_padel tipo = obtenerTipoPista(scanner);
                    ArrayList<Participante> participantes = obtenerParticipantes(scanner);
                    Reserva nuevaReserva = new Reserva(horario, tipo, participantes, ubicacion);
                    controller.agregarReserva(nuevaReserva);
                    System.out.println("\n✅ Reserva creada correctamente.");
                    System.out.println(nuevaReserva);
                }
                case 2 -> controller.mostrarReservas();
                case 3 -> controller.modificarReserva(scanner);
                case 4 -> controller.eliminarReserva(scanner);
                case 5 -> controller.guardarEnArchivo();
                case 6 -> controller.cargarDesdeArchivo();
                case 0 -> System.out.println("👋 Saliendo del programa.");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}

