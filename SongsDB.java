import java.beans.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

// import org.apache.ibatis.executor.ReuseExecutor;


public class SongsDB {

    private static Connection connection;
	public static void main(String[] args) {
		connection = SetUPConnection.getConnection();
		runProgram();



	}

public static void runProgram() {
	Scanner console = new Scanner(System.in);
	String line;

    
		// artistCollaboratorSearch("Britney Spears");
		// artistGenresSearch("Britney Spears");
		// artistInfo("Britney Spears");
		// artistGenresNameSearch("Britney Spears");
		// getSongInfo("From the Bottom of My Broken Heart");
		// albumNameSearch("Pitbull Starring In Rebelution");
        // listTopTenArtistsWithCollaborators();
        // listTopTenAlbumsByArtists();
        // listTopTenArtistsWithMostGenres();
        // listTopTenLabelsWithMostSongs();
        // listTopTenGenres();
        // listTenLeastCommonGenres();
        // listTopTenOldestAlbums();
        // listTopTenYoungestAlbums();
        // listSongsByLabel("Atlantic Records");
        // listSongsByDurationRange(200000, 300000);
        // listArtistsByGenre("pop");
         listSongsAddedBetweenDates(2019, 1, 1, 2020, 12, 31);
}

//Lists all songs added to Spotify between the specified dates
public static void listSongsAddedBetweenDates(int startYear, int startMonth, int startDay,
                                               int endYear, int endMonth, int endDay) {
    String query = "SELECT * FROM Songs " +
                   "WHERE CAST(CONVERT(varchar, [year]) + '-' + " +
                   "CONVERT(varchar, [month]) + '-' + " +
                   "CONVERT(varchar, [day]) AS DATETIME) " +
                   "BETWEEN ? AND ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, startYear + "-" + startMonth + "-" + startDay);
        preparedStatement.setString(2, endYear + "-" + endMonth + "-" + endDay);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            // Display the results
            System.out.println("Songs added to Spotify between " +
                    startYear + "-" + startMonth + "-" + startDay + " and " +
                    endYear + "-" + endMonth + "-" + endDay + ":");

            while (resultSet.next()) {
                String songURI = resultSet.getString("songURI");
                String songName = resultSet.getString("songName");
                int year = resultSet.getInt("year");
                int month = resultSet.getInt("month");
                int day = resultSet.getInt("day");

                System.out.println("Song URI: " + songURI +
                        ", Song Name: " + songName +
                        ", Added Date: " + year + "-" + month + "-" + day);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists all artists associated with the specified genre
public static void listArtistsByGenre(String genre) {
    String query = "SELECT A.artistName FROM Artists A " +
                   "JOIN ArtistGenres AG ON A.artistURI = AG.artistURI " +
                   "WHERE AG.genre = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, genre);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            // Display the results
            System.out.println("Artists associated with the genre '" + genre + "':");
            while (resultSet.next()) {
                String artistName = resultSet.getString("artistName");
                System.out.println("Artist: " + artistName);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


//Lists all songs with the specified label
public static void listSongsByDurationRange(int minDuration, int maxDuration) {
    String query = "SELECT * FROM Songs WHERE trackDuration BETWEEN ? AND ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setInt(1, minDuration);
        preparedStatement.setInt(2, maxDuration);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            // Display the results
            System.out.println("Songs with Duration Between " + minDuration + " and " + maxDuration + ":");
            while (resultSet.next()) {
                String songURI = resultSet.getString("songURI");
                String songName = resultSet.getString("songName");
                int trackDuration = resultSet.getInt("trackDuration");
                System.out.println("URI: " + songURI + ", Song Name: " + songName + ", Duration: " + trackDuration + " seconds");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


//Lists all songs with the specified label
public static void listSongsByLabel(String label) {
    String query = "SELECT S.songName, S.songURI " +
                   "FROM Songs S " +
                   "JOIN SongLabels SL ON S.songURI = SL.songURI " +
                   "WHERE SL.songlabel = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, label);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            // Display the results
            System.out.println("Songs with Label '" + label + "':");
            while (resultSet.next()) {
                String songName = resultSet.getString("songName");
                String songURI = resultSet.getString("songURI");
                System.out.println("Song: " + songName + ", URI: " + songURI);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists the ten youngest albums
public static void listTopTenYoungestAlbums() {
    String query = "SELECT TOP 10 albumName, year, month, day " +
                   "FROM Albums " +
                   "ORDER BY year DESC, month DESC, day DESC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Youngest Albums:");
        while (resultSet.next()) {
            String albumName = resultSet.getString("albumName");
            int year = resultSet.getInt("year");
            int month = resultSet.getInt("month");
            int day = resultSet.getInt("day");

            System.out.println("Album: " + albumName + ", Release Date: " + year + "-" + month + "-" + day);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists the ten oldest albums
public static void listTopTenOldestAlbums() {
    String query = "SELECT TOP 10 albumName, year, month, day " +
                   "FROM Albums " +
                   "ORDER BY year, month, day";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Oldest Albums:");
        while (resultSet.next()) {
            String albumName = resultSet.getString("albumName");
            int year = resultSet.getInt("year");
            int month = resultSet.getInt("month");
            int day = resultSet.getInt("day");

            System.out.println("Album: " + albumName + ", Release Date: " + year + "-" + month + "-" + day);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists the ten least common genres
public static void listTenLeastCommonGenres() {
    String query = "SELECT TOP 10 AG.genre, COUNT(AG.artistURI) AS TotalArtists " +
                   "FROM ArtistGenres AG " +
                   "GROUP BY AG.genre " +
                   "ORDER BY COUNT(AG.artistURI) ASC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Ten Least Common Genres:");
        while (resultSet.next()) {
            String genre = resultSet.getString("genre");
            int totalArtists = resultSet.getInt("TotalArtists");

            System.out.println("Genre: " + genre + ", Total Artists: " + totalArtists);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


//Lists the top ten genres with the most artists
public static void listTopTenGenres() {
    String query = "SELECT TOP 10 AG.genre, COUNT(AG.artistURI) AS TotalArtists " +
                   "FROM ArtistGenres AG " +
                   "GROUP BY AG.genre " +
                   "ORDER BY COUNT(AG.artistURI) DESC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Genres:");
        while (resultSet.next()) {
            String genre = resultSet.getString("genre");
            int totalArtists = resultSet.getInt("TotalArtists");

            System.out.println("Genre: " + genre + ", Total Artists: " + totalArtists);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists the top ten labels with the most songs
public static void listTopTenLabelsWithMostSongs() {
    String query = "SELECT TOP 10 SL.songlabel, COUNT(SL.songURI) AS TotalSongs " +
                   "FROM SongLabels SL " +
                   "GROUP BY SL.songlabel " +
                   "ORDER BY COUNT(SL.songURI) DESC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Labels with Most Songs:");
        while (resultSet.next()) {
            String songLabel = resultSet.getString("songlabel");
            int totalSongs = resultSet.getInt("TotalSongs");

            System.out.println("Label: " + songLabel + ", Total Songs: " + totalSongs);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists top ten artists with the most genres
public static void listTopTenArtistsWithMostGenres() {
    String query = "SELECT TOP 10 AG.artistURI, A.artistName, COUNT(AG.genre) AS TotalGenres " +
                   "FROM ArtistGenres AG " +
                   "JOIN Artists A ON AG.artistURI = A.artistURI " +
                   "GROUP BY AG.artistURI, A.artistName " +
                   "ORDER BY COUNT(AG.genre) DESC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Artists with Most Genres:");
        while (resultSet.next()) {
            String artistURI = resultSet.getString("artistURI");
            String artistName = resultSet.getString("artistName");
            int totalGenres = resultSet.getInt("TotalGenres");

            System.out.println("Artist: " + artistName + ", Total Genres: " + totalGenres);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists the top ten albums that have the most artists who've contributed to them
public static void listTopTenAlbumsByArtists() {
    String query = "SELECT TOP 10 AL.albumName, COUNT(DISTINCT AA.artistURI) AS TotalArtists " +
                   "FROM Albums AL " +
                   "JOIN AlbumArtists AA ON AL.uri = AA.albumURI " +
                   "GROUP BY AL.albumName " +
                   "ORDER BY COUNT(DISTINCT AA.artistURI) DESC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Albums with Most Artists:");
        while (resultSet.next()) {
            String albumName = resultSet.getString("albumName");
            int totalArtists = resultSet.getInt("TotalArtists");

            System.out.println("Album: " + albumName + ", Total Artists: " + totalArtists);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Lists the top ten artists along with the count who've written songs with the most total people
public static void listTopTenArtistsWithCollaborators() {
    String query = "SELECT TOP 10 A.artistName, COUNT(DISTINCT ASong.artistURI) AS TotalCollaborators " +
                   "FROM Artists A " +
                   "JOIN ArtistsSong AS ASong ON A.artistURI = ASong.artistURI " +
                   "GROUP BY A.artistName " +
                   "ORDER BY COUNT(DISTINCT ASong.artistURI) DESC";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {

        // Display the results
        System.out.println("Top Ten Artists with Most Collaborators:");
        while (resultSet.next()) {
            String artistName = resultSet.getString("artistName");
            int totalCollaborators = resultSet.getInt("TotalCollaborators");

            System.out.println("Artist: " + artistName + ", Collaborators: " + totalCollaborators);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//Get all relevant info about said album, including the songs that belong to said album,
//that artists that participated in the album, the album uri, and the date that the album was released on.
public static void albumNameSearch(String name) {
    String query = "SELECT * FROM Albums WHERE albumName LIKE ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, name);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve data from the result set
                String uri = resultSet.getString("uri");
                String albumNameResult = resultSet.getString("albumName");
                int year = resultSet.getInt("year");
                int month = resultSet.getInt("month");
                int day = resultSet.getInt("day");
                // Display the retrieved data
                System.out.println("Album URI: " + uri);
                System.out.println("Album Name: " + albumNameResult);
                System.out.println("Year: " + year);
                System.out.println("Month: " + month);
                System.out.println("Day: " + day);
                System.out.println("The album '" + albumNameResult + "' with URI '" + uri + "' was found.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}

//Get all relevant info about said song, including the songs uri, name, duration,
//the label of the song, and the date that it was added to spotify on.
public static void getSongInfo(String songName) {
    String query = "SELECT Songs.songURI, songName, trackDuration, songlabel, year, month, day " +
                   "FROM Songs " +
                   "JOIN SongLabels ON Songs.songURI = SongLabels.songURI " +
                   "WHERE Songs.songName LIKE ?";

    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, songName);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve data from the result set
                String songURI = resultSet.getString("songURI");
                String retrievedSongName = resultSet.getString("songName");
                int trackDuration = resultSet.getInt("trackDuration");
                String songLabel = resultSet.getString("songlabel");
                int year = resultSet.getInt("year");
                int month = resultSet.getInt("month");
                int day = resultSet.getInt("day");

                // Process or display the retrieved data as needed
                System.out.println("Song URI: " + songURI);
                System.out.println("Song Name: " + retrievedSongName);
                System.out.println("Duration: " + trackDuration + " seconds");
                System.out.println("Label: " + songLabel);
                System.out.println("Date Added to Spotify: " + year + "-" + month + "-" + day);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}

//Get all genres associated with the specified artist.
public static void artistGenresNameSearch(String artistName) {
    String query = "SELECT genre FROM ArtistGenres WHERE artistURI IN " +
            "(SELECT artistURI FROM Artists WHERE artistName LIKE ?)";

    try {
        System.out.println("Genres associated with the artist " + artistName + ":");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, artistName);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve data from the result set
                String retrievedGenre = resultSet.getString("genre");

                // Process or display the retrieved data as needed
                System.out.println("Genre: " + retrievedGenre);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}


//Lists all other artists who have collaborated with the specified artist
public static void artistCollaboratorSearch(String name) {
    String query = "SELECT DISTINCT CAST(A2.artistName AS VARCHAR) AS collaboratorName FROM Artists AS A1 " +
            "JOIN ArtistsSong AS AS1 ON A1.artistURI = AS1.artistURI " +
            "JOIN ArtistsSong AS AS2 ON AS1.songURI = AS2.songURI AND AS1.artistURI != AS2.artistURI " +
            "JOIN Artists AS A2 ON AS2.artistURI = A2.artistURI " +
            "WHERE A1.artistName LIKE ? AND A2.artistName IS NOT NULL";
    try {
        System.out.println("The artist: " + name + " has collaborated with: ");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
       try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve data from the result set
                String retrievedArtistName = resultSet.getString("collaboratorName");
                // Process or display the retrieved data as needed
                System.out.println("Collaborator Name: " + retrievedArtistName);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}


public static void artistGenresSearch(String specifiedArtistName) {
    String query = "SELECT DISTINCT AG.genre FROM Artists AS A " +
                   "JOIN ArtistGenres AS AG ON A.artistURI = AG.artistURI " +
                   "WHERE CAST(A.artistName AS NVARCHAR(MAX)) = ?";

    try {
        System.out.println("Genres associated with the artist " + specifiedArtistName + ":");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, specifiedArtistName);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve data from the result set
                String retrievedGenre = resultSet.getString("genre");

                // Process or display the retrieved data as needed
                System.out.println("Genre: " + retrievedGenre);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}


//Get all relevant info about said artist, including the uri,
//and the names of songs they've participated in
public static void artistInfo(String specifiedArtistName) {
    String query = "SELECT A.artistURI, A.artistName, S.songName " +
            "FROM Artists AS A " +
            "JOIN ArtistsSong AS AS1 ON A.artistURI = AS1.artistURI " +
            "JOIN Songs AS S ON AS1.songURI = S.songURI " +
            "WHERE CAST(A.artistName AS NVARCHAR(MAX)) = ?";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, specifiedArtistName);
       try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String artistURI = resultSet.getString("artistURI");
                String artistName = resultSet.getString("artistName");
                String songName = resultSet.getString("songName");
                System.out.println("Artist URI: " + artistURI);
                System.out.println("Artist Name: " + artistName);
                System.out.println("Song Name: " + songName);
                System.out.println("---------------------");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions as needed
    }
}

}