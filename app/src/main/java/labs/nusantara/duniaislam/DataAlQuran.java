package labs.nusantara.duniaislam;

public class DataAlQuran {
    String id;
    // Surah
    String listnomor;
    String listnamaSurah;
    String listarabSurah;
    String listartiSurah;

    // Ayat
    String nomor;
    String namaSurah;
    String arabSurah;
    String artiSurah;
    String arabAyat;
    String artiAyat;

    public DataAlQuran() {
    }

    public DataAlQuran(String id, String listnomor, String listnamaSurah, String listarabSurah, String listartiSurah, String nomor, String namaSurah, String arabSurah, String artiSurah, String arabAyat, String artiAyat) {
        this.id = id;
        this.listnomor = listnomor;
        this.listnamaSurah = listnamaSurah;
        this.listarabSurah = listarabSurah;
        this.listartiSurah = listartiSurah;
        this.nomor = nomor;
        this.namaSurah = namaSurah;
        this.arabSurah = arabSurah;
        this.artiSurah = artiSurah;
        this.arabAyat = arabAyat;
        this.artiAyat = artiAyat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListnomor() {
        return listnomor;
    }

    public void setListnomor(String listnomor) {
        this.listnomor = listnomor;
    }

    public String getListnamaSurah() {
        return listnamaSurah;
    }

    public void setListnamaSurah(String listnamaSurah) {
        this.listnamaSurah = listnamaSurah;
    }

    public String getListarabSurah() {
        return listarabSurah;
    }

    public void setListarabSurah(String listarabSurah) {
        this.listarabSurah = listarabSurah;
    }

    public String getListartiSurah() {
        return listartiSurah;
    }

    public void setListartiSurah(String listartiSurah) {
        this.listartiSurah = listartiSurah;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNamaSurah() {
        return namaSurah;
    }

    public void setNamaSurah(String namaSurah) {
        this.namaSurah = namaSurah;
    }

    public String getArabSurah() {
        return arabSurah;
    }

    public void setArabSurah(String arabSurah) {
        this.arabSurah = arabSurah;
    }

    public String getArtiSurah() {
        return artiSurah;
    }

    public void setArtiSurah(String artiSurah) {
        this.artiSurah = artiSurah;
    }

    public String getArabAyat() {
        return arabAyat;
    }

    public void setArabAyat(String arabAyat) {
        this.arabAyat = arabAyat;
    }

    public String getArtiAyat() {
        return artiAyat;
    }

    public void setArtiAyat(String artiAyat) {
        this.artiAyat = artiAyat;
    }
}
