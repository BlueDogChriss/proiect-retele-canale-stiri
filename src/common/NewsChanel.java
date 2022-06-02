package common;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NewsChanel implements Serializable {
    private String nume;
    private String descriere;
    private List<String> stiri=new ArrayList<>();
    private Socket creatorCanal;

    private List<Socket> clientiAbonati = new ArrayList<>();

    public NewsChanel(String nume, String descriere, Socket creatorCanal) {
        this.nume = nume;
        this.descriere = descriere;
        this.creatorCanal = creatorCanal;
    }

    public NewsChanel(String nume, String descriere) {
        this.nume = nume;
        this.descriere = descriere;
    }

    public NewsChanel(String nume, String descriere,List<String> stiri) {
        this.nume = nume;
        this.descriere = descriere;
        this.stiri=stiri;
    }
    
    public List<String> getStiri() {
    	return stiri;
    }
    
    public void addStiri(String n) {
    	this.stiri.add(n);
    }
    
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Socket getCreatorCanal() {
        return creatorCanal;
    }

    public void setCreatorCanal(Socket creatorCanal) {
        this.creatorCanal = creatorCanal;
    }

    public List<Socket> getClientiAbonati() {
        return clientiAbonati;
    }

    public void setClientiAbonati(List<Socket> clientiAbonati) {
        this.clientiAbonati = clientiAbonati;
    }
    
    public void addAbonati(Socket socket) {
    	this.clientiAbonati.add(socket);
    }

    public void removeAbonati(Socket socket) {
    	this.clientiAbonati.remove(socket);
    }
    
    public void adaugaClientAbonat(Socket client){
        this.clientiAbonati.add(client);
    }

    public void stergeClientAbonat(Socket client){
        this.clientiAbonati.remove(client);
    }

    @Override
    public String toString() {
        return "Canalul de stiri:" + nume + '\'' +
                ", descriere: '" + descriere + '\'' +
                ", creatororul canalului de stiri: " + creatorCanal +
                ", clientii abonati: " + clientiAbonati +
                ", stiri: "+stiri;
    }
}
