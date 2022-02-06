package ve.com.promomedia.www.rickandmorty.models;

public class Personaje {

    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String url;
    private String created;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getCreated() {
        return created;
    }

    public int getNumber() {
        String[] urlPartes = url.split("/");
        //Log.i("Frank", "URL: " + urlPartes + " / Page: "+ Integer.parseInt(urlPartes[urlPartes.length-1]));
        return Integer.parseInt(urlPartes[urlPartes.length-1]);
    }
}