package src.dao;

import src.database.ConexionDB;
import src.database.DataConnection;
import src.padelmodel.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Padel_Dao_MySQL implements Padel_Dao {
    // Use the connection parameters from DataConnection class
    private ConexionDB conDB;

    public Padel_Dao_MySQL() {
        this.conDB = ConexionDB.getInstance(); // Inicializar el objeto conDB
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        String sqlReserva = "INSERT INTO reservas (fecha, hora_inicio, hora_fin, ubicacion_id, tipo_pista_id) VALUES (?, ?, ?, ?, ?)";
        String sqlParticipante = "INSERT INTO participantes (nombre) VALUES (?)";
        String sqlRelacion = "INSERT INTO reserva_participante (reserva_id, participante_id) VALUES (?, ?)";

        try (Connection conn = conDB.getConnection()) {
            conn.setAutoCommit(false); // Iniciamos una transacción

            try {
                // Procesamos el horario para obtener fecha y horas
                String horarioString = reserva.getHorario().toString();
                String[] horarioParts = horarioString.split(" - ");

                // El formato que viene del Horario podría ser: "dd/MM/yyyy HH:mm - HH:mm"
                String fechaHoraInicio = horarioParts[0];

                // Separamos la fecha y la hora
                String[] fechaHoraParts = fechaHoraInicio.split(" ");
                String fechaStr = fechaHoraParts[0];  // dd/MM/yyyy
                String horaInicioStr = fechaHoraParts[1];  // HH:mm
                String horaFinStr = horarioParts[1];  // HH:mm

                // Parseamos la fecha
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fecha = dateFormat.parse(fechaStr);
                java.sql.Date fechaSql = new java.sql.Date(fecha.getTime());

                // Parseamos la hora de inicio
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                java.util.Date horaInicio = timeFormat.parse(horaInicioStr);
                java.sql.Time horaInicioSql = new java.sql.Time(horaInicio.getTime());

                // Parseamos la hora de fin
                java.util.Date horaFin = timeFormat.parse(horaFinStr);
                java.sql.Time horaFinSql = new java.sql.Time(horaFin.getTime());

                // Obtenemos ID de la ubicación
                int ubicacionId = getUbicacionId(conn, reserva.getUbicacion().toString());

                // Obtenemos ID del tipo de pista
                int tipoPistaId = getTipoPistaId(conn, reserva.getPista().toString());

                // Insertamos la reserva
                PreparedStatement stmtReserva = conn.prepareStatement(sqlReserva, Statement.RETURN_GENERATED_KEYS);
                stmtReserva.setDate(1, fechaSql);
                stmtReserva.setTime(2, horaInicioSql);
                stmtReserva.setTime(3, horaFinSql);
                stmtReserva.setInt(4, ubicacionId);
                stmtReserva.setInt(5, tipoPistaId);
                stmtReserva.executeUpdate();

                // Obtenemos el ID de la reserva insertada
                ResultSet generatedKeys = stmtReserva.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new SQLException("No se pudo obtener el ID de la reserva creada.");
                }
                int reservaId = generatedKeys.getInt(1);

                // Insertamos los participantes
                PreparedStatement stmtParticipante = conn.prepareStatement(sqlParticipante, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmtRelacion = conn.prepareStatement(sqlRelacion);

                for (Participante participante : reserva.getParticipantes()) {
                    stmtParticipante.setString(1, participante.getNombre());
                    stmtParticipante.executeUpdate();

                    ResultSet participanteKeys = stmtParticipante.getGeneratedKeys();
                    if (participanteKeys.next()) {
                        int participanteId = participanteKeys.getInt(1);

                        // Relacionamos participante con reserva
                        stmtRelacion.setInt(1, reservaId);
                        stmtRelacion.setInt(2, participanteId);
                        stmtRelacion.executeUpdate();
                    }
                }

                conn.commit(); // Confirmamos la transacción
                System.out.println("Reserva guardada correctamente en MySQL.");

            } catch (SQLException | ParseException e) {
                conn.rollback(); // Revertimos en caso de error
                System.out.println("Error al guardar la reserva en MySQL: " + e.getMessage());
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Obtiene el ID de la ubicación por su nombre
    private int getUbicacionId(Connection conn, String nombreUbicacion) throws SQLException {
        String sql = "SELECT id FROM ubicaciones WHERE nombre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nombreUbicacion);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        } else {
            // Si no existe, creamos la ubicación
            return insertarUbicacion(conn, nombreUbicacion);
        }
    }

    // Inserta una nueva ubicación y devuelve su ID
    private int insertarUbicacion(Connection conn, String nombreUbicacion) throws SQLException {
        String sql = "INSERT INTO ubicaciones (nombre) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nombreUbicacion);
        stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("No se pudo crear la ubicación: " + nombreUbicacion);
        }
    }

    // Obtiene el ID del tipo de pista por su nombre
    private int getTipoPistaId(Connection conn, String tipoPista) throws SQLException {
        String sql = "SELECT id FROM tipos_pista WHERE nombre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, tipoPista);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        } else {
            // Si no existe, creamos el tipo de pista
            return insertarTipoPista(conn, tipoPista);
        }
    }

    // Inserta un nuevo tipo de pista y devuelve su ID
    private int insertarTipoPista(Connection conn, String tipoPista) throws SQLException {
        String sql = "INSERT INTO tipos_pista (nombre) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, tipoPista);
        stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("No se pudo crear el tipo de pista: " + tipoPista);
        }
    }

    @Override
    public List<Reserva> llegirPadel() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.id, r.fecha, r.hora_inicio, r.hora_fin, tp.nombre as tipo_pista, u.nombre as ubicacion " +
                "FROM reservas r " +
                "JOIN tipos_pista tp ON r.tipo_pista_id = tp.id " +
                "JOIN ubicaciones u ON r.ubicacion_id = u.id " +
                "ORDER BY r.fecha, r.hora_inicio";

        try (Connection conn = conDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int reservaId = rs.getInt("id");
                Date fecha = rs.getDate("fecha");
                Time horaInicio = rs.getTime("hora_inicio");
                Time horaFin = rs.getTime("hora_fin");
                String tipoPista = rs.getString("tipo_pista");
                String ubicacion = rs.getString("ubicacion");

                // Formateamos fecha y hora como lo espera la clase Horario
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

                String fechaStr = formatoFecha.format(fecha);
                String horaInicioStr = formatoHora.format(horaInicio);
                String horaFinStr = formatoHora.format(horaFin);

                // Creamos objeto Horario
                Horario horario = new Horario(fechaStr + " " + horaInicioStr, horaFinStr);

                // Creamos objeto Ubicacion
                Ubicacion ubicacionObj = new Ubicacion(ubicacion);

                // Creamos objeto Tipo_pista_padel
                Tipo_pista_padel tipoPistaObj = Tipo_pista_padel.valueOf(tipoPista);

                // Obtenemos los participantes de esta reserva
                ArrayList<Participante> participantes = getParticipantesPorReserva(conn, reservaId);

                // Creamos objeto Reserva
                Reserva reserva = new Reserva(horario, tipoPistaObj, participantes, ubicacionObj);
                reservas.add(reserva);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer las reservas de MySQL: " + e.getMessage());
            e.printStackTrace();
        }

        return reservas;
    }

    // Obtiene los participantes asociados a una reserva
    private ArrayList<Participante> getParticipantesPorReserva(Connection conn, int reservaId) throws SQLException {
        ArrayList<Participante> participantes = new ArrayList<>();
        String sql = "SELECT p.nombre FROM participantes p " +
                "JOIN reserva_participante rp ON p.id = rp.participante_id " +
                "WHERE rp.reserva_id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, reservaId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            participantes.add(new Participante(nombre));
        }

        return participantes;
    }

    @Override
    public Reserva servirPadel() {
        List<Reserva> reservas = llegirPadel();
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas para servir.");
            return null;
        }

        // Obtenemos la reserva más antigua (la primera en la lista)
        Reserva reservaMasAntigua = reservas.get(0);

        // Eliminamos esa reserva de la base de datos
        eliminarReserva(reservaMasAntigua);

        return reservaMasAntigua;
    }

    // Método auxiliar para eliminar una reserva
    private void eliminarReserva(Reserva reserva) {
        String sql = "DELETE r FROM reservas r " +
                "JOIN ubicaciones u ON r.ubicacion_id = u.id " +
                "WHERE DATE_FORMAT(r.fecha, '%d/%m/%Y') = ? " +
                "AND DATE_FORMAT(r.hora_inicio, '%H:%i') = ? " +
                "AND u.nombre = ?";

        try (Connection conn = conDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Extraemos la información de la reserva
            String[] horarioParts = reserva.getHorario().toString().split(" - ");
            String[] fechaHoraParts = horarioParts[0].split(" ");

            String fecha = fechaHoraParts[0];
            String hora = fechaHoraParts.length > 1 ? fechaHoraParts[1] : "00:00";

            stmt.setString(1, fecha);
            stmt.setString(2, hora);
            stmt.setString(3, reserva.getUbicacion().toString());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Reserva eliminada correctamente.");
            } else {
                System.out.println("No se encontró la reserva para eliminar.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar la reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }
}