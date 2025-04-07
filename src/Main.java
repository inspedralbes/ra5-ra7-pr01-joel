package src;

import src.padelmodel.*;

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
        Horario ho = new Horario(horari);
        return ho;
    }


    public static void mostrarInfolugar (){
        System.out.println("Ahora vamos a seleccionar el lugar donde queremos jugar. Porfavor seleccione uno" +
                " de los clubs que hay en la lista:");
        System.out.println("1. Club Natció Montjuic (Suplemento de 2€)\n2.Go Go Padel (Suplemento de 2€)\n3.Padel Top\n4.Forus Picornell\n5.LET's PADEL" +
                "\n6.Padel Delfos");




    }


    public static Ubicacion obtenerUbicacion(int pista){
        ArrayList<String> lugara = new ArrayList<String>();
        lugara.add("Club Natció Montjuic");
        lugara.add("Go Go Padel");
        lugara.add("Padel Top");
        lugara.add("Forus Picornell");
        lugara.add("LET's PADEL");
        lugara.add("Padel Delfos");
        Ubicacion ub = new Ubicacion(lugara.get(pista -1));
        return ub;
    }


    public static void mostrarError (){
        System.out.println("Porfavor seleccione una de las opciones correctamente. Gracias.");
    }


    public static void mostrarInfotipo (){
        System.out.println("Selecciona el tipo de pista en la que quieres jugar:");
        System.out.println("1. INDOOR DE CEMENTO (SUPLEMENTO DE 3€)\n2. INDOOR DE CRISTAL (SUPLEMENTO DE 3€)\n3. EXTERIOR DE CEMENTO\n4. EXTERIOR DE CRISTAL");
    }


    public static Tipo_pista_padel obtenerTipo (int pista){
        ArrayList<String> tipo = new ArrayList<String>();
        tipo.add("INDOOR DE CEMENTO");
        tipo.add("INDOOR DE CRISTAL");
        tipo.add("EXTERIOR DE CEMENTO");
        tipo.add("EXTERIOR DE CRISTAL");
        Tipo_pista_padel tp = new Tipo_pista_padel(tipo.get(pista -1));
        return tp;
    }


    public static void mostrarInfoparticipantes(){
        System.out.println("Para acabar, necesito saber los nombres de los participantes:");
    }


    public static ArrayList<Participante> obtenerParticipantes() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Participante> participantes = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.println("Añade el nombre y apellido del participante num " + i);
            String persona = scanner.nextLine();
            participantes.add(new Participante(persona));
        }
        return participantes;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mostrarIntroduccion();
        //SELECCIONAR EL HORARIO DE LA PISTA
        Horario ho = obtenerHorario(scanner.next(), scanner.next());
        int precio = 10;
        //SELECCIONAR EL LUGAR DE LA PISTA
        mostrarInfolugar();
        int sitio = scanner.nextInt();
        while (sitio < 1 || sitio > 6){
            mostrarError();
            sitio = scanner.nextInt();
        }
        if (sitio == 1 || sitio == 2){
            precio = precio +2;
        }
        Ubicacion ub = obtenerUbicacion(sitio);
        //SELECCIONAR EL TIPO DE PISTA
        mostrarInfotipo();
        int tipo = scanner.nextInt();


        while (tipo < 1 || tipo > 4){
            mostrarError();
            tipo = scanner.nextInt();
        }
        if (tipo == 1 || tipo == 2){
            precio = precio +3;
        }
        Tipo_pista_padel tp = obtenerTipo(tipo);


        //SELECCIONAR LOS PARTICIPANTES
        mostrarInfoparticipantes();
        ArrayList<Participante> lista = obtenerParticipantes();
        Reserva reserva = new Reserva(ho,tp,lista,ub);


        System.out.println("CONFIRMACION DE LA RESERVA:\n \nDia y la hora de la reserva: " + ho.getHorario() +"\nLugar de la reserva: "+ ub.getUbicacion());
        System.out.println("Tipo de pista: " + tp.getTipo_pista_padel() + "\nLista de participantes: " + lista + "\nEl precio total es: " + precio +"€");
    }
}
