import menus.MainMenu;
import service.ConfiguracaoJogo;

public class Main {
    public static void main(String[] args) {
        new MainMenu(new ConfiguracaoJogo()).executar();
    }
}
