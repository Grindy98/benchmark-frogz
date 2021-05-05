package gui.sceneUtils;

import gui.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

import java.io.IOException;
import java.util.EnumMap;

//only one instance of this class exists in the program
public class SceneManager
{
    public enum SceneType
    {

        START_PAGE,
        OPTIONS_PAGE

    }

    private static SceneManager instance = null;
    private EnumMap<SceneType, Pair<Scene, Object>> sceneMap;      //map the enum(key) to a pair of Scene and Controller(Object)

    private SceneManager()
    {
        instance = this;
        sceneMap = new EnumMap<>(SceneType.class);

        // Add lines to have each enum covered
        sceneMap.put(SceneType.OPTIONS_PAGE, loadScene("/gui/ChooseBenchHere.fxml"));
        sceneMap.put(SceneType.START_PAGE, loadScene("/gui/startpage.fxml"));



        // Check that all enums have a loaded scene
        for(SceneType s : SceneType.values())
        {
            if (!sceneMap.containsKey(s)) {
                throw new RuntimeException("Program does not load scene " + s.name());
            }
        }
    }

    public static SceneManager getInstance()
    {
        if(instance == null)
        {
            instance = new SceneManager();
        }
        return instance;
    }

    public Scene getScene(SceneType sceneKey)
    {
        return sceneMap.get(sceneKey).getKey();
    }

    public <T> T getController(SceneType sceneKey)
    {
        return (T)sceneMap.get(sceneKey).getValue();
    }

    public static Pair<Scene, Object> loadScene(String fxml)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fxml));

        Parent root;
        try
        {
            root = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Location " + fxml + " not loaded properly(FXML URL:" + loader.getLocation() + ")");
        }
        return new Pair<>(new Scene(root), loader.getController());
    }
}
