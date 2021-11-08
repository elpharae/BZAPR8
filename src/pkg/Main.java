package pkg;

class CDPrehravac {

	private CD vlozeneCD;
	private int aktualniZaznamIndex;

    // ulozi vkladane cd do promene vlozeneCD (nutne kontrolovat, jestli už neni jine CD vloženo)
	public void vlozCD(CD cd) throws UzJeVlozenoCDException {
        if (this.vlozeneCD != null) throw new UzJeVlozenoCDException("V přehrávači již je vložené jiné CD");
        else {
            this.aktualniZaznamIndex = 0;
            this.vlozeneCD = cd;
            System.out.println("Bylo vloženo CD: " + this.vlozeneCD);
        }
    }

    // kontrola zda je vlozeno CD
    // pokud je vyjme CD z přehravače (vypise a nasledne nastavi vlozeneCD na null)
	public void vyjmiCD() throws NeniVlozenoCDException {
        if (this.vlozeneCD == null) throw new NeniVlozenoCDException("V přehrávači není vložené žádné CD");
        else {
            System.out.println("Bylo vyjmuto CD: " + this.vlozeneCD);
            this.aktualniZaznamIndex = 0;
            this.vlozeneCD = null;
        }
    }

    // kontrola zda je vlozeno CD
    // vypise aktualni Zaznam
	public void vypisAktualniZaznam() throws NeniVlozenoCDException {
        if (this.vlozeneCD == null) throw new NeniVlozenoCDException("Není vloženo CD");
        else {
            try {
                System.out.println("Aktuální záznam: " + this.vlozeneCD.dejZaznam(this.aktualniZaznamIndex));
            } catch (ZaznamNeniDostupnyException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    // kontrola zda je vlozeno CD
    // prepne na predchozi Zaznam na CD
    // pokud je aktualni Zaznam posledni na CD, prepne se na prvni
    public void predchoziZaznam() throws NeniVlozenoCDException {
        if (this.vlozeneCD == null) throw new NeniVlozenoCDException("Není vloženo CD");
        else {
            System.out.println("Přepínám na předchozí záznam...");
            if (--this.aktualniZaznamIndex < 0) this.aktualniZaznamIndex = this.vlozeneCD.dejPocetZaznamu() - 1;
            try {
                System.out.println("Nový záznam: " + this.vlozeneCD.dejZaznam(this.aktualniZaznamIndex));
            } catch (ZaznamNeniDostupnyException e) {
                System.out.println("CHYBA: " + e.getMessage());
            }
        }
    }

    // kontrola zda je vlozeno CD
    // prepne na dalsi Zaznam na CD
    // pokud je aktualni Zaznam prvni na CD, prepne se na posledni
	public void dalsiZaznam() throws NeniVlozenoCDException {
        if (this.vlozeneCD == null) throw new NeniVlozenoCDException("Není vloženo CD");
        else {
            System.out.println("Přepínám na další záznam...");
            if (++this.aktualniZaznamIndex >= this.vlozeneCD.dejPocetZaznamu()) this.aktualniZaznamIndex = 0;
            try {
                System.out.println("Nový záznam: " + this.vlozeneCD.dejZaznam(this.aktualniZaznamIndex));
            } catch (ZaznamNeniDostupnyException e) {
                System.out.println("CHYBA: " + e.getMessage());
            }

        }
    }
}

class CD {

    private final String nazev;
    private final Zaznam[] zaznamy;

    public CD(String nazev, Zaznam[] zaznamy) {
        this.nazev = nazev;
        this.zaznamy = zaznamy;
    }

    // vrati zaznam na indexu(musi byt osetreno proti preteceni)
    public Zaznam dejZaznam(int index) throws ZaznamNeniDostupnyException {
        if (index < 0 || index >= this.zaznamy.length) throw new ZaznamNeniDostupnyException("Požadovaný záznam neexistuje");
        else return this.zaznamy[index];
    }

    //vrati pocet zaznamu na CD
    public int dejPocetZaznamu() {
        return this.zaznamy.length;
    }

    //vypise nazev CD
    @Override
    public String toString() {
        return this.nazev;
    }
}

class Zaznam {

    private final String nazev;
    private final String autor;
    private final String druh;

    public Zaznam(String nazev, String autor, String druh) {
        this.nazev = nazev;
        this.autor = autor;
        this.druh = druh;
    }

    @Override
    public String toString() {
        return this.autor + " - " + this.nazev + " (" + this.druh + ")";
    }
}

//Chyby
class UzJeVlozenoCDException extends Exception {
    public UzJeVlozenoCDException(String zprava) {super(zprava);}
}

class NeniVlozenoCDException extends Exception {
    public NeniVlozenoCDException(String zprava) {super(zprava);}
}

class ZaznamNeniDostupnyException extends Exception {
    public ZaznamNeniDostupnyException(String zprava) {super(zprava);}
}

public class Main {

    /*
    V metodě main:
        Vytvoř 2 CD, s alespoň 2 Zaznamy
        Vytvoř CDPrehravac
        Do CDPrehravac vlož prvni CD - vlozCD(...)
        Vypis aktualni Zaznam - vypisAktualniZaznam()
        Prepni na předchozí Zaznam - predchoziZaznam()
        Prepni na další Zaznam - dalsiZaznam()
        Vyjmi CD - vyjmiCD()
        Vloz druhe CD - vlozCD(...)
        Vypis aktualni Zaznam - vypisAktualniZaznam()

        Ve tride CDPrehravac vzdy vypisujte udalosti, chyby
        udalosti -> napr. zavolani dalsiZaznam()  - Vypisu nove zpristupneny Zaznam
        chyby -> napr. vkladani CD, pokud uz v prehravaci nejake je - Vypsat chybovou hlasku
    */
    public static void main(String[] args) {
        Zaznam[] zaznamy1 = {
            new Zaznam("Good Ones", "Charli XCX", "Pop"),
            new Zaznam("Unstoppable", "Sia", "Motivational")
        };
        Zaznam[] zaznamy2 = {
            new Zaznam("Nemo", "Nightwish", "Symphonic Metal"),
            new Zaznam("Edge of the Blade", "Epica", "Symphonic Metal")
        };
        CD cd1 = new CD("Pop Music", zaznamy1);
        CD cd2 = new CD("Metal", zaznamy2);

        CDPrehravac prehravac = new CDPrehravac();
        try {
            prehravac.vlozCD(cd1);
            prehravac.vypisAktualniZaznam();
            prehravac.predchoziZaznam();
            prehravac.dalsiZaznam();
            prehravac.vyjmiCD();
            System.out.println("------");
            prehravac.vlozCD(cd2);
            prehravac.vypisAktualniZaznam();
            prehravac.predchoziZaznam();
            prehravac.dalsiZaznam();
            prehravac.vyjmiCD();
        } catch (UzJeVlozenoCDException | NeniVlozenoCDException e) {
            System.out.println("CHYBA: " + e.getMessage());
        }
    }
}
