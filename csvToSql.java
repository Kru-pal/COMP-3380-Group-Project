import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

// NOTE: 35 attributes per entry
public class csvToSql {
    // SONG INDEXES
    final static int TRACK_URI_INDEX = 0;
    final static int TRACK_NAME_INDEX = 1;
    final static int TRACK_NUMBER_INDEX = 11;
    final static int TRACK_DURATION_INDEX = 12;
    final static int TRACK_ADDED_ON_INDEX = 18;

    // ARTIST INDEXES
    final static int ARTISTS_URI_INDEX = 2; 
    final static int ARTISTS_NAME_INDEX = 3; 
    final static int ARTIST_GENRES_INDEX = 19;

    // LABELS INDEX
    final static int LABELS_INDEX = 33;

    // ALBUM INDEXES
    final static int ALBUM_URI_INDEX = 4;
    final static int ALBUM_NAME_INDEX = 5;
    final static int ALBUM_RELEASE_DATE_INDEX = 8;

    // ALBUM ARTIST INDEXES
    final static int ALBUM_ARTISTS_URI_INDEX = 6;
    final static int ALBUM_ARTIST_NAMES_INDEX = 7;







  public static void main(String[] args) {
    try {
        File myObj = new File("data.txt");

        FileWriter myWriter = new FileWriter("songsSQL.sql");
        Scanner myReader = new Scanner(myObj, "UTF-8");
        
        // write out the create tables and drop tables
            myWriter.write("use cs3380;");
            myWriter.write("--drop table if exists:\n");
            myWriter.write("DROP TABLE IF EXISTS \"Songs\";\n");
            myWriter.write("DROP TABLE IF EXISTS \"Albums\";\n");
            myWriter.write("DROP TABLE IF EXISTS \"Artists\";\n");
            myWriter.write("DROP TABLE IF EXISTS \"ArtistsSong\";\n");
            myWriter.write("DROP TABLE IF EXISTS \"ArtistGenres\";\n");
            myWriter.write("DROP TABLE IF EXISTS \"AlbumArtists\";\n");
            myWriter.write("DROP TABLE IF EXISTS \"SongLabels\";\n\n");

            myWriter.write("--create tables:\n");
            myWriter.write("CREATE TABLE \"Songs\" (\n");
            myWriter.write("    \"songURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    \"songName\" NVARCHAR(MAX) DEFAULT 'N/A',\n");
            myWriter.write("    \"songNum\" INTEGER DEFAULT 0,\n");
            myWriter.write("    \"trackDuration\" INTEGER DEFAULT 0,\n");
            myWriter.write("    \"year\" INTEGER DEFAULT 0,\n");
            myWriter.write("    \"month\" INTEGER DEFAULT 0,\n");
            myWriter.write("    \"day\" INTEGER DEFAULT 0,\n");
            myWriter.write("    PRIMARY KEY(\"songURI\")\n");
            myWriter.write(");\n\n");

            myWriter.write("CREATE TABLE \"Albums\" (\n");
            myWriter.write("    \"uri\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    \"albumName\" NVARCHAR(MAX) DEFAULT 'N/A',\n");
            myWriter.write("    \"year\" INTEGER DEFAULT 0,\n");
            myWriter.write("    \"month\" INTEGER DEFAULT 0,\n");
            myWriter.write("    \"day\" INTEGER DEFAULT 0,\n");
            myWriter.write("    PRIMARY KEY(\"uri\")\n");
            myWriter.write(");\n\n");

            myWriter.write("CREATE TABLE \"Artists\" (\n");
            myWriter.write("    \"artistURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    \"artistName\" NVARCHAR(MAX) DEFAULT 'N/A',\n");
            myWriter.write("    PRIMARY KEY(\"artistURI\")\n");
            myWriter.write(");\n\n");

            myWriter.write("CREATE TABLE \"ArtistsSong\" (\n");
            myWriter.write("    \"artistURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    \"songURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    PRIMARY KEY(\"songURI\", \"artistURI\"),\n");
            myWriter.write("    FOREIGN KEY(\"artistURI\") REFERENCES \"Artists\"(\"artistURI\"),\n");
            myWriter.write("    FOREIGN KEY(\"songURI\") REFERENCES \"Songs\"(\"songURI\")\n");
            myWriter.write(");\n\n");

            myWriter.write("CREATE TABLE \"ArtistGenres\" (\n");
            myWriter.write("    \"artistURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    \"genre\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    PRIMARY KEY(\"artistURI\", \"genre\"),\n");
            myWriter.write("    FOREIGN KEY(\"artistURI\") REFERENCES \"Artists\"(\"artistURI\")\n");
            myWriter.write(");\n\n");

            myWriter.write("CREATE TABLE \"AlbumArtists\" (\n");
            myWriter.write("    \"albumURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    \"artistURI\" VARCHAR(100) DEFAULT 'N/A',\n");
            myWriter.write("    PRIMARY KEY(\"albumURI\", \"artistURI\"),\n");
            myWriter.write("    FOREIGN KEY(\"artistURI\") REFERENCES \"Artists\"(\"artistURI\"),\n");
            myWriter.write("    FOREIGN KEY(\"albumURI\") REFERENCES \"Albums\"(\"uri\")\n");
            myWriter.write(");\n\n");

            myWriter.write("CREATE TABLE \"SongLabels\" (\n");
            myWriter.write("    \"songURI\" VARCHAR(100),\n");
            myWriter.write("    \"songlabel\" VARCHAR(100),\n");
            myWriter.write("    PRIMARY KEY(\"songURI\"),\n");
            myWriter.write("    FOREIGN KEY(\"songURI\") REFERENCES \"Songs\"(\"songURI\")\n");
            myWriter.write(");\n\n");

      
        int index = 0;
        while (myReader.hasNextLine()) {
          //System.out.println(index);
            String data = myReader.nextLine();
            if (index > 0) {
               parseLine(data, myWriter);
            }  
            index++;
        }
        myWriter.close();
        myReader.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  
  public static void parseLine(String line, FileWriter writer) throws Exception {
    String[] tokens = line.split("\",\"");

    // Correction of first and last tokens
    tokens[0] = tokens[0].substring(1); // First token starts with single quotation mark 18
    tokens[tokens.length-1] = tokens[tokens.length-1].substring(0, tokens[tokens.length-1].length()-1); // Last token ends with a single quotation mark

    String[] checkLocal = line.split(":");

    if (tokens.length == 35 && !checkLocal[1].equals("local")) {

      String[] artistURITokens = tokens[ARTISTS_URI_INDEX].split(",");
      for (int i=0; i<artistURITokens.length; i++) {
        artistURITokens[i] = artistURITokens[i].trim();
        artistURITokens[i] = artistURITokens[i].split(":")[2];
        if (artistURITokens[i].charAt(artistURITokens[i].length()-1) == ',' ) {
          artistURITokens[i] = artistURITokens[i].substring(0, artistURITokens[i].length()-1);
        }
      }

      String[] artistNameTokens = tokens[ARTISTS_NAME_INDEX].split(",");
      for (int i=0; i<artistNameTokens.length; i++) {
        artistNameTokens[i] = escapeSingleQuotes(artistNameTokens[i].trim());
      }

      String[] artistGenreTokens = tokens[ARTIST_GENRES_INDEX].split(",");
      for (int i=0; i<artistGenreTokens.length; i++) {
        artistGenreTokens[i] = escapeSingleQuotes(artistGenreTokens[i].trim());
      }

      String[] labelTokens = tokens[LABELS_INDEX].split(",");
      for (int i=0; i<labelTokens.length; i++) {
        labelTokens[i] = escapeSingleQuotes(labelTokens[i].trim());
      }

    tableSongsInsert(tokens[TRACK_URI_INDEX], escapeSingleQuotes(tokens[TRACK_NAME_INDEX]), tokens[TRACK_NUMBER_INDEX], tokens[TRACK_DURATION_INDEX], tokens[TRACK_ADDED_ON_INDEX], writer); // Insertions into Songs table

    tableAlbumsInsert(tokens[ALBUM_URI_INDEX], escapeSingleQuotes(tokens[ALBUM_NAME_INDEX]), tokens[ALBUM_RELEASE_DATE_INDEX], writer);

    tableArtistsInsert(artistURITokens, artistNameTokens, writer);

    tableArtistsSongInsert(artistURITokens, tokens[TRACK_URI_INDEX], writer);

    tableArtistGenres(artistURITokens, artistGenreTokens, writer);

    tableAlbumArtistsInsert(tokens[ALBUM_URI_INDEX], artistURITokens, writer);

    tableLabelsInsert(tokens[TRACK_URI_INDEX], labelTokens, writer);
    }

  }

  public static void tableSongsInsert(String trackURI, String trackName, String trackNum, String trackDuration, String addedOn, FileWriter writer) throws Exception {
    String uriParsed = (trackURI.split(":"))[2]; // Track URI format is as follows: spotify:track:[TRACK URI HERE] so last part of split string is required

    String[] dateTokens = addedOn.split("-");
    String year = dateTokens[0];

    String month = dateTokens[1];
    if (month.charAt(0) == '0') {
        month = String.valueOf(month.charAt(1));
    }

    String day = dateTokens[2].substring(0,2);
    if (day.charAt(0) == '0') {
        day = String.valueOf(day.charAt(1));
    }

    String sql = "INSERT INTO Songs VALUES (\'" + uriParsed + "\', \'" + trackName + "\', " + trackNum +  ", " + trackDuration + ", " + year + ", " + month + ", " + day + ");";
    writer.write(sql + "\n");
    //System.out.println(sql);
  }

  public static void tableAlbumsInsert(String albumURI, String albumName, String releaseDate, FileWriter writer) throws Exception {
    String parsedURI = albumURI.split(":")[2];
    String[] dateTokens = releaseDate.split("-");


    String year = dateTokens[0];
    String month = "null";
    String day = "null";
    if (dateTokens.length > 1) {
      month = dateTokens[1];
      

      if (month.charAt(0) == '0') {
        month = String.valueOf(month.charAt(1));

      if (dateTokens.length > 2) {
        day = dateTokens[2];
        if (day.charAt(0) == '0') {
          day = String.valueOf(day.charAt(1));
        }
      }
    }
  }


    String sql = "INSERT INTO Albums VALUES (\'" + parsedURI + "\', \'" + albumName + "\', " + year + ", " + month + ", " + day + ");";
    //System.out.println(sql);
    writer.write(sql + "\n");
  }



  public static void tableArtistsInsert(String artistURI[], String artistName[], FileWriter writer) throws Exception {
    
    for ( int j=0; j<artistURI.length; j++ ) {

      String sql = "INSERT INTO Artists VALUES (\'" + artistURI[j] + "\', \'" + artistName[j] + "\');";
      //System.out.println(sql);
      writer.write(sql + "\n");
    }
  }

  public static void tableArtistsSongInsert(String artistURI[], String songURI, FileWriter writer) throws Exception {
    String uriSongParsed = (songURI.split(":"))[2];
    for (int i=0; i<artistURI.length; i++) {
      String sql = "INSERT INTO ArtistsSong VALUES (\'" + artistURI[i] + "\', \'" + uriSongParsed + "\');";
      //System.out.println(sql);
      writer.write(sql + "\n");
    }

  }

  public static void tableArtistGenres(String artistURI[], String genre[], FileWriter writer) throws Exception {

    for ( int j=0; j<artistURI.length; j++ ) {
      for (int i=0; i<genre.length; i++ ) {

        String sql = "INSERT INTO ArtistGenres VALUES (\'" + artistURI[j] + "\', \'" + genre[i] + "\');";
        //System.out.println(sql);
        if (!genre[i].equals(""))
          writer.write(sql + "\n");
      }
    }
      

  }

  public static void tableAlbumArtistsInsert(String albumURI, String artistURI[], FileWriter writer) throws Exception {

    String albumURIParsed = albumURI.split(":")[2];
    for ( int j=0; j<artistURI.length; j++ ) {
      String sql = "INSERT INTO AlbumArtists VALUES (\'" + albumURIParsed + "\', \'" + artistURI[j] + "\');";
      //System.out.println(sql);
      writer.write(sql + "\n");
    }
  }

  public static void tableLabelsInsert(String songURI, String label[], FileWriter writer) throws Exception {
    String songURIParsed = (songURI.split(":"))[2];
    for (int i=0; i<label.length; i++ ) {
      String sql = "INSERT INTO SongLabels VALUES (\'" + songURIParsed + "\', \'" + label[i] + "\');";
      //System.out.println(sql);
      writer.write(sql + "\n");
    }
  }


    public static String escapeSingleQuotes(String input) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < input.length(); i++) {
        char currentChar = input.charAt(i);
        
        // Check if the current character is a single quote
        if (currentChar == '\'') {
            // If it is, append an additional single quote
            result.append("''");
        } else {
            result.append(currentChar);
        }
    }

    return result.toString();
}
}