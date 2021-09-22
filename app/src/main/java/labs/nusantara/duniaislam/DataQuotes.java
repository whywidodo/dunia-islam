package labs.nusantara.duniaislam;

public class DataQuotes {
    String id;
    String txt_quotes;

    public DataQuotes() {
    }

    public DataQuotes(String id, String txt_quotes) {
        this.id = id;
        this.txt_quotes = txt_quotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxt_quotes() {
        return txt_quotes;
    }

    public void setTxt_quotes(String txt_quotes) {
        this.txt_quotes = txt_quotes;
    }
}
