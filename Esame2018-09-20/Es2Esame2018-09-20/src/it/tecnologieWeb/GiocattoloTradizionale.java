public class GiocattoloTradizionale extends Giocattolo{
   
    private String nome;
    private String autore;
    private String marca;
    private String fasciaEta;
    private String modalitaUso;

    public GiocattoloTradizionale(String id, String nome, String autore, String marca, String fasciaEta, String modalitaUso){
        this.id=id;
        this.nome=nome;
        this.autore=autore;
        this.marca=marca;
        this.fasciaEta=fasciaEta;
        this.modalitaUso=modalitaUso;
    }

    @Override
    public String toString() {
        return "GiocattoloTradizionale [id="+this.id+", autore=" + autore + ", fasciaEta=" + fasciaEta + ", marca=" + marca
                + ", modalitaUso=" + modalitaUso + ", nome=" + nome + "]";
    }

}