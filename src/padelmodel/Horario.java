package src.padelmodel;

import java.io.Serializable;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String inicio; // Almacena fecha y hora de inicio en formato dd/MM/yyyy HH:mm
    private String fin;    // Almacena solo la hora de fin en formato HH:mm

    /**
     * Constructor que toma fecha, hora inicio y hora fin separadas
     */
    public Horario(String fecha, String horaInicio, String horaFin) {
        this.inicio = fecha + " " + horaInicio;
        this.fin = horaFin;
    }

    /**
     * Constructor que toma directamente la fecha+hora inicio y hora fin
     */
    public Horario(String fechaHoraInicio, String horaFin) {
        this.inicio = fechaHoraInicio;
        this.fin = horaFin;
    }

    /**
     * Devuelve una representación del horario en formato dd/MM/yyyy HH:mm - HH:mm
     */
    @Override
    public String toString() {
        return inicio + " - " + fin;
    }

    /**
     * Valida si una fecha y hora tiene un formato correcto
     * @param fecha formato esperado: dd/MM/yyyy HH:mm
     * @return true si el formato es válido
     */
    public boolean esFechaValida(String fecha) {
        try {
            String[] partes = fecha.split(" ");
            if (partes.length != 2) {
                return false;
            }

            String[] fechaParts = partes[0].split("[/-]");
            String[] horaParts = partes[1].split(":");

            if (fechaParts.length != 3 || horaParts.length != 2) {
                return false;
            }

            int dia = Integer.parseInt(fechaParts[0]);
            int mes = Integer.parseInt(fechaParts[1]);
            int anio = Integer.parseInt(fechaParts[2]);
            int hora = Integer.parseInt(horaParts[0]);
            int minuto = Integer.parseInt(horaParts[1]);

            return dia >= 1 && dia <= 31 &&
                    mes >= 1 && mes <= 12 &&
                    anio >= 2024 &&
                    hora >= 0 && hora <= 23 &&
                    minuto >= 0 && minuto <= 59;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Devuelve la parte de la fecha
     */
    public String getFecha() {
        return inicio.split(" ")[0];
    }

    /**
     * Devuelve la parte de la hora de inicio
     */
    public String getHoraInicio() {
        String[] partes = inicio.split(" ");
        return partes.length > 1 ? partes[1] : "";
    }

    /**
     * Devuelve la hora de fin
     */
    public String getHoraFin() {
        return fin;
    }
}