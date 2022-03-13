
import Business.BusinessManager;
import Presentation.Controller;
import Presentation.UIManager;

/**
 * Main class.
 */
public class Main {
    public static void main(String[] args) {
        BusinessManager businessManager = new BusinessManager();
        UIManager uiManager             = new UIManager();
        Controller c                    = new Controller(uiManager, businessManager);
        c.run();
    }
}
