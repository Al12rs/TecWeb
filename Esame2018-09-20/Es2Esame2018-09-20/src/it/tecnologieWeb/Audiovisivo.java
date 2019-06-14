public class Audiovisivo extends Giocattolo{

    private String titolo;
    private String autore;
    private String casaProduttrice;
    private String annoProduzione;
    private String fasciaEta;
    private String lingua;
    private String durata;

    public Audiovisivo(String id, String titolo, String autore, String casaProduttrice, String annoProduzione, String fasciaEta, String lingua, String durata){
        this.id=id;
        this.titolo=titolo;
        this.autore=autore;
        this.casaProduttrice=casaProduttrice;
        this.annoProduzione=annoProduzione;
        this.fasciaEta=fasciaEta;
        this.lingua=lingua;
        this.durata=durata;
    }

    @Override
    public String toString() {
        return "Audiovisivo [id="+this.id+", annoProduzione=" + annoProduzione + ", autore=" + autore + ", casaProduttrice="
                + casaProduttrice + ", durata=" + durata + ", fasciaEta=" + fasciaEta + ", lingua=" + lingua
                + ", titolo=" + titolo + "]";
    }
}