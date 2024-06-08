package MyProgram;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

class Playlist {
	BufferedWriter writer = null;

	private String nazwaPlaylisty; // playlista max 10 utworów, indeksy od 0 do 9
	private Utwor[] listaUtworow;
	int ileUtworow = 0;

	Playlist(String playlistName) throws Exception {
		if (playlistName.isBlank() == true || playlistName == null) {
			throw new Exception("Nie można dodać pustej playlisty!");
		}
		this.nazwaPlaylisty = playlistName;
		listaUtworow = new Utwor[10];
		ileUtworow = 0;
	}

	public void dodajUtwor(Utwor utwor) throws Exception {
		if (utwor == null) {
			throw new Exception("Nie można dodać pustego utworu");
		}
		if (ileUtworow >= 10) {
			System.err.println("Playlista jest zapełniona, nie można dodać więcej utowrów");
			return;
		}
		listaUtworow[ileUtworow] = utwor;
		ileUtworow++;
	}

	public Utwor pobierzUtwor(int numerUtworu) { // numery od 1-10 czyli indeks o 1 mniejszy
		System.out.println(listaUtworow[numerUtworu - 1]);
		return listaUtworow[numerUtworu - 1];
	}

	public void usunUtwor(int numerUtworu) {
		ileUtworow--;
		for (int i = numerUtworu - 1; i <= ileUtworow - 1; i++) {
			listaUtworow[i] = listaUtworow[i + 1];
		}
		listaUtworow[ileUtworow] = null;
	}

	public void wyswietlPlayliste() throws Exception {
		if (listaUtworow[0] == null) {
			throw new Exception("Playlista %s jest pusta".formatted(this.nazwaPlaylisty));
		}
		for (int i = 0; i <= ileUtworow - 1; i++) {
			System.out.println((i + 1) + " " + listaUtworow[i].toString());
		}
	}

	public void posortuj() throws NullPointerException {
		Collections.sort(Arrays.asList(listaUtworow));
	}

	public void zapiszDoPliku() throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nazwaPlaylisty + ".csv"))) {
			for (Utwor utwor : listaUtworow) {
				if (utwor != null) {
					writer.write(utwor.toString());
					writer.newLine();
				}
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
