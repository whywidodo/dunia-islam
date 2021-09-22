package labs.nusantara.duniaislam;

public class DataDoaHarian {
    String id;
    String nomor;
    String title;
    String latin;
    String arabic;
    String translation;

    public DataDoaHarian() {
    }

    public DataDoaHarian(String id, String nomor, String title, String latin, String arabic, String translation) {
        this.id = id;
        this.nomor = nomor;
        this.title = title;
        this.latin = latin;
        this.arabic = arabic;
        this.translation = translation;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getArabic() {
        return arabic;
    }

    public void setArabic(String arabic) {
        this.arabic = arabic;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
