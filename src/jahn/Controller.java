package jahn;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Controller {

    @FXML private ComboBox<String> fileSelector;
    @FXML private TextField searchBox;
    @FXML private ListView<String> resultList;
    @FXML private TextField trieExactTime;
    @FXML private TextField trieAllTime;

    private List<String> words;
    private AutoCompleter trie;

    @FXML
    public void initialize() {
        fileSelector.setItems(FXCollections.observableArrayList(
                "words.txt", "words2.txt", "smlwords.txt", "names2011.txt", "companies.txt"
        ));
        fileSelector.getSelectionModel().selectFirst();
        onFileSelect();

        searchBox.textProperty().addListener((obs, oldVal, newVal) -> onSearch());
    }

    @FXML
    private void onFileSelect() {
        String filename = fileSelector.getValue();
        try {

            words = Files.readAllLines(Paths.get("src/jahn/data/" + filename));
            trie = new Trie(words);

            resultList.setItems(FXCollections.observableArrayList(words));
        } catch (IOException e) {
            showError("Error loading file: " + filename);
        }
    }

    private void onSearch() {
        String query = searchBox.getText().toLowerCase();

        if (query.length() < 3) {
            resultList.setItems(FXCollections.observableArrayList(words));
        } else {


            measureTiming(trie, query, trieExactTime, trieAllTime);
            resultList.setItems(FXCollections.observableArrayList(trie.allMatches(query)));
        }
    }

    private void measureTiming(AutoCompleter ac, String query,
                               TextField exactField, TextField allField) {
        long start = System.nanoTime();
        ac.exactMatch(query);
        long end = System.nanoTime();
        exactField.setText(AutoCompleter.format(end-start));

        start = System.nanoTime();
        ac.allMatches(query);
        end = System.nanoTime();
        allField.setText(AutoCompleter.format(end-start));
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
