package MyProgram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Odtwarzacz {
	int numer;
	Playlist p; // chosen playlist
	Playlist temporary; // temporatry playlisty for operations

	String temp;
	Scanner sc;

	int tempInt;
	int nPlaylist;

	private boolean wlaczony = true;
	
	private BufferedReader reader = null;

	private LinkedHashMap<String, Playlist> map;
	private LinkedHashMap<Integer, String> numerata; // do
	// base playlists
	private Playlist p1;
	private Playlist p2;

	public Odtwarzacz() {
		map = new LinkedHashMap<>();
		numerata = new LinkedHashMap<>();
		nPlaylist = 3;

		sc = new Scanner(System.in);

		try {
			p1 = new Playlist("Rock");
			p2 = new Playlist("Pop");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Sprobuj ponownie");
		}

		try {
			init();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Sporbuj ponownie");
		}
	}

	private void init() throws Exception {
		p1.dodajUtwor(new Utwor("Smoke on the Water", "Deep Purple", 1972));
		p1.dodajUtwor(new Utwor("Child in Time", "Deep Purple", 1970));
		p1.dodajUtwor(new Utwor("Fortunate Son", "CCR", 1969));
		p1.dodajUtwor(new Utwor("Have You Ever Seen The Rain?", "CCR", 1970));
		p1.dodajUtwor(new Utwor("Fear of the Dark", "Iron Maiden", 1992));
		p1.dodajUtwor(new Utwor("Aces High", "Iron Maiden", 1984));
		p1.dodajUtwor(new Utwor("The Trooper", "Iron Maiden", 1983));

		p2.dodajUtwor(new Utwor("Tylko z Tobą Chcę Być Sobą", "Łukasz Zagrobelny", 2014));
		p2.dodajUtwor(new Utwor("Love Me Like You Do", "Ellie Goulding", 2014));
		p2.dodajUtwor(new Utwor("Jutro", "LemON", 2014));
		p2.dodajUtwor(new Utwor("Cool Kids", "Echosmith", 2013));
		p2.dodajUtwor(new Utwor("Perdoname (feat. Adrian Delgado & DyCy)", "Deorro", 2014));
		p2.dodajUtwor(new Utwor("What are you waiting for", "Nickelback", 2014));
		p2.dodajUtwor(new Utwor("Lips Are Movin'", "Meghan Trainor", 2015));
		p2.dodajUtwor(new Utwor("Kalejdoskop szczęścia", "Andrzej Piaseczny", 2015));

		map.put("Rock", p1);
		map.put("Pop", p2);

		numerata.put(1, "Rock");
		numerata.put(2, "Pop");

	}

	// CASE 0
	private void wyswietlPlaylisty() {
		int n = 1;
		for (String name : map.keySet()) {
			System.out.println("(" + n + ")" + " " + name);
			n++;
		}
	}

	// CASE 1
	private void dodajPlayliste() throws Exception {
		System.out.println("Podaj nazwę playlisty: ");
		String name = sc.nextLine();
		if (map.containsKey(name)) {
			System.err.println("Taka playlista już istnieje");
		} else {
			map.put(name, new Playlist(name));
		}

		numerata.put(nPlaylist, name);
		nPlaylist++;
	}

	// CASE 4
	private void usunPlayliste() throws Exception {

		tempInt = sc.nextInt();
		sc.nextLine();

		if (numerata.containsKey(tempInt)) {
			temp = numerata.get(tempInt); // String o nazwie playlisty
			map.remove(temp);
			nPlaylist--;
			numerata.remove(tempInt);

			LinkedHashMap<Integer, String> nowaNumerata = new LinkedHashMap<>();
			int nowyNumer = 1;

			for (Map.Entry<Integer, String> entry : numerata.entrySet()) {
				nowaNumerata.put(nowyNumer, entry.getValue());
				nowyNumer++;
			numerata = nowaNumerata;

			System.out.println("Playlista usunięta.");
			}
		} else {
			throw new Exception("Taka playlista nie istnieje");
		}
	}

	// CASE 5
	private void dodajUtwor() throws Exception {

		System.out.println("Podaj tytul utworu:");
		String tytul = sc.nextLine();

		System.out.println("Podaj wykonawce utworu:");
		String wykonawca = sc.nextLine();

		System.out.println("Podaj rok wydania utworu:");
		int rok = sc.nextInt();
		sc.nextLine();

		p.dodajUtwor(new Utwor(tytul, wykonawca, rok));
	}

	// CASE 6
	private void przenies() throws Exception {

		System.out.println("Podaj numer utworu który chcesz przenieść: ");
		p.wyswietlPlayliste();
		int numer = sc.nextInt();
		sc.nextLine();

		System.out.println("Podaj numer playlisty do której chcesz przenieść");
		wyswietlPlaylisty();

		tempInt = sc.nextInt();
		temp = numerata.get(tempInt);
		temporary = map.get(temp);

		// TODO
		temporary.dodajUtwor(p.pobierzUtwor(numer));
		p.usunUtwor(numer);
	}

	// CASE 7
	private void kopiuj() throws Exception {
		System.out.println("Podaj numer utworu który chcesz skopiować: ");

		p.wyswietlPlayliste();

		numer = sc.nextInt();
		sc.nextLine();

		System.out.println("Podaj numer playlisty do której chcesz przenieść");
		wyswietlPlaylisty();

		tempInt = sc.nextInt();
		temp = numerata.get(tempInt);
		temporary = map.get(temp);

		temporary.dodajUtwor(p.pobierzUtwor(numer));
	}

	// CASE 8
	private void usun() throws Exception {
		System.out.println("Podaj numer utworu który chcesz skasować");

		p.wyswietlPlayliste();
		numer = sc.nextInt();
		sc.nextLine();

		p.usunUtwor(numer);
	}
	
	//CASE 10
	private void zapiszDoPliku() throws Exception {
		p = wybierzPlayliste();
		p.zapiszDoPliku();
	}
	
	//CASE 11
	private void odczytajZPliku() throws Exception {
		System.out.println("Podaj nazwe pliku do odczytu");
		String fileName = sc.nextLine();
		 Playlist playlista = new Playlist(fileName.replace(".csv", ""));
		try {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		String line;
		while((line = reader.readLine()) != null) {
			playlista.dodajUtwor(Utwor.fromString(line));
		}
			
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
	}

	// default
	private Playlist wybierzPlayliste() throws Exception {

		System.out.println("Podaj numer playlisty którą chcesz użyć");
		wyswietlPlaylisty();
		
		try {
		int numer = sc.nextInt();
		sc.nextLine();
		String temp = numerata.get(numer);

		return map.get(temp);
		} catch (InputMismatchException e) {
			throw new Exception("Nieprawidłowy format, proszę wpisać liczbę całkowitą");
		} catch (IndexOutOfBoundsException e1) {
			throw new Exception("Numer playlisty poza zakresem");
		}

	}

	// wyswietlanie menu

	private void wlacz() {

		while (wlaczony) {
			System.out.println("Co chcesz zrobić? Wybierz opcję:\r\n" + "	(0) wyświetlić playlisty\r\n"
					+ "	(1) dodać playliste\r\n" + "	(2) wyswietlic playliste\r\n"
					+ "	(3) posortować playliste\r\n" + "	(4) usunąć playliste\r\n" + "	(5) dodac nowy utwor\r\n"
					+ "	(6) przeniesc utwor\r\n" + "	(7) skopiowac utwor\r\n" + "	(8) skasowac utwor\r\n"
					+ "	(9) wyłączyć odtwarzacz\r\n" + "	(10) zapisać playlistę do pliku\r\n" +
					"	(11) odczytać playlistę z pliku");
			byte opcja = sc.nextByte();
			sc.nextLine();

			try {
				switch (opcja) {

				case 2: // wyswietl utowry na playliscie
					p = wybierzPlayliste();

					p.wyswietlPlayliste();

					break;

				case 5: // dodaj utwor
					p = wybierzPlayliste();
					dodajUtwor();

					break;

				case 6: // przenieś
					p = wybierzPlayliste();
					przenies();
					break;

				case 7: // copy
					p = wybierzPlayliste();
					kopiuj();
					break;

				case 8: // usuwanie utworu
					p = wybierzPlayliste();
					try {
						usun();
					} catch (Exception e) {
						System.err.println(e.getMessage());
						System.err.println("Sprobuj ponownie");
					}
					break;

				case 0: // wyswietlenie playlist
					wyswietlPlaylisty();
					break;

				case 9: // koniec
					System.out.println(" --- KONIEC ---");
					wlaczony = false;
					break;

				case 1: // dodawanie playlisy
					dodajPlayliste();
					break;

				case 3:// sortowanie
					p = wybierzPlayliste();
					p.posortuj();
					break;

				case 4: // usuwanie playlisty
					System.out.println("Którą playlistę chcesz usunąć");
					wyswietlPlaylisty();
					usunPlayliste();
					break;
					
				case 10:
					zapiszDoPliku();
					break;
				
				case 11:
					odczytajZPliku();
					break;
					
				default:
					System.out.println("Opcja " + opcja + " jest niedostepna. Wybierz opcje z menu.");

				}
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
				e.printStackTrace();
				System.err.println("Sprobuj ponownie");
				wlacz();
			}
		}
	}

	public static void main(String[] args) {
		Odtwarzacz o = new Odtwarzacz();
		o.wlacz();
	}
}
