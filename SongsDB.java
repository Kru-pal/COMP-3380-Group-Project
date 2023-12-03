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
		// artistCollaboratorSearch("Britney Spears");
		// artistGenresSearch("Britney Spears");
		
	}

public static void runProgram() {
	Scanner console = new Scanner(System.in);
	String line;
}

///////////
// public static void artistCollaboratorSearch(String name) {
//     String query = "SELECT DISTINCT CAST(A2.artistName AS VARCHAR) AS collaboratorName FROM Artists AS A1 " +
//             "JOIN ArtistsSong AS AS1 ON A1.artistURI = AS1.artistURI " +
//             "JOIN ArtistsSong AS AS2 ON AS1.songURI = AS2.songURI AND AS1.artistURI != AS2.artistURI " +
//             "JOIN Artists AS A2 ON AS2.artistURI = A2.artistURI " +
//             "WHERE A1.artistName LIKE ? AND A2.artistName IS NOT NULL";

//     try {
//         System.out.println("The artist: " + name + " has collaborated with: ");
//         PreparedStatement preparedStatement = connection.prepareStatement(query);
//         preparedStatement.setString(1, name);
//         try (ResultSet resultSet = preparedStatement.executeQuery()) {
//             while (resultSet.next()) {
//                 // Retrieve data from the result set
//                 String retrievedArtistName = resultSet.getString("collaboratorName");

//                 // Process or display the retrieved data as needed
//                 System.out.println("Collaborator Name: " + retrievedArtistName);
//             }
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//         // Handle exceptions as needed
//     }
// }
///////////

// public static void artistGenresSearch(String specifiedArtistName) {
//     String query = "SELECT DISTINCT AG.genre FROM Artists AS A " +
//                    "JOIN ArtistGenres AS AG ON A.artistURI = AG.artistURI " +
//                    "WHERE CAST(A.artistName AS NVARCHAR(MAX)) = ?";

//     try {
//         System.out.println("Genres associated with the artist " + specifiedArtistName + ":");
//         PreparedStatement preparedStatement = connection.prepareStatement(query);
//         preparedStatement.setString(1, specifiedArtistName);

//         try (ResultSet resultSet = preparedStatement.executeQuery()) {
//             while (resultSet.next()) {
//                 // Retrieve data from the result set
//                 String retrievedGenre = resultSet.getString("genre");

//                 // Process or display the retrieved data as needed
//                 System.out.println("Genre: " + retrievedGenre);
//             }
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//         // Handle exceptions as needed
//     }
// }


}
