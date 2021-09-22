package labs.nusantara.duniaislam;

public class DataNiatSholat {
    String id;
    String nomor;
    String nama;
    String arabic;
    String latin;
    String terjemahan;

    public DataNiatSholat(){
    }

    public DataNiatSholat(String id, String nomor, String nama, String arabic, String latin, String terjemahan) {
        this.id = id;
        this.nomor = nomor;
        this.nama = nama;
        this.arabic = arabic;
        this.latin = latin;
        this.terjemahan = terjemahan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getTerjemahan() {
        return terjemahan;
    }

    public void setTerjemahan(String terjemahan) {
        this.terjemahan = terjemahan;
    }
}
