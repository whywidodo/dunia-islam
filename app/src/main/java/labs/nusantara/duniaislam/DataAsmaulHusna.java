package labs.nusantara.duniaislam;

public class DataAsmaulHusna {
    String id;
    String nomor;
    String latin;
    String arabic;
    String translation_id;
    String translation_en;

    public DataAsmaulHusna() {
    }

    public DataAsmaulHusna(String id, String nomor, String latin, String arabic, String translation_id, String translation_en) {
        this.id = id;
        this.nomor = nomor;
        this.latin = latin;
        this.arabic = arabic;
        this.translation_id = translation_id;
        this.translation_en = translation_en;
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

    public String getTranslation_id() {
        return translation_id;
    }

    public void setTranslation_id(String translation_id) {
        this.translation_id = translation_id;
    }

    public String getTranslation_en() {
        return translation_en;
    }

    public void setTranslation_en(String translation_en) {
        this.translation_en = translation_en;
    }
}
