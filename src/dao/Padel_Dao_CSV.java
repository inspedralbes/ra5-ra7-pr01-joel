package src.dao;

import src.padelmodel.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Padel_Dao_CSV implements Padel_Dao {
    private static final Path path = Path.of("files", "reservas.csv");
    private final File file = new File(path.toString());

    public Padel_Dao_CSV() {
        // Asegurar que existe el directorio
        File directorio = new File("files");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        try {
            // Crear el directorio si no existe
            File directorio = new File("files");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            FileWriter myWriter = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            bw.write(reservaToCSV(reserva));
            bw.newLine();
            bw.close();
            System.out.println("Reserva guardada correctamente en el archivo CSV.");
        } catch (IOException e) {
            System.out.println("Error al guardar la reserva en CSV.");
            e.printStackTrace();
        }
    }

    private String reservaToCSV(Reserva reserva) {
        StringBuilder csv = new StringBuilder();

        // Horario
        csv.append(reserva.getHorario().toString()).append(",");

        // Tipo de pista
        csv.append(reserva.getPista().toString()).append(",");

        // Ubicación
        csv.append(reserva.getUbicacion().toString()).append(",");

        // Participantes - formato: nombre1;nombre2;nombre3;nombre4
        for (Participante p : reserva.getParticipantes()) {
            csv.append(p.getNombre()).append(";");
        }

        return csv.toString();
    }

    @Override
    public List<Reserva> llegirPadel() {
        List<Reserva> reservas = new ArrayList<>();

        try {
            // Verificar si el archivo existe
            if (!file.exists()) {
                System.out.println("El archivo CSV no existe. No hay reservas para leer.");
                return reservas;
            }

            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Reserva reserva = reservaFromCSV(data);
                if (reserva != null) {
                    reservas.add(reserva);
                }
            }
            myReader.close();
            System.out.println("Reservas leídas correctamente desde CSV.");
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer las reservas desde CSV.");
            e.printStackTrace();
        }

        return reservas;
    }

    private Reserva reservaFromCSV(String data) {
        try {
            String[] campos = data.split(",");

            if (campos.length < 4) {
                System.out.println("Formato de CSV inválido para una reserva.");
                return null;
            }

            // Procesar horario (formato: inicio - fin)
            String[] horarioPartes = campos[0].split(" - ");
            Horario horario = new Horario(horarioPartes[0], horarioPartes[1]);

            // Procesar tipo de pista
            Tipo_pista_padel tipoPista = Tipo_pista_padel.valueOf(campos[1]);

            // Procesar ubicación
            Ubicacion ubicacion = new Ubicacion(campos[2]);

            // Procesar participantes
            ArrayList<Participante> participantes = new ArrayList<>();
            String[] participantesNombres = campos[3].split(";");
            for (String nombre : participantesNombres) {
                if (!nombre.isEmpty()) {
                    participantes.add(new Participante(nombre));
                }
            }

            return new Reserva(horario, tipoPista, participantes, ubicacion);
        } catch (Exception e) {
            System.out.println("Error al convertir una línea de CSV a una reserva.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Reserva servirPadel() {
        List<Reserva> reservas = llegirPadel();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas para servir.");
            return null;
        }

        // Obtenemos la reserva más antigua (primera en la lista)
        Reserva reservaMasAntigua = reservas.get(0);

        // Eliminamos esa reserva del archivo
        eliminarReserva(reservaMasAntigua);

        return reservaMasAntigua;
    }

    // Método auxiliar para eliminar una reserva específica
    private void eliminarReserva(Reserva reservaAEliminar) {
        List<Reserva> todasLasReservas = llegirPadel();

        // Crear un archivo temporal
        File tempFile = new File(path.toString() + ".tmp");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            boolean primeraReservaEncontrada = false;

            for (Reserva reserva : todasLasReservas) {
                // Si es la primera reserva que coincide y aún no la hemos eliminado
                if (reserva.toString().equals(reservaAEliminar.toString()) && !primeraReservaEncontrada) {
                    primeraReservaEncontrada = true;
                    continue; // Omitimos esta reserva
                }

                // Escribimos todas las demás reservas
                writer.write(reservaToCSV(reserva));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar la reserva.");
            e.printStackTrace();
            return;
        }

        // Reemplazar el archivo original con el temporal
        if (!file.delete()) {
            System.out.println("No se pudo eliminar el archivo original.");
            return;
        }

        if (!tempFile.renameTo(file)) {
            System.out.println("No se pudo renombrar el archivo temporal.");
        }
    }
}