//Representa os dados de utilização de um elevador em um determinado momento.
public class ElevadorData {
    private final int andar;
    private final char elevador;
    private final char turno;

    public ElevadorData(int andar, char elevador, char turno) {
        this.andar = andar;
        this.elevador = elevador;
        this.turno = turno;
    }

    public int getAndar() {
        return andar;
    }

    public char getElevador() {
        return elevador;
    }

    public char getTurno() {
        return turno;
    }
} 